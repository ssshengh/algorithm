package com.javaLearing.chapter14;

// streams/OptionalFilter.java
import java.util.*;
import java.util.stream.*;
import java.util.function.*;
class OptionalFilter {

    static String[] elements = {
            "Foo", "", "Bar", "Baz", "Bingo"
    };

    static Stream<String> testStream() {
        return Arrays.stream(elements);
    }

    static void test(String descr, Predicate<String> pred) {
        System.out.println(" ---( " + descr + " )---");
        for(int i = 0; i <= elements.length; i++) {
            //注意，不同于普通 for 循环，这里的索引值范围并不是 i < elements.length， 而是 i <= elements.length。
            // 所以最后一个元素实际上超出了流。方便的是，这将自动成为 Optional.empty，你可以在每一个测试的结尾中看到。
            System.out.println(
                    testStream()
                            .skip(i)
                            .findFirst()
                            .filter(pred));
            //filter(Predicate)：对 Optional 中的内容应用Predicate 并将结果返回。
            // 如果 Optional 不满足 Predicate ，将 Optional 转化为空 Optional 。如果 Optional 已经为空，则直接返回空Optional 。
        }
    }
    public static void main(String[] args) {
        System.out.println("static test:");
        OptionalFilter.testStream().forEach(System.out::println);

        test("true", str -> true);
        test("false", str -> false);
        test("str != \"\"", str -> str != "");
        test("str.length() == 3", str -> str.length() == 3);
        test("startsWith(\"B\")",
                str -> str.startsWith("B"));
    }
    //即使输出看起来像流，要特别注意 test() 中的 for 循环。
    // 每一次的for循环都重新启动流，然后跳过for循环索引指定的数量的元素，这就是流只剩后续元素的原因。
    // 然后调用findFirst() 获取剩余元素中的第一个元素，并包装在一个 Optional对象中。
}

class OptionalMap {
    static String[] elements = {"12", "", "23", "45"};

    static Stream<String> testStream() {
        return Arrays.stream(elements);
    }

    static void test(String descr, Function<String, String> func) {
        System.out.println(" ---( " + descr + " )---");
        for (int i = 0; i <= elements.length; i++) {
            System.out.println(
                    testStream()
                            .skip(i)
                            .findFirst() // Produces an Optional
                            .map(func));//注意到返回时，被包装为了Operation
        }
    }

    public static void main(String[] args) {
        // If Optional is not empty, map() first extracts
        // the contents which it then passes
        // to the function:
        test("Add brackets", s -> "[" + s + "]");
        test("Increment", s -> {
            try {
                return Integer.parseInt(s) + 1 + "";
            } catch (NumberFormatException e) {
                return s;
            }
        });
        test("Replace", s -> s.replace("2", "9"));
        test("Take last digit", s -> s.length() > 0 ?
                s.charAt(s.length() - 1) + "" : s);
    }
    // After the function is finished, map() wraps the
    // result in an Optional before returning it:
    //映射函数的返回结果会自动包装成为 Optional。Optional.empty 会被直接跳过。
}

class OptionalFlatMap {
    static String[] elements = {"12", "", "23", "45"};

    static Stream<String> testStream() {
        return Arrays.stream(elements);
    }

    static void test(String descr,
                     Function<String, Optional<String>> func) {
        System.out.println(" ---( " + descr + " )---");
        for (int i = 0; i <= elements.length; i++) {
            System.out.println(
                    testStream()
                            .skip(i)
                            .findFirst()
                            .flatMap(func));
            //唯一的区别就是 flatMap() 不会把结果包装在 Optional 中，因为映射函数已经被包装过了。
            // 在如上示例中，我们已经在每一个映射函数中显式地完成了包装，但是很显然 Optional.flatMap() 是为那些自己已经生成 Optional 的函数而设计的。
        }
    }

    public static void main(String[] args) {
        test("Add brackets",
                s -> Optional.of("[" + s + "]"));
        test("Increment", s -> {
            try {
                return Optional.of(
                        Integer.parseInt(s) + 1 + "");
            } catch (NumberFormatException e) {
                return Optional.of(s);
            }
        });
        test("Replace",
                s -> Optional.of(s.replace("2", "9")));
        test("Take last digit",
                s -> Optional.of(s.length() > 0 ?
                        s.charAt(s.length() - 1) + ""
                        : s));
    }
}