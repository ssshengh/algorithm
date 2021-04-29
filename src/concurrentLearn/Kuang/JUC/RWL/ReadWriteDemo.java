package concurrentLearn.Kuang.JUC.RWL;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        //写入
        for (int i = 0; i < 6; i++) {
            final int temp = i;
            new Thread(()->{
                myCache.put(temp+" ", temp+" ");
            }, String.valueOf(i)).start();
        }
        //读取
        for (int i = 0; i < 6; i++) {
            final int temp = i;
            new Thread(()->{
                myCache.get(temp+" ");
            }, String.valueOf(i)).start();
        }
    }
}

/**
 * 自定义缓存
 */
class MyCache{
    //volatile增加内存可见性和顺序性
    private volatile Map<String, Object> map = new HashMap<>();
    //读写锁，更加细粒度的操作，只希望写入时只有一个线程，而读是可以所有线程
    private ReadWriteLock rw = new ReentrantReadWriteLock();
    /**
     * 这里的关键是如何保证线程对资源的操作不乱，如果都用synchronized的话，那么锁的是方法的调用者
     * 这样的话，二者没法达成不同的可见性
     */
    //缓存需要一个存的操作，写只能被一个线程写
    public void put(String key, Object value){
        rw.writeLock().lock();//加入写锁
        try {
            System.out.println(Thread.currentThread().getName()+"写入："+key);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName()+"写入成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rw.writeLock().unlock();//解锁，操作和lock一样
        }

    }

    //但是读的时候可以被所有线程读
    public void get(String key){
        //读锁的最大作用就是防止读的时候被修改
        rw.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"读取："+key);
            Object o = map.get(key);
            System.out.println(Thread.currentThread().getName()+"读取成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rw.readLock().unlock();
        }
    }
}