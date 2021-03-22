package com.javaLearing.chapter22;

import java.util.stream.Stream;

public enum SpaceShip {
    SCOUT, CARGO, TRANSPORT,
    CRUISER, BATTLESHIP, MOTHERSHIP;

    @Override
    public String toString() {
        String id = name();//继承过来的enum的方法，获取枚举的名字
        String lower = id.substring(1).toLowerCase();//去掉第一个，然后后面的小写
        return id.charAt(0) + lower;
    }

    public static void main(String[] args) {
        Stream.of(values()).forEach(System.out::println);
    }
}
