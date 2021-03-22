package com.javaLearing.chapter16;

import java.util.ArrayList;

public class CountedList extends ArrayList<String> {
    private static int counter = 0;//静态的，类的属性
    private int id = counter++;
    public CountedList() {
        System.out.println("CountedList #" + id);
    }
    public int getId() { return id; }
}
