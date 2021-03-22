package com.javaLearing.chapter14;

import java.util.stream.Stream;

public class Bubble {
    public final int i;
    public Bubble(int n) {
        i = n;
    }

    @Override
    public String toString() {
        return "Bubble(" + i + ")";
    }

    private static int count = 0;
    //注意它包含了自己的静态生成器（Static generator）方法。
    public static Bubble bubbler() {
        return new Bubble(count++);
    }
}
class bubbles{
    public static void main(String[] args) {
        //因为Bubble继承于Supply，所以可以作为参数传入，而不用new，用静态生成器也是个好思路
        Stream.generate(Bubble::bubbler)
                .limit(5).forEach(System.out::println);
    }
}
