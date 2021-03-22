package com.javaLearing.chapter20p;

// generics/UnboundedWildcards1.java
import java.util.*;

public class UnboundedWildcards1 {
    static List list1;
    static List<?> list2;
    static List<? extends Object> list3;

    static void assign1(List list) {//不能把普通list赋值给包含所有类型的泛型
        list1 = list;
        list2 = list;
        //- list3 = list;
        // warning: [unchecked] unchecked conversion
        // list3 = list;
        //         ^
        // required: List<? extends Object>
        // found:    List
    }

/**
 * 有很多情况都和你在这里看到的情况类似，即编译器很少关心使用的是原生类型还是 <?> 。
 * 在这些情况中，<?> 可以被认为是一种装饰，但是它仍旧是很有价值的，因为，实际上它是在声明：“我是想用 Java 的泛型来编写这段代码，
 * 我在这里并不是要用原生类型，但是在当前这种情况下，泛型参数可以持有任何类型。”
 * */
    static void assign2(List<?> list) {//但可以反过来
        list1 = list;
        list2 = list;
        list3 = list;
    }

    static void assign3(List<? extends Object> list) {
        list1 = list;
        list2 = list;
        list3 = list;
    }

    public static void main(String[] args) {
        assign1(new ArrayList());
        assign2(new ArrayList());
        //- assign3(new ArrayList());
        // warning: [unchecked] unchecked method invocation:
        // method assign3 in class UnboundedWildcards1
        // is applied to given types
        // assign3(new ArrayList());
        //        ^
        // required: List<? extends Object>
        // found: ArrayList
        // warning: [unchecked] unchecked conversion
        // assign3(new ArrayList());
        //         ^
        // required: List<? extends Object>
        // found:    ArrayList
        // 2 warnings
        assign1(new ArrayList<>());
        assign2(new ArrayList<>());
        assign3(new ArrayList<>());
        // Both forms are acceptable as List<?>:
        List<?> wildList = new ArrayList();
        wildList = new ArrayList<>();
        assign1(wildList);
        assign2(wildList);
        assign3(wildList);
    }
}
