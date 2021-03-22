package com.javaLearing.chapter14;

// streams/NumericStreamInfo.java
import java.util.stream.*;
import static com.javaLearing.chapter14.RandInts.*;
public class NumericStreamInfo {
    public static void main(String[] args) {
        System.out.println(rands().average().getAsDouble());
        System.out.println(rands().max().getAsInt());
        System.out.println(rands().min().getAsInt());
        System.out.println(rands().sum());
        System.out.println(rands().summaryStatistics());
    }
    //上例操作对于 LongStream 和 DoubleStream 同样适用。
}

