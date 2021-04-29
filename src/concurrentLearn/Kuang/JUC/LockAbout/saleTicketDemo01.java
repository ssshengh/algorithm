package concurrentLearn.Kuang.JUC.LockAbout;

/**
 * 基本的卖票例子
 * 写法上需要注意：线程就是一个基本的资源类，没有任何附属操作。因此，不需要再麻烦的重写一个Runnable
 *      另一个需要注意，接口带来的高耦合，而实际开发中需要解耦，这一块在Thinking on java中接口一章有提到
 */
public class saleTicketDemo01 {
    public static void main(String[] args) {
        //关键点，这里去体会一个概念：耦合性，我们希望类或者对象能够不断地被复用，如果它继承了某个接口的话
        //那么其实现行为就只能是该接口下面的函数，与接口耦合性变强
        //但是有更好的做法：

        //资源类：
        Ticket ti = new Ticket();

        //多个线程同时操作资源
        new Thread(()->{
            for (int i = 0; i < 60; i++) {
                ti.sale();
            }
        }, "a").start();
        new Thread(()->{
            for (int i = 0; i < 60; i++) {
                ti.sale();
            }
        }, "b").start();
        new Thread(()->{
            for (int i = 0; i < 60; i++) {
                ti.sale();
            }
        }, "c").start();
    }
}
class Ticket{
    //不需要继承，只需要考虑OOP中的，属性和方法的处理：
    private int number = 50;

    //卖票
    //没有加Synchronize的话,结果顺序并不整齐，但是也没看见出现了资源溢出的情况
    public synchronized void sale(){
        if (number>0){
            System.out.println(Thread.currentThread().getName()+"卖出了"+number--+"张票"+
                    ",剩余："+number);
        }
    }
}
