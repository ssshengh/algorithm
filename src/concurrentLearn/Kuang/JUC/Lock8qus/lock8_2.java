package concurrentLearn.Kuang.JUC.Lock8qus;

import java.util.concurrent.TimeUnit;

/**
 *  接下来两个问题：注意到，sendMsg发短信方法有个睡眠4s
 *  <ul>
 *      <li>3新加一个方法，没有synchronized修饰的普通方法hello，那么在调用的时候顺序如何？hello-发短信
 *      <li>4新加一个实例化的对象，pp2，pp1调用sendMsg，pp2调用call方法，顺序如何？打电话-发短信
 *      <P>
 *          <li>答案----->
 *          问题的关键在于，在问题3中，hello尽管和sendMsg方法同属一个被加锁的方法的调用者，但是hello没有被加锁，而sendMsg
 *          被加锁了。注意到synchronized是让该调用者的所有加锁方法排队进入临界区，同时只有一个方法在操作资源块，因此，如果
 *          hello没有加锁的话，他就可能和发短信一起进入临界区。但是关键在于，就算一起进入了，锁还是使得发短信耐心等待在了后面。
 *          注意两个现象，取消线程间的睡眠不会影响结果，但是取消sendMsg中的睡眠，最后结果就是先发短信了。
 *          所以结果就是，因为sendMsg睡眠了，所以普通方法能够拿到资源的使用权，所以先hello然后过4s发短信。
 *          有个疑惑是，睡眠位于发短信的方法快内，为什么就能明确的停下来，让其他线程获得资源，而不是他拿着资源睡觉呢？
 *          (猜测是类似于wait的释放锁休息)。
 *          另一个有趣的是，在这种条件下，不加锁的一定是乱序存在于加锁的前面执行，而如果sendMsg没有睡眠的话，则是先执行sendMsg
 *          然后不加锁的方法乱序执行在后面。形象的讲就是，一个人排队在一群乱插队的人后面，如果他不想等了就赶紧打完饭，等后面爱插队
 *          的人自己插队去。
 *      </P>
 *      <P>
 *          <li>答案------>
 *          4问题的关键在于，二者是不同对象的调用者，因为二者的锁加在了两个不同的锁的方法调用者身上。因此对于这两把锁，所以就按
 *          时间来.为了看清这个问题，首先去掉两个延迟，包括发短信方法内部的延迟，出来的结果就是先发短信后打电话，如果把发短信发
 *          10遍，打电话也打10遍的话，顺序依然不乱，先发十次然后打十次。这个仔细考虑，应该是由于是两把锁，锁的是不同的方法调用者
 *          对这两个锁来说是并行的，但是对单把锁内部来说是并发的。所以这里再怎么增加数量，都会是一样的顺序。而如果发短信延迟之后
 *          就是打电话先来，发短信的所有线程都慢一些。(但我觉得这个解释还不够，底层实现还是不太清楚)
 *      </P>
 *  </ul>
 */
public class lock8_2 {
    public static void main(String[] args) {
        //两个对象
        phone2 pp1 = new phone2();
        phone2 pp2 = new phone2();
        //第一个对象发短信
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                pp1.sendMsg();
            }
        }, "A").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        //第二个对象打电话
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                pp2.call();
            }
        }, "A").start();

//        new Thread(()->{
//            pp.sendMsg();
//        },"A").start();
//
//        //两个线程之间休息一段时间：注意用TimeUnit类
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            System.out.println(e);
//        }
//        new Thread(()->{
//            pp.hello();
//        },"B").start();
//        new Thread(()->{
//            pp.hello();
//        },"C").start();
//        new Thread(()->{
//            pp.hello();
//        },"D").start();
//        new Thread(()->{
//            pp.hello();
//        },"E").start();
//        new Thread(()->{
//            pp.hello();
//        },"F").start();
    }
}

/**
 * 资源类
 */
class phone2{

    public synchronized void sendMsg(){
//        try {
//            TimeUnit.SECONDS.sleep(4);
//        } catch (InterruptedException e) {
//            System.out.println(e);
//        }
        System.out.println("发短信!");
    }
    public synchronized void call(){
        System.out.println("打电话!");
    }
    public void hello(){
        System.out.println(Thread.currentThread().getName()+"-->hello!");
    }
}