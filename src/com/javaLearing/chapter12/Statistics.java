package com.javaLearing.chapter12;

// collections/Statistics.java
// (c)2017 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://OnJava8.com for more book information.
// Simple demonstration of HashMap
import java.util.*;

public class Statistics {
    public static void main(String[] args) {
        Random rand = new Random(47);
        Map<Integer, Integer> m = new HashMap<>();
        for(int i = 0; i < 10000; i++) {
            // Produce a number between 0 and 20:
            int r = rand.nextInt(20);
            Integer freq = m.get(r); // [1]获得key为r的value
            //自动包装机制将随机生成的 int 转换为可以与 HashMap 一起使用的 Integer 引用（不能使用基本类型的集合）。
            // 如果键不在集合中，则 get() 返回 null （这意味着该数字第一次出现）。
            // 否则， get() 会为键生成与之关联的 Integer 值，然后该值被递增（自动包装机制再次简化了表达式，
            // 但实际上确实发生了对 Integer 的装箱和拆箱）。
            m.put(r, freq == null ? 1 : freq + 1);
        }
        System.out.println(m);
    }
}
/* Output:
{0=481, 1=502, 2=489, 3=508, 4=481, 5=503, 6=519, 7=471, 8=468, 9=549, 10=513, 11=531, 12=521, 13=506, 14=477, 15=497, 16=533, 17=509, 18=478, 19=464}
*/

