package concurrentLearn.Kuang.JUC.LockAbout;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 这里去实现精准通知和唤醒线程
 * <P>
 *     A执行完调用B，B执行完调用C，C执行完调用A
 * </P>
 */
public class pcCondition {
    public static void main(String[] args) {

        DAta dAta = new DAta();


        //希望实现：A执行完了通知B执行，然后B执行完毕通知C
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                dAta.printA();
            }
        }, "A").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                dAta.printB();
            }
        }, "B").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                dAta.printC();
            }
        }, "C").start();
    }
}
class DAta{
    //资源类
    private Lock lock = new ReentrantLock();
    //同步监视器，多几个来做
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    private int number = 1;//1A,2B,3C

    public void printA(){
        lock.lock();
        try {
            //业务：判断->执行->通知
            while (number!=1)
                condition1.await();
            //不再等待了，遇到1的时候意味着进入了A
            number = 2;
            System.out.println(Thread.currentThread().getName()+"AÀAÂ");
            //精准唤醒B的监视器2
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void printB(){
        lock.lock();
        try {
            //业务：判断->执行->通知
            while (number!=2)
                condition2.await();
            number = 3;
            System.out.println(Thread.currentThread().getName()+"BBBB");
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC(){
        lock.lock();
        try {
            //业务：判断->执行->通知
            while (number!=3)
                condition3.await();
            number = 1;
            System.out.println(Thread.currentThread().getName()+"ÇCCCCCCČ");
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
