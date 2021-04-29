package concurrentLearn.Kuang.JUC.LockType;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class SpinLock {
    //自旋锁 关键是判断加锁解锁，不断的重复
    AtomicReference<Thread> atomicReference = new AtomicReference<>();//这个Thread的默认值是null
    //加锁
    public void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName()+"====>myLock");

        //自旋锁，如果是空就置为我们期望的线程,无限循环
        while (!atomicReference.compareAndSet(null, thread)){

        }

    }
    //解锁
    public void myUnlock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName()+"====>myUnlock");

        //解锁就不需要自旋了,如果是我们期望的线程就置为空，完成解锁
        //加锁会一直在那里自旋，直到成功加上锁，然后在这里解锁
        atomicReference.compareAndSet(thread, null);
    }



    public static void main(String[] args) {
        //底层使用了CAS
        SpinLock spinLock = new SpinLock();
        new Thread(()->{
            spinLock.myLock();//加锁
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                spinLock.myUnlock();//休息一会解锁
            }
        }, "A").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }//确保A线程一定能够先拿到锁


        new Thread(()->{
            spinLock.myLock();//加锁
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                spinLock.myUnlock();//休息一会解锁
            }
        }, "B").start();
        //结果的意思就是A拿到锁之后自旋，然后B也进来拿到锁，只有A释放了锁，B才能真正的释放锁
    }
}
