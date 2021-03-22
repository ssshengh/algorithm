// onjava/Stack.java
// A Stack class built with an ArrayDeque
package onjava;
import java.util.Deque;
import java.util.ArrayDeque;


/**
 * 这里引入了使用泛型的类定义的最简单的可能示例。类名称后面的 <T> 告诉编译器这是一个参数化类型，而其中的类型参数 T 会在使用类时被实际类型替换。
 * 基本上，这个类是在声明“我们在定义一个可以持有 T 类型对象的 Stack 。”
 * Stack 是使用 ArrayDeque 实现的，而 ArrayDeque 也被告知它将持有 T 类型对象。
 * 注意， push() 接受类型为 T 的对象，而 peek() 和 pop() 返回类型为 T 的对象。
 * peek() 方法将返回栈顶元素，但并不将其从栈顶删除，而 pop() 删除并返回顶部元素。
 *
 * 如果只需要栈的行为，那么使用继承是不合适的，因为这将产生一个具有 ArrayDeque 的其它所有方法的类
 * （在附录：集合主题中将会看到， Java 1.0 设计者在创建 java.util.Stack 时，就犯了这个错误）。使用组合，可以选择要公开的方法以及如何命名它们。
 * */
public class Stack<T> {
    private Deque<T> storage = new ArrayDeque<>();
    public void push(T v) { storage.push(v); }
    public T peek() { return storage.peek(); }
    public T pop() { return storage.pop(); }
    public boolean isEmpty() { return storage.isEmpty(); }
    @Override
    public String toString() {
        return storage.toString();
    }
}

