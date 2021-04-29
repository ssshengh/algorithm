package com.javaLearing.chapter23;

import java.lang.annotation.*;

/**
 * 在 @Target 注解中指定的每一个 ElementType 就是一个约束，它告诉编译器，这个自定义的注解只能用于指定的类型。
 * 你可以指定 enum ElementType 中的一个值，或者以逗号分割的形式指定多个值。
 * 如果想要将注解应用于所有的 ElementType，那么可以省去 @Target 注解，但是这并不常见。
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBTable {
    String name() default "";
    //注意 @DBTable 中有一个 name() 元素，该注解通过这个元素为处理器创建数据库时提供表的名字。
}
