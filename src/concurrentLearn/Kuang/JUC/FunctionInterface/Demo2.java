package concurrentLearn.Kuang.JUC.FunctionInterface;

import java.util.function.Predicate;

/**
 * 看源码就记起来了，Predicate是用在流编程中的判断字句中,断定型接口
 * 返回值是一个boolean，只有一个参数
 */
public class Demo2 {
    public static void main(String[] args) {
        Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.length() >= 2;
            }
        };
        System.out.println(predicate.test("aaa"));
        Predicate<String> predicate1 = String->{
            return String.length()>=2;
        };
    }
}
