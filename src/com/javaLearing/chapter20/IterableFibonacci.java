package com.javaLearing.chapter20;

import java.util.Iterator;

//通过继承来创建适配器类
public class IterableFibonacci extends Fibonacci implements Iterable<Integer>{
    private int n = 0;
    public IterableFibonacci(int count){n = count;}

    //实现迭代器,这里用匿名内部类
    public Iterator<Integer> iterator(){
        return new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                return n>0;
            }

            @Override
            public Integer next() {
                n--;
                return IterableFibonacci.this.get();//注意这个get是Fibonacci里面的
            }//从这里就调用了Fibonacci对象

            @Override
            public void remove() { // Not implemented
                throw new UnsupportedOperationException();
            }
        };
    }

    public static void main(String[] args) {
        for (int f : new IterableFibonacci(18))
            System.out.println(f);
    }

}
