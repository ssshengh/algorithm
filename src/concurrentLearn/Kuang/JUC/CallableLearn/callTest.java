package concurrentLearn.Kuang.JUC.CallableLearn;

import onjava.Rand;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class callTest {
    public static void main(String[] args) {
        //基本的用法
        resource re = new resource();
        //适配类：
        FutureTask<Integer> futureTask = new FutureTask<>(re);
        new Thread(futureTask).start();
        new Thread(futureTask).start();//再加一个线程不会多输出一次，因为会缓存结果
        try {
            Integer in = futureTask.get();//获取返回值
            System.out.println(in);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class resource implements Callable<Integer>{
    @Override
    public Integer call() throws Exception {
        System.out.println("yes!");
        return 1024;
    }
}
