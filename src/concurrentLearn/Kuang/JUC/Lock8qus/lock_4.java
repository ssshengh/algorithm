package concurrentLearn.Kuang.JUC.Lock8qus;

import java.util.concurrent.TimeUnit;

/**
 * <ul>
 *     6、发短信静态同步锁，打电话没有静态，一个对象。最后结果就是：先打电话
 * </ul>
 * <ul>
 *     7、发短信静态同步锁，打电话没有静态，两个对象。最后结果还是：先打电话
 * </ul>
 * <P>
 *     答案：一个锁的Class一个锁方法的调用者，二者是两把锁互不干扰，是并行了。打电话就不会等待发短信的4s
 * </P>
 */
public class lock_4 {
    public static void main(String[] args) {
        phone4 pp = new phone4();
        phone4 pp1 = new phone4();

        new Thread(phone4::sendMsg,"A").start();
        //两个线程之间休息一段时间：注意用TimeUnit类
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
        new Thread(pp1::call,"B").start();
    }
}

/**
 * 资源类
 */
class phone4{

    public static synchronized void sendMsg(){
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