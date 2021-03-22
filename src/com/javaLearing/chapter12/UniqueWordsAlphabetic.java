package com.javaLearing.chapter12;

// collections/UniqueWordsAlphabetic.java
// Producing an alphabetic listing
import java.util.*;
import java.nio.file.*;

public class UniqueWordsAlphabetic {
    public static void
    main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(
                Paths.get("/Users/shenheng/Code/src/com/javaLearing/chapter12/SetOperations.java"));
        Set<String> words =
                new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for(String line : lines)
            for(String word : line.split("\\W+"))
                if(word.trim().length() > 0)
                    words.add(word);
        System.out.println(words);
    }
}
/* Output:
[A, add, addAll, added, args, B, C, class, collections, contains, containsAll, D, E, F, false, from, G, H, HashSet, I, import, in, J, java, K, L, M, main, N, new, out, Output, println, public, remove, removeAll, removed, Set, set1, set2, SetOperations, split, static, String, System, to, true, util, void, X, Y, Z]
*/

