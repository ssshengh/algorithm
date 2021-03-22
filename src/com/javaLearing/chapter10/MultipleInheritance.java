package com.javaLearing.chapter10;

// interfaces/MultipleInheritance.java
import java.util.*;

interface One {
    default void first() {
        System.out.println("first");
    }
}

interface Two {
    default void second() {
        System.out.println("second");
    }
}

interface Three {
    default void third() {
        System.out.println("third");
    }
}

class MI implements One, Two, Three {}//可以继承多个接口

public class MultipleInheritance {
    public static void main(String[] args) {
        MI mi = new MI();//继承了多个接口，就可以调用多个接口中的方法
        mi.first();
        mi.second();
        mi.third();
    }
}

