package com.javaLearing.chapter17;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class ListOfLines {
    public static void main(String[] args) throws Exception{
        Files.readAllLines(Paths.get("/Users/shenheng/Code/src/com/javaLearing/chapter14/Cheese.dat"))
                .stream()
                .filter(line -> !line.startsWith("//"))
                .map(line->line.substring(0, line.length()/2))
                .forEach(System.out::println);
    }
}
//跳过注释行，其余的内容每行只打印一半。 这实现起来很简单：你只需将 Path 传递给 readAllLines() （以前的 java 实现这个功能很复杂）。
// readAllLines() 有一个重载版本，包含一个 Charset 参数来存储文件的 Unicode 编码。

class Writing {
    static Random rand = new Random(47);
    static final int SIZE = 1000;

    public static void main(String[] args) throws Exception {
        // Write bytes to a file:
        byte[] bytes = new byte[SIZE];
        rand.nextBytes(bytes);
        Files.write(Paths.get("bytes.dat"), bytes);
        System.out.println("bytes.dat: " + Files.size(Paths.get("bytes.dat")));

        // Write an iterable to a file:
        List<String> lines = Files.readAllLines(
                Paths.get("/Users/shenheng/Code/src/com/javaLearing/chapter14/Cheese.dat"));
        Files.write(Paths.get("Cheese.dat"), lines);
        System.out.println("Cheese.txt: " + Files.size(Paths.get("Cheese.dat")));
    }
}
/**
 * 如果文件大小有问题怎么办？ 比如说：
 *
 * 文件太大，如果你一次性读完整个文件，你可能会耗尽内存。
 *
 * 您只需要在文件的中途工作以获得所需的结果，因此读取整个文件会浪费时间。
 *
 * Files.lines() 方便地将文件转换为行的 Stream：
 * */

class ReadLineStream {
    public static void main(String[] args) throws Exception {
        Files.lines(Paths.get("src/com/javaLearing/chapter17/PathInfo.java"))
                .skip(13)
                .findFirst()
                .ifPresent(System.out::println);
    }
}

//Files.lines() 对于把文件处理行的传入流时非常有用，但是如果你想在 Stream 中读取，处理或写入怎么办？这就需要稍微复杂的代码：
class StreamInAndOut {
    public static void main(String[] args) {
        try(
                Stream<String> input =
                        Files.lines(Paths.get("src/com/javaLearing/chapter17/Find.java"));
                PrintWriter output =
                        new PrintWriter("StreamInAndOut.txt")
                //因为我们在同一个块中执行所有操作，所以这两个文件都可以在相同的 try-with-resources 语句中打开。
                // PrintWriter 是一个旧式的 java.io 类，允许你“打印”到一个文件，所以它是这个应用的理想选择。
                // 如果你看一下 StreamInAndOut.txt，你会发现它里面的内容确实是大写的。
        ) {
            input.map(String::toUpperCase)
                    .forEachOrdered(output::println);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}