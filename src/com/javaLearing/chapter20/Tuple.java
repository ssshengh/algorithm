package com.javaLearing.chapter20;

public class Tuple {
    //这样就可以通过更通用的方法，生成元组
    public static <A, B> Tuple2<A, B> tuple(A a, B b){
        return new Tuple2<>(a, b);
    }
    public static <A, B, C> Tuple3<A, B, C>
    tuple(A a, B b, C c) {
        return new Tuple3<>(a, b, c);
    }

    public static <A, B, C, D> Tuple4<A, B, C, D>
    tuple(A a, B b, C c, D d) {
        return new Tuple4<>(a, b, c, d);
    }

    public static <A, B, C, D, E>
    Tuple5<A, B, C, D, E> tuple(A a, B b, C c, D d, E e) {
        return new Tuple5<>(a, b, c, d, e);
    }
}

class TupleTest2 {
    static Tuple2<String, Integer> f() {
        return Tuple.tuple("hi", 47);
    }

    static Tuple2 f2() {
        return Tuple.tuple("hi", 47);
    }
    //请注意，f() 返回一个参数化的 Tuple2 对象，而 f2() 返回一个未参数化的 Tuple2 对象。
    // 编译器不会在这里警告 f2() ，因为返回值未以参数化方式使用。从某种意义上说，它被“向上转型”为一个未参数化的 Tuple2 。
    // 但是，如果尝试将 f2() 的结果放入到参数化的 Tuple2 中，则编译器将发出警告。




    static Tuple3<Amphibian, String, Integer> g() {
        return Tuple.tuple(new Amphibian(), "hi", 47);
    }

    static Tuple4<Vehicle, Amphibian, String, Integer> h() {
        return Tuple.tuple(
                new Vehicle(), new Amphibian(), "hi", 47);
    }

    static Tuple5<Vehicle, Amphibian,
            String, Integer, Double> k() {
        return Tuple.tuple(new Vehicle(), new Amphibian(),
                "hi", 47, 11.1);
    }

    public static void main(String[] args) {
        Tuple2<String, Integer> ttsi = f();
        System.out.println(ttsi);
        System.out.println(f2());
        System.out.println(g());
        System.out.println(h());
        System.out.println(k());
    }
}