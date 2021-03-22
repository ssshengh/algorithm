package com.javaLearing.chapter17;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;

//java.nio.file.Paths 类包含一个重载方法 static get()**，该方法接受一系列 **String 字符串或一个统一资源标识符(URI)作为参数，
// 并且进行转换返回一个 Path 对象
public class PathInfo {
    static void show(String id, Object p) {
        System.out.println(id + ": " + p);
    }

    static void info(Path p) {
        show("toString", p);
        show("Exists", Files.exists(p));
        show("RegularFile", Files.isRegularFile(p));
        show("Directory", Files.isDirectory(p));
        show("Absolute", p.isAbsolute());
        show("FileName", p.getFileName());
        show("Parent", p.getParent());
        show("Root", p.getRoot());
        System.out.println("******************");
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("os.name"));
        info(Paths.get("C:", "path", "to", "nowhere", "NoFile.txt"));
        //这个方法列出了Path对象可用的一些方法

        Path p = Paths.get("/Users/shenheng/Code/src/com/javaLearing/chapter17/PathInfo.java");
        info(p);
        System.out.println("*****************转换为绝对路径");
        Path ap = p.toAbsolutePath();
        info(ap);
        info(ap.getParent());
        System.out.println("******************转换为真实路径");
        try {
            info(p.toRealPath());
        } catch(IOException e) {
            System.out.println(e);
        }
        System.out.println("路径转换为URL");
        URI u = p.toUri();
        System.out.println("URI: " + u);
        Path puri = Paths.get(u);
        System.out.println(Files.exists(puri));
        File f = ap.toFile(); // Don't be fooled
    }
}
