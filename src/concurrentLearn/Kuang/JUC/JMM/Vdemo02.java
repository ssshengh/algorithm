package concurrentLearn.Kuang.JUC.JMM;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 不保证原子性测试,如果是朝add方法加synchronized是保证原子性的，但是朝num加volatile不保证
 * 具体可以看结果，不保证原子性意味着线程运行时有其他线程进入占用了
 */
public class Vdemo02 {
    //private static int num = 0;
    private static AtomicInteger num = new AtomicInteger(0);
    public static void add(){
        //num++;//不是一个原子操作
        num.getAndIncrement();//获取并且+1，和另一个是++1还有1++的区别
    };
    public static void main(String[] args) {
        //开20个线程，对一个静态区域中共享的数据进行修改，理论上来说应该是20000
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                for (int i1 = 0; i1 < 1000; i1++) {
                    Vdemo02.add();
                }
            }).start();
        }
        /**
         * Java线程中的Thread.yield( )方法，译为线程让步。顾名思义，就是说当一个线程使用了这个方法之后，它就会把自己CPU执行的时间让掉，
         * 让自己或者其它的线程运行，注意是让自己或者其他线程运行，并不是单纯的让给其他线程。
         * yield()的作用是让步。它能让当前线程由“运行状态”进入到“就绪状态”，从而让其它具有相同优先级的等待线程获取执行权；
         * 但是，并不能保证在当前线程调用yield()之后，其它具有相同优先级的线程就一定能获得执行权；也有可能是当前线程又进入到“运行状态”继续运行！
         *
         *       举个例子：一帮朋友在排队上公交车，轮到Yield的时候，他突然说：我不想先上去了，咱们大家来竞赛上公交车。然后所有人就一块冲向公交车，
         *       有可能是其他人先上车了，也有可能是Yield先上车了。但是线程是有优先级的，优先级越高的人，就一定能第一个上车吗？
         *       这是不一定的，优先级高的人仅仅只是第一个上车的概率大了一点而已，最终第一个上车的，也有可能是优先级最低的人。并且所谓的优先级执行，是在大量执行次数中才能体现出来的。
         */
        while (Thread.activeCount()>2){
            //只要除了main还有gc外，还有线程在跑
            Thread.yield();//这里就是main礼让出来了
        }
        System.out.println(Thread.currentThread().getName()+"==>num:"+num);
    }
}
