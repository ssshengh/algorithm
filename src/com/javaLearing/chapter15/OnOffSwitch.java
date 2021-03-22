package com.javaLearing.chapter15;

class Switch{
    //想像成一个机器
    private boolean state = false;//状态
    public boolean read() {return state;}//读取状态

    public void on(){//开灯？
        state = true;
        System.out.println(this);//注意这个输出，输出的是一个对象
    }
    public void off(){//关灯？
        state = false;
        System.out.println(this);
    }
    @Override
    public String toString(){
        return state? "on" : "off";
    }
}

class OnOffException1 extends Exception {}
class OnOffException2 extends Exception {}

public class OnOffSwitch {
    private static Switch sw = new Switch();
    public static void f()
            throws OnOffException1, OnOffException2 {}
    public static void main(String[] args) {
        try {
            sw.on();
            // Code that can throw exceptions...
            f();
            sw.off();
        } catch(OnOffException1 e) {
            System.out.println("OnOffException1");
            sw.off();
        } catch(OnOffException2 e) {
            System.out.println("OnOffException2");
            sw.off();
        }
    }//程序的目的是要确保 main() 结束的时候开关必须是关闭的，所以在每个 try 块和异常处理程序的末尾都加入了对 sw.off() 方法的调用。
    //但也可能有这种情况：异常被抛出，但没被处理程序捕获，这时 sw.off() 就得不到调用。

}
//但是有了 finally，只要把 try 块中的清理代码移放在一处即可：
class withFianlly{
    static Switch sw = new Switch();

    public static void main(String[] args) {
        try {
            sw.on();
            OnOffSwitch.f();
        } catch (OnOffException2 onOffException2) {
            System.out.println("onOffException2");
        } catch (OnOffException1 onOffException1) {
            System.out.println("onOffException1");
        }finally {
            sw.off();
        }
    }
}