package com.javaLearing.chapter24;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * 我创建了十个 NapTasks 并将它们提交给 ExecutorService，这意味着它们开始自己运行。然而，在此期间，main() 继续做事。
 * 当我运行 callexec.shutdown() 时，它告诉 ExecutorService 完成已经提交的任务，但不接受任何新任务。
 * 此时，这些任务仍然在运行，因此我们必须等到它们在退出 main() 之前完成。
 * 这是通过检查 exec.isTerminated() 来实现的，这在所有任务完成后变为 true。
 *
 * 请注意，main() 中线程的名称是 main，并且只有一个其他线程 pool-1-thread-1。此外，交错输出显示两个线程确实同时运行。
 *
 * 如果你只是调用 exec.shutdown()，程序将完成所有任务。也就是说，不需要 while(!exec.isTerminated()) 。
 */
public class SingleThreadExecutor {
    public static void main(String[] args) {
        //ExecutorService 为你管理线程，并且在运行任务后重新循环线程而不是丢弃线程。
        ExecutorService exec = Executors.newSingleThreadExecutor();//注意出来只是多了一个线程
        //首先请注意，没有 SingleThreadExecutor 类。newSingleThreadExecutor() 是 Executors 中的一个工厂方法，它创建特定类型的 ExecutorService

        IntStream.range(0,10)
                .mapToObj(NapTask::new)
                .forEach(exec::execute);
        System.out.println("All tasks submitted");
        exec.shutdown();//就是告诉任务提交完成，可以准备运行了

        while (!exec.isTerminated()){
            System.out.println(
                    Thread.currentThread().getName()+
                            " awaiting termination");
            new Nap(0.1);
        }
    }
}

//一旦你调用了 exec.shutdown()，尝试提交新任务将抛出 RejectedExecutionException。
class MoreTasksAfterShutdown{
    public static void main(String[] args) {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.execute(new NapTask(1));
        exec.shutdown();
        try {
            exec.execute(new NapTask(99));
        } catch(RejectedExecutionException e) {
            System.out.println(e);
        }
    }
}