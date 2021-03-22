package com.javaLearing.chapter22;

// enums/CarWash.java
import java.util.*;
public class CarWash {
    //与使用匿名内部类相比较，定义常量相关方法的语法更高效、简洁。
    public enum Cycle {
        UNDERBODY {
            @Override
            void action() {
                System.out.println("Spraying the underbody");
            }
        },
        WHEELWASH {
            @Override
            void action() {
                System.out.println("Washing the wheels");
            }
        },
        PREWASH {
            @Override
            void action() {
                System.out.println("Loosening the dirt");
            }
        },
        BASIC {
            @Override
            void action() {
                System.out.println("The basic wash");
            }
        },
        HOTWAX {
            @Override
            void action() {
                System.out.println("Applying hot wax");
            }
        },
        RINSE {
            @Override
            void action() {
                System.out.println("Rinsing");
            }
        },
        BLOWDRY {
            @Override
            void action() {
                System.out.println("Blowing dry");
            }
        };
        abstract void action();
    }

    EnumSet<Cycle> cycles =
            EnumSet.of(Cycle.BASIC, Cycle.RINSE);

    public void add(Cycle cycle) {
        cycles.add(cycle);
    }

    public void washCar() {
        for(Cycle c : cycles)
            c.action();
    }
    @Override
    public String toString() {
        return cycles.toString();
    }

    /**
     * 这个例子也展示了 EnumSet 了一些特性。因为它是一个集合，所以对于同一个元素而言，只能出现一次，
     * 因此对同一个参数重复地调用 add0 方法会被忽略掉（这是正确的行为，因为一个 bit 位开关只能“打开”一次），
     * 同样地，向 EnumSet 添加 enum 实例的顺序并不重要，因为其输出的次序决定于 enum 实例定义时的次序。
     * */
    public static void main(String[] args) {
        CarWash wash = new CarWash();
        System.out.println(wash);
        wash.washCar();
// Order of addition is unimportant:
        wash.add(Cycle.BLOWDRY);
        wash.add(Cycle.BLOWDRY); // Duplicates ignored
        wash.add(Cycle.RINSE);
        wash.add(Cycle.HOTWAX);
        System.out.println(wash);
        wash.washCar();
    }
}

