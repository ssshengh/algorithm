package com.javaLearing.chapter15;

// exceptions/MessyExceptions.java
import java.io.*;
public class MessyExceptions {
    public static void main(String[] args) {
        InputStream in = null;
        try {
            in = new FileInputStream(
                    new File("/Users/shenheng/Code/src/com/javaLearing/chapter15/MessyExceptions.java"));
            int contents = in.read();
            // Process contents
        } catch(IOException e) {
            // Handle the error
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch(IOException e) {
                    // Handle the close() error
                }
            }
        }
    }
}//当 finally 子句有自己的 try 块时，感觉事情变得过于复杂。

class TryWithResources {
    public static void main(String[] args) {
        //但是现在try可以跟一个带括号的定义 ——这里是我们创建的 FileInputStream 对象。
        // 括号内的部分称为资源规范头（resource specification header）。
        try(
                InputStream in = new FileInputStream(
                        new File("TryWithResources.java"))
        ) {
            int contents = in.read();
            // Process contents
        } catch(IOException e) {
            // Handle the error
        }
        //更重要的是，无论你如何退出 try 块（正常或通过异常），和以前的 finally 子句等价的代码都会被执行，
        // 并且不用编写那些杂乱而棘手的代码。这是一项重要的改进。
    }
}

