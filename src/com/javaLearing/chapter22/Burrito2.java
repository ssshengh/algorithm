package com.javaLearing.chapter22;

import static com.javaLearing.chapter22.SpicinessEnum.*;
public class Burrito2 {
    SpicinessEnum degree;
    public Burrito2(SpicinessEnum degree){this.degree = degree;}
    @Override
    public String toString() {
        return "Burrito is "+ degree;
    }

    public static void main(String[] args) {
        System.out.println(new Burrito2(NOT));//静态导入时的用法
        System.out.println(new Burrito2(MEDIUM));
        System.out.println(new Burrito2(HOT));
    }
}
