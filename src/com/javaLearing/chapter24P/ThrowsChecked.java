package com.javaLearing.chapter24P;

// concurrent/ThrowsChecked.java
import java.util.stream.*;
import java.util.concurrent.*;

public class ThrowsChecked {
    class Checked extends Exception {}

    static ThrowsChecked nochecked(ThrowsChecked tc) {
        return tc;
    }

    static ThrowsChecked withchecked(ThrowsChecked tc) throws Checked {
        return tc;
    }

    static void testStream() {
        Stream.of(new ThrowsChecked())
                .map(ThrowsChecked::nochecked)
                // .map(ThrowsChecked::withchecked); // [1]
                .map(
                        tc -> {
                            try {
                                return withchecked(tc);
                            } catch (Checked e) {
                                throw new RuntimeException(e);
                            }
                        });
    }
//如果你试图像使用 nochecked() 那样使用 withchecked() 的方法引用，编译器会在 [1] 和 [2] 中报错。
// 相反，你必须写出 lambda 表达式 (或者编写一个不会抛出异常的包装器方法)。
    static void testCompletableFuture() {
        CompletableFuture
                .completedFuture(new ThrowsChecked())
                .thenApply(ThrowsChecked::nochecked)
                // .thenApply(ThrowsChecked::withchecked); // [2]
                .thenApply(
                        tc -> {
                            try {
                                return withchecked(tc);
                            } catch (Checked e) {
                                throw new RuntimeException(e);
                            }
                        });
    }
}

