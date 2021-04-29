package com.javaLearing.chapter24;

import onjava.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//Collection 确实有一些批处理操作，如 removeAll() ，removeIf() 和 retainAll() ，但这些都是破坏性的操作。
// ConcurrentHashMap 对 forEach 和 reduce 操作有特别广泛的支持。
public class CollectionIntoStream {
    public static void main(String[] args) {
        List<String> strings = Stream.generate(new Rand.String(5))
                .limit(10)
                .collect(Collectors.toList());

        strings.forEach(System.out::println);//注意这种集合上使用流的用法

        String result = strings.stream()
                .map(String::toUpperCase)
                .map(s -> s.substring(2))
                .reduce(":", (s1, s2) -> s1+s2);
        System.out.println(result);
    }
}
