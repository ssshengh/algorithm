package com.javaLearing.chapter14;

public class ForEach {
    static final int size = 14;

    public static void main(String[] args) {
        RandInts.rands().limit(size)
                .forEach(n -> System.out.format("%d ", n));
        System.out.println("\n接下来使用并行");
        RandInts.rands().limit(size)
                .parallel().forEach(n -> System.out.format("%d ", n));

        //可以看见，上面的是另一个处理器的结果，得到的与第一个不同
        //而这个利用parallel() 和 forEachOrdered()保持了和原来的一致
        System.out.println();
        RandInts.rands().limit(size)
                .parallel()
                .forEachOrdered(n -> System.out.format("%d ", n));
        //同时使用了 parallel() 和 forEachOrdered() 来强制保持原始流顺序。
    }
    // 为了方便测试不同大小的流，我们抽离出了 SZ 变量。然而即使 SZ 值为14也产生了有趣的结果。
    // 在第一个流中，未使用 parallel() ，因此以元素从 rands()出来的顺序输出结果。在第二个流中，引入parallel() ，
    // 即便流很小，输出的结果的顺序也和前面不一样。这是由于多处理器并行操作的原因，如果你将程序多运行几次，
    // 你会发现输出都不相同，这是多处理器并行操作的不确定性造成的结果。
}
