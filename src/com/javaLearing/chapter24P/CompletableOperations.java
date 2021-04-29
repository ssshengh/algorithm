package com.javaLearing.chapter24P;

import java.util.concurrent.CompletableFuture;
import static com.javaLearing.chapter24P.CompletableUtilities.*;
public class CompletableOperations {
    static CompletableFuture<Integer> cfi(int i){
        return CompletableFuture.completedFuture(Integer.valueOf(i));
    }

    public static void main(String[] args) {
        showr(cfi(1)); // Basic test

        //cfi(2) 是调用 runAsync() 的示例。由于 Runnable 不产生返回值，因此使用了返回 CompletableFuture <Void> 的voidr() 方法。
        voidr(cfi(2).runAsync(() ->
                System.out.println("runAsync")));

        /**
         * 注意使用 cfi(3),thenRunAsync() 效果似乎与 上例 cfi(2) 使用的 runAsync()相同，差异在后续的测试中体现：
         * runAsync() 是一个 static 方法，所以你通常不会像cfi(2)一样调用它。相反你可以在 QuittingCompletable.java 中使用它。
         * 后续测试中表明 supplyAsync() 也是静态方法，区别在于它需要一个 Supplier 而不是Runnable, 并产生一个CompletableFuture<Integer> 而不是 CompletableFuture<Void>。
         */
        //而且可以从Async看出是异步的
        voidr(cfi(3).thenRunAsync(() ->
                System.out.println("thenRunAsync")));//他是一个静态方法，需要的是runnable
        voidr(CompletableFuture.runAsync(() ->
                System.out.println("runAsync is static")));
        showr(CompletableFuture.supplyAsync(() -> 99));//关键他是一个静态方法，需要一个supplier而不是runnable

        /**
         * then 系列方法将对现有的 CompletableFuture<Integer> 进一步操作。
         * 与 thenRunAsync() 不同，cfi(4)，cfi(5) 和cfi(6) "then" 方法的参数是未包装的 Integer。
         * 通过使用 voidr()方法可以看到:
         * AcceptAsync()接收了一个 Consumer，因此不会产生结果。
         * thenApplyAsync() 接收一个Function, 并生成一个结果（该结果的类型可以不同于其输入类型）。
         * thenComposeAsync() 与 thenApplyAsync()非常相似，唯一区别在于其 Function 必须产生已经包装在CompletableFuture中的结果。
         */
        voidr(cfi(4).thenAcceptAsync(i ->
                System.out.println("thenAcceptAsync: " + i)));
        showr(cfi(5).thenApplyAsync(i -> i + 42));
        showr(cfi(6).thenComposeAsync(i -> cfi(i + 99)));

        //cfi(7) 示例演示了 obtrudeValue()，它强制将值作为结果。
        CompletableFuture<Integer> c = cfi(7);
        c.obtrudeValue(111);
        showr(c);

        //cfi(8) 使用 toCompletableFuture() 从 CompletionStage 生成一个CompletableFuture。
        showr(cfi(8).toCompletableFuture());

        //c.complete(9) 显示了如何通过给它一个结果来完成一个task（future）（与 obtrudeValue() 相对，后者可能会迫使其结果替换该结果）。
        c = new CompletableFuture<>();
        c.complete(9);
        showr(c);

        //如果你调用 CompletableFuture中的 cancel()方法，如果已经完成此任务，则正常结束。
        // 如果尚未完成，则使用 CancellationException 完成此 CompletableFuture。
        c = new CompletableFuture<>();
        c.cancel(true);
        System.out.println("cancelled: " +
                c.isCancelled());
        System.out.println("completed exceptionally: " +
                c.isCompletedExceptionally());
        System.out.println("done: " + c.isDone());
        System.out.println(c);

        //如果任务（future）完成，则 getNow() 方法返回CompletableFuture的完成值，否则返回getNow()的替换参数。
        c = new CompletableFuture<>();
        System.out.println(c.getNow(777));

        /**
         * 最后，我们看一下依赖 (dependents) 的概念。如果我们将两个thenApplyAsync()调用链路到CompletableFuture上，
         * 则依赖项的数量不会增加，保持为 1。但是，如果我们另外将另一个thenApplyAsync()直接附加到c，
         * 则现在有两个依赖项：两个一起的链路和另一个单独附加的链路。
         * 这表明你可以使用一个CompletionStage，当它完成时，可以根据其结果派生多个新任务。
         */
        c = new CompletableFuture<>();
        c.thenApplyAsync(i -> i + 42)
                .thenApplyAsync(i -> i * 12);//这种依赖没有增加，为两个一起的一条链路
        System.out.println("dependents: " +
                c.getNumberOfDependents());
        c.thenApplyAsync(i -> i / 2);//另外附加一个就会多一个链路，多一个另一个单独附加的链路
        System.out.println("dependents: " +
                c.getNumberOfDependents());
    }
}
