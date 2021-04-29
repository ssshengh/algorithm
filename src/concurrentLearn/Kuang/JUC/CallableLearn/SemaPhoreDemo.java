package concurrentLearn.Kuang.JUC.CallableLearn;

import java.sql.Time;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量
 * 啥意思呢，就是停车位3个，但是来了6个车，进去了123需要提示456等待
 */
public class SemaPhoreDemo {//注意可以用来写生产者消费者模型
    public static void main(String[] args) {
        //停车场容量：3
        Semaphore semaphore = new Semaphore(3);//参数是允许的线程数量，源码中说的是更宽泛的permits，许可证
        for (int i = 0; i < 6; i++) {
            //6辆车
            new Thread(()->{
                try {
                    semaphore.acquire();//得到车位，注意会阻塞线程
                    System.out.println(Thread.currentThread().getName()+"->>获得车位");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(Thread.currentThread().getName()+"->>离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();//释放车位，后面三个线程才能进入
                }
            }, String.valueOf(i)).start();
        }
    }
}
