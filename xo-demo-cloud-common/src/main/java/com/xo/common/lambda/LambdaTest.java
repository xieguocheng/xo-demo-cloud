package com.xo.common.lambda;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author: xo
 * @DATE: 2020/9/21
 * @Description: Test
 **/
public class LambdaTest {


    public static void main(String[] args) {
        //自定义
        Converter<String, Integer> converter =(from) -> Integer.valueOf(from);
//        Converter<String, Integer> test = new Converter<String, Integer>() {
//            @Override
//            public Integer convert(String from) {
//                return Integer.valueOf(from);
//            }
//        };
        Integer converted = converter.convert("123");
        System.out.println(converted);

        //java内置
        Predicate<String> predicate = (s) -> s.length() > 0;
        predicate.test("foo"); // true
        predicate.negate().test("foo"); // false 取反
        Predicate<Boolean> nonNull = Objects::nonNull;
        Predicate<Boolean> isNull = Objects::isNull;
        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();

        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String, String> backToString = toInteger.andThen(String::valueOf);
        backToString.apply("123"); // "123"

        Supplier<String> supplier=()-> {return "hhh";};
        System.out.println(supplier.get());

        Consumer<Person> greeter = (p) -> System.out.println("Hello, " + p.name);
        greeter.accept(new Person());

        Comparator<Person> comparator = (p1, p2) -> p1.name.compareTo(p2.name);
        Person p1 = new Person();
        Person p2 = new Person();
        comparator.compare(p1, p2); // > 0
        comparator.reversed().compare(p1, p2); // < 0


    }

}
