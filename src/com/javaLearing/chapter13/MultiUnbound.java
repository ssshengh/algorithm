package com.javaLearing.chapter13;

// functional/MultiUnbound.java

// 未绑定的方法与多参数的结合运用
// 需要指出的是，我将类命名为 This，并将函数式方法的第一个参数命名为 athis，但你在生产级代码中应该使用其他名字，以防止混淆。
class This {
    void two(int i, double d) {}
    void three(int i, double d, String s) {}
    void four(int i, double d, String s, char c) {}
}

interface TwoArgs {
    void call2(This athis, int i, double d);
}

interface ThreeArgs {
    void call3(This athis, int i, double d, String s);
}

interface FourArgs {
    void call4(
            This athis, int i, double d, String s, char c);
}

public class MultiUnbound {
    public static void main(String[] args) {
        TwoArgs twoargs = This::two;
        ThreeArgs threeargs = This::three;
        FourArgs fourargs = This::four;
        This athis = new This();
        // 这里绑定了对象中多个方法中的其中一个，可以看出，athis这个对象的引用传递进入未绑定方法引用绑定的方法内时
        // 实际上是，传入了一个this代表This这个类，而不是传入了athis代表的这个引用指向的独特的唯一对象
        // 因此就可以通过后面的参数列表进行匹配
        twoargs.call2(athis, 11, 3.14);
        threeargs.call3(athis, 11, 3.14, "Three");
        fourargs.call4(athis, 11, 3.14, "Four", 'Z');
    }
}

