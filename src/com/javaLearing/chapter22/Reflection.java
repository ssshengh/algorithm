package com.javaLearing.chapter22;

import java.lang.reflect.*;
import java.util.*;

enum Explore { HERE, THERE }
enum Search { HITHER, YON }
public class Reflection {
    public static Set<String> analyze(Class<?> enumClass){
        System.out.println(
                "_____ Analyzing " + enumClass + " _____");
        System.out.println("查看接口：");
        for (Type t : enumClass.getInterfaces())//反射
            System.out.println(t);
        System.out.println("获取基类");
        System.out.println(
                "Base: " + enumClass.getSuperclass());
        System.out.println("查看方法：");
        Set<String> methods = new TreeSet<>();
        for (Method method: enumClass.getMethods())
            methods.add(method.getName());//把方法名加入集合中
        System.out.println(methods);

        return methods;
    }

    public static void main(String[] args) {
        Set<String> exploreMethods =
                analyze(Explore.class);
        Set<String> enumMethods = analyze(Enum.class);System.out.println(
                "Explore.containsAll(Enum)? " +
                        exploreMethods.containsAll(enumMethods));
        System.out.print("Explore.removeAll(Enum): ");
        exploreMethods.removeAll(enumMethods);
        System.out.println(exploreMethods);
//// Decompile the code for the enum:
//        OSExecute.command(
//                "javap -cp build/classes/main Explore");

        //答案是，values() 是由编译器添加的 static 方法。
        // 可以看出，在创建 Explore 的过程中，编译器还为其添加了 valueOf() 方法。这可能有点令人迷惑，Enum 类不是已经有 valueOf() 方法了吗。

    }
    //没执行的那句的结果
    //Compiled from "Reflection.java"
    //final class Explore extends java.lang.Enum<Explore> {
    //  public static final Explore HERE;
    //  public static final Explore THERE;
    //  public static Explore[] values();
    //  public static Explore valueOf(java.lang.String);
    //  static {};
    //}
}

/**
 * 不过 Enum 中的 valueOf() 方法需要两个参数，而这个新增的方法只需一个参数。
 * 由于这里使用的 Set 只存储方法的名字，而不考虑方法的签名，所以在调用 Explore.removeAll(Enum) 之后，就只剩下[values] 了。
 *
 * 从最后的输出中可以看到，编译器将 Explore 标记为 final 类，所以无法继承自 enum，其中还有一个 static 的初始化子句，稍后我们将学习如何重定义该句。
 *
 * 由于擦除效应（在泛型 章节中介绍过），反编译无法得到 Enum 的完整信息，所以它展示的 Explore 的父类只是一个原始的 Enum，而非事实上的 Enum<Explore>。
 *
 * 由于 values() 方法是由编译器插入到 enum 定义中的 static 方法，所以，如果你将 enum 实例向上转型为 Enum，那么 values() 方法就不可访问了。
 * 不过，在 Class 中有一个 getEnumConstants() 方法，所以即便 Enum 接口中没有 values() 方法，我们仍然可以通过 Class 对象取得所有 enum 实例。
 * */
class UpcastEnum {
    public static void main(String[] args) {
        Search[] vals = Search.values();
        Enum e = Search.HITHER; // Upcast向上转型后，value()方法不再存在
// e.values(); // No values() in Enum
        for(Enum en : e.getClass().getEnumConstants())
            System.out.println(en);
    }
}
//因为 getEnumConstants() 是 Class 上的方法，所以你甚至可以对不是枚举的类调用此方法：
class NonEnum {
    public static void main(String[] args) {
        Class<Integer> integerClass = Integer.class;
        try {
            for (Object o : integerClass.getEnumConstants())
                System.out.println(o);
        }catch (Exception e)
        {
            System.out.println("Expected: " + e);//只不过，此时该方法返回 null，所以当你试图使用其返回的结果时会发生异常。
        }
    }
}