package com.javaLearing.chapter20;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * 泛型也可以应用于接口。例如 生成器，这是一种专门负责创建对象的类。实际上，这是 工厂方法 设计模式的一种应用。
 * 不过，当使用生成器创建新的对象时，它不需要任何参数，而工厂方法一般需要参数。生成器无需额外的信息就知道如何创建新对象。
 *
 * 注意体会生成器
 * */
public class CoffeeSupplier
implements Supplier<Coffee>, Iterable<Coffee>
    //Supplier的最关键的作用是，确保返回值和参数一样，他的get就是生成器，用来从.class中创建对象
{
    private Class<?>[] type = {Latte.class, Mocha.class,
    Cappuccino.class, Americano.class, Breve.class};
    private static Random rand = new Random(47);

    public CoffeeSupplier(){}

    private int size = 0;//为了迭代用
    public CoffeeSupplier(int size){this.size = size;}

    @Override
    public Coffee get(){
        try {
            return (Coffee) type[rand.nextInt(type.length)].getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException
                | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    //迭代器的书写：首先用一个内部类
    class CoffeeIterator implements Iterator<Coffee>{
        int count = size;//调用外部类的参数
        @Override
        public boolean hasNext(){return count > 0;}
        @Override
        public Coffee next(){
            count--;
            return CoffeeSupplier.this.get();//随机迭代一个出来
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<Coffee> iterator(){
        return new CoffeeIterator();
    }//到这里结束，通过返回一个内部类来实现迭代器

    public static void main(String[] args) {
        //参数化的 Supplier 接口确保 get() 返回值是参数的类型。
        // CoffeeSupplier 同时还实现了 Iterable 接口，所以能用于 for-in 语句。不过，它还需要知道何时终止循环，这正是第二个构造函数的作用。
        Stream.generate(new CoffeeSupplier())
                .limit(5).forEach(System.out::println);
        for (Coffee c: new CoffeeSupplier(5))//必须实现迭代器
            System.out.println(c);
    }

}
