package com.javaLearing.chapter22;

import java.util.Random;
import java.util.function.Supplier;

enum CartoonCharacter implements Supplier<CartoonCharacter>{
    SLAPPY, SPANKY, PUNCHY,
    SILLY, BOUNCY, NUTTY, BOB;
    Random random = new Random(47);

    @Override
    public CartoonCharacter get() {
        return values()[random.nextInt(values().length)];
    }
}
public class EnumImplementation {
    public static <T> void printNext(Supplier<T> supplier){
        System.out.print(supplier.get() + ", ");
    }

    public static void main(String[] args) {
        CartoonCharacter cc = CartoonCharacter.BOB;
        for (int i =0; i<10; i++)
            printNext(cc);//必须有个Supplier传递进去
    }
    //这个结果有点奇怪，不过你必须要有一个 enum 实例才能调用其上的方法。
    // 现在，在任何接受 Supplier 参数的方法中，例如 printNext()，都可以使用 CartoonCharacter。
}
