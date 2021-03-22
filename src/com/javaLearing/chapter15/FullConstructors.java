package com.javaLearing.chapter15;

class MyException extends Exception{
    MyException(){}
    MyException(String msg){
        super(msg);
    }
    //对于第二个构造器，使用 super 关键字明确调用了其基类构造器，它接受一个字符串作为参数。
}
public class FullConstructors {
    public static void f() throws MyException {
        System.out.println("Throwing MyException from f()");
        throw new MyException();
    }
    public static void g() throws MyException {
        System.out.println("Throwing MyException from g()");
        throw new MyException("Originated in g()");
    }

    public static void main(String[] args) {
        try {
            FullConstructors.f();
        }catch (MyException e){
            e.printStackTrace(System.out);
        }

        try{
            g();
        }catch (MyException e){
            e.printStackTrace(System.err);
        }
        //在异常处理程序中，调用了在 Throwable 类声明（Exception 即从此类继承）的 printStackTrace() 方法。
        // 就像从输出中看到的，它将打印“从方法调用处直到异常抛出处”的方法调用序列。
        // 这里，信息被发送到了 System.out，并自动地被捕获和显示在输出中。
        // 但是，如果调用默认版本：e.printStackTrace(); 将会是System.err
    }
}
