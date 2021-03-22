package com.javaLearing.chapter23;


import java.util.*;
import java.util.stream.*;
import java.lang.reflect.*;
//注解解释器，用于解释UseCase

/**
 * 这个程序用了两个反射的方法：getDeclaredMethods() 和 getAnnotation()，它们都属于 AnnotatedElement 接口（Class，Method 与 Field 类都实现了该接口）。
 * getAnnotation() 方法返回指定类型的注解对象，在本例中就是 “UseCase”。
 * 如果被注解的方法上没有该类型的注解，返回值就为 null。
 * 我们通过调用 id() 和 description() 方法来提取元素值。
 * 注意 encryptPassword() 方法在注解的时候没有指定 description 的值，因此处理器在处理它对应的注解时，通过 description() 取得的是默认值 “no description”。
 */
public class UseCaseTracker {
    public static void trackUseCase(List<Integer> useCases, Class<?> cl){
        for (Method m : cl.getDeclaredMethods()) {
            System.out.println(m);
            UseCase uc = m.getAnnotation(UseCase.class);//返回指定类型的注解对象
            if (uc!=null){
                System.out.println("Found Use Case " +
                        uc.id() + "\n " + uc.description());
                useCases.remove(Integer.valueOf(uc.id()));
            }
        }
        useCases.forEach(i ->
                System.out.println("Missing use case " + i));
    }

    public static void main(String[] args) {
        List<Integer> useCase = IntStream.range(47, 51)
                .boxed().collect(Collectors.toList());
        trackUseCase(useCase, PasswordUtils.class);
    }

}
