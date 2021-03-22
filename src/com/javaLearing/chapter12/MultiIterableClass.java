package com.javaLearing.chapter12;

// collections/MultiIterableClass.java
// Adding several Adapter Methods
import java.util.*;

public class MultiIterableClass extends IterableClass {
    //通过重新写一个方法来实现for in反向遍历
    public Iterable<String> reversed() {
        return new Iterable<String>() {
            public Iterator<String> iterator() {
                return new Iterator<String>() {
                    int current = words.length - 1;
                    public boolean hasNext() {
                        return current > -1;
                    }
                    public String next() {
                        return words[current--];
                    }
                    public void remove() { // Not implemented
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }
    //注意，第二个方法 random() 没有创建它自己的 Iterator ，而是直接返回被打乱的 List 中的 Iterator 。
    public Iterable<String> randomized() {
        return new Iterable<String>() {
            public Iterator<String> iterator() {
                List<String> shuffled =
                        new ArrayList<String>(Arrays.asList(words));
                Collections.shuffle(shuffled, new Random(47));
                return shuffled.iterator();
            }
        };
    }
    //从输出中可以看到， Collections.shuffle() 方法不会影响到原始数组，而只是打乱了 shuffled 中的引用。
    // 之所以这样，是因为 randomized() 方法用一个 ArrayList 将 Arrays.asList() 的结果包装了起来。
    // 如果这个由 Arrays.asList() 生成的 List 被直接打乱，那么它将修改底层数组。

    public static void main(String[] args) {
        MultiIterableClass mic = new MultiIterableClass();
        for(String s : mic.reversed())
            System.out.print(s + " ");
        System.out.println();
        for(String s : mic.randomized())
            System.out.print(s + " ");
        System.out.println();
        for(String s : mic)
            System.out.print(s + " ");
    }
}
/* Output:
banana-shaped. be to Earth the know we how is that And
is banana-shaped. Earth that how the be And we know to
And that is how we know the Earth to be banana-shaped.
*/

