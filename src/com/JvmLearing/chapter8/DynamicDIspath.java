package com.JvmLearing.chapter8;

public class DynamicDIspath {
    static abstract class Human{
        protected abstract void sayHello();
    }
    static class Man extends Human{
        @Override
        protected void sayHello() {
            System.out.println("Hello, Man!");
        }
    }
    static class Woman extends Human{
        @Override
        protected void sayHello() {
            System.out.println("Hello, lady!");
        }
    }
    public static void main(String[] args) {
        Human man = new Man(), woman = new Woman();
        man.sayHello();
        woman.sayHello();
        man = new Woman();
        man.sayHello();
    }

}
