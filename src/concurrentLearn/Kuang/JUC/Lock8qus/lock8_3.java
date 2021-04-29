package concurrentLearn.Kuang.JUC.Lock8qus;

import java.util.concurrent.TimeUnit;

/**
 * <ul>5、如果发短信和打电话都是加锁的static方法，顺序如何？发短信-打电话
 * <ul>6、如果是两个不同实例化的对象分别调用发短信和打电话，顺序如何？发短信-打电话
 * <P>
 *     <ul>答案---->
 *         问题的关键在于，static方法是在类加载的初始化阶段加载的，他们代表的是一个类，里面没有this指针，本质上是类
 *         而不是对象，因此对二者加锁，锁上的是Class，就是反射的那个。锁上的是同一块资源，所以二者其实是在同一个资源内
 *         的并发的问题。所以加锁后就按照严格的队列顺序执行。
 * </P>
 */
public class lock8_3 {
    public static void main(String[] args) {
        phone3 pp1 = new phone3();
        phone3 pp2 = new phone3();

        new Thread(phone3::sendMsg,"A").start();

        //两个线程之间休息一段时间：注意用TimeUnit类
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        new Thread(phone3::call,"B").start();
        new Thread(phone3::sendMsg,"C").start();
        new Thread(phone3::call,"D").start();
        new Thread(phone3::sendMsg,"E").start();

    }
}

/**
 * 资源类
 */
class phone3{

    public static synchronized void sendMsg(){
//        try {
//            TimeUnit.SECONDS.sleep(4);
//        } catch (InterruptedException e) {
//            System.out.println(e);
//        }
        System.out.println("发短信!");
    }
    public static synchronized void call(){
        System.out.println("打电话!");
    }
}