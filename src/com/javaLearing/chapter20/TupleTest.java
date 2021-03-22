package com.javaLearing.chapter20;

// generics/TupleTest.java
import onjava.*;
class Amphibian {}

// generics/Vehicle.java
class Vehicle {}

public class TupleTest {
    static Tuple2<String, Integer> f() {
        // 47 自动装箱为 Integer
        return new Tuple2<>("hi", 47);
    }

    static Tuple3<Amphibian, String, Integer> g() {
        return new Tuple3<>(new Amphibian(), "hi", 47);
    }

    static Tuple4<Vehicle, Amphibian, String, Integer> h() {
        return new Tuple4<>(new Vehicle(), new Amphibian(), "hi", 47);
    }

    static Tuple5<Vehicle, Amphibian, String, Integer, Double> k() {
        return new Tuple5<>(new Vehicle(), new Amphibian(), "hi", 47, 11.1);
    }

    //对存入不同对象的元组，可以看出，对其只读。它的目的很明确，就是联系多个对象，但是不允许修改它们
    public static void main(String[] args) {
        Tuple2<String, Integer> ttsi = f();
        System.out.println(ttsi);
        // ttsi.a1 = "there"; // 编译错误，因为 final 不能重新赋值
        System.out.println(g());
        System.out.println(h());
        System.out.println(k());
    }
}

/* 输出：
 (hi, 47)
 (Amphibian@1540e19d, hi, 47)
 (Vehicle@7f31245a, Amphibian@6d6f6e28, hi, 47)
 (Vehicle@330bedb4, Amphibian@2503dbd3, hi, 47, 11.1)
 */

