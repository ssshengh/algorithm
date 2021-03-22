package com.javaLearing.chapter15;

// exceptions/InputFile2.java
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;
public class InputFile2 {
    private String fname;

    public InputFile2(String fname) {
        this.fname = fname;
    }

    public Stream<String> getLines() throws IOException {
        return Files.lines(Paths.get(fname));
    }

    public static void
    main(String[] args) throws IOException {
        new InputFile2("/Users/shenheng/Code/src/com/javaLearing/chapter15/InputFile2.java").getLines()
                .skip(18)
                .limit(1)
                .forEach(System.out::println);
    }
}

