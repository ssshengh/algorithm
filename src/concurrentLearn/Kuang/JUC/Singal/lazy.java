package concurrentLearn.Kuang.JUC.Singal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

/**
 * 懒汉式单例模式：为了解决饿汉式的内存浪费
 */
public class lazy {
    private boolean shenheng = false;
    private lazy(){
        synchronized (lazy.class){
            if (shenheng == false)
                shenheng = true;
            else
                throw new RuntimeException("不要试图利用反射获取对象");
        }

        System.out.println(Thread.currentThread().getName()+"ok");

    }
    private static volatile lazy LAZY;//一样的静态对象一个
    //解决线程不安全问题：双重监测锁模式懒汉单例，DCL懒汉单例,好处参见effective Java
    public static lazy getLazy(){
        if (LAZY == null){
            synchronized (lazy.class){
                //注意锁的是这个玩意儿，因为是在静态区
                if (LAZY == null)
                    LAZY = new lazy();//DCL的问题：这里不是原子性操作
                //这里会：(类加载)首先分配内存空间、修改实例引用到直接引用、最后利用构造器初始化
                //但是这里是对象的new：首先分配空间、然后构造器初始化对象、最后指向这个空间(三个操作称为123)
                //甚至存在指令重排的问题
                //如果一个线程A指令是132，正常是123，这时候来了个线程B
                //问题出来了，A线程没有问题，它先占了这个空间，准备分配内存但是还没有分配，但是同时线程B就判断LAZY不为空了(引用)
                //所以就出大问题了，最大的if直接跳过，B线程直接返回了一个LAZY，但是其还没完成构造，是一个空空间，可能就有问题，永远都没有被创建
                //所以DCL，还需要更近一步LAZY加volatile
            }
        }
        return LAZY;
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                lazy.getLazy();//创建十个线程可以看出懒汉式的线程不安全
            }).start();
        }

        //另一个问题：无视了权限的反射
        lazy lazy1 = lazy.getLazy();//正常获取一个对象
        /*使用反射获取一个对象*/
        Constructor<lazy> constructor = lazy.class.getDeclaredConstructor(null);
        constructor.setAccessible(true);//设置为true很霸道，就无视了private权限
        lazy instance = constructor.newInstance();//最后通过反射创建了对象

        //那么我加锁加标识处理反射:还是有办法，毕竟私有变量也可以获取
        Field shenheng = lazy.class.getDeclaredField("shenheng");
        shenheng.setAccessible(true);
        shenheng.set(instance, true);

        System.out.println(lazy1);
        System.out.println(instance);

    }
}
