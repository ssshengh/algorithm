package com.javaLearing.chapter15;

// exceptions/AutoCloseableDetails.java
//try-with-resources 定义子句中创建的对象（在括号内）必须实现  java.lang.AutoCloseable 接口，这个接口只有一个方法：close()。
class Reporter implements AutoCloseable {
    String name = getClass().getSimpleName();
    Reporter() {
        System.out.println("Creating " + name);
    }
    public void close() {
        System.out.println("Closing " + name);
    }
}

class First extends Reporter {}
class Second extends Reporter {}
public class AutoCloseableDetails {
    public static void main(String[] args) {
        try(
                First f = new First();
                Second s = new Second()
        ) {
        }
    }
    //退出 try 块会调用两个对象的 close() 方法，并以与创建顺序相反的顺序关闭它们。
    // 顺序很重要，因为在这种情况下，Second 对象可能依赖于 First 对象，因此如果 First 在第 Second 关闭时已经关闭。
    // Second 的 close() 方法可能会尝试访问 First 中不再可用的某些功能。
}

/*class Anything {}
class TryAnything {
    public static void main(String[] args) {
        try(
                Anything a = new Anything()
        ) {
        }
    }
    这样就会报错
}*/

//如果其中一个构造函数抛出异常怎么办？
// exceptions/ConstructorException.java
class CE extends Exception {}
class SecondExcept extends Reporter {
    SecondExcept() throws CE {
        super();
        throw new CE();
    }
}

class ConstructorException {
    public static void main(String[] args) {
        try(
                First f = new First();
                SecondExcept s = new SecondExcept();
                Second s2 = new Second()
                //正如预期的那样，First 创建时没有发生意外，SecondExcept 在创建期间抛出异常。
                // 请注意，不会为 SecondExcept 调用 close()，！！！因为如果构造函数失败，则无法假设你可以安全地对该对象执行任何操作！！！
                // 包括关闭它。由于 SecondExcept 的异常，Second 对象实例 s2 不会被创建，因此也不会有清除事件发生。
        ) {
            System.out.println("In body");
        } catch(CE e) {
            System.out.println("Caught: " + e);
        }
        //现在资源规范头中定义了 3 个对象，中间的对象抛出异常。因此，编译器强制我们使用 catch 子句来捕获构造函数异常。
        // 这意味着资源规范头实际上被 try 块包围。
    }
}

//如果没有构造函数抛出异常，但在 try 的主体中可能抛出异常，那么你将再次被强制要求提供一个catch 子句：
/**
 * 请注意，第 3 个对象永远不会被清除。那是因为它不是在资源规范头中创建的，所以它没有被保护！！！！！！
 * 这很重要，因为 Java 在这里没有以警告或错误的形式提供指导，因此像这样的错误很容易漏掉！！！！！！
 * 实际上，如果依赖某些集成开发环境来自动重写代码，以使用 try-with-resources 特性，那么它们（在撰写本文时）通常只会保护它们遇到的第一个对象，
 * 而忽略其余的对象。
 * */
class Third extends Reporter {}
class BodyException {
    public static void main(String[] args) {
        try(
                First f = new First();
                Second s2 = new Second()
        ) {
            System.out.println("In body");
            Third t = new Third();
            new SecondExcept();
            System.out.println("End of body");
        } catch(CE e) {
            System.out.println("Caught: " + e);
        }
    }
}