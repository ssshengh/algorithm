package com.javaLearing.chapter24P;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletedMachina {
    public static void main(String[] args) {
        /**
         * completedFuture() 创建一个“已经完成”的 CompletableFuture 。对这样一个未来做的唯一有用的事情是 get() 里面的对象，
         * 所以这看起来似乎没有用。注意 CompletableFuture 被输入到它包含的对象。这个很重要。
         *
         * 通常，get() 在等待结果时阻塞调用线程。此块可以通过 InterruptedException 或 ExecutionException 中断。
         * 在这种情况下，阻止永远不会发生，因为 CompletableFuture 已经完成，所以结果立即可用。
         */
        CompletableFuture<Machina> cf = CompletableFuture.completedFuture(new Machina(0));
        //Returns a new CompletableFuture that is already completed with the given value.
        try {
            Machina m = cf.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
