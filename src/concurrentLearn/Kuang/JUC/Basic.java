package concurrentLearn.Kuang.JUC;


import java.util.concurrent.TimeUnit;

//基础知识与回顾
public class Basic {
    public static void main(String[] args) {
        //java无法开启线程，只能通过底层代码C++操作操作系统
        System.out.println(Runtime.getRuntime().availableProcessors());//获取CPU的线程数
        //注意区分：CPU密集型和IO密集型

        try {
            //我们一般也不用sleep和wait休眠，用的这个
            TimeUnit.DAYS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
