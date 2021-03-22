package com.javaLearing.chapter19;

//interface 关键字的一个重要目标就是允许程序员隔离组件，进而降低耦合度。
// 使用接口可以实现这一目标，但是通过类型信息，这种耦合性还是会传播出去——接口并不是对解耦的一种无懈可击的保障。
// 比如我们先写一个接口：


//然后实现这个接口，你可以看到其代码是怎么从实际类型开始顺藤摸瓜的：
class B implements A {
    public void f() {
    }

    public void g() {
    }
}

public class InterfaceViolation {
    public static void main(String[] args) {
        A a = new B();
        a.f();
        // a.g(); // Compile error
        System.out.println(a.getClass().getName());
        if (a instanceof B) {
            B b = (B) a;//转型为B
            b.g();
        }
    }
    //通过使用 RTTI，我们发现 a 是用 B 实现的。通过将其转型为 B，我们可以调用不在 A 中的方法。
    //
    //这样的操作完全是合情合理的，但是你也许并不想让客户端开发者这么做，因为这给了他们一个机会，使得他们的代码与你的代码的耦合度超过了你的预期。
    // 也就是说，你可能认为 interface 关键字正在保护你，但其实并没有。另外，在本例中使用 B 来实现 A 这种情况是有公开案例可查的[^3]。
}