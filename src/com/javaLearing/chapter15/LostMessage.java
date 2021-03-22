package com.javaLearing.chapter15;

// exceptions/LostMessage.java
// How an exception can be lost
class VeryImportantException extends Exception {
    @Override
    public String toString() {
        return "A very important exception!";
    }
}
class HoHumException extends Exception {
    @Override
    public String toString() {
        return "A trivial exception";
    }
}

public class LostMessage {
    void f() throws VeryImportantException {
        throw new VeryImportantException();
    }
    void dispose() throws HoHumException {
        throw new HoHumException();
    }

    public static void main(String[] args) {
        try {
            LostMessage lm = new LostMessage();
            try {
                lm.f();
            } finally {
                lm.dispose();
            }
        } catch(VeryImportantException | HoHumException e) {
            System.out.println(e);
        }//这里捕获到的异常就少了VeryImportantException，该异常丢失了！！！它被 finally 子句里的 HoHumException 所取代。
    }
}
//如果运行这个程序，就会看到即使方法里抛出了异常，它也不会产生任何输出。
//最简单的异常丢失
class ExceptionSilencer {
    public static void main(String[] args) {
        try {
            throw new RuntimeException();
        } finally {
            // Using 'return' inside the finally block
            // will silence any thrown exception.
            return;
        }
    }
}
