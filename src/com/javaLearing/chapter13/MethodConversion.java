package com.javaLearing.chapter13;

// functional/MethodConversion.java

import java.util.function.*;

class In1 {}
class In2 {}

public class MethodConversion {
    static void accept(In1 i1, In2 i2) {
        System.out.println("accept()");
    }
    static void someOtherName(In1 i1, In2 i2) {
        System.out.println("someOtherName()");
    }
    public static void main(String[] args) {
        BiConsumer<In1,In2> bic;

        bic = MethodConversion::accept;
        bic.accept(new In1(), new In2());

        bic = MethodConversion::someOtherName;
        // bic.someOtherName(new In1(), new In2()); // Nope，关键在于方法引用绑定方法，是绑定在接口函数上，而不是用来绑定的方法
        // 因此调用的时候因该是调用函数接口，但是绑定上去的方法就作为该方法引用的方法了
        bic.accept(new In1(), new In2());
        //查看 BiConsumer 的文档，你会看到它的函数式方法为 accept() 。
        // 的确，如果我们将方法命名为 accept()，它就可以作为方法引用。
        // 但是我们也可用不同的名称，比如 someOtherName()。只要参数类型、返回类型与 BiConsumer 的 accept() 相同即可。
    }
}

