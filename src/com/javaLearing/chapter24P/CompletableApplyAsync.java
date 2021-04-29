package com.javaLearing.chapter24P;

import onjava.Timer;

import java.util.concurrent.CompletableFuture;

public class CompletableApplyAsync {
    public static void main(String[] args) {
        Timer timer = new Timer();
        /**
         * 同步调用 (我们通常使用的那种) 意味着：“当你完成工作时，才返回”，
         * 而异步调用以意味着： “立刻返回并继续后续工作”。 正如你所看到的，cf 的创建现在发生的更快。每次调用 thenApplyAsync() 都会立刻返回，
         * 因此可以进行下一次调用，整个调用链路完成速度比以前快得多。
         */
        CompletableFuture<Machina> cf = CompletableFuture.completedFuture(new Machina(0))
                .thenApplyAsync(Machina::work)
                .thenApplyAsync(Machina::work)
                .thenApplyAsync(Machina::work)
                .thenApplyAsync(Machina::work);//这里只是提交，join才是执行了并返回


        System.out.println(timer.duration());
        //事实上，如果没有回调 cf.join() 方法，程序会在完成其工作之前退出。而 cf.join() 直到 cf 操作完成之前，阻止 main() 进程结束。
        // 我们还可以看出本示例大部分时间消耗在 cf.join() 这。如果注销的话，可以发现，中间压根没执行
        System.out.println(cf.join());
        System.out.println(timer.duration());
    }
}
