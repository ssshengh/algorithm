package com.javaLearing.chapter13;

// functional/UnboundMethodReference.java

// 没有方法引用的对象

class X {
    String f() { return "X::f()"; }
}

interface MakeString {
    String make();
}

interface TransformX {
    String transform(X x);
}

public class UnboundMethodReference {
    public static void main(String[] args) {
        /**
         * 在 [1] 中，我们尝试同样的做法，把 X 的 f() 方法引用赋值给 MakeString。
         * 结果即使 make() 与 f() 具有相同的签名，编译也会报“invalid method reference”（无效方法引用）错误。
         * 问题在于，这里其实还需要另一个隐藏参数参与：我们的老朋友 this。 你不能在没有 X 对象的前提下调用 f()。
         * 因此，X :: f 表示未绑定的方法引用，因为它尚未“绑定”到对象。
         *
         * 要解决这个问题，我们需要一个 X 对象，因此我们的接口实际上需要一个额外的参数，正如在 TransformX 中看到的那样。
         * 如果将 X :: f 赋值给 TransformX，在 Java 中是允许的。
         * 我们必须做第二个心理调整——使用未绑定的引用时，函数式方法的签名（接口中的单个方法）不再与方法引用的签名完全匹配。 ！！！！！！！！
         * 原因是：你需要一个对象来调用方法。
         * */
        // MakeString ms = X::f; // [1]
        TransformX sp = X::f;
        X x = new X();
        System.out.println(sp.transform(x)); // [2]的结果有点像脑筋急转弯。
        // 我拿到未绑定的方法引用，并且调用它的transform()方法，将一个X类的对象传递给它，最后使得 x.f() 以某种方式被调用。
        // Java知道它必须拿第一个参数，该参数实际就是this 对象，然后对此调用方法。
        // 这里的这个第一个参数就是X类型的对象，它作为this来调用了方法，所以尽管绑定的时候没有对象，
        // 但是定义了一个对象X x后可以通过第一个参数调用，绑定的X对象的方法
        System.out.println(x.f()); // 同等效果
    }
}

