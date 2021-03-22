package com.javaLearing.chapter20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class RandomList<T> extends ArrayList<T> {
    private Random random = new Random(47);
    public T select(){
        return get(random.nextInt(size()));
    }//注意可以直接调用这两个静态方法

    public static void main(String[] args) {
        RandomList<String> randomList = new RandomList<>();
        Arrays.stream("The quick brown fox jumped over the lazy brown dog".split(" ")).forEach(randomList::add);
        IntStream.range(0,11).forEach(
                i -> System.out.print(randomList.select()+ " ")
        );

    }
}
