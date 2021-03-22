package com.javaLearing.chapter20Q;
// generics/CovariantReturnTypes.java

class Base {}
class Derived extends Base {}

interface OrdinaryGetter {
    Base get();
}

interface DerivedGetter extends OrdinaryGetter {
    // Overridden method return type can vary:
    @Override
    Derived get();
}
//DerivedGetter 中的 get() 方法覆盖了 OrdinaryGetter 中的 get() ，并返回了一个从 OrdinaryGetter.get() 的返回类型中导出的类型。
// 尽管这是完全合乎逻辑的事情（导出类方法应该能够返回比它覆盖的基类方法更具体的类型）但是这在早先的 Java 版本中是不合法的。

public class CovariantReturnTypes {
    void test(DerivedGetter d) {
        Derived d2 = d.get();
    }
}

