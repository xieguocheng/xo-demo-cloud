package com.xo.common.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: xo
 * @DATE: 2020/9/21
 * @Description: StreamTest
 **/
public class StreamTest {
    public static void main(String[] args) {

//stream
        List<String> stringList = new ArrayList<>();
        stringList.add("ddd2");
        stringList.add("aaa2");
        stringList.add("bbb1");
        stringList.add("aaa1");
        stringList.add("bbb3");
        stringList.add("ccc");
        stringList.add("bbb2");
        stringList.add("ddd1");

        // 测试 Filter(过滤)
        stringList.stream().filter((s) -> s.startsWith("a")).forEach(System.out::println);//aaa2 aaa1

        // 测试 Sort (排序)
        stringList.stream() .filter((s) -> s.startsWith("a")).sorted().forEach(System.out::println);// aaa1 aaa2

        // 测试 Map 操作
        stringList .stream() .map(String::toUpperCase).sorted((a, b) -> b.compareTo(a)).forEach(System.out::println); // "DDD2", "DDD1", "CCC", "BBB3", "BBB2", "AAA2", "AAA1"

        // 测试 Match (匹配)操作
        boolean anyStartsWithA = stringList.stream().anyMatch((s) -> s.startsWith("a"));
        System.out.println(anyStartsWithA);// true
        boolean allStartsWithA = stringList.stream() .allMatch((s) -> s.startsWith("a"));
        System.out.println(allStartsWithA); // false
        boolean noneStartsWithZ = stringList.stream() .noneMatch((s) -> s.startsWith("z"));
        System.out.println(noneStartsWithZ); // true

        //测试 Count (计数)操作
        long startsWithB = stringList.stream().filter((s) -> s.startsWith("b")) .count();
        System.out.println(startsWithB); // 3

        //测试 Reduce (规约)操作
        Optional<String> reduced = stringList.stream().sorted().reduce((s1, s2) -> s1 + "#" + s2);
        reduced.ifPresent(System.out::println);//aaa1#aaa2#bbb1#bbb2#bbb3#ccc#ddd1#ddd2

//parallelStream
        int max = 1000000;
        List<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }
        //并行排序
        long t0 = System.nanoTime();
        long count = values.parallelStream().sorted().count();
        System.out.println(count);
        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("parallel sort took: %d ms", millis));
        //1000000 stream sort took: 643 ms//串行排序所用的时间
        //1000000 parallelStream sort took: 230 ms//并行排序所用的时间
    }
}
