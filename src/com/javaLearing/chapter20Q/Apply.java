package com.javaLearing.chapter20Q;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Apply {
    public static <T, S extends Iterable<T>>
        void apply(S seq, Method f, Object... args){

        for (T t:seq) {
            try {
                f.invoke(t, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
/**
 * 在 Apply.java 中，异常被转换为 RuntimeException ，因为没有多少办法可以从这种异常中恢复——在这种情况下，它们实际上代表着程序员的错误。
 *
 * 为什么我们不只使用 Java 8 方法参考（稍后显示）而不是反射方法 f ？ 注意，invoke() 和 apply() 的优点是它们可以接受任意数量的参数。
 * 在某些情况下，灵活性可能至关重要。
 *
 * 为了测试 Apply ，我们首先创建一个 Shape 类：
 * */
class Shape {
    private static long counter = 0;
    private final long id = counter++;
    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + id;
    }
    public void rotate() {
        System.out.println(this + " rotate");
    }
    public void resize(int newSize) {
        System.out.println(this + " resize " + newSize);
    }
}
class Square extends Shape {}