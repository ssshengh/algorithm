package com.javaLearing.chapter20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenericVarargs {
    //@SafeVarargs 注解保证我们不会对变长参数列表进行任何修改，这是正确的，因为我们只从中读取。如果没有此注解，编译器将无法知道这些并会发出警告
    //@SafeVarargs自于参数化可变参数类型的可能的堆污染
    @SafeVarargs
    public static <T> List<T> makeList(T... args){
        //此处显示的 makeList() 方法产生的功能与标准库的 java.util.Arrays.asList() 方法相同。
        List<T> list = new ArrayList<>(Arrays.asList(args));
        /*List<T> result = new ArrayList<>();
        for (T item : args)
            result.add(item);
            等价于这种写法*/
        return list;
    }

    public static void main(String[] args) {
        List<String> ls = makeList("A");
        System.out.println(ls);
        ls = makeList("A", "B", "C");
        System.out.println(ls);
        ls = makeList(
                "ABCDEFFHIJKLMNOPQRSTUVWXYZ".split(""));
        System.out.println(ls);
    }
}
