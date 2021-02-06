package com.javaLearing.chapter7;

// reuse/DerivedSpaceShip.java
// (c)2017 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://OnJava8.com for more book information.

//利用SpaceShipControl的一种办法是继承
public class
DerivedSpaceShip extends SpaceShipControls {
    private String name;
    //新加方法
    public DerivedSpaceShip(String name) {
        this.name = name;
    }
    //继承和重用方法
    @Override
    public String toString() { return name; }
    public static void main(String[] args) {
        DerivedSpaceShip protector =
                new DerivedSpaceShip("NSEA Protector");
        protector.forward(100);
    }
}
