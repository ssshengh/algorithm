package com.javaLearing.chapter7;

// reuse/Wind.java
// Inheritance & upcasting
class Instrument {
    public void play() {}

    static void tune(Instrument i) {
        // ...
        i.play();
    }
}

// Wind objects are instruments
// because they have the same interface:
public class Wind extends Instrument {
    public static void main(String[] args) {
        Wind flute = new Wind();
        /**
         * tune() 方法接受了一个 Instrument 类型的引用。但是，在 Wind 的 main() 方法里，tune() 方法却传入了一个 Wind 引用。
         * 鉴于 Java 对类型检查十分严格，一个接收一种类型的方法接受了另一种类型看起来很奇怪，
         * 除非你意识到 Wind 对象同时也是一个 Instrument 对象，而且 Instrument 的 tune 方法一定会存在于 Wind 中。
         * 在 tune() 中，代码对 Instrument 和 所有 Instrument 的派生类起作用，这种把 Wind 引用转换为 Instrument 引用的行为称作向上转型。
         * */
        Instrument.tune(flute); // Upcasting
    }
}
