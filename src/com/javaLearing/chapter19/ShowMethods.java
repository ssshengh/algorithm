package com.javaLearing.chapter19;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

//java ShowMethods.java  com.javaLearing.chapter19.ShowMethods
public class ShowMethods {
    private static String usage =
            "usage:\n" +
                    "ShowMethods qualified.class.name\n" +
                    "To show all methods in class or:\n" +
                    "ShowMethods qualified.class.name word\n" +
                    "To search for methods involving 'word'";
    private static Pattern p = Pattern.compile("\\w+\\.");

    /**
     * Class 方法 getmethods() 和 getconstructors() 分别返回 Method 数组和 Constructor 数组。
     * 这些类中的每一个都有进一步的方法来解析它们所表示的方法的名称、参数和返回值。
     * 但你也可以像这里所做的那样，使用 toString()，生成带有整个方法签名的 String。
     * 代码的其余部分提取命令行信息，确定特定签名是否与目标 String（使用 indexOf()）匹配，并使用正则表达式（在 Strings 一章中介绍）删除名称限定符。
     * */
    public static void main(String[] args) {
        if (args.length <1){
            System.out.println(usage);
            System.exit(0);
        }

        int lines = 0;
        try {
            Class<?> c = Class.forName(args[0]);//从第一个参数提取类型信息
            Method[] methods = c.getMethods();//获取类型的方法
            Constructor[] ctors = c.getConstructors();

            if (args.length == 1){
                for (Method method:methods)
                    System.out.println(
                            p.matcher(method.toString()).replaceAll("")
                    );
                for (Constructor constructor:ctors)
                    System.out.println(
                            p.matcher(constructor.toString()).replaceAll("")
                    );
                lines = methods.length + ctors.length;//获取长度
            }else {
                for (Method method:methods)
                    if (method.toString().contains(args[1])) {
                        System.out.println(
                                p.matcher(method.toString()).replaceAll("")
                        );
                        lines++;
                    }
                for (Constructor constructor:ctors)
                    if (ctors.toString().contains(args[1])) {
                        System.out.println(
                                p.matcher(constructor.toString()).replaceAll("")
                        );
                        lines++;
                    }
            }
        }catch (ClassNotFoundException e){
            System.out.println("No such class: "+ e);
        }

    }
}
//编译时无法知道 Class.forName() 生成的结果，因此所有方法签名信息都是在运行时提取的。
// 如果你研究 JDK 反射文档，你将看到有足够的支持来实际设置和对编译时完全未知的对象进行方法调用（本书后面有这样的例子）。
// 虽然最初你可能认为你永远都不需要这样做，但是反射的全部价值可能会令人惊讶。

//输出包含一个 public 无参数构造函数，即使未定义构造函数。你看到的构造函数是由编译器自动合成的。
// 如果将 ShowMethods 设置为非 public 类（即只有包级访问权），则合成的无参数构造函数将不再显示在输出中。自动为合成的无参数构造函数授予与类相同的访问权。
//
//尝试运行 java ShowMethods java.lang.String，并附加一个 char、int、String 等参数。
//
//编程时，当你不记得某个类是否有特定的方法，并且不想在 JDK 文档中搜索索引或类层次结构时，
// 或者如果你不知道该类是否可以对 Color 对象执行任何操作时，该工具能节省不少时间。
