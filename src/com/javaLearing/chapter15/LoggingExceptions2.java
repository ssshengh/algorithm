package com.javaLearing.chapter15;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

public class LoggingExceptions2 {
    private static Logger logger = Logger.getLogger("LoggingExceptions2");

    static void logException(Exception e){
        //自定义，在get到Exception时该怎么做
        StringWriter trace = new StringWriter();
        e.printStackTrace(new PrintWriter(trace));//把错误信息写入trace
        logger.severe(trace.toString());//把写入的错误信息输出到日志
    }

    public static void main(String[] args) {
        try {
            throw new NullPointerException();
        }catch (Exception e ) {
            logException(e);
        }
    }
}
