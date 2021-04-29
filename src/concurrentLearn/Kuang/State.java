package concurrentLearn.Kuang;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.stream.IntStream;

public class State {
}

//停止状态，不使用弃用的方法，这里使用推荐的标识位方法：
class stopTest implements Runnable{
    private boolean flag = true;//标识位
    private int id = 0;

    //框架，必须写的
    @Override
    public void run() {
        while (flag){
            System.out.println(
                    Thread.currentThread().getName()+
                    ": Running..."+id++
            );
        }
    }
    //需要一个合理的公开方法修改标识位，使得可以停下来
    public void stop(){this.flag = false;}

    public static void main(String[] args) {
        stopTest st = new stopTest();
        new Thread(st).start();
        IntStream.range(0, 100)
                .mapToObj(i ->{
                    if (i == 90)
                        st.stop();
                    return "now is:"+i;
                }).forEach(System.out::println);
    }
}

//休眠状态：sleep
//模拟网络延时
class testSleep implements Runnable{
    private int nums = 10;//10张票
    @Override
    public void run() {
        try {
            Thread.sleep(200);//模拟延时
        } catch (InterruptedException e) {
            System.out.println("异常出现，第："+nums+"张票");
        }
        while (nums > 0){
            System.out.println(Thread.currentThread().getName()+
                    "-->去抢票，抢到第"+ nums-- +"张票");
        }
    }

    public static void main(String[] args) {
        testSleep t = new testSleep();
        //注意，启动了三个线程，但是是对同一个资源块进行操作
        new Thread(t, "明").start();
        new Thread(t, "小红").start();
        new Thread(t, "黄牛").start();
    }
}
//模拟倒计时
class testSleep2{
    public static void tenDown() throws InterruptedException{//倒计时
        int num = 10;
        do {
            Thread.sleep(2000);
            System.out.println("now is:" + num--);
        } while (num > 0);

    }

    public static void main(String[] args) {
        //获取系统的时间倒计时也可以：
        Date date = new Date(System.currentTimeMillis());
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            System.out.println(new SimpleDateFormat("HH:mm:ss").format(date));
            date = new Date(System.currentTimeMillis());//更新时间
        }

//        try {
//            testSleep2.tenDown();
//        } catch (InterruptedException e) {
//            System.out.println(e);
//        }
    }
}
//礼让状态：礼让不一定成功，看是否接下来能够抢到时间片，在cpu里执行的线程先出来，但是不阻塞，也会参加下一次的时间片抢夺
class MyYield implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"线程开始执行");
        Thread.yield();//礼让
        System.out.println(Thread.currentThread().getName()+"线程结束");
    }

    public static void main(String[] args) {
        new Thread(new MyYield(), "a").start();
        new Thread(new MyYield(), "b").start();

    }
}

//join:线程强制执行==插队，关键是其他线程会被强行变为阻塞状态
class testJoin implements Runnable{
    @Override
    public void run() {
        IntStream.range(0,100)
                .mapToObj(i->{
                    return "vip来了："+i;
                }).forEach(System.out::println);
    }

    public static void main(String[] args) {
        Thread tt = new Thread(new testJoin());//启动线程
        tt.start();
        //主线程
        IntStream.range(0,200)
                .mapToObj(i->{
                    if (i == 20) {
                        //注意到在20后面全被阻塞使得自线程能够完全跑完
                        try {
                            tt.join();
                        } catch (InterruptedException e) {
                            System.out.println(e);
                        }
                    }
                    return "main线程："+i;
                }).forEach(System.out::println);
    }
}

//线程状态观测
class testState{
    public static void main(String[] args) {
        Thread thread = new Thread(()->{
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
            System.out.println("//////");
        });

        //获取状态
        Thread.State state = thread.getState();
        System.out.println("1:现在的状态是："+state);

        //开始
        thread.start();
        state = thread.getState();
        System.out.println("2:现在的状态是："+state);

        int i = 3;
        while (state!=Thread.State.TERMINATED){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            state = thread.getState();
            System.out.println(i++ +":现在的状态是："+state);
        }
        //停止之后不能在运行

    }
}