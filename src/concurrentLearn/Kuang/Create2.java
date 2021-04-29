package concurrentLearn.Kuang;

import java.util.Random;
import java.util.stream.IntStream;

//通过Runnable创建线程
public class Create2 implements Runnable{
    @Override
    public void run() {
        IntStream.range(0, 10)
                .mapToObj(i->"第："+i + "个；")
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        Create2 c2 = new Create2();
        new Thread(c2).start();//单纯的Runnable只是一个函数接口，要开启多线程需要其他的帮助，也比如CompletableFuture

        Random random = new Random(47);
        random.ints().limit(100).mapToObj(integer -> "现在是："+integer)
                .forEach(System.out::println);//能够明显看出乱了
    }
}

//写一个小例子，模拟一个买火车票的过程：多个线程操作同一个对象
class test3 implements Runnable{
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
        test3 t = new test3();
        //注意，启动了三个线程，但是是对同一个资源块进行操作
        new Thread(t, "明").start();
        new Thread(t, "小红").start();
        new Thread(t, "黄牛").start();
    }
}