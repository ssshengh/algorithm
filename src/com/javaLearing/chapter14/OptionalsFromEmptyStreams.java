package com.javaLearing.chapter14;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class OptionalsFromEmptyStreams {
    public static void main(String[] args) {
        //注意，空流是通过 Stream.<String>empty() 创建的。
        //这些方法都返回一个Optional类型对象
        System.out.println(Stream.<String>empty()
        .findFirst());
        System.out.println(Stream.<String>empty()
                .findAny());
        System.out.println(Stream.<String>empty()
                .max(String.CASE_INSENSITIVE_ORDER));
        System.out.println(Stream.<String>empty()
                .min(String.CASE_INSENSITIVE_ORDER));
        //当流为空的时候你会获得一个 Optional.empty 对象，而不是抛出异常。
        System.out.println(Stream.<String>empty()
                .reduce((s1, s2) -> s1 + s2));//reduce() 不再以 identity 形式开头，而是将其返回值包装在 Optional 中。
        System.out.println(IntStream.empty()
                .average());
        //对于数字流 IntStream、LongStream 和 DoubleStream，average() 会将结果包装在 Optional 以防止流为空。
    }
}
class OptionalBasics{
    static void test(Optional<String> optString) {
        if(optString.isPresent())
            System.out.println(optString.get());
        else
            System.out.println("Nothing inside!");
    }
    public static void main(String[] args) {
        test(Stream.of("Epithets").findFirst());//从结果来看，显然这个函数返回一个Optional<String>类型对象
        test(Stream.<String>empty().findFirst());
    }
}
