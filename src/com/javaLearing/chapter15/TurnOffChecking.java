package com.javaLearing.chapter15;

// exceptions/TurnOffChecking.java
// "Turning off" Checked exceptions
import java.io.*;
class WrapCheckedException {
    void throwRuntimeException(int type) {
        try {
            switch(type) {
                case 0: throw new FileNotFoundException();
                case 1: throw new IOException();
                case 2: throw new
                        RuntimeException("Where am I?");
                default: return;
            }
        } catch(IOException | RuntimeException e) {
            // Adapt to unchecked:
            throw new RuntimeException(e);
        }
        //WrapCheckedException.throwRuntimeException() 包含可生成不同类型异常的代码。
        // 这些异常被捕获并包装进RuntimeException 对象，
        // 所以它们成了这些运行时异常的原因（"cause"）。！！！！！
    }
}

class SomeOtherException extends Exception {}
/**
 * 在 TurnOfChecking 里，可以不用 try 块就调用 throwRuntimeException()，因为它没有抛出“被检查的异常”。
 * 但是，当你准备好去捕获异常的时候，还是可以用 try 块来捕获任何你想捕获的异常的。
 * 应该捕获 try 块肯定会抛出的异常，这里就是 SomeOtherException，RuntimeException 要放到最后去捕获。
 * 然后把 getCause() 的结果（也就是被包装的那个原始异常）抛出来。
 * 这样就把原先的那个异常给提取出来了，然后就可以用它们自己的 catch 子句进行处理。
 *
 * 这种把被检查的异常用 RuntimeException 包装起来的技术，将在本书余下部分使用。
 * 另一种解决方案是创建自己的 RuntimeException 的子类。这样的话，异常捕获将不被强制要求，但是任何人都可以在需要的时候捕获这些异常。
 * */
public class TurnOffChecking {
    public static void main(String[] args) {
        WrapCheckedException wce =
                new WrapCheckedException();
        // You can call throwRuntimeException() without
        // a try block, and let RuntimeExceptions
        // leave the method:
        wce.throwRuntimeException(3);
        // Or you can choose to catch exceptions:
        for(int i = 0; i < 4; i++)
            try {
                if(i < 3)
                    wce.throwRuntimeException(i);
                else
                    throw new SomeOtherException();
            } catch(SomeOtherException e) {
                System.out.println(
                        "SomeOtherException: " + e);
            } catch(RuntimeException re) {
                try {
                    throw re.getCause();//获得异常链上面的原因
                } catch(FileNotFoundException e) {
                    System.out.println(
                            "FileNotFoundException: " + e);
                } catch(IOException e) {
                    System.out.println("IOException: " + e);
                } catch(Throwable e) {
                    System.out.println("Throwable: " + e);
                }
            }
    }
}

