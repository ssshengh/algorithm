package com.javaLearing.chapter20;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class ArrayMaker<T>{
    private Class<T> kind;//即使 kind 被存储为 Class<T>，擦除也意味着它实际被存储为没有任何参数的 Class。

    public ArrayMaker(Class<T> kind) {
        this.kind = kind;
    }

    //@SuppressWarnings("unchecked")
    T[] create(int size){
        //所以这里会警告，因为在使用它时，例如创建数组，Array.newInstance() 实际上并未拥有 kind 所蕴含的类型信息！！！！
        return (T[]) Array.newInstance(kind, size);
    }//对于在泛型中创建数组，使用 Array.newInstance() 是推荐的方式。!!!!!!!!!!!!!!!!!!!!!!
    public static void main(String[] args) {
        ArrayMaker<String> stringMaker = new ArrayMaker<>(String.class);
        String[] stringArray = stringMaker.create(9);
        System.out.println(Arrays.toString(stringArray));
    }

}
//如果我们创建一个集合而不是数组，情况就不同了
class ListMaker<T> {
    List<T> create() {
        return new ArrayList<>();//注意这里的<>内没有任何东西，没有T
        //在运行时，类内部没有任何 <T>，因此这看起来毫无意义。但是如果你遵从这种思路，并将这个表达式改为 new ArrayList()，编译器就会发出警告。
    }

    public static void main(String[] args) {
        ListMaker<String> stringMaker = new ListMaker<>();
        List<String> stringList = stringMaker.create();
    }
}//但是这种移除真的毫无意义吗？查看filledList.java

