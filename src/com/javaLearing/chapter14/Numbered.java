package com.javaLearing.chapter14;

// streams/FunctionMap2.java
// Different input and output types （不同的输入输出类型）
import java.util.*;
import java.util.stream.*;
class Numbered {
    final int n;
    Numbered(int n) {
        this.n = n;
    }
    @Override
    public String toString() {
        return "Numbered(" + n + ")";
    }
}
class FunctionMap2 {
    public static void main(String[] args) {
        Stream.of(1, 5, 7, 9, 11, 13)
                .peek(System.out::print)
                .map(Numbered::new)//我们将获取到的整数通过构造器 Numbered::new 转化成为 Numbered 类型。
                .forEach(System.out::println);
    }
}

