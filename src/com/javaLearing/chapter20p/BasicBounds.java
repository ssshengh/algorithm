package com.javaLearing.chapter20p;

interface HasColor{
    java.awt.Color getColor();
}

class WithColor<T extends HasColor>{
    T item;
    WithColor(T item){this.item = item;}
    T getItem(){return item;}

    //第一个注意点，因为界限的存在，T实际上是HasColor的子类或者本身，因此可以直接调用其中方法
    java.awt.Color color(){
        return item.getColor();
    }
}
class Coord {
    public int x, y, z;
}

//多界限的使用,必须类在前面，接口在后面
class withColorCoord<T extends Coord & HasColor>{
    T item;
    withColorCoord(T item){this.item = item; }
    T getItem(){return item;}

    java.awt.Color getColor(){return item.getColor();}

    int getX(){return item.x;}
    int getY(){return item.y;}
    int getZ(){return item.z;}
}

interface Weight {
    int weight();
}
//和继承一样，只能有一个具体类，可以有多个接口
class Solid<T extends Coord & HasColor & Weight> {
    T item;

    Solid(T item) {
        this.item = item;
    }

    T getItem() {
        return item;
    }

    java.awt.Color color() {
        return item.getColor();
    }

    int getX() {
        return item.x;
    }

    int getY() {
        return item.y;
    }

    int getZ() {
        return item.z;
    }

    int weight() {
        return item.weight();
    }
}

//看继承的写法
class Bounded
        extends Coord implements HasColor, Weight{
    @Override
    public java.awt.Color getColor() {
        return null;
    }

    @Override
    public int weight() {
        return 0;
    }
}

public class BasicBounds {
    public static void main(String[] args) {
        //注意看使用，Bound继承于这三者(一个都不能少),才能写进去
        Solid<Bounded> solid= new Solid<>(new Bounded());
        solid.color();
        solid.getY();
        solid.weight();
        //Solid<withColorCoord> solid1 出错，实现不完全
        //Solid<HasColor>出错，没有实现其他两个
        //Solid<Weight>出错，没有实现其他两个
        //所以就是多继承的时候，必须同时实现了这三个才行，一个都不能少，就算这仨个也不行
    }
}
