package com.javaLearing.chapter24P;

import onjava.Timer;

import java.util.concurrent.CompletableFuture;

public class CompletableApply {
    public static void main(String[] args) {
        //它们会在你执行操作时自动解包并重新包装它们所携带的对象。这使得编写和理解代码变得更加简单， 而不会在陷入在麻烦的细节中。
        CompletableFuture<Machina> cf = CompletableFuture.completedFuture(new Machina(0));


        /**
         * thenApply() 应用一个接收输入并产生输出的函数。在本例中，work() 函数产生的类型与它所接收的类型相同 （Machina），
         * 因此每个 CompletableFuture添加的操作的返回类型都为 Machina，但是 (类似于流中的 map() ) 函数也可以返回不同的类型，
         * 这将体现在返回类型上。
         */
        CompletableFuture<Machina> cf2 = cf.thenApply(Machina::work);
        CompletableFuture<Machina> cf3 =
                cf2.thenApply(Machina::work);
        CompletableFuture<Machina> cf4 =
                cf3.thenApply(Machina::work);
        CompletableFuture<Machina> cf5 =
                cf4.thenApply(Machina::work);
        //结果很有趣，多个线程操作同一个对象
    }
}

//我们可以消除中间变量并将操作链接在一起，就像我们使用 Streams 一样：

/**
 * 这里我们还添加了一个 Timer，它的功能在每一步都显性地增加 100ms 等待时间之外，
 * 还将 CompletableFuture 内部 thenApply 带来的额外开销给体现出来了。
 * CompletableFutures 的一个重要好处是它们鼓励使用私有子类原则（不共享任何东西）。
 * 默认情况下，使用 thenApply() 来应用一个不对外通信的函数 - 它只需要一个参数并返回一个结果。
 * 这是函数式编程的基础，并且它在并发特性方面非常有效[^5]。并行流和 ComplempleFutures 旨在支持这些原则。
 * 只要你不决定共享数据（共享非常容易导致意外发生）你就可以编写出相对安全的并发程序。
 */
class CompletableApplyChained{
    public static void main(String[] args) {
        Timer timer = new Timer();
        CompletableFuture<Machina> cf = CompletableFuture.completedFuture(
                new Machina(0))
                .thenApply(Machina::work)
                .thenApply(Machina::work)
                .thenApply(Machina::work)
                .thenApply(Machina::work);
        System.out.println(timer.duration());
    }
}