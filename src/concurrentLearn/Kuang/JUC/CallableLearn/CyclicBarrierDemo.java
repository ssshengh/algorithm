package concurrentLearn.Kuang.JUC.CallableLearn;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**\
 * 加法计数器
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        /*集齐七棵龙珠召唤神龙*/
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, ()->{
            System.out.println("神龙！");
        });
        for (int i = 0; i < 7; i++) {
            //要将i传递到lambda中，需要一个中继的final变量，因为本质上lambda是一个类，其只能接受一个不可修改的变量
            final int k = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"收集了"+k+"颗龙珠");
                try {
                    cyclicBarrier.await();//等待达到7才行
                    //如果是等待8个线程只给了7个的话，await会阻塞线程
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }
}
