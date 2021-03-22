package com.javaLearing.chapter20;

import java.util.stream.Stream;

public class CountedObject {
    //跟踪自身创建了多少个实例
    private static long count = 0;
    private final long id = count++;

    public long id() {
        return id;
    }

    @Override
    public String toString() {
        return "CountedObject " + id +" "+ count;
    }
}

class BasicSupplierDemo{
    public static void main(String[] args) {
        Stream.generate(BasicSupplier.create(CountedObject.class))
                .limit(15)
                .forEach(System.out::println);
    }//结果上看，可以看见不论是static还是final的，都改变了，他们共用一个字段
    //另一个是，注意这种生成Supplier对象的方法
    //通过creat方法，创造了一个Supplier对象，并调用get方法，获取传入的CountedObject.class信息，然后提取出来
    //最后就通过toString方法输出，可见的就是一个Supplier对象，但CountedObject并不继承于Supplier
    //即：将一个任意类型的对象，变成一个Supplier对象，其虽然没有get方法，但是通过了toString完成了流元素的创建
}