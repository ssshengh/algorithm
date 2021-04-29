package concurrentLearn.Kuang;

import com.javaLearing.chapter19.A;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class syn {
}

class unsafeTicket implements Runnable{
    //不安全的买票
    //会出现负数，过于不安全了
    //简单的说就是在只有一张票的时候，三个线程同时把这个数据取到了自己的工作内存并进行修改，所以就出了大问题
    private int nums = 10;

    private boolean flag = true;//标志位，线程停止

    /**
     * 买票
     */
    @Override
    public void run() {
        while (flag)
            buy();
    }
    //加上synchronize的话就可以变成同步方法，它锁的是this对象
    //多个线程变成了排队进入，因为有一个线程再使用该对象时，其他线程必须等待
    private synchronized void buy(){
        //判断是否有票
        if (nums<=0) {
            flag = false;
            return;
        }

//        try {
//            Thread.sleep(1000);//模拟延时
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        System.out.println(
                Thread.currentThread().getName()+"拿到票"
                +nums--
        );
    }


    public static void main(String[] args) {
        unsafeTicket aa = new unsafeTicket();

        new Thread(aa, "Man").start();
        new Thread(aa, "sb黄牛").start();
        new Thread(aa, "mary").start();
    }
}

//不安全的取钱，两个人同时取
//不想写了，思路和上面一样，但是代码命名看得我头疼

//不安全的集合
class unsafeList{
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            new Thread(()->{
                list.add(Thread.currentThread().getName());
            }).start();
        }
        System.out.println(list.size());//少了3个，tmd
    }
}

//测试juc安全类型的集合
class TestJUC{
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 1000; i++) {
            new Thread(()->{
                list.add(Thread.currentThread().getName());
            }).start();
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(list.size());

    }
}
