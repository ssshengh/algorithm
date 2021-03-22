package com.javaLearing.chapter18;

import java.util.Scanner;

public class ScannerDelimiter {
    public static void main(String[] args) {
        Scanner sc = new Scanner("12, 23, 45, 56, 67, 90");
        sc.useDelimiter("\\s*,\\s*");
        while (sc.hasNext())
            System.out.println(sc.nextInt());
    }
}
