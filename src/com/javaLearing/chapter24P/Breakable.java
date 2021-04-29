package com.javaLearing.chapter24P;

import java.util.concurrent.*;

public class Breakable {
    String id;
    private int failcount;

    public Breakable(String id, int failcount) {
        this.id = id;
        this.failcount = failcount;
    }

    @Override
    public String toString() {
        return "Breakable_" + id + " [" + failcount + "]";
    }

    /**
     * 当failcount > 0，且每次将对象传递给 work() 方法时， failcount - 1 。
     * 当failcount - 1 = 0 时，work() 将抛出一个异常。如果传给 work() 的 failcount = 0 ，work() 永远不会抛出异常。
     * @param b:
     * @return
     */
    public static Breakable work(Breakable b){
        if (--b.failcount == 0)
        {
            System.out.println(
                    "Throwing Exception for " + b.id + ""
            );
            throw new RuntimeException(//注意，异常信息此示例中被抛出（ RuntimeException )
                    "Breakable_" + b.id + " failed"
            );
        }

        System.out.println(b);
        return b;
    }
}
//在下面示例  test() 方法中，work() 多次应用于 Breakable，因此如果 failcount 在范围内，就会抛出异常。
// 然而，在测试A到E中，你可以从输出中看到抛出了异常，但它们从未出现:
class CompletableExceptions{
    static CompletableFuture<Breakable> test(String id, int failcount) {
        return CompletableFuture.completedFuture(
                new Breakable(id, failcount))
                .thenApply(Breakable::work)
                .thenApply(Breakable::work)
                .thenApply(Breakable::work)
                .thenApply(Breakable::work);
    }

    public static void main(String[] args) {
        // Exceptions don't appear ...
        test("A", 1);
        test("B", 2);
        test("C", 3);
        test("D", 4);
        test("E", 5);


        // ... until you try to fetch the value:
        try {
            test("F", 2).get(); // or join()
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Test for exceptions:
        System.out.println(
                test("G", 2).isCompletedExceptionally()//这里也是可以看出抛出异常的
        );

        // Counts as "done":
        System.out.println(test("H", 2).isDone());//显示已经完成了


        /**
         * 测试 A 到 E 运行到抛出异常，然后…并没有将抛出的异常暴露给调用方。只有在测试 F 中调用 get() 时，我们才会看到抛出的异常。
         * 测试 G 表明，你可以首先检查在处理期间是否抛出异常，而不抛出该异常。
         * 然而，test H 告诉我们，不管异常是否成功，它仍然被视为已“完成”。
         * 代码的最后一部分展示了如何将异常插入到 CompletableFuture 中，而不管是否存在任何失败。
         * ！！！
         * 在连接或获取结果时，我们使用 CompletableFuture 提供的更复杂的机制来自动响应异常，而不是使用粗糙的 try-catch。
         */
        // Force an exception:
        CompletableFuture<Integer> cfi =
                new CompletableFuture<>();
        System.out.println("done? " + cfi.isDone());
        cfi.completeExceptionally(
                new RuntimeException("forced"));
        try {
            cfi.get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}