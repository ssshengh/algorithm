package com.javaLearing.chapter12;

// collections/AddingGroups.java
// Adding groups of elements to Collection objects
import java.util.*;

public class AddingGroups {
    public static void main(String[] args) {
        Collection<Integer> collection =
                new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));//注意初始化时放的位置
        Integer[] moreInts = { 6, 7, 8, 9, 10 };
        //collection可以接收另一个collection用以初始化

        //但是addAll运行更快
        collection.addAll(Arrays.asList(moreInts));

        //注意，这里多了个s，是collections
        // Runs significantly faster, but you can't
        // construct a Collection this way:
        Collections.addAll(collection, 11, 12, 13, 14, 15);
        Collections.addAll(collection, moreInts);


        // Produces a list "backed by" an array:
        List<Integer> list = Arrays.asList(16,17,18,19,20);

        //修改元素
        list.set(1, 99); // OK -- modify an element
        // list.add(21); // Runtime error; the underlying
        // array cannot be resized.
    }
}

