package streamInJava8;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/*
* 与集合相比，流完成的工作是：说明想要完成什么任务，而不是说明如何实现它。
* */
public class AboutStream {
    public static <T> void show (String title, @NotNull Stream<T> stream){
        final int SIZE = 10;
        List<T> firstElements = stream.limit(SIZE+1).collect(Collectors.toList());
        System.out.print(title + ": ");

        for (int i = 0; i<firstElements.size(); i++){
            if (i>0) System.out.print(", ");
            if (i<SIZE) System.out.print(firstElements.get(i));
            else
                System.out.print("...");
        }
        System.out.println();
    }

    public static void main(String[] args) throws IOException
    {
        //首先来看看流和集合操作的对比：
        //其实感觉就是C++中流的引伸
        var contents = Files.readString(
                Paths.get("/Users/shenheng/bin/b.txt"));
        List<String> words = List.of(contents.split(" "));

        long count = 0;
        for (String w : words){
            if (w.length() > 5)
                count ++;
            System.out.print(w + "+++");
        }
        System.out.println("count is : "+ count);

        //同样的操作用流实现：任何一个集合都可以转化为流
        count = words.stream().filter(w -> w.length() > 5 ).count();
        System.out.println("by stream count is:" + count );

        count = words.parallelStream().filter(w -> w.length() > 5 ).count();
        System.out.println("并行流使用后：" + count);

        //流的几种创建方法：
        //page 7 可以看各个API的意义
        Path path = Paths.get("/Users/shenheng/bin/a.txt");
        var content1 = Files.readString(path);

        Stream<String> wordA = Stream.of(content1.split(" "));//of就是从什么创建流
        show("words", wordA);
        Stream<String> song = Stream.of("gently", "down", "the", "stream");
        show("song", song);
        Stream<String> silence = Stream.empty();
        show("silence", silence);

        Stream<String> echos = Stream.generate(() -> "Echo");
        //产生一个无限流，通过反复调用函数s(以后说的这个都是指鼠标放上面看见的定义)构建的，接受一个不包含任何引元的函数
        show("echo", echos);
        Stream<Double> randoms = Stream.generate(Math::random);//产生一个0-1之间的随机数
        show("Randoms", randoms );

        Stream<BigInteger> integers = Stream.iterate(BigInteger.ONE, n -> n.add(BigInteger.ONE));
        //iterate是一个迭代器，第一个参数是seed(随机数种子)，为输出流的第一个元素。第二个参数是个函数f，完成对第一个元素的函数实现，
        //其实就是f(seed)，然后流以后的每一个元素就是f(n-1) = n了。还有一个重载，在这两个参数中插入一个hasNext，在遇到不满足该谓词
        //的元素时终止
        //这个调用中，f函数的意义就是f(x) = x+1
        show("integers", integers);

        Stream<String> wordsAnotherWay = Pattern.compile("\\PL").splitAsStream(content1);
        //JAVA中大量API可以产生流，Pattern中的compile方法，可以通过某个正则表达式来分割一个charSequence对象。这里分割为了一个一个的单词
        show("wordsByPattern", wordsAnotherWay);

        Stream<String> wordsByScanner = new Scanner(content1).tokens();
        //产生一个扫描器的符号流

        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)){
            //返回一个包含了文件中所有行的流，元素为指定的行
            show("lines", lines);
        }

        Iterable<Path> iterable = FileSystems.getDefault().getRootDirectories();//产生关于root路径的迭代器
        Stream<Path> rootDirectories = StreamSupport.stream(iterable.spliterator(), false);
        //产生一个流，包含了一个由可分割迭代器组成的一个流，就是将迭代器转化为stream类型的方法，其中第二个参数是并行化选项
        show("directories ", rootDirectories);

        Iterator<Path> iterator = Paths.get("/Users/shenheng/bin").iterator();
        Stream<Path> pathComponents = StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                iterator, Spliterator.ORDERED), false);
        //通过一个iterator对象获得由其结果构成的流
        show("PathComponents", pathComponents);


        //注意流的终止操作执行完毕前，不要尝试修改流背后的集合！！！！！

    }
}
