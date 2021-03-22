package com.javaLearing.chapter14;

import java.util.stream.IntStream;

class Repeat{
    public static void repeat(int n, Runnable run){
        //forEach的参数是一个匿名函数或者方法引用，
        IntStream.range(0, n).forEach(i->run.run());
        //这个的理解是这样，run()方法是接口Runnable中的一个空方法，没有参数也没有返回值
        //一版来说实现run()我们需要定义一个Runnable对象，然后给其赋值一个方法引用对象，变成方法引用
        //这时候方法引用调用run()方法执行传递进去的方法
        //这里foreach是遍历所有流中的元素，对每一个元素执行传递进来的方法，这个方法就是run，对其调run()，那么返回的就是每个元素i
    }
}
public class Looping {
    static void hi (){
        System.out.println("hi!");
    }

    public static void main(String[] args) {
        Repeat.repeat(3, ()-> System.out.println("i"));//Runnable是无参数的，不能i->sout(i)
        Repeat.repeat(2, Looping::hi);
    }

}
