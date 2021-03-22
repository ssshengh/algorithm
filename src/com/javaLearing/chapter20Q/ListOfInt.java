package com.javaLearing.chapter20Q;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListOfInt {
    public static void main(String[] args) {
        List<Integer> list = IntStream.range(30,48)
                .boxed()//自动装箱
                .collect(Collectors.toList());
        System.out.println(list);
    }
}
class ByteSet {
    Byte[] possibles = { 1,2,3,4,5,6,7,8,9 };
    Set<Byte> mySet = new HashSet<>(Arrays.asList(possibles));
    // But you can't do this:
    // Set<Byte> mySet2 = new HashSet<>(
    // Arrays.<Byte>asList(1,2,3,4,5,6,7,8,9));
}
