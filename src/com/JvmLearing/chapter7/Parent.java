package com.JvmLearing.chapter7;

public class Parent {
    public static int A = 1;
    static {
        A = 2;
    }
}
class sub extends Parent{
    public static int B = A;

    public static void main(String[] args) {
        System.out.println(sub.B);
    }
}
class Test{
    static {
        i = 0;//可以被正常编译
        //System.out.println(i);//但是会提示非法向前引用
    }
    static int i = 1;
}
