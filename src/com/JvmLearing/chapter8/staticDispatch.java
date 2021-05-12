package com.JvmLearing.chapter8;

public class staticDispatch {
    static abstract class Human{}
    static class Man extends Human{}
    static class Woman extends Human {}
    public void sayHello(Human human){
        System.out.println("Hello! guy!");
    }
    public void sayHello(Man man){
        System.out.println("Hello, gentleman!");
    }
    public void sayHello(Woman woman){
        System.out.println("Hello, lady!");
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        staticDispatch staticDispatch = new staticDispatch();
        staticDispatch.sayHello(man);
        staticDispatch.sayHello(woman);
    }
}
