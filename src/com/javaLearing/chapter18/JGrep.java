package com.javaLearing.chapter18;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JGrep {
    public static void main(String[] args) throws Exception{
        if (args.length < 2){
            System.out.println("Usage: java JGrep file regex!!!");
            System.exit(0);//
        }
        Pattern p = Pattern.compile(args[1]);

        //迭代输入的行
        int index = 0;
        Matcher m = p.matcher("");
        for (String lines: Files.readAllLines(Paths.get(args[0]))){
            m.reset(lines);
            while (m.find())
                System.out.println(index++ + ": " +
                        m.group() + ": " + m.start());
        }

    }//java JGrep.java ./JGrep.java 'return|for|String'
}
