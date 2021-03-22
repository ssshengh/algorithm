package com.javaLearing.chapter20Q;

import java.util.Arrays;
import java.util.function.*;
import java.util.function.Supplier;

interface FillArray {
    static <T> T[] fill(T[] a, Supplier<T> gen){
        Arrays.setAll(a, n -> gen.get());
        return a;
    }

    static int[] fill(int[] a, IntSupplier gen) {
        Arrays.setAll(a, n -> gen.getAsInt());
        return a;
    }

    static long[] fill(long[] a, LongSupplier gen) {
        Arrays.setAll(a, n -> gen.getAsLong());
        return a;
    }

    static double[] fill(double[] a, DoubleSupplier gen) {
        Arrays.setAll(a, n -> gen.getAsDouble());
        return a;
    }

}

/**
 * 自动装箱不适用于数组，因此我们必须创建 FillArray.fill() 的重载版本，或创建产生 Wrapped 输出的生成器。
 * FillArray 仅比 java.util.Arrays.setAll() 有用一点，因为它返回填充的数组。
 * */
//public class PrimitiveGenericTest {
//    public static void main(String[] args) {
//        String[] strings = FillArray.fill(
//                new String[5], new Rand.String(9));
//        System.out.println(Arrays.toString(strings));
//        int[] integers = FillArray.fill(
//                new int[9], new Rand.Pint());
//        System.out.println(Arrays.toString(integers));
//    }
//}
