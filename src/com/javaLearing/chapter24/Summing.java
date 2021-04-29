package com.javaLearing.chapter24;

import onjava.Timer;

import java.util.function.LongSupplier;
import java.util.stream.LongStream;

public class Summing {
    static void timeTest(String id, long checkValue, LongSupplier operation){
        System.out.println(id + ": ");
        Timer timer = new Timer();
        long result = operation.getAsLong();
        if (result == checkValue)
            System.out.println(timer.duration() + "ms");
        else
            System.out.format("result:%d%ncheckValue: %d%n", result, checkValue);
    }
    public static final int SZ = 100_000_000;//扩大10倍也可以 1_000_000_000
    public static final long CHECK = (long) SZ * ((long) SZ +1)/2;//高斯求和公式
    //问题的关键在于转换为long

    /**
     * main() 的第一个版本使用直接生成 Stream 并调用 sum() 的方法。
     * 我们看到流的好处在于即使 SZ 为十亿（1_000_000_000）程序也可以很好地处理而没有溢出（为了让程序运行得快一点，我使用了较小的数字）。
     * 使用 parallel() 的基本范围操作明显更快。
     *
     * 如果使用 iterate() 来生成序列，则减速是相当明显的，可能是因为每次生成数字时都必须调用 lambda。
     * 但是如果我们尝试并行化，当 SZ 超过一百万时，结果不仅比非并行版本花费的时间更长，而且也会耗尽内存（在某些机器上）。
     * @param args:命令行参数
     */
    public static void main(String[] args) {
        System.out.println(CHECK);
        timeTest("Sum Stream:", CHECK,
                ()-> LongStream.rangeClosed(0, SZ).sum());//就是看流中的0-SZ求和与咱们的高斯公式求和结果是否一致
        timeTest("使用并行流：", CHECK,
                ()->LongStream.rangeClosed(0, SZ).parallel().sum());
        timeTest("使用迭代器求和：", CHECK,
                () ->LongStream.iterate(0, i->i+1).limit(SZ+1).sum());
        // Slower & runs out of memory above 1_000_000:
        // timeTest("Sum Iterated Parallel", CHECK, () ->
        //   LongStream.iterate(0, i -> i + 1)
        //     .parallel()
        //     .limit(SZ+1).sum());
    }
    /**
     * 当然，当你可以使用 range() 时，你不会使用 iterate() ，但如果你生成的东西不是简单的序列，你必须使用 iterate() 。
     * 应用 parallel() 是一个合理的尝试，但会产生令人惊讶的结果。我们将在后面的部分中探讨内存限制的原因，但我们可以对流并行算法进行初步观察：
     *
     * 流并行性将输入数据分成多个部分，因此算法可以应用于那些单独的部分。
     * 数组分割成本低，分割均匀且对分割的大小有着完美的掌控。
     * 链表没有这些属性;“拆分”一个链表仅仅意味着把它分成“第一元素”和“其余元素”，这相对无用。
     * 无状态生成器的行为类似于数组;上面使用的 range() 就是无状态的。
     * 迭代生成器的行为类似于链表; iterate() 是一个迭代生成器。
     */
}
