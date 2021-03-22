package com.javaLearing.chapter9;

// polymorphism/music/Music2.java
// Overloading instead of upcasting
// {java polymorphism.music.Music2}


/**
 * 从music.java中可以看到向上转型，但为什么需要这么做——使得可以忘记对象的类型？
 * 因为不这么做，就会导致，需要为每一个继承类编写一个相同的方法:见下
 * */
class Stringed extends Instrument {
    @Override
    public void play(Note n) {
        System.out.println("Stringed.play() " + n);
    }
}

class Brass extends Instrument {
    @Override
    public void play(Note n) {
        System.out.println("Brass.play() " + n);
    }
}

public class Music2 {
    public static void tune(Wind i) {
        i.play(Note.MIDDLE_C);
    }

    public static void tune(Stringed i) {
        i.play(Note.MIDDLE_C);
    }

    public static void tune(Brass i) {
        i.play(Note.MIDDLE_C);
    }

    public static void main(String[] args) {
        Wind flute = new Wind();
        Stringed violin = new Stringed();
        Brass frenchHorn = new Brass();
        tune(flute); // No upcasting
        tune(violin);
        tune(frenchHorn);
    }
}

