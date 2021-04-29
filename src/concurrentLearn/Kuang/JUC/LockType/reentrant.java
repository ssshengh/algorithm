package concurrentLearn.Kuang.JUC.LockType;

import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class reentrant {
    static AtomicInteger atomicInteger = new AtomicInteger(10);

    //可重入锁
    public static void main(String[] args) {

        Phone2 phone = new Phone2();//注意锁的是方法调用者，那么拿到这个sms的锁之后，自然也就拿到了call的锁
        //理论上来说应该是, A进入sms后拿到锁，接着不用拿到锁直接可以进入call，然后出了call才需要返还锁
        //这时候B才能拿到锁
        new Thread(()->{
            phone.sms();
        },"A").start();

        new Thread(()->{
            phone.sms();
        },"B").start();
    }
}
class phone{
    public synchronized void sms(){
        System.out.println(Thread.currentThread().getName()+"发短信");
        call();//这里也有一把锁
    }
    public synchronized void call(){
        System.out.println(Thread.currentThread().getName()+"打电话");
    }
}
class Phone2{
    //使用lock来看可重入性
    Lock lock = new ReentrantLock();
    public void sms(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"发短信");
            call();//这里也有一把锁
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void call(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"打电话");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
