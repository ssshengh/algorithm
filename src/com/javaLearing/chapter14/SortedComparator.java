package com.javaLearing.chapter14;

import java.util.Comparator;

public class SortedComparator {
    public static void main(String[] args) throws Exception{
        FileToWords.stream("/Users/shenheng/Code/src/com/javaLearing/chapter14/Cheese.dat")
                .skip(10)
                .limit(10)
                .sorted(Comparator.reverseOrder())
                //这里我们使用的是反转“自然排序”。当然你也可以把 Lambda 函数作为参数传递给 sorted()。
                .map(w -> w + " ")
                .forEach(System.out::print);
    }
}
