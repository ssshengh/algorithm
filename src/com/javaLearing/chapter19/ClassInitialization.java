package com.javaLearing.chapter19;

// typeinfo/ClassInitialization.java
import java.util.*;

class Initable {
    static final int STATIC_FINAL = 47;
    static final int STATIC_FINAL2 =
            ClassInitialization.rand.nextInt(1000);
    static {
        System.out.println("Initializing Initable");
    }
}

class Initable2 {
    static int staticNonFinal = 147;
    static {
        System.out.println("Initializing Initable2");
    }
}

class Initable3 {
    static int staticNonFinal = 74;
    static {
        System.out.println("Initializing Initable3");
    }
}

public class ClassInitialization {
    public static Random rand = new Random(47);
    public static void
    main(String[] args) throws Exception {
        /**
         * 如果一个 static final 值是“编译期常量”（如 Initable.staticFinal），那么这个值不需要对 Initable 类进行初始化就可以被读取。
         * 但是，如果只是将一个字段设置成为 static 和 final，还不足以确保这种行为。
         * 例如，对 Initable.staticFinal2 的访问将强制进行类的初始化，因为它不是一个编译期常量。
         *
         * 如果一个 static 字段不是 final 的，那么在对它访问时，总是要求在它被读取之前，
         * 要先进行链接（为这个字段分配存储空间）和初始化（初始化该存储空间），就像在对 Initable2.staticNonFinal 的访问中所看到的那样。
         * */
        Class initable = Initable.class;
        System.out.println("After creating Initable ref");
        // Does not trigger initialization:
        System.out.println(Initable.STATIC_FINAL);//编译期常量，直接可以使用，不需要对类初始化就可以
        // Does trigger initialization:
        System.out.println(Initable.STATIC_FINAL2);//这个并不是，因为调用了其他不一定被初始化的类
        // Does trigger initialization:
        System.out.println(Initable2.staticNonFinal);

        //初始化有效地实现了尽可能的“惰性”，从对 initable 引用的创建中可以看到，仅使用 .class 语法来获得对类对象的引用不会引发初始化。
        // 但与此相反，使用 Class.forName() 来产生 Class 引用会立即就进行初始化，如 initable3。
        //但实际上，现在修改为不会初始化了，抛出错误
        Initable3 a = new Initable3();
        Class initable3 = Class.forName("Initable3");
        System.out.println("After creating Initable3 ref");
        System.out.println(Initable3.staticNonFinal);
    }
}
