package com.javaLearing.chapter22;

import java.text.DateFormat;
import java.util.Date;

public enum ConstantSpecificMethod {
    /**
     * 允许程序员为 enum 实例编写方法，从而为每个 enum 实例赋予各自不同的行为。
     * 要实现常量相关的方法，你需要为 enum 定义一个或多个 abstract 方法，然后为每个 enum 实例实现该抽象方法。
     * */
    DATE_TIME{
        @Override
        String getInfo(){
            return
                    DateFormat.getDateInstance()
                            .format(new Date());
        }
    },
    CLASSPATH {
        @Override
        String getInfo() {
            return System.getenv("CLASSPATH");
        }
    },
    VERSION {
        @Override
        String getInfo() {
            return System.getProperty("java.version");
        }
    };
    abstract String getInfo();

    public static void main(String[] args) {
        for (ConstantSpecificMethod c : values())
            System.out.println(c.getInfo());
    }
}
