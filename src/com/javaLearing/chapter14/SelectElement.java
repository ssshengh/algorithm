package com.javaLearing.chapter14;

// streams/SelectElement.java
import java.util.*;
import java.util.stream.*;
import static com.javaLearing.chapter14.RandInts.*;
public class SelectElement {
    public static void main(String[] args) {
        System.out.println(rands().findFirst().getAsInt());
        System.out.println(
                rands().parallel().findFirst().getAsInt());
        System.out.println(rands().findAny().getAsInt());
        System.out.println(
                rands().parallel().findAny().getAsInt());
    }
    //无论流是否为并行化，findFirst() 总是会选择流中的第一个元素。
    // 对于非并行流，findAny()会选择流中的第一个元素（即使从定义上来看是选择任意元素）。
    // 在这个例子中，用 parallel() 将流并行化，以展示 findAny() 不选择流的第一个元素的可能性。
}
//如果必须选择流中最后一个元素，那就使用 reduce()。代码示例
class LastElement {
    public static void main(String[] args) {
        OptionalInt last = IntStream.range(10, 20)
                .reduce((n1, n2) -> n2);
        //n1是上一个元素，n2是当前元素

        System.out.println(last.orElse(-1));

        // Non-numeric object:
        Optional<String> lastobj =
                Stream.of("one", "two", "three")
                        .reduce((n1, n2) -> n2);
        System.out.println(
                lastobj.orElse("Nothing there!"));
    }
}
//reduce() 的参数只是用最后一个元素替换了最后两个元素，最终只生成最后一个元素。
// 如果为数字流，你必须使用相近的数字 Optional 类型（ numeric optional type），否则使用 Optional 类型，就像上例中的 Optional<String>。