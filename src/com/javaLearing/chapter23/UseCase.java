package com.javaLearing.chapter23;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UseCase {
    //注意 id 和 description 与方法定义类似。由于编译器会对 id 进行类型检查，因此将跟踪数据库与用例文档和源代码相关联是可靠的方式。
    //description 元素拥有一个 default 值，如果在注解某个方法时没有给出 description 的值。则该注解的处理器会使用此元素的默认值。
    int id();
    String description() default "no description";
}
/**
 * 下面是一个简单的注解，我们可以用它来追踪项目中的用例。
 * 程序员可以使用该注解来标注满足特定用例的一个方法或者一组方法。
 * 于是，项目经理可以通过统计已经实现的用例来掌控项目的进展，而开发者在维护项目时可以轻松的找到用例用于更新，或者他们可以调试系统中业务逻辑。
 * */