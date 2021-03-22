package com.javaLearing.chapter19;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

abstract class Shape {
    void draw() { System.out.println(this + ".draw()"); }
    @Override
    public abstract String toString();
    //注意是abstract的，这样就强制继承类必须重写该方法，这样 draw() 在不同情况下就打印出不同的消息(多态)
}

class Circle extends Shape {
    @Override
    public String toString() { return "Circle"; }
}

class Square extends Shape {
    @Override
    public String toString() { return "Square"; }
}

class Triangle extends Shape {
    @Override
    public String toString() { return "Triangle"; }
}


public class Shapes {
//    public void count  (){
//        Map<String, Integer> aa = new HashMap<>();
//        aa.entrySet().stream()
//    }
    public static void main(String[] args) {
        Stream.of(
                new Circle(), new Square(), new Triangle()
                //这个例子中，在把 Shape 对象放入 Stream<Shape> 中时就会进行向上转型(隐式)，但在向上转型的时候也丢失了这些对象的具体类型
        ).forEach(Shape::draw);//重写了toString，因此调用的this表示的类型信息是otString后的
    }
    //严格来说，Stream<Shape> 实际上是把放入其中的所有对象都当做 Object 对象来持有，只是取元素时会自动将其类型转为 Shape。
    // 这也是 RTTI 最基本的使用形式，因为在 Java 中，所有类型转换的正确性检查都是在运行时进行的。
    // 这也正是 RTTI 的含义所在：在运行时，识别一个对象的类型。
}

/**
 * 在这个例子中，类型转换并不彻底：Object 被转型为 Shape ，而不是 Circle、Square 或者 Triangle。
 * 这是因为目前我们只能确保这个 Stream<Shape> 保存的都是 Shape
 *
 * 编译期，stream 和 Java 泛型系统确保放入 stream 的都是 Shape 对象（Shape 子类的对象也可视为 Shape 的对象），否则编译器会报错；
 * 运行时，自动类型转换确保了从 stream 中取出的对象都是 Shape 类型。
 *
 * 接下来就是多态机制的事了，Shape 对象实际执行什么样的代码，是由引用所指向的具体对象（Circle、Square 或者 Triangle）决定的。
 * */