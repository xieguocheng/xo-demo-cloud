package com.xo.common.executor.executor02;

import org.HdrHistogram.Recorder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author: xo
 * @DATE: 2020/9/16
 * @Description: BbtTaskExecutor
 **/
public class BbtExecutor {

    private static Log LOG = LogFactory.getLog(BbtExecutor.class);

    private static volatile Boolean isInited = false;

    private static volatile BbtExecutor bbtExecutor;

    private volatile ThreadPoolTaskExecutor threadPoolTaskExecutor = null;

    private final static Lock lock = new ReentrantLock();

    private static Integer DEFAULT_THREAD_COUNT =  Runtime.getRuntime().availableProcessors() * 10;

    private static Integer CONFIGURED_THREAD_COUNT = 200;

    private static Boolean IS_PRINT_THREAD_POOL_STATUS = true;

    private BbtExecutor() {
        lock.lock();
        try{
            init();
        }finally{
            lock.unlock();
        }
    }

    public synchronized void init(){
        if (isInited) {
            return;
        }
        LOG.info("BbtTaskExecutor init start........");
        try {
            if (threadPoolTaskExecutor == null){
                threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
                threadPoolTaskExecutor.setCorePoolSize(DEFAULT_THREAD_COUNT);
                if (CONFIGURED_THREAD_COUNT < DEFAULT_THREAD_COUNT){
                    CONFIGURED_THREAD_COUNT =  DEFAULT_THREAD_COUNT * 5;
                }
                threadPoolTaskExecutor.setMaxPoolSize(CONFIGURED_THREAD_COUNT);
                threadPoolTaskExecutor.setQueueCapacity(2000);
                threadPoolTaskExecutor.setThreadNamePrefix("BBT_EXECUTOR_");
                threadPoolTaskExecutor.setRejectedExecutionHandler(new RejectedExecutionHandler(){
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        BbtExecutor.rejectedExecution(r, executor);
                    }

                });
                threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);

                threadPoolTaskExecutor.initialize();
            }

            if (IS_PRINT_THREAD_POOL_STATUS){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(true){
                            try {
                                LOG.info(BbtExecutor.getInstance().getStatus());
                                Thread.sleep(30000);
                            } catch (InterruptedException e) {
                                ;
                            }
                        }
                    }
                }).start();
            }
            LOG.info("BbtTaskExecutor init success........" + this.getStatus());
            isInited = true;
        } catch (Exception e) {
            LOG.error("BbtTaskExecutor init error", e);
            System.exit(-1);
        }

    }

    public synchronized static BbtExecutor getInstance() {
        if (bbtExecutor == null) {
            LOG.info("BbtTaskExecutor null........");
            bbtExecutor = new BbtExecutor();
        }
        return bbtExecutor;
    }

    public void execute(BaseTask baseTask) {
        threadPoolTaskExecutor.execute(baseTask);
    }

    /**
     * 用户分页大小已经限制
     * @param consumer 回调 分页 函数
     * @param threadSize 线程数
     * @param totalPageSize 分页大小
     */
    public List<CompletableFuture<Void>> accelerateThreadConsumerList(Consumer<Integer> consumer, Integer threadSize, Integer totalPageSize){
        if (threadSize == null) {
            throw new RuntimeException("线程大小（threadSize）不能为空！");
        }

        if (threadSize > 100) {
            throw new RuntimeException("线程大小不能超过100！");
        }
        List<CompletableFuture<Void>>  completableFutures = new ArrayList<>();
        if(totalPageSize == null || totalPageSize == 0){
            return completableFutures;
        }
        CompletableFuture<Void> completableFuture = null;
        if(threadSize>totalPageSize){
            threadSize = totalPageSize;
        }
        final int threadPageSize = totalPageSize / threadSize;
        final int mol = totalPageSize % threadSize;

        for (int i = 1; i <=threadSize; i++) {
            final int startPageNo = i==1 ? 1 : (threadPageSize * (i - 1)+1);
            final int endPageNo = i == threadSize ?threadPageSize * i +mol: threadPageSize * i ;
            completableFuture = CompletableFuture.runAsync(() -> {
                for(int j = startPageNo;j <= endPageNo;j++){
                    consumer.accept(j);
                }
            },threadPoolTaskExecutor);
            completableFutures.add(completableFuture);
        }
        return completableFutures;
    }

    /**
     *
     * @param consumer 回调 分页 函数
     * @param threadSize 线程数
     * @param totalCount 总记录数
     */
    public void accelerateThreadConsumer(Consumer<Integer[]> consumer,Integer threadSize,Integer totalCount){
        if(totalCount == null || totalCount == 0){
            return;
        }

        if (threadSize == null) {
            throw new RuntimeException("线程大小（threadSize）不能为空！");
        }

        if (threadSize > 100) {
            throw new RuntimeException("线程大小不能超过100！");
        }

        if(totalCount < threadSize){
            threadSize = totalCount;
        }
        List<Future<?>>  completableFutures = new ArrayList<Future<?>>();
        CompletableFuture<Void> completableFuture = null;
        int pageSize = totalCount/threadSize;
        int modRes = totalCount % threadSize;
        Integer[] ints = null;
        for (int i = 1; i <=threadSize; i++) {
            if(i == threadSize){
                pageSize += modRes;
            }
            ints = new Integer[2];
            ints[0] = i;
            ints[1] = pageSize;
            final Integer[] temp = ints;
            completableFuture = CompletableFuture.runAsync(()->consumer.accept(temp),threadPoolTaskExecutor);
            completableFutures.add(completableFuture);
        }

        CompletableFuture.allOf(completableFutures.stream().toArray(CompletableFuture[]::new)).join();
    }

    /**
     *
     * @param func 执行的任务
     * @param threadSize 线程数
     * @param totalPageSize 执行次数
     * @param <T>
     * @return
     */
    public <T> List<T> accelerateThreadFunctionWithResultNomoal(Function<Integer,List<T>> func, Integer threadSize, Integer totalPageSize) {
        if(totalPageSize == null || totalPageSize == 0){
            return new ArrayList<T>(0);
        }
        if (threadSize == null) {
            throw new RuntimeException("线程大小（threadSize）不能为空！");
        }
        if (threadSize > 100) {
            throw new RuntimeException("线程大小不能超过100！");
        }
        if(threadSize>totalPageSize){
            threadSize = totalPageSize;
        }
        final int threadPageSize = totalPageSize / threadSize;
        final int mol = totalPageSize % threadSize;
        List<Future<?>>  completableFutures = new ArrayList<>();
        CompletableFuture<List<T>> completableFuture = null;
        for (int i = 1; i <=threadSize; i++) {
            final int startPageNo = i==1 ? 1 : (threadPageSize * (i - 1)+1);
            final int endPageNo = i == threadSize ?threadPageSize * i +mol: threadPageSize * i ;
            completableFuture = CompletableFuture.supplyAsync(() -> {
                List<T> list = new ArrayList<T>();
                for(int j = startPageNo;j <= endPageNo;j++){
                    list.addAll(func.apply(j));
                }
                return list;
            },threadPoolTaskExecutor);
            completableFutures.add(completableFuture);
        }
        try {
            List<T> resultList = mergeResult(completableFutures);
            return resultList;
        } catch (Exception e) {
            LOG.error("execute error",  e);
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> accelerateThreadFunctionWithResult(Function<Integer[],List<T>> func,Integer threadSize,Integer totalCount){
        if(totalCount == null || totalCount == 0){
            return null;
        }

        if (threadSize == null) {
            throw new RuntimeException("线程大小（threadSize）不能为空！");
        }

        if (threadSize > 100) {
            throw new RuntimeException("线程大小不能超过100！");
        }

        if(totalCount < threadSize){
            threadSize = totalCount;
        }

        List<Future<?>>  completableFutures = new ArrayList<>();
        CompletableFuture<List<T>> completableFuture = null;
        int pageSize = totalCount/threadSize;
        int modRes = totalCount % threadSize;
        Integer[] ints = null;
        for (int i = 1; i <=threadSize; i++) {
            if(i == threadSize){
                pageSize += modRes;
            }
            ints = new Integer[2];
            ints[0] = i;
            ints[1] = pageSize;
            final Integer[] temp = ints;
            completableFuture = CompletableFuture.supplyAsync(()->func.apply(temp),threadPoolTaskExecutor);

            completableFutures.add(completableFuture);
        }
        try {
            List<T> resultList = mergeResult(completableFutures);
            return resultList;
        } catch (Exception e) {
            LOG.error("execute error",  e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 以下都是accelerateThread
     * @param clazz
     * @param targetList
     * @param map
     * @param threadSize
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void accelerateThread(Class<? extends BaseCallable<?>> clazz, List<?> targetList, Map<String, Object> map, Integer threadSize)
            throws InstantiationException, IllegalAccessException, InterruptedException, ExecutionException {
        if (targetList == null || targetList.size() == 0) {
            return;
        }

        if (threadSize == null) {
            throw new RuntimeException("线程大小（threadSize）不能为空！");
        }

        if (threadSize > 100) {
            throw new RuntimeException("线程大小不能超过100！");
        }

        List<Future<?>> resultList = new ArrayList<Future<?>>();

        int listSize = targetList.size();
        Integer divideSize = targetList.size() / threadSize;

        if(divideSize.intValue() == 0) {
            divideSize = targetList.size();
            threadSize = 1;
        }

        BaseCallable<?> bbtCallable = null;

        for (int i = 0; i < threadSize; i++) {
            bbtCallable = clazz.newInstance();
            bbtCallable.setMap(map);
            if(i == threadSize-1) {
                bbtCallable.setTargetList(targetList.subList(i* divideSize, listSize));
            } else {
                bbtCallable.setTargetList(targetList.subList(i* divideSize, (i + 1) * divideSize));
            }

            resultList.add(threadPoolTaskExecutor.submit(bbtCallable));
        }

        for (Future<?> future : resultList) {
            future.get();
        }
    }

    public <T> void accelerateThread(Consumer<List<T>> consumer,List<T> targetList,Integer threadSize)
            throws InstantiationException, IllegalAccessException, InterruptedException, ExecutionException {
        if (targetList == null || targetList.size() == 0) {
            return;
        }

        if (threadSize == null) {
            throw new RuntimeException("线程大小（threadSize）不能为空！");
        }

        if (threadSize > 100) {
            throw new RuntimeException("线程大小不能超过100！");
        }

        List<Future<?>> resultList = new ArrayList<Future<?>>();

        int listSize = targetList.size();
        Integer divideSize = targetList.size() / threadSize;

        if(divideSize.intValue() == 0) {
            divideSize = targetList.size();
            threadSize = 1;
        }

        CompletableFuture<Void> completableFuture = null;

        for (int i = 0; i < threadSize; i++) {
            List<T> list = (i == threadSize-1) ? targetList.subList(i* divideSize, listSize)
                    : targetList.subList(i* divideSize, (i + 1) * divideSize);
            completableFuture = CompletableFuture.runAsync(()->consumer.accept(list),threadPoolTaskExecutor);

            resultList.add(completableFuture);
        }

        CompletableFuture.allOf(resultList.stream().toArray(CompletableFuture[]::new)).join();

    }

    public void accelerateThread(Class<? extends BaseCallable<?>> clazz, List<?> targetList, Integer divideSize, Map<String, Object> map)
            throws InstantiationException, IllegalAccessException, InterruptedException, ExecutionException {
        if (targetList == null || targetList.size() == 0) {
            return;
        }

        if (divideSize == null) {
            throw new RuntimeException("线程分割大小（divideListSize）不能为空！");
        }

        Integer threadSize = targetList.size() / divideSize;

        if (threadSize > 100) {
            throw new RuntimeException("线程大小不能超过100！");
        }

        List<Future<?>> resultList = new ArrayList<Future<?>>();

        int listSize = targetList.size();

        BaseCallable<?> bbtCallable = null;
        for (int i = 0; i < threadSize+1; i++) {
            bbtCallable = clazz.newInstance();
            bbtCallable.setMap(map);
            if(i == threadSize) {
                bbtCallable.setTargetList(targetList.subList(i* divideSize, listSize));
            } else {
                bbtCallable.setTargetList(targetList.subList(i* divideSize, (i + 1) * divideSize));
            }

            resultList.add(threadPoolTaskExecutor.submit(bbtCallable));
        }

        for (Future<?> future : resultList) {
            future.get();
        }
    }

    public void accelerateThread(Class<? extends BaseCallable<?>> clazz, List<?> targetList, Map<String, Object> map, Integer threadSize, Recorder recorder)
            throws InstantiationException, IllegalAccessException, InterruptedException, ExecutionException {
        if (targetList == null || targetList.size() == 0) {
            return;
        }

        if (threadSize == null) {
            throw new RuntimeException("线程大小（threadSize）不能为空！");
        }

        if (threadSize > 100) {
            throw new RuntimeException("线程大小不能超过100！");
        }

        List<Future<?>> resultList = new ArrayList<Future<?>>();

        int listSize = targetList.size();
        Integer divideSize = targetList.size() / threadSize;

        if(divideSize.intValue() == 0) {
            divideSize = targetList.size();
            threadSize = 1;
        }

        BaseCallable<?> bbtCallable = null;

        for (int i = 0; i < threadSize; i++) {
            bbtCallable = clazz.newInstance();
            bbtCallable.setMap(map);
            bbtCallable.setRecorder(recorder);
            if(i == threadSize-1) {
                bbtCallable.setTargetList(targetList.subList(i* divideSize, listSize));
            } else {
                bbtCallable.setTargetList(targetList.subList(i* divideSize, (i + 1) * divideSize));
            }

            resultList.add(threadPoolTaskExecutor.submit(bbtCallable));
        }

        for (Future<?> future : resultList) {
            future.get();
        }
    }

    public List<?> accelerateThreadWithResult(Class<? extends BaseCallable<?>> clazz, List<?> targetList, Map<String, Object> map, Integer threadSize)
            throws InstantiationException, IllegalAccessException, InterruptedException, ExecutionException {
        if (targetList == null || targetList.size() == 0) {
            return null;
        }
        if (threadSize == null) {
            throw new RuntimeException("线程大小（threadSize）不能为空！");
        }

        if (threadSize > 100) {
            throw new RuntimeException("线程大小不能超过100！");
        }

        List<Future<?>> resultList = new ArrayList<Future<?>>();

        int listSize = targetList.size();
        Integer divideSize = targetList.size() / threadSize;

        if(divideSize.intValue() == 0) {
            divideSize = targetList.size();
            threadSize = 1;
        }

        BaseCallable<?> bbtCallable = null;

        for (int i = 0; i < threadSize; i++) {
            bbtCallable = clazz.newInstance();
            bbtCallable.setMap(map);
            if(i == threadSize-1) {
                bbtCallable.setTargetList(targetList.subList(i* divideSize, listSize));
            } else {
                bbtCallable.setTargetList(targetList.subList(i* divideSize, (i + 1) * divideSize));
            }

            resultList.add(threadPoolTaskExecutor.submit(bbtCallable));
        }

        return mergeResult(resultList);
    }

    public <T> List<T> accelerateThreadWithResult(Function<List<T>,List<T>> func, List<T> targetList, Map<String, Object> map, Integer threadSize)
            throws InstantiationException, IllegalAccessException, InterruptedException, ExecutionException {
        //传入的targetList会经过切分处理成每个list，分给每个线程消费，
        // 其中list会通过func.apply(list)返回到Function<List<T>,List<T>> func中的T也就是input中，通过接口式编程获取输入值
        //从而回调我们写的(param)->{逻辑代码。。。}中
        if (targetList == null || targetList.size() == 0) {
            return null;
        }
        if (threadSize == null) {
            throw new RuntimeException("线程大小（threadSize）不能为空！");
        }

        if (threadSize > 100) {
            throw new RuntimeException("线程大小不能超过100！");
        }

        List<Future<?>> resultList = new ArrayList<Future<?>>();

        int listSize = targetList.size();
        Integer divideSize = targetList.size() / threadSize;

        if(divideSize.intValue() == 0) {
            divideSize = targetList.size();
            threadSize = 1;
        }

        CompletableFuture<List<T>> completableFuture = null;
        for (int i = 0; i < threadSize; i++) {
            List<T> list = (i == threadSize-1) ? targetList.subList(i* divideSize, listSize)
                    : targetList.subList(i* divideSize, (i + 1) * divideSize);
            completableFuture = CompletableFuture.supplyAsync(() -> {
                return func.apply(list);
            },threadPoolTaskExecutor);
            resultList.add(completableFuture);
        }
        return mergeResult(resultList);
    }

    public Map<?,?> accelerateThreadWithResultForMap(Class<? extends BaseCallable<?>> clazz, List<?> targetList, Map<String, Object> map, Integer threadSize)
            throws InstantiationException, IllegalAccessException, InterruptedException, ExecutionException {
        if (targetList == null || targetList.size() == 0) {
            return null;
        }
        if (threadSize == null) {
            throw new RuntimeException("线程大小（threadSize）不能为空！");
        }

        if (threadSize > 100) {
            throw new RuntimeException("线程大小不能超过100！");
        }

        List<Future<?>> resultList = new ArrayList<Future<?>>();

        int listSize = targetList.size();
        Integer divideSize = targetList.size() / threadSize;

        if(divideSize.intValue() == 0) {
            divideSize = targetList.size();
            threadSize = 1;
        }

        BaseCallable<?> bbtCallable = null;

        for (int i = 0; i < threadSize; i++) {
            bbtCallable = clazz.newInstance();
            bbtCallable.setMap(map);
            if(i == threadSize-1) {
                bbtCallable.setTargetList(targetList.subList(i* divideSize, listSize));
            } else {
                bbtCallable.setTargetList(targetList.subList(i* divideSize, (i + 1) * divideSize));
            }

            resultList.add(threadPoolTaskExecutor.submit(bbtCallable));
        }

        return mergeResultForMap(resultList);
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> mergeResult(List<Future<?>> resultList) throws InterruptedException, ExecutionException {
        List<T> res = null;
        List<T> mergeResultList = new ArrayList<T>();
        for (Future<?> future : resultList) {
            Object futureResult = future.get();
            if (futureResult instanceof Collection) {
                if (!CollectionUtils.isEmpty((Collection<T>) futureResult)) {
                    mergeResultList.addAll((Collection<T>) futureResult);
                }
            } else {
                mergeResultList.add((T) futureResult);
            }
        }
        return mergeResultList;
    }

    @SuppressWarnings("unchecked")
    private static <T> Map<Object,T> mergeResultForMap(List<Future<?>> resultList) throws InterruptedException, ExecutionException{
        Map<Object,T> res = null;
        Map<Object,T> mergeResultMap = new HashMap<Object, T>();
        for (Future<?> future : resultList) {
            res = (Map<Object,T>)future.get();
            if (res != null && !res.isEmpty()){
                mergeResultMap.putAll(res);
            }
        }
        return mergeResultMap;
    }

    public String getStatus() {
        ThreadPoolExecutor executor = threadPoolTaskExecutor.getThreadPoolExecutor();
        String status = "BbtTaskExecutor [corePoolSize=" + executor.getCorePoolSize() + ", poolSize=" + executor.getPoolSize()
                + ", activeCount=" + executor.getActiveCount() + ", largestPoolSize=" + executor.getLargestPoolSize() + ", taskCount="
                + executor.getTaskCount() + ", queueSize=" +  executor.getQueue().size() + "]";
        return status;
    }

    private static void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        try {
            String taskName = null;
            String taskContext = null;
            String executorStatus = "BbtTaskExecutor [corePoolSize=" + executor.getCorePoolSize() + ", poolSize=" + executor.getPoolSize()
                    + ", activeCount=" + executor.getActiveCount() + ", largestPoolSize=" + executor.getLargestPoolSize() + ", taskCount=" + executor.getTaskCount() + ", queueSize=" +  executor.getQueue().size() + "]";
            if (r instanceof BaseTask){
                BaseTask baseTask = (BaseTask)r;
                taskName = baseTask.getClass().getName();
//                taskContext = JSONObject.fromObject(bbtTask.getContext()).toString();
                baseTask.run();
            } else if (r instanceof BaseCallable){
                BaseCallable<?> bbtCallable = (BaseCallable<?>)r;
                taskName = bbtCallable.getClass().getName();
//                taskContext = JSONObject.fromObject(bbtCallable.getMap()).toString();
                bbtCallable.call();
            } else {
                taskName = r.getClass().getName();
                taskContext = "unknow";
            }
            LOG.warn(Thread.currentThread().getName() + ": BBT TAKS REJECTED! taskName = " + taskName + ",taskContext = " + taskContext + ",executorStatus = " + executorStatus);
        } catch (Exception e) {
            LOG.error("rejected error", e);
        }
    }

    /**
     * 对外开放线程池，以便业务自己执行
     * @return
     */
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        return threadPoolTaskExecutor;
    }


    /**
     * 重写accelerateThreadWithResult，使得放回对象更加灵活
     * 传入 T 类型返回 U 类型
     * @param func
     * @param targetList
     * @param threadSize
     * @param <T>
     * @param <U>
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public <T, R> List<R> accelerateThreadWithResult_2(Function<List<T>,List<R>> func, List<T> targetList, Integer threadSize)
            throws InterruptedException, ExecutionException {
        if (targetList == null || targetList.size() == 0) {
            return null;
        }
        if (threadSize == null) {
            throw new RuntimeException("线程大小（threadSize）不能为空！");
        }

        if (threadSize > 100) {
            throw new RuntimeException("线程大小不能超过100！");
        }

        List<Future<?>> resultList = new ArrayList<Future<?>>();

        int listSize = targetList.size();
        Integer divideSize = targetList.size() / threadSize;

        if(divideSize.intValue() == 0) {
            divideSize = targetList.size();
            threadSize = 1;
        }

        CompletableFuture<List<R>> completableFuture = null;
        for (int i = 0; i < threadSize; i++) {
            List<T> list = (i == threadSize-1) ? targetList.subList(i* divideSize, listSize)
                    : targetList.subList(i* divideSize, (i + 1) * divideSize);
            completableFuture = CompletableFuture.supplyAsync(() -> {
                return func.apply(list);
            },threadPoolTaskExecutor);
            resultList.add(completableFuture);
        }
        return mergeResult(resultList);
    }


}
