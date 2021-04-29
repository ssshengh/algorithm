package concurrentLearn.Kuang.JUC.UnsafeList;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

public class SetTest {
    public static void main(String[] args) {
        Set<String> strings = new HashSet<>();
        //java.util.ConcurrentModificationException
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                strings.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(strings);
            }, String.valueOf(i)).start();
        }

        //使用JUC和工具类
        Set<String> strings1 = Collections.synchronizedSet(new HashSet<>());
        Set<String> strings2 = new CopyOnWriteArraySet<>();

    }
}
