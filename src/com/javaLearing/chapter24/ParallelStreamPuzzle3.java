package com.javaLearing.chapter24;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParallelStreamPuzzle3 {
    public static void main(String[] args) {
        List<Integer> x = IntStream.range(0,30)
                //为了表明 parallel() 确实有效，我添加了一个对 peek() 的调用，
                // 这是一个主要用于调试的流函数：它从流中提取一个值并执行某些操作但不影响从流向下传递的元素。
                // 注意这会干扰线程行为，但我只是尝试在这里做一些事情，而不是实际调试任何东西。
                .peek(value -> System.out.println(value + ":" + Thread.currentThread().getName()))
                .limit(10)
                .parallel()
                .boxed()
                .collect(Collectors.toList());
        System.out.println(x);
        //现在我们得到多个线程产生不同的值，但它只产生 10 个请求的值，而不是 1024 个产生 10 个值。
    }
}
