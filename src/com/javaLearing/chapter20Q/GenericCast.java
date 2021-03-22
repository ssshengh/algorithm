package com.javaLearing.chapter20Q;

import com.javaLearing.chapter20.Widget;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.*;
import java.util.stream.Stream;

class FixedSizeStack<T>{
    private final int size;
    private Object[] storage;
    private int index = 0;
    FixedSizeStack(int size) {
        this.size = size;
        storage = new Object[size];
    }

    public void push(T item) {
        if(index < size)
            storage[index++] = item;
    }

    /**
     * 如果没有 @SuppressWarnings 注解，编译器将对 pop() 产生 “unchecked cast” 警告。
     * 由于擦除的原因，编译器无法知道这个转型是否是安全的，并且 pop() 方法实际上并没有执行任何转型。
     * 这是因为，T 被擦除到它的第一个边界，默认情况下是 Object ，
     * 因此 pop() 实际上只是将 Object 转型为 Object
     * */
    @SuppressWarnings("unchecked")
    public T pop() {
        return index == 0 ? null : (T)storage[--index];
    }

    @SuppressWarnings("unchecked")
    Stream<T> stream() {
        return (Stream<T>) Arrays.stream(storage);
    }
}

public class GenericCast {
    static String[] letters = "ABCDEFGHIJKLMNOPQRS".split("");

    public static void main(String[] args) {
        FixedSizeStack<String> strings =
                new FixedSizeStack<>(letters.length);
        Arrays.stream("ABCDEFGHIJKLMNOPQRS".split(""))
                .forEach(strings::push);//只需要有一个输入的方法引用就行
        System.out.println(strings.pop());
        strings.stream()
                .map(s -> s + " ")
                .forEach(System.out::print);
    }
}

class NeedCasting {
    @SuppressWarnings("unchecked")
    public void f(String[] args) throws Exception {
        ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(args[0]));
        ///readObject() 无法知道它正在读取的是什么，因此它返回的是必须转型的对象。
        List<Widget> shapes = (List<Widget>)in.readObject();
    }
}
//当注释掉 @SuppressWarnings 注解并编译这个程序时，就会得到下面的警告。
// And if you follow the instructions and recompile with  -
//Xlint:unchecked :(如果遵循这条指示，使用-Xlint:unchecked来重新编译：)

//你会被强制要求转型，但是又被告知不应该转型。为了解决这个问题，必须使用 Java 5 引入的新的转型形式，即通过泛型类来转型：
class ClassCasting {
    @SuppressWarnings("unchecked")
    public void f(String[] args) throws Exception {
        ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(args[0]));
        // Won't Compile:
        //    List<Widget> lw1 =
        //    List<>.class.cast(in.readObject());
        List<Widget> lw2 = List.class.cast(in.readObject());
        //但是，不能转型到实际类型（ List<Widget> ）。也就是说，不能声明：
        //List<Widget>.class.cast(in.readobject())
        //甚至当你添加一个像下面这样的另一个转型时：(List<Widget>)List.class.cast(in.readobject())
        //也会被警告
    }
}