package concurrentLearn.Kuang.JUC.CAS;

import java.util.concurrent.atomic.AtomicInteger;

public class casdemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(2020);
        //CompareAndSet的底层是Compare and swap Int，也就是CAS
        //查看源码，这个方法有两个参数，一个是期望一个是更新：如果期望达到了就更新否则就不更新的思想
        //这个就是CAS的思想，CPU的并发原语
        atomicInteger.compareAndSet(2020, 2021);
        System.out.println(atomicInteger.get());
        //可以看见上面实实在在的更新了原子int中的值
        atomicInteger.compareAndSet(2021, 21);
        System.out.println(atomicInteger.get());

        //捣乱的线程=============================
        atomicInteger.compareAndSet(2020, 2021);
        System.out.println(atomicInteger.get());
        atomicInteger.compareAndSet(2021, 2020);
        System.out.println(atomicInteger.get());
        //原本我们期望的===========================
        atomicInteger.compareAndSet(2020, 666);
        System.out.println(atomicInteger.get());
    }
}
