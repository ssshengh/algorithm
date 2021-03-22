package com.javaLearing.chapter18;

import java.util.Arrays;

public class IntegerMatch {
    public static void main(String[] args) {
        System.out.println("-1234".matches("-?\\d+"));
        System.out.println("5678".matches("-?\\d+"));
        System.out.println("+911".matches("-?\\d+"));
        System.out.println("+911".matches("(-|\\+)?\\d+"));
        //?就是要么有要么没有，不能是其他的
        //因此，我们的正则表达式应该描述为：“可能以一个加号或减号开头”。
        // 在正则表达式中，用括号将表达式进行分组，用竖线 | 表示或操作。也就是：
    }
}

//String类还自带了一个非常有用的正则表达式工具——split() 方法，其功能是“将字符串从正则表达式匹配的地方切开。”
class Splitting {
    public static String knights =
            "Then, when you have found the shrubbery, " +
                    "you must cut down the mightiest tree in the " +
                    "forest...with... a herring!";
    public static void split(String regex) {
        System.out.println(
                Arrays.toString(knights.split(regex)));//返回的是一个String数组
    }
    public static void main(String[] args) {
        split(" "); // Doesn't have to contain regex chars
        split("\\W+"); // Non-word characters
        split("n\\W+"); // 'n' followed by non-words
    }
    //第二个和第三个 split() 都用到了 \\W，它的意思是一个非单词字符（如果 W 小写，\\w，则表示一个单词字符）。
    //第三个 split() 表示“字母 n 后面跟着一个或多个非单词字符。”可以看到，在原始字符串中，与正则表达式匹配的部分，在最终结果中都不存在了。
}

//String.split() 还有一个重载的版本，它允许你限制字符串分割的次数。
//
//用正则表达式进行替换操作时，你可以只替换第一处匹配，也可以替换所有的匹配：
class Replacing {
    static String s = Splitting.knights;
    public static void main(String[] args) {
        System.out.println(
                s.replaceFirst("f\\w+", "located"));
        System.out.println(
                s.replaceAll("shrubbery|tree|herring","banana"));
    }
}//第一个表达式要匹配的是，以字母 f 开头，后面跟一个或多个字母（注意这里的 w 是小写的）。
// 并且只替换掉第一个匹配的部分，所以 “found” 被替换成 “located”。

//第二个表达式要匹配的是三个单词中的任意一个，因为它们以竖线分割表示“或”，并且替换所有匹配的部分。