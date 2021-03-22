package com.javaLearing.chapter14;

import java.util.stream.IntStream;
import static java.util.stream.IntStream.*;//要在static方法里使用，需要static import
public class Ranges {
    public static void main(String[] args) {
        //传统方法
        int result = 0;
        for (int i = 10; i<20; i++)
            result+=i;
        System.out.println(result);

        //for-in方法
        result = 0;
        for (int item: range(10,20).toArray())//上一个import需要IntStream.range()
            result+=item;
        System.out.println(result);

        //流方法：
        System.out.println(range(10,20).sum());
    }
}
