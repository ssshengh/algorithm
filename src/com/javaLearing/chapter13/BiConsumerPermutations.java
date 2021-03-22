package com.javaLearing.chapter13;

// functional/BiConsumerPermutations.java

import java.util.function.*;

public class BiConsumerPermutations {
    /**
     * 这里使用 System.out.format() 来显示。它类似于 System.out.println() 但提供了更多的显示选项。
     * 这里，%f 表示我将 n 作为浮点值给出，%d 表示 n 是一个整数值。
     * 这其中可以包含空格，输入 %n 会换行 — 当然使用传统的 \n 也能换行，但 %n 是自动跨平台的，这是使用 format() 的另一个原因。
     * */
    static BiConsumer<Integer, Double> bicid = (i, d) ->
            System.out.format("%d, %f%n", i, d);
    static BiConsumer<Double, Integer> bicdi = (d, i) ->
            System.out.format("%d, %f%n", i, d);
    static BiConsumer<Integer, Long> bicil = (i, l) ->
            System.out.format("%d, %d%n", i, l);
    public static void main(String[] args) {
        bicid.accept(47, 11.34);
        bicdi.accept(22.45, 92);
        bicil.accept(1, 11L);
    }
    //上例只是简单使用了合适的包装类型，而装箱和拆箱负责它与基本类型之间的来回转换。
}



class FunctionWithWrapped {
    public static void main(String[] args) {
        Function<Integer, Double> fid = i -> (double)i;//参数Integer 返回值Double
        IntToDoubleFunction fid2 = i -> i;
        //如果没有强制转换，则会收到错误消息：“Integer cannot be converted to Double”（Integer 无法转换为 Double），
        // 而使用 IntToDoubleFunction 就没有此类问题。
        // IntToDoubleFunction 接口的源代码是这样的：
    }
}

//@FunctionalInterface
//public interface IntToDoubleFunction {
//    double applyAsDouble(int value);
//}
