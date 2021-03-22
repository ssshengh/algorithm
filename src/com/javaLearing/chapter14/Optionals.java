package com.javaLearing.chapter14;

// streams/Optionals.java
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
public class Optionals {
    static void basics(Optional<String> optString) {
        if(optString.isPresent())
            System.out.println(optString.get());
        else
            System.out.println("Nothing inside!");
    }

    //ifPresent(Consumer)：当值存在时调用 Consumer，否则什么也不做。
    static void ifPresent(Optional<String> optString) {
        optString.ifPresent(System.out::println);
    }

    //orElse(otherObject)：如果值存在则直接返回，否则生成 otherObject。
    static void orElse(Optional<String> optString) {
        System.out.println(optString.orElse("Nada"));
    }

    //orElseGet(Supplier)：如果值存在则直接返回，否则使用 Supplier 函数生成一个可替代对象。
    static void orElseGet(Optional<String> optString) {
        System.out.println(
                optString.orElseGet(() -> "Generated"));
    }

    //orElseThrow(Supplier)：如果值存在直接返回，否则使用 Supplier 函数生成一个异常。
    static void orElseThrow(Optional<String> optString) {
        try {
            System.out.println(optString.orElseThrow(
                    () -> new Exception("Supplied")));
        } catch(Exception e) {
            System.out.println("Caught " + e);
        }
    }
    static void test(String testName, Consumer<Optional<String>> cos) {
        //这个函数接口接收一个Optional<String>对象，不反回结果
        System.out.println(" === " + testName + " === ");
        cos.accept(Stream.of("Epithets").findFirst());//accept内的参数被获取，输入cos方法中
        cos.accept(Stream.<String>empty().findFirst());
    }


    public static void main(String[] args) {
        test("basics", Optionals::basics);
        test("ifPresent", Optionals::ifPresent);
        test("orElse", Optionals::orElse);
        test("orElseGet", Optionals::orElseGet);
        test("orElseThrow", Optionals::orElseThrow);
    }
}

