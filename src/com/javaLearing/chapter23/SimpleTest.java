package com.javaLearing.chapter23;
// annotations/simplest/SimpleTest.java
// Test the "Simple" annotation
// {java annotations.simplest.SimpleTest}

//@Target 声明了几乎所有的目标类型（除了 PACKAGE） ，同样是为了演示。下面是一个测试示例。
@Simple
public class SimpleTest {
    @Simple
    int i;
    @Simple
    public SimpleTest() {}
    @Simple
    public void foo() {
        System.out.println("SimpleTest.foo()");
    }
    @Simple
    public void bar(String s, int i, float f) {
        System.out.println("SimpleTest.bar()");
    }
    @Simple
    public static void main(String[] args) {
        @Simple
        SimpleTest st = new SimpleTest();
        st.foo();
    }
}

