package com.javaLearing.chapter15;

public class WhoCalled {
    static void f(){
        try{
            throw new Exception();
        }catch (Exception e){
            //printStackTrace() 方法所提供的信息可以通过 getStackTrace() 方法来直接访问，
            // 这个方法将返回一个由栈轨迹中的元素所构成的数组，
            for(StackTraceElement ste:e.getStackTrace())
                //这种方法可以把里面的每一个元素提取出来
                System.out.println(ste.getMethodName());
        }

    }
    static void g(){f();}
    static void k(){g();}

    public static void main(String[] args) {
        f();
        System.out.println("***************");
        g();
        System.out.println("***************");
        k();
    }//这里，我们只打印了方法名，但实际上还可以打印整个 StackTraceElement，它包含其他附加的信息。
}
