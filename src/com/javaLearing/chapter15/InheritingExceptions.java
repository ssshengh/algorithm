package com.javaLearing.chapter15;

class SimpleException extends Exception {}
//从java中继承出异常
public class InheritingExceptions {
    public void f() throws SimpleException{
        System.out.println("Throw SimpleException from f()");
        throw new SimpleException();
        //编译器创建了无参构造器，它将自动调用基类的无参构造器。
        // 本例中不会得到像 SimpleException(String) 这样的构造器，这种构造器也不实用。
        // 你将看到，对异常来说，最重要的部分就是类名，所以本例中建立的异常类在大多数情况下已经够用了。
    }

    public static void main(String[] args) {
        InheritingExceptions sed = new InheritingExceptions();
            try {
                sed.f();
            } catch (SimpleException e) {
                System.out.println("Get it!");
            }

    }
}
