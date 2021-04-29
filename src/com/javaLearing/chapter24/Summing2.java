package com.javaLearing.chapter24;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class Summing2 {
    //数组求和
    static long basicSum(long[] ia){
        long sum = 0;
        int size = ia.length;
        for (long l:ia)
            sum+=l;
        return sum;
    }

    public static final int SZ = 20_000_000;//扩大10倍也可以 1_000_000_000
    public static final long CHECK = (long) SZ * ((long) SZ +1)/2;//高斯求和公式

    static void print(long a){
        System.out.print(a + " ");
    }
    public static void main(String[] args) {
        System.out.println(CHECK);
        long[] la = new long[SZ+1];
        Arrays.parallelSetAll(la, i->i);//这个是一个连续内存


        System.out.println("la is:");
        Arrays.stream(la).limit(100).forEach(Summing2::print);

        Summing.timeTest("数组流的和：", CHECK,
                ()->Arrays.stream(la).sum());
        Summing.timeTest("并行流：", CHECK,
                ()->Arrays.stream(la).parallel().sum());
        Summing.timeTest("函数求和：", CHECK,
                ()->Summing2.basicSum(la));
        Summing.timeTest("ParallelPrefix:" , CHECK,
                ()-> {
                    Arrays.parallelPrefix(la, Long::sum);
                    // For example if the array initially holds [2, 1, 0, 3] and the operation performs addition,
                    // then upon return the array holds [2, 3, 3, 6].
                    // Parallel prefix computation is usually more efficient than sequential loops for large arrays.
                    return la[la.length-1];
                });

    }
}
