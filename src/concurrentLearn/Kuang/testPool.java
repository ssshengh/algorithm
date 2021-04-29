package concurrentLearn.Kuang;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//线程池
public class testPool {
    public static void main(String[] args) {
        //创建线程池
        ExecutorService exec = Executors.newFixedThreadPool(10);//10个线程

        //执行多个线程
        exec.execute(new myThread());
        exec.execute(new myThread());
        exec.execute(new myThread());
        exec.execute(new myThread());

        //关闭连接
        exec.shutdown();

    }
}
class myThread implements Runnable{
    @Override
    public void run() {
            System.out.println(Thread.currentThread().getName());

    }
}