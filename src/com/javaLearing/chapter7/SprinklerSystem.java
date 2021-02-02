package com.javaLearing.chapter7;

// reuse/SprinklerSystem.java
// (c)2017 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://OnJava8.com for more book information.
// Composition for code reuse

class WaterSource {
    private String s;
    WaterSource() {
        System.out.println("WaterSource()");
        s = "Constructed";
    }
    //注意为什么需要这个方法
    @Override
    public String toString() { return s; }
}

public class SprinklerSystem {
    //注意这里的默认值
    private String valve1, valve2, valve3, valve4;
    private WaterSource source = new WaterSource();
    private int i;
    private float f;

    /**
     * **@Override** 是可选的，但它有助于验证你没有拼写错误 (或者更微妙地说，大小写字母输入错误)。
     * 类中的基本类型字段自动初始化为零，正如 object Everywhere 一章中所述。
     * 但是对象引用被初始化为 null，如果你尝试调用其任何一个方法(来自Object类)，你将得到一个异常（一个运行时错误）。！！！！！！！
     * 方便的是，打印 null 引用却不会得到异常。
     * */
    @Override
    public String toString() {
        return
                "valve1 = " + valve1 + " " +
                        "valve2 = " + valve2 + " " +
                        "valve3 = " + valve3 + " " +
                        "valve4 = " + valve4 + "\n" +
                        "i = " + i + " " + "f = " + f + " " +
                        "source = " + source; // [1]字符串只能拼接字符串，所以会自动调用WaterSource的toString方法
    }
    public static void main(String[] args) {
        SprinklerSystem sprinklers = new SprinklerSystem();
        System.out.println(sprinklers);
    }
}
/* Output:
WaterSource()
valve1 = null valve2 = null valve3 = null valve4 = null
i = 0 f = 0.0 source = Constructed
*/
