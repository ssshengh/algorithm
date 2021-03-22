package com.javaLearing.chapter14;

// streams/Signal.java
import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public class Signal {
    private final String msg;

    public Signal(String msg) { this.msg = msg; }
    public String getMsg() { return msg; }

    @Override
    public String toString() {
        System.out.println("11111");
        return "Signal(" + msg + ")";
    }

    static Random rand = new Random(47);

    //静态构造器
    public static Signal morse() {
        switch(rand.nextInt(4)) {
            case 1: return new Signal("dot");
            case 2: return new Signal("dash");
            default: return null;
        }
    }

    public static Stream<Optional<Signal>> stream() {
        return Stream.generate(Signal::morse)
                .map(signal -> Optional.ofNullable(signal));//这里存入的不是对象，而是对象的toString方法
        //如果注销了toString，结果是Optional[com.javaLearing.chapter14.Signal@eed1f14]
    }
}

//当我们使用这个流的时候，必须要弄清楚如何解包 Optional。代码示例：
class StreamOfOptionals {
    public static void main(String[] args) {
        Signal.stream()
                .limit(10)
                .forEach(System.out::println);
        System.out.println(" ---");
        Signal.stream()
                .limit(10)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(System.out::println);
        //在这里，我们使用 filter() 来保留那些非空 Optional，然后在 map() 中使用 get() 获取元素。
        // 由于每种情况都需要定义“空值”的含义，所以通常我们要为每个应用程序采用不同的方法。
    }
}