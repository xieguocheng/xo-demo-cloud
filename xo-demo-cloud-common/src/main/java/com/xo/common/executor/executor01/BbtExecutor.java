package com.xo.common.executor.executor01;

import org.HdrHistogram.Recorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * @author: xo
 * @DATE: 2020/9/7
 * @Description: BbtExecutor
 **/
public class BbtExecutor {

    /**
     * 线程管理工具
     */
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     *
     * @param clazz
     * @param targetList
     * @param map
     * @param threadSize
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static void accelerateThread(Class<? extends BaseCallable<?>> clazz, List<?> targetList, Map<String, Object> map, Integer threadSize)
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

        List<Future<?>> resultList = new ArrayList<>();

        int listSize = targetList.size();
        Integer divideSize = targetList.size() / threadSize;

        if(divideSize.intValue() == 0) {
            divideSize = targetList.size();
            threadSize = 1;
        }

        BaseCallable<?> baseCallable = null;

        for (int i = 0; i < threadSize; i++) {
            baseCallable = clazz.newInstance();
            baseCallable.setMap(map);
            if(i == threadSize-1) {
                baseCallable.setTargetList(targetList.subList(i* divideSize, listSize));
            } else {
                baseCallable.setTargetList(targetList.subList(i* divideSize, (i + 1) * divideSize));
            }

            resultList.add(executorService.submit(baseCallable));
        }

        for (Future<?> future : resultList) {
            future.get();
        }
    }

    public static void accelerateThread(Class<? extends BaseCallable<?>> clazz, List<?> targetList, Integer divideSize, Map<String, Object> map)
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

        BaseCallable<?> baseCallable = null;
        for (int i = 0; i < threadSize+1; i++) {
            baseCallable = clazz.newInstance();
            baseCallable.setMap(map);
            if(i == threadSize) {
                baseCallable.setTargetList(targetList.subList(i* divideSize, listSize));
            } else {
                baseCallable.setTargetList(targetList.subList(i* divideSize, (i + 1) * divideSize));
            }

            resultList.add(executorService.submit(baseCallable));
        }

        for (Future<?> future : resultList) {
            future.get();
        }
    }

    public static void accelerateThread(Class<? extends BaseCallable<?>> clazz, List<?> targetList, Map<String, Object> map, Integer threadSize, Recorder recorder)
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

        BaseCallable<?> baseCallable = null;

        for (int i = 0; i < threadSize; i++) {
            baseCallable = clazz.newInstance();
            baseCallable.setMap(map);
            baseCallable.setRecorder(recorder);
            if(i == threadSize-1) {
                baseCallable.setTargetList(targetList.subList(i* divideSize, listSize));
            } else {
                baseCallable.setTargetList(targetList.subList(i* divideSize, (i + 1) * divideSize));
            }

            resultList.add(executorService.submit(baseCallable));
        }

        for (Future<?> future : resultList) {
            future.get();
        }
    }

    public static List<?> accelerateThreadWithResult(Class<? extends BaseCallable<?>> clazz, List<?> targetList, Map<String, Object> map, Integer threadSize)
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

        List<Future<?>> resultList = new ArrayList<>();

        int listSize = targetList.size();
        Integer divideSize = targetList.size() / threadSize;

        if(divideSize.intValue() == 0) {
            divideSize = targetList.size();
            threadSize = 1;
        }

        BaseCallable<?> baseCallable = null;

        for (int i = 0; i < threadSize; i++) {
            baseCallable = clazz.newInstance();
            baseCallable.setMap(map);
            if(i == threadSize-1) {
                baseCallable.setTargetList(targetList.subList(i* divideSize, listSize));
            } else {
                baseCallable.setTargetList(targetList.subList(i* divideSize, (i + 1) * divideSize));
            }

            resultList.add(executorService.submit(baseCallable));
        }

        return mergeResult(resultList);
    }

    public static Map<?,?> accelerateThreadWithResultForMap(Class<? extends BaseCallable<?>> clazz, List<?> targetList, Map<String, Object> map, Integer threadSize) throws InstantiationException, IllegalAccessException, InterruptedException, ExecutionException {
        if (targetList == null || targetList.size() == 0) {
            return null;
        }
        if (threadSize == null) {
            throw new RuntimeException("线程大小（threadSize）不能为空！");
        }

        if (threadSize > 100) {
            throw new RuntimeException("线程大小不能超过100！");
        }

        List<Future<?>> resultList = new ArrayList<>();

        int listSize = targetList.size();
        Integer divideSize = targetList.size() / threadSize;

        if(divideSize.intValue() == 0) {
            divideSize = targetList.size();
            threadSize = 1;
        }

        BaseCallable<?> baseCallable = null;

        for (int i = 0; i < threadSize; i++) {
            baseCallable = clazz.newInstance();
            baseCallable.setMap(map);
            if(i == threadSize-1) {
                baseCallable.setTargetList(targetList.subList(i* divideSize, listSize));
            } else {
                baseCallable.setTargetList(targetList.subList(i* divideSize, (i + 1) * divideSize));
            }

            resultList.add(executorService.submit(baseCallable));
        }

        return mergeResultForMap(resultList);
    }

    private static <T> List<T> mergeResult(List<Future<?>> resultList){
        List<T> res = null;
        List<T> mergeResultList = new ArrayList<T>();
        for (Future<?> future : resultList) {
            try {
                res = (List<T>)future.get();
                if (res != null && !res.isEmpty()){
                    mergeResultList.addAll(res);
                }
            } catch (Exception e) {
                ;
            }
        }
        return mergeResultList;
    }

    private static <T> Map<Object,T> mergeResultForMap(List<Future<?>> resultList){
        Map<Object,T> res = null;
        Map<Object,T> mergeResultMap = new HashMap<Object, T>();
        for (Future<?> future : resultList) {
            try {
                res = (Map<Object,T>)future.get();
                if (res != null && !res.isEmpty()){
                    mergeResultMap.putAll(res);
                }
            } catch (Exception e) {
                ;
            }
        }
        return mergeResultMap;
    }

//    public static List<CrmMember> getCrmGroupList(Class<? extends BbtCallable<?>> clazz, Integer totalPage, Map<String, Object> map, Integer threadSize) throws InstantiationException, IllegalAccessException, InterruptedException, ExecutionException {
//        List<CrmMember> res = new ArrayList<>();
//        BbtCallable<?> bbtCallable = clazz.newInstance();
//        List<Future<?>> resultList = new ArrayList<Future<?>>();
//        for(int i=1 ;i<=totalPage; i++){
//            map.put("pageSize", i);
//            bbtCallable.setMap(map);
//            resultList.add(executorService.submit(bbtCallable));
//        }
//
//        for(Future<?> future : resultList){
//            future.get();
//        }
//        return res;
//    }

}
