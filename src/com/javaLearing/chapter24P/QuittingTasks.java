package com.javaLearing.chapter24P;


import com.javaLearing.chapter24.Nap;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuittingTasks {
    public static final int COUNT = 150;//150个线程

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        List<QuittableTask> tasks = IntStream.range(0, COUNT)
                .mapToObj(QuittableTask::new)
                .peek(qt -> exec.execute(qt))
                .collect(Collectors.toList());

        System.out.println("新建Nap挂起一个线程");
        new Nap(1);
        System.out.println("挂起完毕，顺序的修改150个线程的标签，让他们都结束");
        //tasks.forEach(QuittableTask::quit);
        exec.shutdown();//停止接收提交，并执行任务
        tasks.forEach(QuittableTask::quit);//这一句放后面也行，关键可能就是
        //所有线程都被堵在挂起中，在结束的时候才能明确观察到线程
        /**
         * 我使用 peek() 将 QuittableTasks 传递给 ExecutorService ，然后将这些任务收集到 List.main() 中，只要任何任务仍在运行，
         * 就会阻止程序退出。即使为每个任务按顺序调用 quit() 方法，任务也不会按照它们创建的顺序关闭。独立运行的任务不会确定性地响应信号。
         */
    }
}
