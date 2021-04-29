package concurrentLearn.Kuang.JUC.LockAbout;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class pcWithJUC {
    //操作资源类：
    public static void main(String[] args) {
        Data1 data = new Data1();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

        //两个线程的时候一点问题没有，但是再多两个线程呢？这样会多一些++和--的，还能一直保证number为0码？
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();

    }

}


class Data1{
    //资源类，注意到我们只需要里面包含OOP需要的元素即可，方法加上资源
    private int number = 0;
    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    //Condition condition2 = lock.newCondition();

    //+1操作
    public void increment() throws InterruptedException{
        //加锁
        lock.lock();
        try {
            while (number!=0)
                //等待，等待不为0的时候到来，那么我就进行++
                condition1.await();
            //执行业务：
            number++;
            System.out.println(Thread.currentThread().getName()+"==>"+number);
            //通知其他线程我已经执行完毕了:
            condition1.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    //-1操作
    public void decrement() throws InterruptedException{
        //加锁
        lock.lock();
        try {
            //业务代码
            while (number==0)
                //等待，等待为0的时候到来，那么我就进行--
                condition1.await();
            //执行业务：
            number--;
            System.out.println(Thread.currentThread().getName()+"==>"+number);
            //通知其他线程我已经执行完毕了:
            condition1.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}