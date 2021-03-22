package com.javaLearing.chapter18;

import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UsingStringBuilder {
    public static String string1(){
        Random rand = new Random(47);
        StringBuilder result = new StringBuilder("[");
        for(int i = 0; i<25; i++){
            result.append(rand.nextInt(100));
            result.append(", ");
        }
        result.delete(result.length()-2, result.length());//删除最后一个逗号和空格，以便添加右括号。
        result.append("]");
        return result.toString();
    }
    //如果你想走捷径，例如：append(a + ": " + c)，编译器就会掉入陷阱，
    // 从而为你另外创建一个 StringBuilder 对象处理括号内的字符串操作。如果拿不准该用哪种方式，随时可以用 javap 来分析你的程序。

    //string2() 使用了 Stream，这样代码更加简洁美观。
    // 可以证明，Collectors.joining() 内部也是使用的 StringBuilder，这种写法不会影响性能！
    public static String string2(){
        String result = new Random(47)
                .ints(25, 0, 100)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(", "));
        return "[" + result + ']';
    }

    public static void main(String[] args) {
        System.out.println(string1());
        System.out.println(string2());
    }
}
