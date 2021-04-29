package concurrentLearn.Kuang.JUC.LockType;

import java.util.concurrent.TimeUnit;

public class diedLock {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        //出现死锁
        new Thread(new MyThread(lockA, lockB),"T1").start();
        new Thread(new MyThread(lockB, lockA),"T2").start();
    }
}

class MyThread extends Thread{
    private String lockA;
    private String lockB;

    public MyThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            //A想拿B
            System.out.println(Thread.currentThread().getName()+"lock"+lockA+"但是我想要线程B");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB){
                //B想拿A
                System.out.println(Thread.currentThread().getName()+"lock"+lockB+"但是我想要线程A");
            }
        }
    }
}