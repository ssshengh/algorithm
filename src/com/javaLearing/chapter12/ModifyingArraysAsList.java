package com.javaLearing.chapter12;

// collections/ModifyingArraysAsList.java
import java.util.*;

public class ModifyingArraysAsList {
    public static void main(String[] args) {
        Random rand = new Random(47);
        Integer[] ia = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        List<Integer> list1 =
                new ArrayList<>(Arrays.asList(ia));
        System.out.println("Before shuffling: " + list1);
        Collections.shuffle(list1, rand);
        System.out.println("After shuffling: " + list1);
        System.out.println("array: " + Arrays.toString(ia));
        //关键在此，他只是打乱了list1中的引用，并没有修改原始数组！！！！！
        //与下面的区别是，这里使用的是ArrayList的构造器完成，直接将一个List返回给LIst引用

        List<Integer> list2 = Arrays.asList(ia);
        System.out.println("Before shuffling: " + list2);
        Collections.shuffle(list2, rand);
        System.out.println("After shuffling: " + list2);
        System.out.println("array: " + Arrays.toString(ia));
        //如果直接使用 Arrays.asList(ia) 的结果，这种打乱就会修改 ia 的顺序。
        // 重要的是要注意 Arrays.asList() 生成一个 List 对象，该对象使用底层数组作为其物理实现。
        // 如果对 List 对象做了任何修改，又不想让原始数组被修改，那么就应该在另一个集合中创建一个副本。
    }
}
/* Output:
Before shuffling: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
After shuffling: [4, 6, 3, 1, 8, 7, 2, 5, 10, 9]
array: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
Before shuffling: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
After shuffling: [9, 1, 6, 3, 7, 2, 5, 10, 4, 8]
array: [9, 1, 6, 3, 7, 2, 5, 10, 4, 8]
*/

