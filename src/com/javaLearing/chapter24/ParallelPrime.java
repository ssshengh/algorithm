package com.javaLearing.chapter24;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Java 8 流的一个显著优点是，在某些情况下，它们可以很容易地并行化。这来自库的仔细设计，特别是流使用内部迭代的方式 - 也就是说，它们控制着自己的迭代器。
 * 特别是，他们使用一种特殊的迭代器，称为 Spliterator，它被限制为易于自动分割。
 * 我们只需要念 .parallel() 就会产生魔法般的结果，流中的所有内容都作为一组并行任务运行。
 * 如果你的代码是使用 Streams 编写的，那么并行化以提高速度似乎是一种琐事
 */
//例如，考虑来自 Streams 的 Prime.java。查找质数可能是一个耗时的过程，我们可以看到该程序的计时
public class ParallelPrime {
    private static final int COUNT = 100_1000;
    public static boolean isPrime(long n){
        return LongStream.rangeClosed(2, (long) Math.sqrt(n)).noneMatch(i -> n%i==0);
        //只有不存在或者流为空时返回true，步长为1
    }

    public static void main(String[] args) throws IOException{
        onjava.Timer timer = new onjava.Timer();//计时器
        List<String> primes = LongStream.iterate(2, i->i+1)//迭代顺序为seed，f(seed),f(f(seed))。。。
                .parallel()//并发,注释掉，时间提高三倍多
                .filter(ParallelPrime::isPrime)//Returns a stream consisting of the elements of this stream that match the given predicate.
                //一种判断范式，传入一个标签为上面的函数即可
                .limit(COUNT)//得限制流的数量，短路操作
                .mapToObj(Long::toString)
                .collect(Collectors.toList());
        System.out.println(timer.duration());
        Files.write(Paths.get("Primes.txt"), primes, StandardOpenOption.CREATE);

        //请注意，这不是微基准测试，因为我们计时整个程序。
        // 我们将数据保存在磁盘上以防止编译器过激的优化;如果我们没有对结果做任何事情，那么一个高级的编译器可能会观察到程序没有意义并且终止了计算（这不太可能，但并非不可能）。请注意使用 nio2 库编写文件的简单性（在文件 一章中有描述）。
    }
}
