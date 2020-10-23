package com.xo.common.executor.executor02;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author: xo
 * @DATE: 2020/9/16
 * @Description: Test
 **/
public class Test {


    public static void main(String[] args) throws InterruptedException, ExecutionException, InstantiationException, IllegalAccessException {

        CompletableFuture.supplyAsync(()->{
            return 1;
        }).thenApply(i -> {
            return i+1;
        }).thenApply(i->{
            return i*i;
        }).whenComplete(((integer, throwable) -> System.out.println(integer)));

        CompletableFuture.supplyAsync(()->"hello").thenApply(s -> s+" world").thenApply(String::toUpperCase)
                .whenComplete(((s, throwable) -> System.out.println(s)));

        CompletableFuture.supplyAsync(()->"hello").thenApply(s -> s+" world").thenApply(String::toUpperCase)
                .thenCombine(CompletableFuture.completedFuture(" java"),(s1,s2)->s1+s2)
                .thenAccept(System.out::println);

        String []list={"a","b","c"};
        List<CompletableFuture<String>> resList=new ArrayList<>();
        for (String str : list) {
            CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> str).thenApply((s -> s.toUpperCase()));
            resList.add(stringCompletableFuture);
        }
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf((resList.toArray(new CompletableFuture[resList.size()])))
                .whenComplete(((aVoid, throwable) -> {
                    if (null == throwable) {


                    } else {
                        throw new RuntimeException(throwable);
                    }
                }));


        //BbtExecutor
        List<?> objects1 = BbtExecutor.getInstance().accelerateThreadWithResult((Class<? extends BaseCallable<?>>) null, null, null, 1);

        List<Integer> stringList1 = BbtExecutor.getInstance().accelerateThreadFunctionWithResultNomoal((i) -> {
            List<Integer> l = new ArrayList<>();
            l.add(i);
            return l;
        }, 1, 2);
        for (Integer integer : stringList1) {
            System.out.println("stringList1: "+integer);
        }

        List<String> test=new ArrayList<>(3);
        test.add("111");
        List<String> stringList = BbtExecutor.getInstance().accelerateThreadWithResult((strings) -> {
                    System.out.println("strings=" + strings.toString());
                    return new ArrayList<>(0);
                },
                test, null, 5);


        //重写accelerateThreadWithResult
        List<String> list1=new ArrayList<>();
        list1.add("111");
        List<Integer> integers1 = BbtExecutor.getInstance().accelerateThreadWithResult_2((s) -> {
                    String s1 = s.get(0);
                    System.out.println("s1 "+s1);
                    ArrayList<Integer> integers = new ArrayList<>();
                    integers.add(Integer.valueOf(s1));
                    return integers;
                }, list1, 5);
        System.out.println(integers1);


    }

}
