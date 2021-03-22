package com.javaLearing.chapter14;

// streams/Matching.java
// Demonstrates short-circuiting of *Match() operations
import java.util.stream.*;
import java.util.function.*;
import static com.javaLearing.chapter14.RandInts.*;

interface Matcher extends BiPredicate<Stream<Integer>, Predicate<Integer>> {}
//BiPredicate 是一个二元谓词，它接受两个参数并返回 true 或者 false。
// 第一个参数是我们要测试的流，第二个参数是一个谓词 Predicate。
// Matcher 可以匹配所有的 Stream::*Match 方法，所以可以将每一个Stream::*Match方法引用传递到 show() 中。
// 对match.test() 的调用会被转换成 对方法引用Stream::*Match 的调用。

public class Matching {
    static void show(Matcher match, int val) {
        System.out.println(
                match.test(
                        IntStream.rangeClosed(1, 9)
                                .boxed()
                                .peek(n -> System.out.format("%d ", n)),//参数1，是一个流
                        n -> n < val)//参数2，是另一个谓词
        );
    }
    public static void main(String[] args) {
        //能这么用，至少说明，Stream::*Match方法引用的签名和Matcher一样
        //但是是不一样的，很困惑，但是allmatch这三个方法是，对流的每个元素执行Predict类型的谓词，又和上面的一摸一样
        show(Stream::allMatch, 10);
        show(Stream::allMatch, 4);
        show(Stream::anyMatch, 2);
        show(Stream::anyMatch, 0);
        show(Stream::noneMatch, 5);
        show(Stream::noneMatch, 0);

        //show() 接受一个Matcher和一个 val 参数，val 在判断测试 n < val中指定了最大值。
        // show() 方法生成了整数1-9组成的一个流。peek()用来展示在测试短路之前测试进行到了哪一步。

        //短路是什么呢，如果流针对每个元素都有同样操作的化，那么在判断语句这些的时候，就可能会有短路
        //就看输出，有一些是没有遍历9个int的
        Matcher mm = Stream::allMatch;

    }
}

