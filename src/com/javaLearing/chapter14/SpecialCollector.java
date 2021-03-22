package com.javaLearing.chapter14;

// streams/SpecialCollector.java
import java.util.*;
import java.util.stream.*;
public class SpecialCollector {
    public static void main(String[] args) throws Exception {
        ArrayList<String> words =
                FileToWords.stream("/Users/shenheng/Code/src/com/javaLearing/chapter14/Cheese.dat")
                        .collect(ArrayList::new,
                                ArrayList::add,
                                ArrayList::addAll);
        System.out.println(words);
        words.stream()
                .filter(s -> s.equals("cheese"))
                .forEach(System.out::println);
        //在这里， ArrayList 的方法已经做了你所需要的操作，但更有可能的是，如果你必须使用这种形式的 collect()，就要自己创建特定的定义。
    }
}

