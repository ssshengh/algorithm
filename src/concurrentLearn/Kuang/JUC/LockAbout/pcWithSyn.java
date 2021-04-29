package concurrentLearn.Kuang.JUC.LockAbout;

/**
 * 生产者消费者问题：线程之间的通信问题,线程交替执行
 * <P>
 *     例子：比如两个线程A，B同时去操作一个资源num，并且要保证该资源为始终保持num=0
 *     但是A执行num++后，B必须执行num--，这样的话就需要线程之间能够通信
 *     为了完成这个，在传统的方法中，使用的就是：
 *     <P>
 *         等待唤醒和通知唤醒(在Thread接口源码中能找到这种书写注释的方法)
 *     </P>
 * </P>
 *
 */
public class pcWithSyn {
    //操作资源类：
    public static void main(String[] args) {
        Data data = new Data();
        
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

        //两个线程的时候一点问题没有，但是再多两个线程呢？这样会多一些++和--的，还能一直保证number为0码？
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();

    }

}

class Data{
    //资源类，注意到我们只需要里面包含OOP需要的元素即可，方法加上资源
    private int number = 0;

    //+1操作
    public synchronized void increment() throws InterruptedException{
        while (number!=0)
            //等待，等待不为0的时候到来，那么我就进行++
            this.wait();
        //执行业务：
        number++;
        System.out.println(Thread.currentThread().getName()+"==>"+number);
        //通知其他线程我已经执行完毕了:
        this.notifyAll();
    }
    //-1操作
    public synchronized void decrement() throws InterruptedException{
        while (number==0)
            //等待，等待为0的时候到来，那么我就进行--
            this.wait();
        //执行业务：
        number--;
        System.out.println(Thread.currentThread().getName()+"==>"+number);
        //通知其他线程我已经执行完毕了:
        this.notifyAll();
    }

}