package com.javaLearing.chapter13;

// functional/LambdaExpressions.java

//我们从三个接口开始，每个接口都有一个单独的方法（很快就会理解它的重要性）。
// 但是，每个方法都有不同数量的参数，以便演示 Lambda 表达式语法。
interface Description {
    String brief();
}

interface Body {
    String detailed(String head);
}

interface Multi {
    String twoArg(String head, Double d);
    //String test(String a, Double b, Double c);加一个方法后，lambda表达式并不能识别出方法，似乎接口里只能有一个方法
}

public class LambdaExpressions {

    static Body bod = h -> h + " No Parens!"; // [1]

    static Body bod2 = (h) -> h + " More details"; // [2]

    static Description desc = () -> "Short info"; // [3]

    static Multi mult = (h, n) -> h + n; // [4]

    //static Multi aa = (a, b, c) -> a+b+c;

    static Description moreLines = () -> { // [5]
        System.out.println("moreLines()");
        return "from moreLines()";
    };

    public static void main(String[] args) {
        System.out.println(bod.detailed("Oh!"));
        System.out.println(bod2.detailed("Hi!"));
        System.out.println(desc.brief());
        System.out.println(mult.twoArg("Pi! ", 3.14159));
        System.out.println(moreLines.brief());
    }
}

