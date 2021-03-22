package com.javaLearing.chapter14;

import javax.print.DocFlavor;
import java.util.stream.Stream;

public class Fibonacci {
    int x =1;
    Stream<Integer> numbers(){
        return Stream.iterate(0, i->{
            int result = x+i;
            x = i;
            return result;
        });
        //返回的流，第一个元素是种子0，第二个i=0进入函数，x=0，得到0，第三个是上一个函数的结果i=result=1进入函数，返回1
        //第四个是，i=1进入函数，result=2输出，x用来记录dp[i-2]状态，完成斐波那契
    }

    public static void main(String[] args) {
        new Fibonacci().numbers()
                .skip(20)//过滤前20个
                .limit(5).forEach(System.out::println);
    }
}
