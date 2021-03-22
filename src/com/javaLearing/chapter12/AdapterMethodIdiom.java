package com.javaLearing.chapter12;

// collections/AdapterMethodIdiom.java
// The "Adapter Method" idiom uses for-in
// with additional kinds of Iterables
import java.util.*;

class ReversibleArrayList<T> extends ArrayList<T> {
    ReversibleArrayList(Collection<T> c) {
        super(c);
    }

    public Iterable<T> reversed() {

        return new Iterable<T>() {
            public Iterator<T> iterator() {
                //匿名内部类
                return new Iterator<T>() {
                    int current = size() - 1;
                    public boolean hasNext() {
                        return current > -1;
                    }
                    //实现反向遍历
                    public T next() { return get(current--); }
                    public void remove() { // Not implemented
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };

    }
}

public class AdapterMethodIdiom {
    public static void main(String[] args) {
        ReversibleArrayList<String> ral =
                new ReversibleArrayList<String>(
                        Arrays.asList("To be or not to be".split(" ")));

        // Grabs the ordinary iterator via iterator():自带的Iterable<T>类型方法，正向遍历
        for(String s : ral)
            System.out.print(s + " ");
        System.out.println();
        // Hand it the Iterable of your choice，调用了刚刚重新写的Iterable<T>类型方法
        for(String s : ral.reversed())
            System.out.print(s + " ");
    }
}
/* Output:
To be or not to be
be to not or be To
*/
