package com.javaLearing.chapter20;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class BasicSupplier <T> implements Supplier<T> {
    private Class<T> type;

    public BasicSupplier(Class<T> type){
        this.type = type;
    }//输入一个任意类型的类，这个类具有无参构造函数的话，可以生成Supplier类
    @Override
    public T get(){
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    //Produce a default Supplier from a type token:
    public static <T> Supplier<T> create(Class<T> tClass){
        return new BasicSupplier<>(tClass);
    }
    //具有无参构造方法。要创建一个这样的 BasicSupplier 对象，请调用 create() 方法，并将要生成类型的类型令牌传递给它。
    // 通用的 create() 方法提供了 BasicSupplier.create(MyType.class) 这种较简洁的语法来代替较笨拙的 new BasicSupplier <MyType>(MyType.class)。
}
