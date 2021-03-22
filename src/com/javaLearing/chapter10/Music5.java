package com.javaLearing.chapter10;

// interfaces/music5/Music5.java
// {java interfaces.music5.Music5}
import com.javaLearing.chapter9.Note;

interface Instrument1 {
    // Compile-time constant:
    int VALUE = 5; // static & final

    default void play(Note n)  // Automatically public
    {
        System.out.println(this + ".play() " + n);
    }

    default void adjust() {
        System.out.println("Adjusting " + this);
    }
}

class Wind1 implements Instrument1 {
    @Override
    public String toString() {
        return "Wind";
    }
}

class Percussion1 implements Instrument1 {
    @Override
    public String toString() {
        return "Percussion";
    }
}

class Stringed1 implements Instrument1 {
    @Override
    public String toString() {
        return "Stringed";
    }
}

class Brass1 extends Wind1 {
    @Override
    public String toString() {
        return "Brass";
    }
}

class Woodwind1 extends Wind1 {
    @Override
    public String toString() {
        return "Woodwind";
    }
}

public class Music5 {
    // Doesn't care about type, so new types
    // added to the system still work right:
    static void tune(Instrument1 i) {
        // ...
        i.play(Note.MIDDLE_C);
    }
//注意到，无论是将其向上转型为称作 Instrument 的普通类，或称作 Instrument 的抽象类，
// 还是叫作 Instrument 的接口，其行为都是相同的。
// 事实上，从 tune() 方法上看不出来 Instrument 到底是一个普通类、抽象类，还是一个接口。
    static void tuneAll(Instrument1[] e) {
        for (Instrument1 i: e) {
            tune(i);
        }
    }

    public static void main(String[] args) {
        // Upcasting during addition to the array:
        Instrument1[] orchestra = {
                new Wind1(),
                new Percussion1(),
                new Stringed1(),
                new Brass1(),
                new Woodwind1()
        };
        tuneAll(orchestra);
    }
}

