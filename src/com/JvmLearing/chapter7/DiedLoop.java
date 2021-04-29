package com.JvmLearing.chapter7;

public class DiedLoop {
    static {
        if (true){//不加这个if编译器会提示，初始化尚未完成
            System.out.println(Thread.currentThread().getName()+"初始化死循环类");
            while (true){

            }
        }
    }

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"线程开始");
                DiedLoop diedLoop = new DiedLoop();
                System.out.println(Thread.currentThread().getName()+"线程结束");
            }
        };
        new Thread(runnable, "A").start();
        new Thread(runnable, "B").start();

    }
}
