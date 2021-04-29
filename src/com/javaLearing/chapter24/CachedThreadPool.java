package com.javaLearing.chapter24;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class CachedThreadPool {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        IntStream.range(0, 10)
                .mapToObj(NapTask::new)
                .forEach(exec::execute);
        exec.shutdown();
    }
    //一方面注意到，这种多线程确实比只多一个线程快。所以问题来了，为什么还需要单独只增加一个线程呢？
    //第二是，注意到他们的顺序是乱的，并行执行的.而且是先调用了toString然后调用run
}
