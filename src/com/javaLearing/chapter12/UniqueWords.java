package com.javaLearing.chapter12;

// collections/UniqueWords.java
import java.util.*;
import java.nio.file.*;

public class UniqueWords {
    //读取文件单词
    public static void
    main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(
                Paths.get("/Users/shenheng/Code/src/com/javaLearing/chapter12/SetOperations.java"));
        Set<String> words = new TreeSet<>();
        for(String line : lines)
            for(String word : line.split("\\W+"))//这里使用正则表达式 \\ W + ，这意味着它会依据一个或多个（即 + ）非单词字母来拆分字符串（正则表达式将在字符串章节介绍）
                if(word.trim().length() > 0)
                    words.add(word);
        System.out.println(words);
    }
}
/* Output:
[A, B, C, Collections, D, E, F, G, H, HashSet, I, J, K, L, M, N, Output, Set, SetOperations, String, System, X, Y, Z, add, addAll, added, args, class, collections, contains, containsAll, false, from, import, in, java, main, new, out, println, public, remove, removeAll, removed, set1, set2, split, static, to, true, util, void]
*/

