package com.javaLearing.chapter18;

// strings/TestRegularExpression.java
// Simple regular expression demonstration
// {java TestRegularExpression
// abcabcabcdefabc "abc+" "(abc)+" }
import java.util.regex.*;

public class TestRegularExpression {
    public static void main(String[] args) {
        if(args.length < 2) {
            System.out.println(
                    "Usage:\njava TestRegularExpression " +
                            "characterSequence regularExpression+");
            System.exit(0);
        }
        System.out.println("Input: \"" + args[0] + "\"");//命令行第一个参数为输入判定参数
        for(String arg : args) {
            System.out.println(
                    "Regular expression: \"" + arg + "\"");
            Pattern p = Pattern.compile(arg);//命令行的几个参数分别放进去，进行匹配
            Matcher m = p.matcher(args[0]);//用参数分别与第一个对比，对比后结果得到一个对象
            while(m.find()) {//不断用正则去匹配，得到一个一个的匹配子序列
                System.out.println(
                        "Match \"" + m.group() + "\" at positions " +//匹配到的子序列
                                m.start() + "-" + (m.end() - 1));
            }
        }
    }
}
/* Output:
Input: "abcabcabcdefabc"
Regular expression: "abcabcabcdefabc"
Match "abcabcabcdefabc" at positions 0-14
Regular expression: "abc+"
Match "abc" at positions 0-2
Match "abc" at positions 3-5
Match "abc" at positions 6-8
Match "abc" at positions 12-14
Regular expression: "(abc)+"
Match "abcabcabc" at positions 0-8
Match "abc" at positions 12-14
*/

//Matcher.find() 方法可用来在 CharSequence 中查找多个匹配。例如：
class Finding{
    public static void main(String[] args) {
        //另一种构造Matcher的方法：Pattern调用compile是得到正则表达式，在正则基础上调用原数组得到Matcher对象
        Matcher m = Pattern.compile("\\w+")
                .matcher("Evening is full of the linnet's wings");
        while (m.find())
            System.out.println(m.group()+ " ");
        System.out.println();
        int i = 0;
        while(m.find(i)) {
            System.out.print(m.group() + " ");
            i++;
            System.out.println();
            //而第二个重载的 find() 接收一个整型参数，该整数表示字符串中字符的位置，并以其作为搜索的起点。
            // 从结果可以看出，后一个版本的 find() 方法能够根据其参数的值，不断重新设定搜索的起始位置。
        }
    }
}
