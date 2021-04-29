package concurrentLearn.Kuang.JUC.CdequeAbout;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 同步队列的问题:关键就是其大小始终为1
 */
public class SynTest {
    public static void main(String[] args) {
        SynchronousQueue<String> syn = new SynchronousQueue<>();
        new Thread(()->{
            try {
                //按照阻塞队列应该是一次性全部put进去了
                System.out.println(Thread.currentThread().getName()+"->put 1");
                syn.put("1");
                System.out.println(Thread.currentThread().getName()+"->put 2");
                syn.put("2");
                System.out.println(Thread.currentThread().getName()+"->put 3");
                syn.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "T1").start();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(3);//等个3s中确定上面线程一定先执行，先放进去
                System.out.println(Thread.currentThread().getName()+" take");
                syn.take();
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+" take");
                syn.take();
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+" take");
                syn.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "T2").start();
    }
}
