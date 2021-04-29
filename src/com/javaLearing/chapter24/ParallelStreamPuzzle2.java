package com.javaLearing.chapter24;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParallelStreamPuzzle2 {
    public static final Deque<String> TRACE = new ConcurrentLinkedDeque<>();

    static class IntGenerator implements Supplier<Integer>{
        private AtomicInteger current = new AtomicInteger();//线程安全的

        @Override
        public Integer get() {
            TRACE.add(current.get() + ":" + Thread.currentThread().getName());
            return current.getAndIncrement();//Atomically increments the current value, with memory effects as specified by VarHandle.getAndAdd.
        }
    }

    /**
     * current 是使用线程安全的 AtomicInteger 类定义的，可以防止竞争条件；parallel() 允许多个线程调用 get() 。
     * 在查看 PSP2.txt .IntGenerator.get() 被调用 1024 次时，你可能会感到惊讶。
     * @param args:
     * @throws IOException:
     */
    public static void main(String[] args) throws IOException {
        List<Integer> x = Stream.generate(new IntGenerator())
                .limit(1000)
                .parallel()
                .collect(Collectors.toList());
        System.out.println(x);
        Files.write(Paths.get("/Users/shenheng/Code/PSP2.txt"), TRACE);
        System.out.println(TRACE);
    }
}
