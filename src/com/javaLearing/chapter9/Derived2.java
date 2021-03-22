package com.javaLearing.chapter9;

// polymorphism/PrivateOverride2.java
// Detecting a mistaken override using @Override
// {WillNotCompile}


class PrivateOverride2 {
    private void f() {
        System.out.println("private f()");
    }

    public void print(){
        System.out.println("yes! in father");
    }

    public void OnlyFa(){
        System.out.println("基类独有方法，继承类对象可以访问");
    }
    public static void main(String[] args) {
        PrivateOverride2 po = new Derived2();
        po.f();
        po.print();
        ((Derived2)po).draw();
        po.OnlyFa();
        Derived2 p1 = new Derived2();
        p1.OnlyFa();
    }
}

public class Derived2 extends PrivateOverride2 {

    public void f() {
        System.out.println("public f()");
    }
    public void print(){
        System.out.println("yes!");
    }
    public void draw(){
        System.out.println("俺需要被转换");
    }
}
