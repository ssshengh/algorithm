package com.javaLearing.chapter22;

// enums/NotClasses.java
// {javap -c LikeClasses}
enum LikeClasses {
    WINKEN {
        @Override
        void behavior() {
            System.out.println("Behavior1");
        }
    },
    BLINKEN {
        @Override
        void behavior() {
            System.out.println("Behavior2");
        }
    },
    NOD {
        @Override
        void behavior() {
            System.out.println("Behavior3");
        }
    };
    abstract void behavior();
}
public class NotClasses {
    //不能这么创建一个新的对象，因此其实际上还是不是一个真正的类
    // void f1(LikeClasses.WINKEN instance) {} // Nope
}

