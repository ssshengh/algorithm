package com.javaLearing.chapter20;

// onjava/Tuple2.java
//这个概念称为元组，它是将一组对象直接打包存储于单一对象中。
// 可以从该对象读取其中的元素，
// 但不允许向其中存储新对象（这个概念也称为 数据传输对象 或 信使 ）
public class Tuple2<A, B> {
    public final A a1;
    public final B a2;
    //注意，上面的数据不是private，而是final，仔细思考，我们令为private的关键思路是不允许其被客户端所修改
    //那么如果我们自己也不想修改的话，完全可以这么做
    //此外，元组存在的意义就是，我们可以读取a1和a2，但是不能对其赋值，因此作为只读的a1和a2完全可以这么做
    public Tuple2(A a, B b) { a1 = a; a2 = b; }
    public String rep() { return a1 + ", " + a2; }

    @Override
    public String toString() {
        return "(" + rep() + ")";
    }
    //另一种设计思路是允许元组的用户给 a1 和 a2 重新赋值。
    // 然而，采用上例中的形式无疑更加安全，如果用户想存储不同的元素，！！！就会强制他们创建新的 Tuple2 对象。！！！
    //即假设tuple2是一个不可修改的数组元素，我们想继续储存的话，那就新加一个元素进去
}
