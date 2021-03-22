package com.javaLearing.chapter20Q;

// generics/ApplyFunctional.java

import java.util.*;
import java.util.stream.*;
import java.util.function.*;

import com.javaLearing.chapter20.FilledList;
import com.javaLearing.chapter20.Suppliers;
import onjava.*;

public class ApplyFunctional {
    public static void main(String[] args) {
        Stream.of(
                Stream.generate(Shape::new).limit(2),
                Stream.generate(Square::new).limit(2))
                .flatMap(c -> c) // flatten into one stream
                .peek(Shape::rotate)
                .forEach(s -> s.resize(7));
        //我们首先生成两个 Stream ： 一个是 Shape ，一个是 Square ，并将它们展平为单个流。
        // 尽管 Java 缺少功能语言中经常出现的 flatten() ，但是我们可以使用 flatMap(c-> c) 产生相同的结果，
        // 后者使用身份映射将操作简化为“ flatten ”。

//        new FilledList<>(Shape::new, 2)
//                .forEach(Shape::rotate);
//        new FilledList<>(Square::new, 2)
//                .forEach(Shape::rotate);

        SimpleQueue<Shape> shapeQ = Suppliers.fill(
                new SimpleQueue<>(), SimpleQueue::add,
                Shape::new, 2);
        Suppliers.fill(shapeQ, SimpleQueue::add,
                Square::new, 2);
        shapeQ.forEach(Shape::rotate);
    }
}
/* Output:
Shape 0 rotate
Shape 0 resize 7
Shape 1 rotate
Shape 1 resize 7
Square 2 rotate
Square 2 resize 7
Square 3 rotate
Square 3 resize 7
Shape 4 rotate
Shape 5 rotate
Square 6 rotate
Square 7 rotate
Shape 8 rotate
Shape 9 rotate
Square 10 rotate
Square 11 rotate
*/
