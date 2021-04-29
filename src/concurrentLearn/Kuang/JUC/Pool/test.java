package concurrentLearn.Kuang.JUC.Pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Executor 是一个工具类，三大方法
public class test {
    public static void main(String[] args) {
/*        Executors.newSingleThreadExecutor();//该线程池，单个线程
        Executors.newFixedThreadPool(5);//固定线程池的大小
        Executors.newCachedThreadPool();//可伸缩线程池大小，遇强则强遇弱则弱*/

        //ExecutorService exs = Executors.newSingleThreadExecutor();
        ExecutorService exs = Executors.newCachedThreadPool();

        //线程池的创建线程的方法：
        try {
            for (int i = 0; i < 10; i++) {
                exs.execute(()->{
                    System.out.println(Thread.currentThread().getName()+" ok");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //线程池用完，需要关闭,放在finally里确定要一定被执行
            exs.shutdown();
        }

    }
}
