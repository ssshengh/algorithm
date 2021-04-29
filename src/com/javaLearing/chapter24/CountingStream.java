package com.javaLearing.chapter24;

import java.util.stream.IntStream;

public class CountingStream {
    public static void main(String[] args)  {
        //这不仅更容易理解，而且我们需要做的就是将 parallel() 插入到其他顺序操作中，然后一切都在同时运行。
        System.out.println(
                IntStream.range(0,10).parallel()
                .mapToObj(CountingTask::new)
                .map(countingTask -> {
                    try {
                        return countingTask.call();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .reduce(0, Integer::sum)
        );
    }
}
