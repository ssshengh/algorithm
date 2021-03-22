package com.javaLearing.chapter15;

class ThreeException extends Exception{}
public class FinallyWorks {
    public static int count = 0;

    public static void main(String[] args) {
        while (true){
            try {
                if (count++ == 0)
                    throw new ThreeException();
                System.out.println("没毛病！");
            }catch (ThreeException e){
                System.out.println("抛出ThreeException");
            }finally {
                System.out.println("In finally clause");
                if (count == 2)
                    break;
            }//无论是否抛出异常，finally字块都会被运行
        }
    }
}//这也为解决 Java 不允许我们回到异常抛出点这一问题，提供了一个思路。
// 如果将 try 块放在循环里，就可以设置一种在程序执行前一定会遇到的异常状况。
// 还可以加入一个 static 类型的计数器或者别的装置，使循环在结束以前能尝试一定的次数。这将使程序的健壮性更上一个台阶。
