package com.javaLearing.chapter20;


import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class ClassAsFactory<T> implements Supplier<T> {
    Class<T> kind;

    ClassAsFactory(Class<T> kind){this.kind = kind;}

    @Override
    public T get(){
        try {
            return kind.getDeclaredConstructor().newInstance();//这样通过工厂和类型标签，就创建了一个T类型的对象
        } catch (InstantiationException | IllegalAccessException
                | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
class Employ{
    @Override
    public String toString() {
        return "Employ!!!!";
    }
}

class InstantiateGenericType{
    public static void main(String[] args) {
        ClassAsFactory<Employ> employ = new ClassAsFactory<>(Employ.class);//把类型标签传入
        System.out.println(employ.get());

        ClassAsFactory<Integer> fi =
                new ClassAsFactory<>(Integer.class);
        try {
            System.out.println(fi.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());//抛出了这个错误
            //虽然可以编译，但是 Integer 没有无参构造函数，所以生成newInstance的时候没得调用
        }
    }
}