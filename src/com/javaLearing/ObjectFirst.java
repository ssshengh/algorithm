package com.javaLearing;

public class ObjectFirst {
    public static void main(String[] args) {
        {
            String s = new String("aaaaa");
        }
        System.getProperties().list(System.out);
        System.out.println(System.getProperty("user.name"));
        System.out.println(System.getProperty("java.library.path"));
    }

}
