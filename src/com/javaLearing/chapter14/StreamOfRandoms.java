package com.javaLearing.chapter14;

// streams/StreamOfRandoms.java
import java.util.*;
import java.util.stream.*;
public class StreamOfRandoms {
    static Random rand = new Random(47);
    public static void main(String[] args) {
        Stream.of(1, 2, 3, 4, 5)
                .flatMapToInt(i -> IntStream.concat(//在这里我们引入了 concat()，它以参数顺序组合两个流。
                        rand.ints(0, 100).limit(i), IntStream.of(-1)))
                //流的每个元素都是流,然后把元素流拆解，整体变成了一个流
                .forEach(n -> {
                    System.out.format("%d ", n);
                    if (n == -1)
                        System.out.println();
                });
    }
}

