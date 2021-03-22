package com.javaLearing.chapter14;
import java.io.*;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

// 比较RandomWords.java
public class FileToWordsBuilder {
    //首先创建一个 builder 对象，然后将创建流所需的多个信息传递给它，最后builder 对象执行”创建“流的操作。
    Stream.Builder<String> builder = Stream.builder();

    public FileToWordsBuilder(String filePath) throws Exception{
        Files.lines(Paths.get(filePath))
                .skip(1)//跳过第一行
                .forEach(line -> {
                    for (String w : line.split("[ .?,]+"))
                        builder.add(w);//朝这里新建了个流
                });
    }
    //你可以添加一个标志位用于查看 build() 是否被调用，并且可能的话增加一个可以添加更多单词的方法。在 Stream.Builder 调用 build() 方法后继续尝试添加单词会产生一个异常。
    Stream<String> stream(){
        return builder.build();
    }

    public static void main(String[] args) {
        try {
            new FileToWordsBuilder("Cheese.dat")
                    .stream()
                    .limit(7)
                    .map(w -> w + " ")
                    .forEach(System.out::print);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
