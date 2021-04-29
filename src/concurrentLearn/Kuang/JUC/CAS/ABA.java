package concurrentLearn.Kuang.JUC.CAS;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABA {
    public static void main(String[] args) {
        //这个可以同时赋值，并给予一个版本号的值
        //因为Integer的问题，这是一个大坑
//        AtomicStampedReference<Integer> atomicReference =
//                new AtomicStampedReference<>(2020, 1100);
        AtomicStampedReference<Integer> atomicReference =
                new AtomicStampedReference<>(1, 1100);

        new Thread(()->{
            int stamp = atomicReference.getStamp();//获取版本号
            System.out.println("A第一次拿到版本号："+stamp);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //如果期望值为2020，那么我拿到期望值就变为2022，然后将目前版本号+1
            System.out.println(atomicReference.compareAndSet(1, 2,
                    atomicReference.getStamp(), atomicReference.getStamp() + 1));
            System.out.println("A第二次的版本号"+atomicReference.getStamp());
            //ABA
            System.out.println(atomicReference.compareAndSet(2, 1,
                    atomicReference.getStamp(), atomicReference.getStamp() + 1));
            System.out.println("A第三次的版本号"+atomicReference.getStamp());
        }, "A").start();
        new Thread(()->{
            int stamp = atomicReference.getStamp();//获取版本号
            System.out.println("B第一次拿到版本号："+stamp);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //如果期望值为2020，那么我拿到期望值就变为2022，然后将目前版本号+1
            System.out.println(atomicReference.compareAndSet(1, 6,
                    atomicReference.getStamp(), atomicReference.getStamp() + 1));
            System.out.println("B第二次拿到版本号："+atomicReference.getStamp());
        }, "B").start();
    }
}
