package concurrentLearn.Kuang.JUC.CdequeAbout;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        try {
            test4();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 抛出异常的一组阻塞队列API
     */
    public static void test1(){
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(arrayBlockingQueue.add("a"));
        System.out.println(arrayBlockingQueue.add("b"));
        System.out.println(arrayBlockingQueue.add("c"));

        //上面三个加满了,再加一个的话抛出异常：IllegalStateException
//        System.out.println(arrayBlockingQueue.add("d"));
        System.out.println(arrayBlockingQueue.element());
        System.out.println("==========================");
        //接下来看移除的操作
        System.out.println(arrayBlockingQueue.remove());
        System.out.println(arrayBlockingQueue.remove());
        System.out.println(arrayBlockingQueue.remove());
        //已经空了，再取：NoSuchElementException
        System.out.println(arrayBlockingQueue.remove());

    }

    /**
     * 不抛出异常的阻塞队列API
     */
    public static void test2(){
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(arrayBlockingQueue.offer("a"));
        System.out.println(arrayBlockingQueue.offer("b"));
        System.out.println(arrayBlockingQueue.offer("c"));
        //再加上一组，不会抛出异常，返回false
        System.out.println(arrayBlockingQueue.offer("c"));
        //查看队首元素：
        System.out.println(arrayBlockingQueue.peek());
        System.out.println("++++++++++++++++++++++++");
        System.out.println(arrayBlockingQueue.poll());
        System.out.println(arrayBlockingQueue.poll());
        System.out.println(arrayBlockingQueue.poll());
        //移除完毕，再加上一个，不抛出异常，返回null
        System.out.println(arrayBlockingQueue.poll());
    }

    /**
     * 等待、阻塞(一直阻塞)
     */
    public static void test3() throws InterruptedException {
        ArrayBlockingQueue<String> aa = new ArrayBlockingQueue<>(3  );

        //一直阻塞：
        aa.put("a");
        aa.put("b");
        aa.put("c");
        //aa.put("d");//此时队列没有位置，会一直等待
        System.out.println(aa.take());
        System.out.println(aa.take());
        System.out.println(aa.take());
        System.out.println(aa.take());//没有这个元素，再取一个，也会一直阻塞
    }
    /**
     * 等待，阻塞(超时等待)
     */
    public static void test4() throws InterruptedException {
        ArrayBlockingQueue<String> aa = new ArrayBlockingQueue<>(3);

        aa.offer("A");
        aa.offer("B");
        aa.offer("C");
        aa.offer("D",2,  TimeUnit.SECONDS);//超过2s时退出，前面加入的也会全部作废

        System.out.println("======================");
        aa.poll();
        aa.poll();
        aa.poll();
        aa.poll(2, TimeUnit.SECONDS);//超过2s不等了

    }
}
