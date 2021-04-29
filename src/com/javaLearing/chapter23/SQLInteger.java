package com.javaLearing.chapter23;

// annotations/database/SQLInteger.java
import java.lang.annotation.*;
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLInteger {
    String name() default "";
    Constraints constraints() default @Constraints;
}

@interface Uniqueness{
    Constraints constrains() default @Constraints(primaryKey = false);//可以这么来更改，使得不全部是默认的元素
}
