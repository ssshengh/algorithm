package com.javaLearing.chapter20;

public class GenericMethods {
    //尽管可以同时对类及其方法进行参数化，但这里未将 GenericMethods 类参数化。只有方法 f() 具有类型参数，该参数由方法返回类型之前的参数列表指示
    public <T> void f(T x){
        System.out.println(x.getClass().getName());
    }

    public static void main(String[] args) {
        GenericMethods gs = new GenericMethods();
        gs.f(" ");
        gs.f(1);
        gs.f(1.0);//自动装箱了
        gs.f(1.0f);
        gs.f('c');
        gs.f(gs);
    }
}
