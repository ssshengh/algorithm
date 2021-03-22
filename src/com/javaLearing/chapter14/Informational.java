package com.javaLearing.chapter14;

// streams/Informational.java
import java.util.stream.*;
import java.util.function.*;
public class Informational {
    public static void
    main(String[] args) throws Exception {
        String path = "/Users/shenheng/Code/src/com/javaLearing/chapter14/Cheese.dat";
        System.out.println(
                FileToWords.stream(path).count());
        System.out.println(
                FileToWords.stream(path)
                        .min(String.CASE_INSENSITIVE_ORDER)
                        .orElse("NONE"));
        System.out.println(
                FileToWords.stream(path)
                        .max(String.CASE_INSENSITIVE_ORDER)
                        .orElse("NONE"));
    }
    //min() 和 max() 的返回类型为 Optional，这需要我们使用 orElse()来解包。
}

