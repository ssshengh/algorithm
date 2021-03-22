package com.javaLearing.chapter20;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class Fibonacci implements Supplier<Integer> {
    private int count = 0;
    @Override
    public Integer get(){
        return fib(count++);
    }
    private int fib(int n){
        if (n<2)
            return 1;
        return fib(n-1) + fib(n-2);
    }

    public static void main(String[] args) {
        Stream.generate(Fibonacci::new)//这种是生成对象
                .limit(18)
                .forEach(System.out::print);
        System.out.println();
        Stream.generate(new Fibonacci())//这样才是调用get或者toString
                .limit(10)
                .map(n-> n + " ")
                .forEach(System.out::print);//可以注意到，流的元素共用了一个count字段
    }

}
