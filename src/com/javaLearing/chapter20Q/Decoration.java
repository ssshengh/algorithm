package com.javaLearing.chapter20Q;

// generics/decorator/Decoration.java

// {java generics.decorator.Decoration}


/**
 * 产生自泛型的类包含所有感兴趣的方法，但是由使用装饰器所产生的对象类型是最后被装饰的类型。
 * 也就是说，尽管可以添加多个层，但是最后一层才是实际的类型，因此只有最后一层的方法是可视的，而混型的类型是所有被混合到一起的类型。
 * 因此对于装饰器来说，其明显的缺陷是它只能有效地工作于装饰中的一层（最后一层），而混型方法显然会更自然一些。
 * 因此，装饰器只是对由混型提出的问题的一种局限的解决方案。
 * */
//import java.util.*;
//
//class Basic1 {
//    private String value;
//    public void set(String val) { value = val; }
//    public String get() { return value; }
//}
//
//class Decorator extends Basic {
//    protected Basic basic;
//    Decorator(Basic basic) { this.basic = basic; }
//    @Override
//    public void set(String val) { basic.set(val); }
//    @Override
//    public String get() { return basic.get(); }
//}
//
//class TimeStamped extends Decorator {
//    private final long timeStamp;
//    TimeStamped(Basic basic) {
//        super(basic);
//        timeStamp = new Date().getTime();
//    }
//    public long getStamp() { return timeStamp; }
//}
//
//class SerialNumbered extends Decorator {
//    private static long counter = 1;
//    private final long serialNumber = counter++;
//    SerialNumbered(Basic basic) { super(basic); }
//    public long getSerialNumber() { return serialNumber; }
//}
//
//public class Decoration {
//    public static void main(String[] args) {
//        TimeStamped t = new TimeStamped(new Basic());
//        TimeStamped t2 = new TimeStamped(
//                new SerialNumbered(new Basic()));
//        //- t2.getSerialNumber(); // Not available
//        SerialNumbered s = new SerialNumbered(new Basic());
//        SerialNumbered s2 = new SerialNumbered(
//                new TimeStamped(new Basic()));
//        //- s2.getStamp(); // Not available
//    }
//}

