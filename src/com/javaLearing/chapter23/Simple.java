package com.javaLearing.chapter23;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE,
            ElementType.PACKAGE, ElementType.FIELD, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.SOURCE)//注意这个源码上处理
//@Retention 的参数现在为 SOURCE，这意味着注解不会再存留在编译后的代码。这在编译时处理注解是没有必要的，它只是指出，在这里，javac 是唯一有机会处理注解的代理。
public @interface Simple {
    String value() default "-default-";
}
