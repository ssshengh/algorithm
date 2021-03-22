package com.javaLearing.chapter9;

class Instrument {
    public void play(Note n) {
        System.out.println("Instrument.play()");
    }
}

class Wind extends Instrument {
    // Redefine interface method:
    @Override
    public void play(Note n) {
        System.out.println("Wind.play() " + n);
    }
}

public class Music {
    public static void tune(Instrument i) {
        // ...
        i.play(Note.B_FLAT);
    }

    public static void main(String[] args) {
        Wind flute = new Wind();
        tune(flute); // Upcasting,将wind向上转型为instrument
    }
    //在 main() 中你看到了 tune() 方法传入了一个 Wind 引用，而没有做类型转换。
    // 这样做是允许的—— Instrument 的接口一定存在于 Wind 中，因此 Wind 继承了 Instrument。
    // 从 Wind 向上转型为 Instrument 可能“缩小”接口，但不会比 Instrument 的全部接口更少。
}