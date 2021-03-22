package com.javaLearing.chapter15;

import java.io.*;
import java.util.logging.*;

class LoggingException extends Exception{
    private static Logger logger = Logger.getLogger("LoggingException");
    //静态的 Logger.getLogger() 方法创建了一个 String 参数相关联的 Logger 对象（通常与错误相关的包名和类名），
    // 这个 Logger 对象会将其输出发送到 System.err。

    LoggingException() {
        StringWriter trace = new StringWriter();
        printStackTrace(new PrintWriter(trace));
        logger.severe(trace.toString());
        //向 Logger 写入的最简单方式就是直接调用与日志记录消息的级别相关联的方法，这里使用的是 severe()。
    }
    //为了产生日志记录消息，我们欲获取异常抛出处的栈轨迹，但是 printStackTrace() 不会默认地产生字符串。
    // 为了获取字符串，我们需要使用重载的 printStackTrace() 方法，
    // 它接受一个 java.io.PrintWriter 对象作为参数（PrintWriter 会在 附录：I/O 流 一章详细介绍）。
    // 如果我们将一个 java.io.StringWriter 对象传递给这个 PrintWriter 的构造器，
    // 那么通过调用 toString() 方法，就可以将输出抽取为一个 String。
}
public class LoggingExceptions {
    public static void main(String[] args) {
        try {
            throw new LoggingException();
        } catch(LoggingException e) {
            System.err.println("Caught " + e);
        }
        try {
            throw new LoggingException();
        } catch(LoggingException e) {
            System.err.println("Caught " + e);
        }
    }
}
