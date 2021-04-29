package concurrentLearn.Kuang.JUC.LockAbout;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class saleTicketDemo02 {
    public static void main(String[] args) {
        //关键点，这里去体会一个概念：耦合性，我们希望类或者对象能够不断地被复用，如果它继承了某个接口的话
        //那么其实现行为就只能是该接口下面的函数，与接口耦合性变强
        //但是有更好的做法：

        //资源类：
        Ticket1 ti = new Ticket1();

        //多个线程同时操作资源
        new Thread(()->{ for (int i = 0; i < 60; i++) ti.sale(); }, "a").start();
        new Thread(()->{ for (int i = 0; i < 60; i++) ti.sale(); }, "b").start();
        new Thread(()->{ for (int i = 0; i < 60; i++) ti.sale(); }, "c").start();

    }
}
class Ticket1{
    //不需要继承，只需要考虑OOP中的，属性和方法的处理：
    private int number = 50;

    //卖票
    //使用lock锁来完成，注意该锁的完成步骤：
    public synchronized void sale(){
        Lock lock = new ReentrantLock();//如果没有值是非公平锁

        try {
            //首先在其中加锁：
            lock.lock();
            if (number>0){
                System.out.println(Thread.currentThread().getName()+"卖出了"+number--+"张票"+
                        ",剩余："+number);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            //最后在finally中解锁
            //记起那就话，finally都比fielized好
            lock.unlock();
        }
    }

}
