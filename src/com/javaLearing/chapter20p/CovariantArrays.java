package com.javaLearing.chapter20p;

import com.javaLearing.chapter19.A;

import java.util.ArrayList;
import java.util.List;

class Fruit {}

class Apple extends Fruit {}

class Jonathan extends Apple {}

class Orange extends Fruit {}


public class CovariantArrays {
    public static void main(String[] args) {
        //数组里面的元素可以是基类，也可以是其子类，可以将派生类的数组赋值给基类的引用
        Fruit[] fruit = new Apple[10];//派生类Apple对象，赋值给了基类引用Fruit
        fruit[0] = new Apple(); // OK
        fruit[1] = new Jonathan(); // OK

        /**
         * 如果实际的数组类型是 Apple[]**，你可以在其中放置 **Apple 或 Apple 的子类型，这在编译期和运行时都可以工作。
         * 但是你也可以在数组中放置 Fruit 对象。
         * 这对编译器来说是有意义的，因为它有一个 Fruit[] 引用——它有什么理由不允许将 Fruit 对象或任何从 Fruit 继承出来的对象（比如 Orange），
         * 放置到这个数组中呢？因此在编译期，这是允许的。
         * 然而，运行时的数组机制知道它处理的是 **Apple[]**，因此会在向数组中放置异构类型时抛出异常。
         * */
        // Runtime type is Apple[], not Fruit[] or Orange[]:
        try {
            // Compiler allows you to add Fruit:关键就在这里，运行时数组类型是Apple而不是Fruit，因此朝它放置这两个都不行
            fruit[0] = new Fruit(); // ArrayStoreException
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            // Compiler allows you to add Oranges:
            fruit[0] = new Orange(); // ArrayStoreException
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

class GenericsAndCovariance{
    public static void main(String[] args) {
        List<? extends Fruit> list = new ArrayList<>();
//        list.add(new Apple());
//        list.add(new Fruit());
        list.add(null);
        Fruit fruit = list.get(0);
    }
}