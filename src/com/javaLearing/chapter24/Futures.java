package com.javaLearing.chapter24;


import java.util.concurrent.*;

public class Futures {
    public static void main(String[] args)throws InterruptedException, ExecutionException {
        ExecutorService exec
                =Executors.newSingleThreadExecutor();
        Future<Integer> f =
                exec.submit(new CountingTask(99));
        System.out.println(f.get()); // [1]当你的任务在尚未完成的 Future 上调用 get() 时，调用会阻塞（等待）直到结果可用
        //但这意味着，在 CachedThreadPool3.java 中，Future 似乎是多余的，因为 invokeAll() 甚至在所有任务完成之前都不会返回。
        // 但是，这里的 Future 并不用于延迟结果，而是用于捕获任何可能发生的异常。
        exec.shutdown();
    }
}
