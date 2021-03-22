package com.javaLearing.chapter18;

import java.util.Scanner;
import java.util.regex.MatchResult;

public class ThreatAnalyzer {
    static String threatData =
            "58.27.82.161@08/10/2015\n" +
                    "204.45.234.40@08/11/2015\n" +
                    "58.27.82.161@08/11/2015\n" +
                    "58.27.82.161@08/12/2015\n" +
                    "58.27.82.161@08/12/2015\n" +
                    "[Next log section with different data format]";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(threatData);
        String pattern = "(\\d+[.]\\d+[.]\\d+[.]\\d+)@" +
                "(\\d{2}/\\d{2}/\\d{4})";
        while (scanner.hasNext()){
            scanner.next(pattern);
            //当 next() 方法配合指定的正则表达式使用时，将找到下一个匹配该模式的输入部分，
            // 调用 match() 方法就可以获得匹配的结果。如上所示，它的工作方式与之前看到的正则表达式匹配相似。
            MatchResult matchResult = scanner.match();
            String ip = matchResult.group(1);
            String date = matchResult.group(2);
            System.out.format(
                    "Threat on %s from %s%n", date,ip);
        }
        //在配合正则表达式使用扫描时，有一点需要注意：它仅仅针对下一个输入分词进行匹配，
        // 如果你的正则表达式中含有分隔符，那永远不可能匹配成功。
    }
}
