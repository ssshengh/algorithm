package concurrentLearn.Kuang.JUC.Pool;

import java.util.concurrent.*;

public class Bank {
    public static void main(String[] args) {

        System.out.println(Runtime.getRuntime().availableProcessors());//获取本机的核数->允许的线程数
        //自定义线程池
        ExecutorService exc = new ThreadPoolExecutor(
                2,//核心线程池，常开的柜台是2个
                5,//总共有5个柜台
                3,//等待3的时间后，关闭三个非核心线程
                TimeUnit.SECONDS,//等待时间的单位
                new LinkedBlockingDeque<>(3),//候客区，3个位置
                Executors.defaultThreadFactory(),//默认的线程工厂，这个一般不改变
                //new ThreadPoolExecutor.AbortPolicy()//使用这个拒绝策略，如果队列满了，还有人进来就拒绝处理抛出异常
                //new ThreadPoolExecutor.CallerRunsPolicy()//哪来的去哪里，打发出来了，滚回main线程去
                //new ThreadPoolExecutor.DiscardPolicy()//处理线程类似第一个，队列满了不抛出异常，但是任务直接被丢了
                new ThreadPoolExecutor.DiscardOldestPolicy()//尝试和最早的一个的线程竞争，如果第一个结束了，就执行，否则就丢掉
                //不会抛出异常
        );
        try {
            //最大是8个，线程数量加上阻塞队列容量，可以从1一直试到8看结果
            // 第一个策略：再看9：RejectedExecutionException
            // 第二个策略：看9：main ok
            for (int i = 0; i < 9; i++) {
                exc.execute(()->{
                    System.out.println(Thread.currentThread().getName()+" ok");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //线程池用完，需要关闭,放在finally里确定要一定被执行
            exc.shutdown();
        }
    }
}
