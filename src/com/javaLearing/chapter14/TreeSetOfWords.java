package com.javaLearing.chapter14;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class TreeSetOfWords {
    public static void main(String[] args) throws Exception{
        Set<String> words =
                Files.lines(Paths.get("/Users/shenheng/Code/src/com/javaLearing/chapter14/TreeSetOfWords.java"))
                .flatMap(line -> Arrays.stream(line.split("\\W+")))
                .filter(s -> !s.matches("\\d+"))//使用 matches(\\d+) 查找并移除全部是数字的字符串（注意,words2 是通过的）。
                .map(String::trim)
                .filter(s->s.length()>2)
                .limit(100)
                .collect(Collectors.toCollection(TreeSet::new));
        //然后用 String.trim() 去除单词两边的空白，filter() 过滤所有长度小于3的单词，并只获取前100个单词，最后将其保存到 TreeSet 中。
        System.out.println(words);
    }
}
