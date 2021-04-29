package concurrentLearn.Kuang;


import java.util.concurrent.locks.ReentrantLock;

//多个线程互相抱着对象的资源，造成僵持
public class DeadLock {
    public static void main(String[] args) {
        makeUp g1 = new makeUp(0, "灰姑凉");
        makeUp g2 = new makeUp(1, "白雪公主");
        g1.start();
        g2.start();



    }
}

//口红
class Lipstick{

}
//镜子
class Mirror{

}
class makeUp extends Thread{
    //需要的资源都只有一份
    static final Lipstick lipstick = new Lipstick();
    static final Mirror mirror = new Mirror();

    int choice;//选择
    String name;//使用的人的名字
    public makeUp(int choice, String name) {this.choice = choice; this.name = name;}

    /**
     * 化妆，需要拿到对方的资源
     */
    @Override
    public void run() {
        try {
            makeup();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void makeup() throws InterruptedException{
        //两人僵持，互相锁住资源
        if (choice==0)//第一个人
        {
            synchronized (lipstick){//获得口红的锁
                System.out.println(this.getName() + "获得口红的锁");//this.getName调用的是Thread里面的方法，因此获得本线程的信息
                Thread.sleep(1000);//1s后想要获得镜子的锁
                synchronized (mirror){
                    System.out.println(this.getName() + "获得镜子的锁");
                }
            }
        }else {
            synchronized (mirror){//获得口红的锁
                System.out.println(this.getName() + "获得口红的锁");//this.getName调用的是Thread里面的方法，因此获得本线程的信息
                Thread.sleep(1000);//1s后想要获得镜子的锁
                synchronized (lipstick){
                    System.out.println(this.getName() + "获得镜子的锁");
                }
            }
        }

    }
}


//更好的锁：lock
class TestLock{
    public static void main(String[] args) {
        buyTicket2 buy = new buyTicket2();

        new Thread(buy, "小明").start();
        new Thread(buy, "黄牛").start();
        new Thread(buy, "小红").start();
    }
}
class buyTicket2 implements Runnable{
    int nus = 10;//10张票

    //定义lock锁，可重入锁
    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
//        while (nus>1){
//            try {
//                lock.lock();//加锁
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    System.out.println(e);
//                }
//                System.out.println(Thread.currentThread().getName()+"获取了票："+ nus--);
//
//            }finally {
//                lock.unlock();//解锁，必然会执行
//            }
//
//        }加锁失败，因为没有把资源加进去

        try {
            lock.lock();
            while (nus>1){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"获取了票："+ nus--);
            }

        }finally {
            lock.unlock();
        }
    }
}