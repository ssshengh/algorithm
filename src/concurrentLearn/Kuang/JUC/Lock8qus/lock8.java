package concurrentLearn.Kuang.JUC.Lock8qus;

import java.util.concurrent.TimeUnit;

/**
 * 8锁：即关于锁的八个问题：
 * <ul>
 *     <li>1在标准情况下，在发短信和打电话两个线程之间加了一个睡眠，那么是先打印谁呢？最后结果是：发短信-打电话
 *     <li>2在把sendMsg延迟4s后，两个线程之间先打印谁呢？最后结果还是发短信-打电话
 *     <p>
 *         答案------>
 *         synchronized锁，锁的是方法的调用者，即下面实例化的Phone类型引用pp，所以对于打电话和发短信来说
 *         关键就是谁先拿到锁谁先执行，中见停顿的一秒就是为了确认发短信一定能先拿到锁。
 *     </p>
 * </ul>
 */
public class lock8 {
    public static void main(String[] args) {
        phone pp = new phone();

        new Thread(()->{
            pp.sendMsg();
        },"A").start();
        //两个线程之间休息一段时间：注意用TimeUnit类
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        new Thread(()->{
            pp.call();
        },"B").start();
    }
}

/**
 * 资源类
 */
class phone{

    public synchronized void sendMsg(){
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("发短信!");
    }
    public synchronized void call(){
        System.out.println("打电话!");
    }
}

