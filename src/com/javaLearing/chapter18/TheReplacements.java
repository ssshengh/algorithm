package com.javaLearing.chapter18;

// strings/TheReplacements.java
import java.util.regex.*;
import java.nio.file.*;
import java.util.stream.*;

/*! Here's a block of text to use as input to
    the regular expression matcher. Note that we
    first extract the block of text by looking for
    the special delimiters, then process the
    extracted block. !*/

public class TheReplacements {
    public static void main(String[] args) throws Exception {
        String s = Files.lines(
                Paths.get("src/com/javaLearing/chapter18/TheReplacements.java"))
                .collect(Collectors.joining("\n"));

        // Match specially commented block of text above:
        Matcher mInput = Pattern.compile(
                "/\\*!(.*)!\\*/", Pattern.DOTALL).matcher(s);//双斜杠取消转义

        if(mInput.find())
            s = mInput.group(1); // Captured by parentheses
        System.out.println("*******************");

        /**
         * 接下来，将存在两个或两个以上空格的地方，缩减为一个空格，并且删除每行开头部分的所有空格
         * （为了使每一行都达到这个效果，而不仅仅是删除文本开头部分的空格，这里特意开启了多行模式）。
         * 这两个替换操作所使用的的 replaceAll() 是 String 对象自带的方法，在这里，使用此方法更方便。
         * 注意，因为这两个替换操作都只使用了一次 replaceAll()，所以，与其编译为 Pattern，不如直接使用 String 的 replaceAll() 方法，
         * 而且开销也更小些。
         * */
        // Replace two or more spaces with a single space:
        s = s.replaceAll(" {2,}", " ");

        // Replace 1+ spaces at the beginning of each
        // line with no spaces. Must enable MULTILINE mode:
        s = s.replaceAll("(?m)^ +", "");
        System.out.println(s);


        /**
         * 此外，replaceFirst() 和 replaceAll() 方法用来替换的只是普通字符串，所以，如果想对这些替换字符串进行某些特殊处理，
         * 这两个方法时无法胜任的。如果你想要那么做，就应该使用 appendReplacement() 方法。该方法允许你在执行替换的过程中，
         * 操作用来替换的字符串。在这个例子中，先构造了 sbuf 用来保存最终结果，然后用 group() 选择一个组，并对其进行处理，
         * 将正则表达式找到的元音字母替换成大些字母。一般情况下，你应该遍历执行所有的替换操作，然后再调用 appendTail() 方法，
         * 但是，如果你想模拟 replaceFirst()（或替换n次）的行为，那就只需要执行一次替换，然后调用 appendTail() 方法，
         * 将剩余未处理的部分存入 sbuf 即可。
         * */
        System.out.println("*********************");
        s = s.replaceFirst("[aeiou]", "(VOWEL1)");
        StringBuffer sbuf = new StringBuffer();
        Pattern p = Pattern.compile("[aeiou]");
        Matcher m = p.matcher(s);
        // Process the find information as you
        // perform the replacements:
        while(m.find())
            m.appendReplacement(sbuf, m.group().toUpperCase());
        // Put in the remainder of the text:
        m.appendTail(sbuf);
        System.out.println(sbuf);
        //同时，appendReplacement() 方法还允许你通过 \$g 直接找到匹配的某个组，这里的 g 就是组号。然而，它只能应付一些简单的处理，无法实现类似前面这个例子中的功能。
    }
}
/* Output:
Here's a block of text to use as input to
the regular expression matcher. Note that we
first extract the block of text by looking for
the special delimiters, then process the
extracted block.
H(VOWEL1)rE's A blOck Of tExt tO UsE As InpUt tO
thE rEgUlAr ExprEssIOn mAtchEr. NOtE thAt wE
fIrst ExtrAct thE blOck Of tExt by lOOkIng fOr
thE spEcIAl dElImItErs, thEn prOcEss thE
ExtrActEd blOck.
*/

