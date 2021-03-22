package com.javaLearing.chapter18;

// strings/Groups.java
import java.util.regex.*;

public class Groups {
    public static final String POEM =
            "Twas brillig, and the slithy toves\n" +
                    "Did gyre and gimble in the wabe.\n" +
                    "All mimsy were the borogoves,\n" +
                    "And the mome raths outgrabe.\n\n" +
                    "Beware the Jabberwock, my son,\n" +
                    "The jaws that bite, the claws that catch.\n" +
                    "Beware the Jubjub bird, and shun\n" +
                    "The frumious Bandersnatch.";
    public static void main(String[] args) {
        Matcher m = Pattern.compile(
                "(?m)(\\S+)\\s+((\\S+)\\s+(\\S+))$")//分组数目在这里看，总体为组0，第一个括号为组1，第二个大括号组2，里面组三组四
                .matcher(POEM);
        while(m.find()) {//find返回每一次匹配的对象
            for(int j = 0; j <= m.groupCount(); j++)//返回该匹配器的模式中的分组数目，组 0 不包括在内。
                System.out.print("[" + m.group(j) + "]");
            System.out.println();
        }
    }
}
//可以看到这个正则表达式模式有许多圆括号分组，由任意数目的非空白符（\\S+）及随后的任意数目的空白符（\\s+）所组成。
// 目的是捕获每行的最后3个词，每行最后以 \$ 结束。不过，在正常情况下是将 \$ 与整个输入序列的末端相匹配。
// 所以我们一定要显式地告知正则表达式注意输入序列中的换行符。这可以由序列开头的模式标记 (?m) 来完成（模式标记马上就会介绍）。

/* Output:
[the slithy toves][the][slithy toves][slithy][toves]
[in the wabe.][in][the wabe.][the][wabe.]
[were the borogoves,][were][the
borogoves,][the][borogoves,]
[mome raths outgrabe.][mome][raths
outgrabe.][raths][outgrabe.]
[Jabberwock, my son,][Jabberwock,][my son,][my][son,]
[claws that catch.][claws][that catch.][that][catch.]
[bird, and shun][bird,][and shun][and][shun]
[The frumious Bandersnatch.][The][frumious
Bandersnatch.][frumious][Bandersnatch.]
*/

class StartEnd {
    public static String input =
            "As long as there is injustice, whenever a\n" +
                    "Targathian baby cries out, wherever a distress\n" +
                    "signal sounds among the stars " +
                    "... We'll be there.\n"+
                    "This fine ship, and this fine crew ...\n" +
                    "Never give up! Never surrender!";
    private static class Display {
        private boolean regexPrinted = false;
        private String regex;
        Display(String regex) { this.regex = regex; }

        void display(String message) {
            if(!regexPrinted) {
                System.out.println(regex);
                regexPrinted = true;
            }
            System.out.println(message);
        }
    }

    static void examine(String s, String regex) {
        Display d = new Display(regex);
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);
        while(m.find())
            d.display("find() '" + m.group() +
                    "' start = "+ m.start() + " end = " + m.end());
        if(m.lookingAt()) // No reset() necessary
            d.display("lookingAt() start = "
                    + m.start() + " end = " + m.end());
        if(m.matches()) // No reset() necessary
            d.display("matches() start = "
                    + m.start() + " end = " + m.end());
    }

    public static void main(String[] args) {
        for(String in : input.split("\n")) {
            System.out.println("input : " + in);
            for(String regex : new String[]{"\\w*ere\\w*",
                    "\\w*ever", "T\\w+", "Never.*?!"})
                examine(in, regex);
        }
    }
}
/* Output:
input : As long as there is injustice, whenever a
\w*ere\w*
find() 'there' start = 11 end = 16
\w*ever
find() 'whenever' start = 31 end = 39
input : Targathian baby cries out, wherever a distress
\w*ere\w*
find() 'wherever' start = 27 end = 35
\w*ever
find() 'wherever' start = 27 end = 35
T\w+ find() 'Targathian' start = 0 end = 10
lookingAt() start = 0 end = 10
input : signal sounds among the stars ... We'll be
there.
\w*ere\w*
find() 'there' start = 43 end = 48
input : This fine ship, and this fine crew ...
T\w+ find() 'This' start = 0 end = 4
lookingAt() start = 0 end = 4
input : Never give up! Never surrender!
\w*ever
find() 'Never' start = 0 end = 5
find() 'Never' start = 15 end = 20
lookingAt() start = 0 end = 5
Never.*?!
find() 'Never give up!' start = 0 end = 14
find() 'Never surrender!' start = 15 end = 31
lookingAt() start = 0 end = 14
matches() start = 0 end = 31
*/