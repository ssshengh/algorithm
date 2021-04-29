package com.javaLearing.chapter24;

import java.util.concurrent.TimeUnit;

public class Nap {
    /**
     * 对 TimeUnit.MILLISECONDS.sleep() 的调用获取“当前线程”并在参数中将其置于休眠状态，这意味着该线程被挂起。
     * 这并不意味着底层处理器停止。操作系统将其切换到其他任务，例如在你的计算机上运行另一个窗口。OS 任务管理器定期检查 sleep() 是否超时。
     * 当它执行时，线程被“唤醒”并给予更多处理时间。
     * @param t:
     */
    public Nap(double t){
        try {
            TimeUnit.MILLISECONDS.sleep((long) ((long) 1000*t));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //你可以看到 sleep() 抛出一个受检的 InterruptedException ;这是原始 Java 设计中的一个工件，它通过突然断开它们来终止任务。
        // 因为它往往会产生不稳定的状态，所以后来不鼓励终止。但是，我们必须在需要或仍然发生终止的情况下捕获异常。

    }
    public Nap(double t, String msg) {
        this(t);
        System.out.println(msg);
    }
}
