package concurrentLearn.Kuang.JUC.FunctionInterface;

import java.util.function.Function;

/**
 * 函数式接口，这里看Function:一个输入一个输出，看源码就知道了
 * 函数式接口=另一种类型的lambda
 */
public class Demo1 {
    public static void main(String[] args) {
        Function<String, String> function = new Function<String, String>() {
            @Override
            public String apply(String s) {
                return s+" ~!";
            }
        };
        System.out.println(function.apply("ss"));

        Function<String, String> ff = (String)->{
            System.out.print(String+"  ❤️  ");
            return "sisi";
        };
        System.out.println(ff.apply("hengheng"));
    }
}
