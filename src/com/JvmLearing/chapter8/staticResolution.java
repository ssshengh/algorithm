package com.JvmLearing.chapter8;

public class staticResolution {
    public static void sayHello(){
        System.out.println("hello");
    }
    public static void main(String[] args) {
        staticResolution.sayHello();
    }
}
