package com.javaLearing.chapter19;

// typeinfo/FamilyVsExactType.java
// instanceof 与 class 的差别
// {java typeinfo.FamilyVsExactType}

class Base {}
class Derived extends Base {}

public class FamilyVsExactType {
    static void test(Object x) {
        System.out.println(
                "Testing x of type " + x.getClass());
        System.out.println(
                "x instanceof Base " + (x instanceof Base));
        System.out.println(
                "x instanceof Derived " + (x instanceof Derived));
        System.out.println(
                "Base.isInstance(x) " + Base.class.isInstance(x));
        System.out.println(
                "Derived.isInstance(x) " +
                        Derived.class.isInstance(x));
        System.out.println(
                "x.getClass() == Base.class " +
                        (x.getClass() == Base.class));
        System.out.println(
                "x.getClass() == Derived.class " +
                        (x.getClass() == Derived.class));
        System.out.println(
                "x.getClass().equals(Base.class)) "+
                        (x.getClass().equals(Base.class)));
        System.out.println(
                "x.getClass().equals(Derived.class)) " +
                        (x.getClass().equals(Derived.class)));
    }

    public static void main(String[] args) {
        test(new Base());
        test(new Derived());
    }
    //令人放心的是，instanceof 和 isInstance() 产生的结果相同， equals() 和 == 产生的结果也相同。
    // 但测试本身得出了不同的结论。与类型的概念一致，instanceof 说的是“你是这个类，还是从这个类派生的类？”。而如果使用 == 比较实际的Class 对象，则与继承无关 —— 它要么是确切的类型，要么不是。
}
