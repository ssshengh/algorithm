package com.javaLearing.chapter7;

// reuse/SpaceShipDelegation.java
// (c)2017 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://OnJava8.com for more book information.

/**
 * 继承得到的DerivedSpaceShip 并不是真正的“一种” SpaceShipControls ，即使你“告诉” DerivedSpaceShip 调用 forward()。
 * 更准确地说，一艘宇宙飞船应该是：包含了 SpaceShipControls，同时 SpaceShipControls 中的所有方法都暴露在宇宙飞船中。
 * 委托解决了这个难题:
 * */
public class SpaceShipDelegation {
    private String name;
    private SpaceShipControls controls =
            new SpaceShipControls();
    public SpaceShipDelegation(String name) {
        this.name = name;
    }
    // Delegated methods:
    //注意到其实委托就是在里面调用了这个类
    public void back(int velocity) {
        controls.back(velocity);
    }
    public void down(int velocity) {
        controls.down(velocity);
    }
    public void forward(int velocity) {
        controls.forward(velocity);
    }
    public void left(int velocity) {
        controls.left(velocity);
    }
    public void right(int velocity) {
        controls.right(velocity);
    }
    public void turboBoost() {
        controls.turboBoost();
    }
    public void up(int velocity) {
        controls.up(velocity);
    }
    public static void main(String[] args) {
        SpaceShipDelegation protector =
                new SpaceShipDelegation("NSEA Protector");
        protector.forward(100);
    }
}

