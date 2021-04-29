package concurrentLearn.Kuang.JUC.UnsafeList;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class mapTest {
    public static void main(String[] args) {
        //map是这样用的吗？显然有更好的方法,在并发下使用
        //默认了什么参数？
        //Map<String, String> map = new HashMap<>(16, 0.75);
        //注意看源码，hashMap的实现使用了加载因子和初始容量，分别为0.75和16
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0,5));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }

        //JUC里的Map不再是写复制的了
        Map<String, String> map1 = new ConcurrentHashMap<>();
    }
}
