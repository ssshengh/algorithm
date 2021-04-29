package com.javaLearing.chapter24;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//在许多情况下，只在集合上调用 stream() 或者 parallelStream() 没有问题。但是，有时将 Stream 与 Collection 混合会产生意想不到的结果。这是一个有趣的难题：
public class ParallelStreamPuzzle {
    static class IntGenerator
                implements Supplier<Integer>{
        private int current = 0;

        @Override
        public Integer get() {
            return current++;
        }
    }

    public static void main(String[] args) {
        List<Integer> x = Stream.generate(new IntGenerator())
                .limit(10)
                .parallel()
                .collect(Collectors.toList());
        System.out.println(x);
    }
}
