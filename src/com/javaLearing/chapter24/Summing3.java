package com.javaLearing.chapter24;

// concurrent/Summing3.java
// {ExcludeFromTravisCI}
import java.util.*;
public class Summing3 {
    static long basicSum(Long[] ia) {
        long sum = 0;
        int size = ia.length;
        for (Long aLong : ia) sum += aLong;
        return sum;
    }
    // Approximate largest value of SZ before
    // running out of memory on my machine:
    public static final int SZ = 10_000_000;
    public static final long CHECK = (long)SZ * ((long)SZ + 1)/2;

    //现在可用的内存量大约减半，并且所有情况下所需的时间都会很长，除了 basicSum() ，它只是循环遍历数组。
    // 令人惊讶的是， Arrays.parallelPrefix() 比任何其他方法都要花费更长的时间。
    public static void main(String[] args) {
        System.out.println(CHECK);
        Long[] aL = new Long[SZ+1];
        Arrays.parallelSetAll(aL, i -> (long)i);//这里是一个连续的long对象引用数组，注意区分第2个文件

        Summing.timeTest("Long Array Stream Reduce", CHECK, () ->
                Arrays.stream(aL).reduce(0L, Long::sum));
        //这个reduce可以用来求和求最大最小等，具体看说明
        Summing.timeTest("Long Basic Sum", CHECK, () ->
                basicSum(aL));
        // Destructive summation:
        Summing.timeTest("Long parallelPrefix",CHECK, ()-> {
            Arrays.parallelPrefix(aL, Long::sum);
            return aL[aL.length - 1];
        });
    }
}

//我将 parallel() 版本分开了，因为在上面的程序中运行它导致了一个冗长的垃圾收集，扭曲了结果：
class Summing4 {
    public static void main(String[] args) {
        System.out.println(Summing3.CHECK);
        Long[] aL = new Long[Summing3.SZ+1];
        Arrays.parallelSetAll(aL, i -> (long)i);
        Summing.timeTest("Long Parallel",
                Summing3.CHECK, () ->
                        Arrays.stream(aL)
                                .parallel()
                                .reduce(0L,Long::sum));
    }
}