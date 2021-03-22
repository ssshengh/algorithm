package com.javaLearing.chapter20;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

//看这个类
public class FilledList<T> extends ArrayList<T> {
    FilledList(Supplier<T> gen, int size) {
        Suppliers.fill(this, gen, size);
    }

    public FilledList(T t, int size) {
        for (int i = 0; i < size; i++) {
            this.add(t);
        }
    }//即使编译器无法得知 add() 中的 T 的任何信息，但它仍可以在编译期确保你放入 FilledList 中的对象是 T 类型。
    // 因此，即使擦除移除了方法或类中的实际类型的信息，编译器仍可以确保方法或类中使用的类型的内部一致性。

    public static void main(String[] args) {
        List<String> list = new FilledList<>("Hello", 4);//编译期不知道这个T到底是一个String还是一个lambda
        //但是编译期确保了其放入后，类中的类型是一致的，可以正确的匹配到对应的方法
        System.out.println(list);
        // Supplier version:
        List<Integer> ilist = new FilledList<>(() -> 47, 4);
        System.out.println(ilist);
    }
}
//这里的意思就是，不管如何，编译器就算不知道T这个类型，就算这个类型被擦除，只剩下一个基本类型
//但是他可以确保你放入的T是正确的