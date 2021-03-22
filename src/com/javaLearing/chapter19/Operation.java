package com.javaLearing.chapter19;

// typeinfo/Operation.java

import java.util.function.*;

//Operation 包含一个描述和一个命令（这用到了命令模式）。
// 它们被定义成函数式接口的引用，所以可以把 lambda 表达式或者方法的引用传给 Operation 的构造器
public class Operation {
    public final Supplier<String> description;
    public final Runnable command;

    public Operation(Supplier<String> descr, Runnable cmd) {
        description = descr;
        command = cmd;
    }
}

