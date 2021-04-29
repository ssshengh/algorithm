package com.javaLearing.chapter24;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class NotRunnable {
    public void go() {
        System.out.println("NotRunnable");
    }
}
class NotCallable {
    public Integer get() {
        System.out.println("NotCallable");
        return 1;
    }
}

public class LambdasAndMethodReferences {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.submit(()-> System.out.println("使用lambda1"));
        exec.submit(new NotRunnable()::go);//使用同样方法签名的方法引用

        exec.submit(() -> {
            System.out.println("Lambda2");
            return 1;
        });//类似callable的方法

        exec.submit(new NotCallable()::get);
        exec.shutdown();
    }
}
