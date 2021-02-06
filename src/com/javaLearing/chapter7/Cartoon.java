package com.javaLearing.chapter7;

// reuse/Cartoon.java
// (c)2017 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://OnJava8.com for more book information.
// Constructor calls during inheritance

class Art {
    Art() {
        System.out.println("Art constructor");
    }
}

class Drawing extends Art {
    Drawing() {
        System.out.println("Drawing constructor");
    }
}

public class Cartoon extends Drawing {
    //即使不为 Cartoon 创建构造函数，编译器也会为你合成一个无参数构造函数，调用基类构造函数。尝试删除 Cartoon 构造函数来查看这个。
    public Cartoon() {
        System.out.println("Cartoon constructor");
    }
    public static void main(String[] args) {
        Cartoon x = new Cartoon();
    }
}
//注意输出结果，调用了最上层的构造函数：构造从基类向外进行！！
/* Output:
Art constructor
Drawing constructor
Cartoon constructor
*/

