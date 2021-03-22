package com.javaLearing.chapter18;

// strings/SimpleRead.java
import java.io.*;

public class SimpleRead {
    public static BufferedReader input =
            new BufferedReader(new StringReader(
                    "Sir Robin of Camelot\n22 1.61803"));
    /**
     * input 字段使用的类来自 java.io，附录:流式 I/O 详细介绍了相关内容。
     * StringReader 将 String 转化为可读的流对象，然后用这个对象来构造 BufferedReader 对象，
     * 因为我们要使用 BufferedReader 的 readLine() 方法。
     * 最终，我们可以使用 input 对象一次读取一行文本，就像从控制台读入标准输入一样。
     * */

    public static void main(String[] args) {
        try {
            System.out.println("What is your name?");
            String name = input.readLine();
            System.out.println(name);
            System.out.println("How old are you? " +
                    "What is your favorite double?");
            System.out.println("(input: <age> <double>)");
            String numbers = input.readLine();
            System.out.println(numbers);

            String[] numArray = numbers.split(" ");
            int age = Integer.parseInt(numArray[0]);
            double favorite = Double.parseDouble(numArray[1]);
            System.out.format("Hi %s.%n", name);
            System.out.format("In 5 years you will be %d.%n", age + 5);
            System.out.format("My favorite double is %f.", favorite / 2);
        } catch(IOException e) {
            System.err.println("I/O exception");
        }
    }
}
/* Output:
What is your name?
Sir Robin of Camelot
How old are you? What is your favorite double?
(input: <age> <double>)
22 1.61803
Hi Sir Robin of Camelot.
In 5 years you will be 27.
My favorite double is 0.809015.
*/
