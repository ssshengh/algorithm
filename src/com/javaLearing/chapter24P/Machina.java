package com.javaLearing.chapter24P;

import com.javaLearing.chapter24.Nap;

/**
 * 这是一个有限状态机，一个微不足道的机器，因为它没有分支......它只是从头到尾遍历一条路径。
 * work() 方法将机器从一个状态移动到下一个状态，并且需要 100 毫秒才能完成“工作”。
 */
public class Machina {
    public enum State{
        START, ONE, TWO, THREE, END;
        State sleep(){
            if (equals(END))
                return END;
            return values()[ordinal()+1];
        }
    }

    private State state = State.START;
    private final int id;
    public Machina(int id) {
        this.id = id;
    }

    public static Machina work(Machina m){
        if (!m.state.equals(State.END)) {//没有到达最后一个状态的时候，用100ms完成工作，然后跳到下一个状态
            System.out.println("inside" + m);
            new Nap(0.1);
            m.state = m.state.sleep();//不为END时往后递增一个
        }
        System.out.println(m);
        //System.out.println("inside" + m);
        return m;
    }

    @Override
    public String toString() {
        return"Machina" + id + ": " + (state.equals(State.END)? "complete" : state);
    }
}
