package com.javaLearing.chapter24P;

import onjava.Timer;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.*;

public class OnePizza {
    public static void main(String[] args) {
        Pizza za = new Pizza(0);
        System.out.println(Timer.duration(
                () -> {
                    while (!za.complete()) za.next();
                }
        ));
    }
}
//时间以毫秒为单位，加总所有步骤的工作量，会得出与我们的期望值相符的数字。
// 如果你以这种方式制作了五个披萨，那么你会认为它花费的时间是原来的五倍。 但是，如果这还不够快怎么办？ 我们可以从尝试并行流方法开始：
class PizzaStreams{
    static final int QUANTITY = 8;

    public static void main(String[] args){
        Timer timer = new Timer();
        IntStream.range(0, QUANTITY)
                .mapToObj(Pizza::new)
                .parallel()//[1]现在，我们制作五个披萨的时间与制作单个披萨的时间就差不多了。 尝试删除标记为[1] 的行后，你会发现它花费的时间是原来的五倍。
                // 你还可以尝试将 QUANTITY 更改为 4、8、10、16 和 17，看看会有什么不同，并猜猜看为什么会这样。
                .forEach(za -> { while(!za.complete()) za.next(); });
        System.out.println(timer.duration());
    }
}

//PizzaStreams 类产生的每个并行流在它的forEach()内完成所有工作，如果我们将其各个步骤用映射的方式一步一步处理，情况会有所不同吗？
class PizzaParallelSteps{
    static final int QUANTITY = 5;

    public static void main(String[] args){
        Timer timer = new Timer();
        IntStream.range(0, QUANTITY)
                .mapToObj(Pizza::new)
                .parallel()
                .map(Pizza::roll)
                .map(Pizza::sauce)
                .map(Pizza::cheese)
                .map(Pizza::toppings)
                .map(Pizza::bake)
                .map(Pizza::slice)
                .map(Pizza::box)
                .forEach(za -> System.out.println(za));
        System.out.println(timer.duration());
        //答案是“否”，事后看来这并不奇怪，因为每个披萨都需要按顺序执行步骤。
        // 因此，没法通过分步执行操作来进一步提高速度，就像上文的 PizzaParallelSteps.java 里面展示的一样。
    }
}

//我们可以使用 CompletableFutures 重写这个例子：
class CompletablePizza{
    static final int QUANTITY = 5;

    public static CompletableFuture<Pizza> makeCF(Pizza za){
        return CompletableFuture
                .completedFuture(za)
                .thenApplyAsync(Pizza::roll)
                .thenApplyAsync(Pizza::sauce)
                .thenApplyAsync(Pizza::cheese)
                .thenApplyAsync(Pizza::toppings)
                .thenApplyAsync(Pizza::bake)
                .thenApplyAsync(Pizza::slice)
                .thenApplyAsync(Pizza::box);
    }

    public static void show(CompletableFuture<Pizza> cf){
        try{
            System.out.println(cf.get());
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        Timer timer = new Timer();
        List<CompletableFuture<Pizza>> pizzas =
                IntStream.range(0, QUANTITY)
                        .mapToObj(Pizza::new)
                        .map(CompletablePizza::makeCF)
                        .collect(Collectors.toList());
        System.out.println(timer.duration());
        pizzas.forEach(CompletablePizza::show);
        System.out.println(timer.duration());
    }
}