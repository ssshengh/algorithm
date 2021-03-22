package com.javaLearing.chapter14;

// streams/Reduce.java
import java.util.*;
import java.util.stream.*;
class Frobnitz {
    int size;
    Frobnitz(int sz) { size = sz; }
    @Override
    public String toString() {
        return "Frobnitz(" + size + ")";
    }
    // Generator:
    static Random rand = new Random(47);
    static final int BOUND = 100;
    static Frobnitz supply() {
        return new Frobnitz(rand.nextInt(BOUND));
    }
    //Frobnitz 包含一个可生成自身的生成器 supply() ；
    // 因为 supply() 方法作为一个 Supplier<Frobnitz> 是签名兼容的，我们可以把 supply() 作为一个方法引用传递给 Stream.generate()
    // （这种签名兼容性被称作结构一致性）。(无参数，返回类型任意)
}
public class Reduce {
    public static void main(String[] args) {
        Stream.generate(Frobnitz::supply)//结构一致性
                .limit(10)
                .peek(System.out::println)
                .reduce((fr0, fr1) -> fr0.size < 50 ? fr0 : fr1)
                //我们使用了没有“初始值”作为第一个参数的 reduce()方法，所以产生的结果是 Optional 类型。
                //Lambda 表达式中的第一个参数 fr0 是 reduce() 中上一次调用的结果。而第二个参数 fr1 是从流传递过来的值。
                .ifPresent(System.out::println);
                //Optional.ifPresent() 方法只有在结果非空的时候才会调用 Consumer<Frobnitz>
        // （println 方法可以被调用是因为 Frobnitz 可以通过 toString() 方法转换成 String）。
    }
}

