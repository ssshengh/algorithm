package com.JvmLearing.chapter7;

public class NotInit {
    public static void main(String[] args) {
        //第三种被动引用：
        System.out.println(ConstClass.HELLO);
        //注意看：我直接通过子类调用父类的静态字段，只会初始化父类，没有初始化子类
//        System.out.println(SubClass.value);
        //第二个通过数组定义来引用类，也不会初始化：
        SuperClass[] superClasses = new SuperClass[10];


    }
}
