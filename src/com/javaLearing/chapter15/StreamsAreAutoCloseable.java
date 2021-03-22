package com.javaLearing.chapter15;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class StreamsAreAutoCloseable {
    public static void main(String[] args) throws IOException {
        try(
                Stream<String> in = Files.lines(
                        Paths.get("/Users/shenheng/Code/src/com/javaLearing/chapter15/StreamsAreAutoCloseable.java"));
                PrintWriter outFile = new
                        PrintWriter("Results.txt");
                /**
                 * 你在这里可以看到其他的特性：资源规范头中可以包含多个定义，并且通过分号进行分割（最后一个分号是可选的）。
                 * 规范头中定义的每个对象都会在 try 语句块运行结束之后调用 close() 方法。
                 * */
                ){
            in.skip(5)
                    .limit(1)
                    .map(String::toLowerCase)
                    .forEachOrdered(outFile::println);
        }//try-with-resources 里面的 try 语句块可以不包含 catch 或者 finally 语句而独立存在。
        // 在这里，IOException 被 main() 方法抛出，所以这里并不需要在 try 后面跟着一个 catch 语句块。
    }
}
