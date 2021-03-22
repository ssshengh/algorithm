package com.javaLearing.chapter20Q;

// generics/SimpleQueue.java

// A different kind of Iterable collection
import java.util.*;

public class SimpleQueue<T> implements Iterable<T> {
    private LinkedList<T> storage = new LinkedList<>();
    public void add(T t) { storage.offer(t); }
    public T get() { return storage.poll(); }
    @Override
    public Iterator<T> iterator() {
        return storage.iterator();
    }
}
