package com.javaLearing.chapter15;

public class MultipleReturns {
    public static void f(int i ){
        System.out.println("Initialization that requires cleanup");

        try {
            System.out.println("Point 1");
            if(i == 1) return;
            System.out.println("Point 2");
            if(i == 2) return;
            System.out.println("Point 3");
            if(i == 3) return;
            System.out.println("End");
            return;
        }finally {
            System.out.println("Performing cleanup");
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i< 4; i++)
            f(i);
    }//可以看见，return不能阻止程序的进行，在执行完try后一定会执行finally
}
