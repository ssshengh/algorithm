package concurrentLearn.Kuang.JUC.JMM;

import java.util.concurrent.TimeUnit;

public class JMMdemo {
    private static volatile int num = 0;

    public static void main(String[] args) {
        //首先有一个线程0：main
        //然后定义一个线程1
        new Thread(()->{
            while (num==0){
            }
            System.out.println("我修改拉！");
        }).start();//这样的话，该线程会一直执行，等待标志位被修改
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //main线程睡1s修改num
        num = 1;
        System.out.println(num);
    }
}
