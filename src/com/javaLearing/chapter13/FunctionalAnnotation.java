package com.javaLearing.chapter13;

// functional/FunctionalAnnotation.java

@FunctionalInterface
interface Functional {
    String goodbye(String arg);
}

interface FunctionalNoAnn {
    String goodbye(String arg);
}

/*
@FunctionalInterface
interface NotFunctional {
  String goodbye(String arg);
  String hello(String arg);
}
产生错误信息:
NotFunctional is not a functional interface
multiple non-overriding abstract methods！！！！！！！
found in interface NotFunctional
只能有一个方法
*/

//@FunctionalInterface 注解是可选的; Java 会在 main() 中把 Functional 和 FunctionalNoAnn 都当作函数式接口来看待。
// 在 NotFunctional 的定义中可看出@FunctionalInterface 的作用：当接口中抽象方法多于一个时产生编译期错误

public class FunctionalAnnotation {
    public String goodbye(String arg) {
        return "Goodbye, " + arg;
    }
    public static void main(String[] args) {
        FunctionalAnnotation fa =
                new FunctionalAnnotation();

        /**
         * Functional 和 FunctionalNoAnn 声明了是接口，然而被赋值的只是方法 goodbye()。
         * 首先，这只是一个方法而不是类；其次，它甚至都不是实现了该接口的类中的方法。
         * 这是添加到Java 8中的一点小魔法：如果将方法引用或 Lambda 表达式赋值给函数式接口（类型需要匹配），Java 会适配你的赋值到目标接口。
         * 编译器会在后台把方法引用或 Lambda 表达式包装进实现目标接口的类的实例中。
         * */
        Functional f = fa::goodbye;
        FunctionalNoAnn fna = fa::goodbye;
        // Functional fac = fa; // Incompatible虽然 FunctionalAnnotation 确实符合 Functional 模型，但是 Java不允许我们像fac定义的那样，将 FunctionalAnnotation 直接赋值给 Functional，因为 FunctionalAnnotation 并没有显式地去实现 Functional 接口。
        System.out.println(f.goodbye("yes!"));//上面就已经完成了方法引用的绑定方法

        //前面遇到的一些只有一个方法的接口，也是函数式接口
        //在把方法引用或者lambda赋值给他们成为方法引用的时候，java自动的将所赋值的式子适配到了接口
        //比如方法引用依靠签名，lambda表达式依靠下面的a作为输入参数，返回函数式接口中函数的返回值
        Functional fl = a -> "Goodbye, " + a +a +"!!!";
        FunctionalNoAnn fnal = a -> "Goodbye, " + a;
        System.out.println(fl.goodbye("wao!"));
    }
}

