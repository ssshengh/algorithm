package com.javaLearing.chapter14;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Pair{
    public final Character c;
    public final Integer i;
    public Pair(Character c, Integer i){
        this.c = c;
        this.i = i;
    }

    public Character getC(){
        return this.c;
    }
    public Integer getI(){
        return this.i;
    }

    @Override
    public String toString(){
        return "Pair(" + c + ", " + i + ")";
    }
}

class RandomPair{
    Random rand = new Random(47);

    Iterator<Character> capChars = rand
            .ints(65, 91)
            .mapToObj(i -> (char) i)
            .iterator();

    public Stream<Pair> stream() {
        return rand.ints(100, 1000).distinct()//消除重复元素
                .mapToObj(i -> new Pair(capChars.next(), i));
    }
}
//RandomPair 创建了随机生成的 Pair 对象流。在 Java 中，我们不能直接以某种方式组合两个流。
// 所以我创建了一个整数流，并且使用 mapToObj() 将整数流转化成为 Pair 流。
// capChars的随机大写字母迭代器创建了流，然后next()让我们可以在stream()中使用这个流。
// 就我所知，这是将多个流组合成新的对象流的唯一方法。

public class MapCollector {
    public static void main(String[] args) {
        Map<Integer, Character> map =
                new RandomPair().stream()
                .limit(8)
                .collect(Collectors.toMap(Pair::getI, Pair::getC));
        System.out.println(map);
        //我们只使用最简单形式的 Collectors.toMap()，这个方法只需要两个从流中获取键和值的函数。
        // 还有其他重载形式，其中一种当是键发生冲突时，使用一个函数来处理冲突。

    }
}
