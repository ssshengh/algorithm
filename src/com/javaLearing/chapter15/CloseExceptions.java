package com.javaLearing.chapter15;

// exceptions/CloseExceptions.java
class CloseException extends Exception {}
class Reporter2 implements AutoCloseable {
    String name = getClass().getSimpleName();
    Reporter2() {
        System.out.println("Creating " + name);
    }
    public void close() throws CloseException {
        System.out.println("Closing " + name);
    }
}
class Closer extends Reporter2 {
    @Override
    public void close() throws CloseException {
        super.close();
        throw new CloseException();
    }
}

//从技术上讲，我们并没有被迫在这里提供一个 catch 子句；你可以通过 main() throws CloseException 的方式来报告异常。
// 但 catch 子句是放置错误处理代码的典型位置。
public class CloseExceptions {
    public static void main(String[] args) {
        try(
                First f = new First();
                Closer c = new Closer();
                Second s = new Second()
        ) {
            System.out.println("In body");
        } catch(CloseException e) {
            System.out.println("Caught: " + e);
        }
    }
}
/**
 * 请注意，因为所有三个对象都已创建，所以它们都以相反的顺序关闭 - 即使 Closer.close() 抛出异常也是如此。
 * 仔细想想，这就是你想要的结果。但如果你必须亲手编写所有的逻辑，或许会丢失一些东西并使得逻辑出错。
 * 想想那些程序员没有考虑 Clean up 的所有影响并且出错的代码。因此，如果可以，你应当始终使用 try-with-resources。
 * 这个特性有助于生成更简洁，更易于理解的代码。
 * */
