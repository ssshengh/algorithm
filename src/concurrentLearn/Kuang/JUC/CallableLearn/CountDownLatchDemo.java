package concurrentLearn.Kuang.JUC.CallableLearn;

import java.util.concurrent.CountDownLatch;

/**
 * 计数器，这个是一个减法计数器
 */
public class CountDownLatchDemo {
    public static void main(String[] args) {
        //倒计时6
        CountDownLatch cdl = new CountDownLatch(6);
        /*一种应用场景是，大门打开，里面6个人，看门的需要等待6个人(线程)都出去才能关门*/
        for (int i = 0; i < 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + "--->走了");
                cdl.countDown();//计数-1
            }, String.valueOf(i)).start();
        }
        //等待计数器减到0，再向下执行
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("关门");//如果没有await，main也是一个线程，可能就提前关门了
    }
}
