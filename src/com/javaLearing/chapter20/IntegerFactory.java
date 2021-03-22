package com.javaLearing.chapter20;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

//IntegerFactory 本身就是通过实现 Supplier<Integer> 的工厂。
public class IntegerFactory implements Supplier<Integer> {
    private int i = 0;

    @Override
    public Integer get() {
        return ++i;
    }
}



// 还要注意，Fudge 并没有做任何类似于工厂的操作，并且传递 Fudge::new 仍然会产生工厂行为，因为编译器将对函数方法 ::new 的调用转换为对 get() 的调用。
class Fudge {
    private static int count = 1;
    private int n = count++;

    @Override
    public String toString() {
        return "Fudge " + n;
    }
}


class Foo2<T> {
    private List<T> x = new ArrayList<>();

    Foo2(Supplier<T> factory) {
        Suppliers.fill(x, factory, 5);
    }

    @Override
    public String toString() {
        return x.toString();
    }
}

class FactoryConstraint {
    public static void main(String[] args) {
        System.out.println(
                new Foo2<>(new IntegerFactory()));
        System.out.println(
                new Foo2<>(new Widget.Factory()));
        System.out.println(
                new Foo2<>(Fudge::new));//产生了工厂行为：创建多个对象
    }
}