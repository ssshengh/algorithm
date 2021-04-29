package com.javaLearing.chapter24P;

public interface HasID {
    int getID();
}
//然后 StaticIDField 类显式地实现该接口。代码示例：
class StaticIDField implements HasID {
    private static int counter = 0;
    private int id = counter++;
    public int getID() { return id; }
}