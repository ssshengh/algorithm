package com.javaLearing.chapter22;
enum Signal { GREEN, YELLOW, RED, }
public class TrafficLight {
    Signal color = Signal.RED;
    public void change() {
        // Note you don't have to say Signal.RED
        // in the case statement:
        switch (color) {//增强型switch
            case RED -> color = Signal.GREEN;
            case GREEN -> color = Signal.YELLOW;
            case YELLOW -> color = Signal.RED;
        }
    }
    @Override
    public String toString() {
        return "The traffic light is " + color;
    }

    public static void main(String[] args) {
        TrafficLight t = new TrafficLight();
        for(int i = 0; i < 7; i++) {
            System.out.println(t);
            t.change();
        }
    }


}
