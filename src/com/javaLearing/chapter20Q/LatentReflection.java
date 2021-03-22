package com.javaLearing.chapter20Q;

// generics/LatentReflection.java
// Using reflection for latent typing
import com.javaLearing.chapter19.Robot;

import java.lang.reflect.*;

// Does not implement Performs:
class Mime {
    public void walkAgainstTheWind() {}
    public void sit() {
        System.out.println("Pretending to sit");
    }
    public void pushInvisibleWalls() {}
    @Override
    public String toString() { return "Mime"; }
}

// Does not implement Performs:
class SmartDog {
    public void speak() { System.out.println("Woof!"); }
    public void sit() { System.out.println("Sitting"); }
    public void reproduce() {}
}

class CommunicateReflectively {
    public static void perform(Object speaker) {
        Class<?> spkr = speaker.getClass();//获取反射信息
        try {
            try {
                Method speak = spkr.getMethod("speak");//反射获取方法
                speak.invoke(speaker);
            } catch(NoSuchMethodException e) {
                System.out.println(speaker + " cannot speak");//甚至处理了Mime只有一个sit方法
            }
            try {
                Method sit = spkr.getMethod("sit");//反射获取方法
                sit.invoke(speaker);
            } catch(NoSuchMethodException e) {
                System.out.println(speaker + " cannot sit");
            }
        } catch(SecurityException |
                IllegalAccessException |
                IllegalArgumentException |
                InvocationTargetException e) {
            throw new RuntimeException(speaker.toString(), e);
        }
    }
}

/**
 * 上例中，这些类完全是彼此分离的，没有任何公共基类（除了 Object ）或接口。
 * 通过反射, CommunicateReflectively.perform() 能够动态地确定所需要的方法是否可用并调用它们。
 * 它甚至能够处理 Mime 只具有一个必需的方法这一事实，并能够部分实现其目标。
 * */
public class LatentReflection {
    public static void main(String[] args) {
        CommunicateReflectively.perform(new SmartDog());
        //CommunicateReflectively.perform(new Robot());
        CommunicateReflectively.perform(new Mime());
    }
}
/* Output:
Woof!
Sitting
Click!
Clank!
Mime cannot speak
Pretending to sit
*/

