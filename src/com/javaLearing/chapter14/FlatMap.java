package com.javaLearing.chapter14;

// streams/FlatMap.java
import java.util.stream.*;
class StreamOfStreams {
    public static void main(String[] args) {
        Stream.of(1, 2, 3)
                .map(i -> Stream.of("Gonzo", "Kermit", "Beaker"))
                .map(e-> e.getClass().getName())
                .forEach(System.out::println);
    }
}
//java.util.stream.ReferencePipeline$Head
//java.util.stream.ReferencePipeline$Head
//java.util.stream.ReferencePipeline$Head
//我们天真地希望能够得到字符串流，但实际得到的却是“Head”流的流。我们可以使用 flatMap() 解决这个问题：
public class FlatMap {
    public static void main(String[] args) {
        Stream.of(1, 2, 3)
                .flatMap(i -> Stream.of("Gonzo", "Fozzie", "Beaker"))
                .forEach(System.out::println);
        //这里得到的就是流的组合了
    }
}

