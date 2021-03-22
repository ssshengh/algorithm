package com.javaLearing.chapter19;

// typeinfo/DynamicSupplier.java
import java.util.function.*;
import java.util.stream.*;

class CountedInteger {
    private static long counter;
    private final long id = counter++;
    @Override
    public String toString() { return Long.toString(id); }
}

//注意，这个类必须假设与它一起工作的任何类型都有一个无参构造器，否则运行时会抛出异常。编译期对该程序不会产生任何警告信息
public class DynamicSupplier<T> implements Supplier<T> {
    private Class<T> type;
    public DynamicSupplier(Class<T> type) {
        this.type = type;
    }
    public T get() {
        try {
            return type.newInstance();//生成类的对象
        } catch(InstantiationException |
                IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    //当你将泛型语法用于 Class 对象时，newInstance() 将返回该对象的确切类型，而不仅仅只是在 ToyTest.java 中看到的基类 Object。
    public static void main(String[] args) {
        Stream.generate(
                new DynamicSupplier<>(CountedInteger.class))//因为其继承与Supplier，所以可以直接生成流，并且调用get方法
                .skip(10)
                .limit(5)
                .forEach(System.out::println);
    }
}
//然而，这在某种程度上有些受限：
/**
 * 如果你手头的是超类，那编译器将只允许你声明超类引用为“某个类，它是 FancyToy 的超类”，就像在表达式 Class<? super FancyToy> 中所看到的那样。
 * 而不会接收 Class<Toy> 这样的声明。这看上去显得有些怪，因为 getSuperClass() 方法返回的是基类（不是接口），
 * 并且编译器在编译期就知道它是什么类型了（在本例中就是 Toy.class），而不仅仅只是"某个类"。
 * 不管怎样，正是由于这种含糊性，up.newInstance 的返回值不是精确类型，而只是 Object。
 * */
class GenericToyTest {
    public static void
    main(String[] args) throws Exception {
        Class<FancyToy> ftClass = FancyToy.class;
        // Produces exact type:
        FancyToy fancyToy = ftClass.newInstance();
        Class<? super FancyToy> up =
                ftClass.getSuperclass();
        // This won't compile:
        // Class<Toy> up2 = ftClass.getSuperclass();
        // Only produces Object:
        Object obj = up.newInstance();
    }
}