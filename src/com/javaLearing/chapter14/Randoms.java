package com.javaLearing.chapter14;

import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

public class Randoms {
    //声明式编程：
    public static void main(String[] args) {
        new Random(47)
                .ints(5, 20)//ints() 方法产生一个流并且 ints() 方法有多种方式的重载 —— 两个参数限定了产生的数值的边界。
                .distinct()//distinct() 使流中的整数不重复
                .limit(7)//使用 limit() 方法获取前 7 个元素
                .sorted()
                .forEach(System.out::println);//forEach() 方法遍历输出，它根据传递给它的函数对流中的每个对象执行操作。
    }

}
//如果用命令式编程：
class ImperativeRandoms {
    public static void main(String[] args) {
        Random rand = new Random(47);
        SortedSet<Integer> rints = new TreeSet<>();
        while(rints.size() < 7) {
            int r = rand.nextInt(20);
            if(r < 5) continue;
            rints.add(r);
        }
        System.out.println(rints);
    }
}