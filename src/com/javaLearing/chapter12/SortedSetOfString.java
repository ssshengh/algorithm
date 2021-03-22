package com.javaLearing.chapter12;

// collections/SortedSetOfString.java
import java.util.*;

public class SortedSetOfString {
    public static void main(String[] args) {
        Set<String> colors = new TreeSet<>();//与hashSet用散列表不同，TreeSet使用红黑树，可以完成排序
        for(int i = 0; i < 100; i++) {
            colors.add("Yellow");
            colors.add("Blue");
            colors.add("Red");
            colors.add("Red");
            colors.add("Orange");
            colors.add("Yellow");
            colors.add("Blue");
            colors.add("Purple");
        }
        System.out.println(colors);
    }
}
/* Output:
[Blue, Orange, Purple, Red, Yellow]
*/

