package concurrentLearn.Kuang.JUC.UnsafeList;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListTest {
    public static void main(String[] args) {
        //常规的列表的使用，在单线程下没问题
        List<String> list = Arrays.asList("1", "2", "3");
        list.forEach(System.out::println);

        //多线程下赋值呢？
        //java.util.ConcurrentModificationException 出现了并发修改异常！
        List<String> list1 = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            new Thread(()->{
//                list1.add(UUID.randomUUID().toString().substring(0,5));
//                System.out.println(list1);
//            }).start();
//        }
        /*如何解决？
        * 1、不用ArrayList实现，使用Vector，但是Vector存在一些问题，甚至早于ArrayList，它能实现的原因是add方法有synchronized
        * 2、并发的方法的话，通过工具类，将ArrayList变得安全：*/
        List<String> list_syn = Collections.synchronizedList(new ArrayList<>());
        //JUC方案：直接用并发数组的实现
        List<String> stringList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                stringList.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(stringList);
            }).start();
        }



    }
}
