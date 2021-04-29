package com.javaLearing.chapter23;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 另外两个 @interface 定义的是 SQL 类型。如果希望这个框架更有价值的话，我们应该为每个 SQL 类型都定义相应的注解。不过作为示例，两个元素足够了。
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLString {
    int value() default 0;
    String name() default "";
    Constraints constrains() default @Constraints;//注意这个的用法
}
