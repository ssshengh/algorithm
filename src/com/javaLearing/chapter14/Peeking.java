package com.javaLearing.chapter14;

public class Peeking {
    public static void main(String[] args) throws Exception{
        FileToWords.stream("/Users/shenheng/Code/src/com/javaLearing/chapter14/Cheese.dat")
                .skip(21)
                .limit(4)
                .map(w -> w+ " ")
                .peek(System.out::print)//peek() 操作的目的是帮助调试。它允许你无修改地查看流中的元素。
                .map(String::toUpperCase)
                .peek(System.out::print)//目的是取出其中的一个元素，对其进行操作，然后对流中每一个元素都这么干
                .map(String::toLowerCase)
                .forEach(System.out::println);
    }
}
