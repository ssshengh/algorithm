package com.javaLearing.chapter12;

// collections/IterableClass.java
// Anything Iterable works with for-in
import java.util.*;

public class IterableClass implements Iterable<String> {
    protected String[] words = ("And that is how " +
            "we know the Earth to be banana-shaped."
    ).split(" ");

    /**
     * 注意这种匿名内部类的写法，也是一种迭代器模版
     * 这里体现出了匿名内部类的强大，他不是一个is-a关系，而是一个单独的个体，所以他匿名的继承了Iterator<String>类，接口也一样
     * 变成其继承类，但是只用实现需要用的这个方法
     * */
    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < words.length;
            }
            @Override
            public String next() { return words[index++]; }
            @Override
            public void remove() { // Not implemented
                throw new UnsupportedOperationException();
            }
        };
    }


    public static void main(String[] args) {
        for(String s : new IterableClass())
            System.out.print(s + " ");
    }
}
/* Output:
And that is how we know the Earth to be banana-shaped.
*/

