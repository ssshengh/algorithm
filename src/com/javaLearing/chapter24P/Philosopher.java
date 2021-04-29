package com.javaLearing.chapter24P;

public class Philosopher implements Runnable{
    private final int seat;
    private final StickHolder left, right;

    //每个哲学家都是一项任务，他们试图把筷子分别 pickUp() 在左手和右手上，这样筷子才能吃东西，然后通过 putDown() 放下 stick
    public Philosopher(int seat, StickHolder left, StickHolder right) {
        this.seat = seat;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "哲学家：" + seat;
    }

    @Override
    public void run() {
        System.out.println("Thinking");   // [1]

        //没有两个哲学家可以同时成功调用 take() 同一只筷子。
        // 另外，如果一个哲学家已经拿过筷子，那么下一个试图拿起同一根筷子的哲学家将阻塞，等待其被释放
        right.pickUp();
        left.pickUp();
        System.out.println(this + " eating");//左右手都拿起来吃，此时阻塞了两根筷子
        right.putDown();
        left.putDown();
    }
}

