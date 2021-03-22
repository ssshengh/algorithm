package com.javaLearing.chapter19;

//标记接口：用于表示空值


// typeinfo/Robot.java


import java.util.*;

interface Null{}
//注意是个接口
public interface Robot {
    String name();//一个名字

    String model();//一个模型

    List<Operation> operations();//一个描述 Robot 行为能力的 List<Operation>

    static void test(Robot r) {
        if (r instanceof Null)
            System.out.println("[Null Robot]");
        System.out.println("Robot name: " + r.name());
        System.out.println("Robot model: " + r.model());
        for (Operation operation : r.operations()) {
            //注意一个需要print一个不需要
            System.out.println(operation.description.get());
            operation.command.run();
        }
    }
}

