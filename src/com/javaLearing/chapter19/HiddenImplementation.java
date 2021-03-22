package com.javaLearing.chapter19;

// typeinfo/HiddenImplementation.java
// Sneaking around package hiding

import typeinfo.packageaccess.*;

import java.lang.reflect.*;

public class HiddenImplementation {
    public static void main(String[] args) throws Exception {
        A a = HiddenC.makeA();//得到一个C的对象
        a.f();
        System.out.println(a.getClass().getName());

        //这样就无法转型为C，实现了解耦
        // Compile error: cannot find symbol 'C':
        /* if(a instanceof C) {
            C c = (C)a;
            c.g();
        } */


        //正如你所看到的，通过使用反射，仍然可以调用所有方法，甚至是 private 方法！如果知道方法名，你
        // 就可以在其 Method 对象上调用 setAccessible(true)，就像在 callHiddenMethod() 中看到的那样。
        // Oops! Reflection still allows us to call g():
        callHiddenMethod(a, "g");
        // And even less accessible methods!
        callHiddenMethod(a, "u");
        callHiddenMethod(a, "v");
        callHiddenMethod(a, "w");
    }

    static void callHiddenMethod(Object a, String methodName) throws Exception {
        Method g = a.getClass().getDeclaredMethod(methodName);
        g.setAccessible(true);
        g.invoke(a);
    }
}

