package concurrentLearn.Kuang;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import org.apache.commons.io.*;

//创建一个线程，有几个办法：继承Thread, Runnable, Callable
//千万别忘了，最好的办法：并行流和CF
public class Create extends Thread{
    static final int id = 0;
    @Override
    public void run() {
        IntStream.range(0, 20)
                .mapToObj(i -> "线程id是："+id+",跑了第几次："+i)
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        Create test = new Create();
        test.start();//开启线程一个，如果是run的话将不会是开启线程，而是顺序执行
        //注意这个是单一个线程的实现，并不是很多线程，而且一个线程开启后不能再次被开启

        Random random = new Random(47);
        random.ints().limit(1000).mapToObj(integer -> "现在是："+integer)
                .forEach(System.out::println);//能够明显看出乱了
//        List<Integer> list = Arrays.asList(1,2,3,3,4,5,6,8);
//        list.stream().map(integer -> "现在是："+integer)
//                .forEach(System.out::println);
    }
}

//尝试使用一个外部包，多线程下载一个图片啥的，IO密集型任务很适合多线程
class test2 extends Thread{
    private final String url;
    private final String path;
    private final int id = 0;
    public test2(String url, String path){
        this.path = path;
        this.url = url;
    }

    //多线程方法复写

    @Override
    public void run() {
        System.out.println("开始下载:"+path);
        DownLoader t = new DownLoader();
        t.down(url, path);
        System.out.println("下载完毕");
    }

    public static void main(String[] args) {
        test2 t1 = new test2("https://www.kuangstudy.com/assert/course/c1/01.jpg", "01.jpg");
        test2 t2 = new test2("https://www.kuangstudy.com/assert/course/c1/02.jpg", "02.jpg");
        test2 t3 = new test2("https://www.kuangstudy.com/assert/course/c1/03.jpg", "03.jpg");

        t1.start();
        t2.start();
        t3.start();

    }
}
class DownLoader{
    //这个用于下载图片
    private final int id = 0;
    public void down(String url, String path){
        try {
            FileUtils.copyURLToFile(new URL(url), new File(path));
            System.out.println("正在下载图片"+path);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("图片下载失败");
        }
    }
}