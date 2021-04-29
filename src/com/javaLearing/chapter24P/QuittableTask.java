package com.javaLearing.chapter24P;

import com.javaLearing.chapter24.Nap;
import java.util.concurrent.atomic.AtomicBoolean;

//虽然多个任务可以在同一个实例上成功调用 quit() ，但是 AtomicBoolean 可以防止多个任务同时实际修改 running ，从而使 quit() 方法成为线程安全的。
public class QuittableTask implements Runnable{
    final int id;
    public  QuittableTask(int id){this.id = id;}

    private AtomicBoolean running = new AtomicBoolean(true);//java提供的一组类型，不用担心并发的问题
    public void quit(){
        System.out.println("quite:" + id);
        running.set(false);
    }//一个标识位来标志进程的结束

    @Override
    public void run() {
        while (running.get())
            new Nap(0.1);
        System.out.println(id + " ");//显示仅在任务退出时发生。
    }
}
