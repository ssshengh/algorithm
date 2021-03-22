package com.javaLearing.chapter18;

import java.io.PrintStream;
import java.util.Formatter;

public class Turtle {
    private String name;
    private Formatter f;
    public Turtle(String name, Formatter f){
        this.f = f; this.name = name;
    }
    public void move(int x, int y) {
        f.format("%s The Turtle is at (%d,%d)%n",
                name, x, y);
    }
    public static void main(String[] args) {
        PrintStream outAlias = System.out;//Io类

        Turtle tommy = new Turtle("Tommy",
                new Formatter(System.out));
        Turtle terry = new Turtle("Terry",
                new Formatter(outAlias));
        tommy.move(0,0);
        terry.move(4,8);
        tommy.move(3,4);
        terry.move(2,5);
        tommy.move(3,3);
        terry.move(3,3);
        //Formatter 的重载构造器支持输出到多个路径，不过最常用的还是 PrintStream()（如上例）、OutputStream 和 File。
    }
}
