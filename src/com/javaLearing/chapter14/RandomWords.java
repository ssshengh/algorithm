package com.javaLearing.chapter14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomWords implements Supplier<String> {
    //Supplier 函数接口
    List<String> words = new ArrayList<>();
    Random rand = new Random(47);
    RandomWords(String fname) throws IOException{
        //用一个列表存储所有的行
        List<String> lines = Files.readAllLines(Paths.get(fname));
        //略过第一行处理
        for (String line: lines.subList(1, lines.size())){//注意这个取子列操作
            for (String word: line.split("[ .?,]+"))
            //每一行都被 split() 通过方括号内的空格或其它标点符号分割。
            // 在方括号后面的 + 表示 + 前面的东西可以出现一次或者多次（正则表达式）。
                words.add(word.toLowerCase(Locale.ROOT));
        }
        //你会发现构造函数使用命令式编程（外部迭代）进行循环。
        // 在以后的例子中，你会看到我们是如何去除命令式编程。这种旧的形式虽不是特别糟糕，但使用流会让人感觉更好。
    }

    public String get() {
        return words.get(rand.nextInt(words.size()));
    }
    @Override
    public String toString(){
        //在toString() 和main()方法中你看到了 collect() 操作，它根据参数来结合所有的流元素。
        // 当你用 Collectors.joining()作为 collect() 的参数时，将得到一个String 类型的结果，
        // 该结果是流中的所有元素被joining()的参数隔开。
        return words.stream()
                .collect(Collectors.joining(" "));
    }

    public static void main(String[] args) throws Exception{
        System.out.println(
                //我们提前看到了 Stream.generate() 的用法，它可以把任意  Supplier<T> 用于生成 T 类型的流。
                Stream.generate(new RandomWords("Cheese.dat"))
                .limit(10)
                .collect(Collectors.joining(" "))
        );
    }
}
