package concurrentLearn.Kuang;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.*;

public class testCallable implements Callable<Boolean> {
    private final String url;
    private final String path;
    private final int id = 0;
    public testCallable(String url, String path){
        this.path = path;
        this.url = url;
    }

    //多线程方法复写

    @Override
    public Boolean call() {
        System.out.println("开始下载:"+path);
        DownLoader t = new DownLoader();
        t.down(url, path);
        System.out.println("下载完毕");
        return true;
    }

    public static void main(String[] args) {
        testCallable t1 = new testCallable("https://www.kuangstudy.com/assert/course/c1/01.jpg", "01.jpg");
        testCallable t2 = new testCallable("https://www.kuangstudy.com/assert/course/c1/02.jpg", "02.jpg");
        testCallable t3 = new testCallable("https://www.kuangstudy.com/assert/course/c1/03.jpg", "03.jpg");

        //创建线程池
        ExecutorService exec = Executors.newSingleThreadExecutor();//单个线程

        //提交线程
        Future<Boolean> f1 = exec.submit(t1);
        Future<Boolean> f2 = exec.submit(t2);
        Future<Boolean> f3 = exec.submit(t3);

        //获取结果
        try {
            Boolean b1 = f1.get();
            Boolean b2 = f2.get();
            Boolean b3 = f3.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e);
        }

        exec.shutdown();


    }
}

