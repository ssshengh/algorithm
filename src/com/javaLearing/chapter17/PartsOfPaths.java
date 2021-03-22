package com.javaLearing.chapter17;

import java.nio.file.*;


public class PartsOfPaths {
    public static void main(String[] args) {
        System.out.println(System.getProperty("os.name"));
        Path p = Paths.get("PartsOfPaths.java").toAbsolutePath();
        System.out.println(p);

        System.out.println("分段获取路径");
        for (int i =0; i<p.getNameCount(); i++)
            System.out.println(p.getName(i));

        System.out.println("查看是否最后一个字段是某个string");
        System.out.println("ends with '.java': " +
                p.endsWith(".java"));
        System.out.println(p.endsWith("PartsOfPaths.java"));

        System.out.println("for-each迭代路径");
        for(Path pp : p) {
            System.out.print(pp + ": ");
            System.out.print(p.startsWith(pp) + " : ");
            System.out.println(p.endsWith(pp));
        }
        System.out.println("Starts with " + p.getRoot() + " " + p.startsWith(p.getRoot()));
    }
}
