package com.javaLearing.chapter22;

import java.util.EnumMap;
import java.util.Map;

import static com.javaLearing.chapter22.AlarmPoints.*;
//下面的例子演示了命令设计模式的用法。一般来说，命令模式首先需要一个只有单一方法的接口，然后从该接口实现具有各自不同的行为的多个子类。
interface Command { void action(); }//其实就是函数引用
public class EnumMaps {
    public static void main(String[] args) {
        EnumMap<AlarmPoints, Command> em = new EnumMap<AlarmPoints, Command>(AlarmPoints.class);
        System.out.println(em);//这里初始化出来似乎是一个空map，但其实不是，只是指向了null

        em.put(KITCHEN,
                () -> System.out.print("Kitchen fire!"));
        em.put(BATHROOM,
                () -> System.out.print("Bathroom alert!"));

        for (Map.Entry<AlarmPoints, Command> entry : em.entrySet()){//遍历map的方法
            System.out.print("键为：" + entry.getKey() + "。 值为调用lambda输出：");
            entry.getValue().action();
            System.out.println();
        }

        //main() 方法的最后部分说明，enum 的每个实例作为一个键，总是存在的。
        // 但是，如果你没有为这个键调用 put() 方法来存入相应的值的话，其对应的值就是 null。
        try { // If there's no value for a particular key:
            em.get(UTILITY).action();
        } catch(Exception e) {
            System.out.println("Expected: " + e);
        }
    }
}
//与 EnumSet 一样，enum 实例定义时的次序决定了其在 EnumMap 中的顺序。