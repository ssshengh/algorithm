package com.javaLearing.chapter24P;

import com.javaLearing.chapter24.Nap;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

class Batter{
    static class Eggs{}
    static class Sugar{}
    static class Milk{}
    static class Flour{}

    static <T> T prepare(T ingredient){
        new Nap(0.1);//每准备一个都需要100ms
        return ingredient;
    }

    static <T> CompletableFuture<T> prep (T ingredient){
        return CompletableFuture.completedFuture(ingredient)
                .thenApplyAsync(Batter::prepare);
    }

    public static CompletableFuture<Batter> mix() {
        CompletableFuture<Eggs> eggs = prep(new Eggs());
        CompletableFuture<Milk> milk = prep(new Milk());
        CompletableFuture<Sugar> sugar = prep(new Sugar());
        CompletableFuture<Flour> flour = prep(new Flour());
        //竞争关系中，allOf需要四个全都准备好才能返回值，通过join来确保四者不会被提前结束
        CompletableFuture
                .allOf(eggs, milk, sugar, flour)
                .join();
        new Nap(0.1); // Mixing time   allOf() 等待所有的配料都准备好，然后使用更多些的时间将其混合成面糊。
        return CompletableFuture.completedFuture(new Batter());
    }
}

class Baked{
    //面糊准备好了，接下来需要把单批面糊放进四个平底锅烤
    static class Pan {
    }

    //装进平底锅
    static Pan pan(Batter b) {
        new Nap(0.1);
        return new Pan();
    }
    //加热
    static Baked heat(Pan p) {
        new Nap(0.1);
        return new Baked();
    }

    static CompletableFuture<Baked> bake(CompletableFuture<Batter> cfb) {
        return cfb
                .thenApplyAsync(Baked::pan)//新生成一个阶段，关键是注意异步的这个方法，依赖项是否增加了
                .thenApplyAsync(Baked::heat);
    }

    public static Stream<CompletableFuture<Baked>> batch() {
        CompletableFuture<Batter> batter = Batter.mix();
        return Stream.of(
                bake(batter),
                bake(batter),
                bake(batter),
                bake(batter)
        );
    }
}

//最后，我们制作了一批糖，并用它对蛋糕进行糖化：
public class Frosting {
    private Frosting() {
    }

    static CompletableFuture<Frosting> make() {
        new Nap(0.1);
        return CompletableFuture
                .completedFuture(new Frosting());
    }
}

class FrostedCake {
    public FrostedCake(Baked baked, Frosting frosting) {
        new Nap(0.1);
    }

    @Override
    public String toString() {
        return "FrostedCake";
    }

    public static void main(String[] args) {
        Baked.batch().forEach(//对煎好的每一个饼子进行接下来的糖化和成蛋糕
                baked -> baked//thenCombineAsync等待两个任务都完成，才会交给这个lambda函数
                        .thenCombineAsync(Frosting.make(),
                                (cake, frosting) ->
                                        new FrostedCake(cake, frosting))
                        .thenAcceptAsync(System.out::println)
                        .join());
    }
}
