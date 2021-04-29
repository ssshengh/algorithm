package com.javaLearing.chapter24P;

import java.util.concurrent.*;

public class StickHolder {
    private static class Chopstick {
    }

    //为简单起见，Chopstick(static) 实际上不是由 StickHolder 生产的，而是在其类中保持私有的。

    //注意，该类中的所有线程安全都是通过 BlockingQueue 实现的。
    private Chopstick stick = new Chopstick();//筷子，每个哲学家需要两根筷子吃饭，但是他们中间只有一根
    private BlockingQueue<Chopstick> holder = new ArrayBlockingQueue<>(1);

    //如果您调用了pickUp()，而 stick 不可用，那么pickUp()将阻塞该 stick，直到另一个哲学家调用putDown() 将 stick 返回。
    public void pickUp(){
        try {
            holder.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void putDown() {
        try {
            holder.put(stick);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public StickHolder() {
        putDown();
    }
}
