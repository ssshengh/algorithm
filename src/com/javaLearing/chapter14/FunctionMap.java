package com.javaLearing.chapter14;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

public class FunctionMap {
    static String[] elements = { "12", "", "23", "45" };
    static Stream<String> testStream(){
        return Arrays.stream(elements.clone());
    }

    static void test(String descr, Function<String, String> func) {
        System.out.println(" ---( " + descr + " )---");
        testStream()
                .map(func)//把一个字符串映射为另一个字符串
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        FunctionMap.testStream().forEach(System.out::println);

        test("add brackets", s -> "[" + s + "]");
        test("Increment", s -> {
                    try {
                        return Integer.parseInt(s) + 1 + "";
                    }//我们用 Integer.parseInt() 尝试将一个字符串转化为整数。如果字符串不能被转化成为整数就会抛出 NumberFormatException 异常，此时我们就回过头来把原始字符串放到输出流中。
                    catch(NumberFormatException e) {
                        return s;
                    }
                }
        );
        test("Replace", s -> s.replace("2", "9"));
        test("Take last digit", s -> s.length() > 0 ?
                s.charAt(s.length() - 1) + "" : s);
    }
}
