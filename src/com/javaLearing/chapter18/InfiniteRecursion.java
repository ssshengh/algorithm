package com.javaLearing.chapter18;

import java.util.stream.Stream;

public class InfiniteRecursion {
    //public InfiniteRecursion(String i){}  显然，修改了方法签名就不可以了
    @Override
    public String toString() {
        //return " InfiniteRecursion address: " + this + "\n";
        return " InfiniteRecursion address: " + super.toString() + "\n";
    }

    public static void main(String[] args) {
        Stream.generate(InfiniteRecursion::new)//很关键的一点，这种方法默认继承了Supplier，因为其实就是签名一样
            .limit(10)//这里就是默认调用toString，而不是get了
            .forEach(System.out::println);
    }
}
//这里发生了自动类型转换，由 InfiniteRecursion 类型转换为 String 类型。
// 因为编译器发现一个 String 对象后面跟着一个 “+”，而 “+” 后面的对象不是 String，于是编译器试着将 this 转换成一个 String。
// 它怎么转换呢？正是通过调用 this 上的 toString() 方法，于是就发生了递归调用。
//
//如果你真的想要打印对象的内存地址，应该调用 Object.toString() 方法，这才是负责此任务的方法。
// 所以，不要使用 this，而是应该调用 super.toString() 方法。
