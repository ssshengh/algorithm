package concurrentLearn.Kuang;

import java.util.TreeSet;
import java.util.stream.IntStream;

//线程同步及优先级
//线程优先级
public class testPriority{
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName()+"--->priority:"
                +Thread.currentThread().getPriority());
        myPriority my = new myPriority();

        Thread t1 = new Thread(my);
        Thread t2 = new Thread(my);
        Thread t3 = new Thread(my);
        Thread t4 = new Thread(my);
        Thread t5 = new Thread(my);
        Thread t6 = new Thread(my);

        //设置优先级然后启动
        t1.start();

        t2.setPriority(1);
        t2.start();

        t3.setPriority(4);
        t3.start();

        t4.setPriority(Thread.MAX_PRIORITY);
        t4.start();

        //注意看源码就知道了，如果高于最高低于最低就会报错
        t5.setPriority(-1);
        t5.start();

        t6.setPriority(11);
        t6.start();

    }
}
class myPriority implements Runnable{
    @Override
    public void run() {
        System.out.println(
                Thread.currentThread().getName()+"--->priority:"
                +Thread.currentThread().getPriority()
        );
    }
}

//测试守护线程
//上帝守护人，人只有3万多天
class man implements Runnable{
    @Override
    public void run() {
        IntStream.range(0, 36500)
                .mapToObj(i->"一生开心活着")
                .forEach(System.out::println);
        System.out.println("_____goodbye world!_____");
    }
}
//上帝
class God implements Runnable{
    @Override
    public void run() {
        while (true)
            System.out.println("上帝看着你");
    }
}
//测试守护线程
class testDaemon{
    public static void main(String[] args) {
        man man = new man();
        God god = new God();

        Thread thread = new Thread(god);//创建线程
        thread.setDaemon(true);//守护线程
        //jvm是不会等待守护线程结束的，直接结束了

        new Thread(man).start();
    }
}