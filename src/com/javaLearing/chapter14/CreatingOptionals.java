package com.javaLearing.chapter14;

// streams/CreatingOptionals.java
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
class CreatingOptionals {
    static void test(String testName, Optional<String> opt) {
        System.out.println(" === " + testName + " === ");
        System.out.println(opt.orElse("Null"));
    }


    public static void main(String[] args) {
        test("empty", Optional.empty());
        test("of", Optional.of("Howdy"));

        try {
            test("of", Optional.of(null));
        } catch(Exception e) {
            System.out.println(e);
        }
        //我们不能通过传递 null 到 of() 来创建 Optional 对象。最安全的方法是， 使用 ofNullable() 来优雅地处理 null。
        test("ofNullable", Optional.ofNullable("Hi"));
        test("ofNullable", Optional.ofNullable(null));
    }
}

