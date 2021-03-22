package com.javaLearing.chapter20Q;

// generics/NotSelfBounded.java

public class NotSelfBounded<T> {
    T element;
    NotSelfBounded<T> set(T arg) {
        element = arg;
        return this;
    }
    T get() { return element; }
}

class A2 extends NotSelfBounded<A2> {}
class B2 extends NotSelfBounded<A2> {}

class C2 extends NotSelfBounded<C2> {
    C2 setAndGet(C2 arg) {
        set(arg);
        return get();
    }
}

class D2 {}
// Now this is OK:
class E2 extends NotSelfBounded<D2> {}//区分自限定的关键
//自限定中继承的泛型基类的参数类型必须是继承过基类的

//还可以把自限定用于方法
//可以防止这个方法被应用于除上述形式的自限定参数之外的任何事物上
class SelfBoundingMethods {
    static <T extends SelfBounded<T>> T f(T arg) {
        return arg.set(arg).get();
    }

    public static void main(String[] args) {
        A a = f(new A());
    }
}