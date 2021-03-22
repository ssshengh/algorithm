package com.javaLearing.chapter22;

import java.util.EnumSet;

//使用 EnumSet 的优点是，它在说明一个二进制位是否存在时，具有更好的表达能力，并且无需担心性能
enum AlarmPoints {
    STAIR1, STAIR2, LOBBY, OFFICE1, OFFICE2, OFFICE3,
    OFFICE4, BATHROOM, UTILITY, KITCHEN
}
public class EnumSets {
    public static void main(String[] args) {
        EnumSet<AlarmPoints> points = EnumSet.noneOf(AlarmPoints.class);//空集合
        points.add(AlarmPoints.STAIR1);//添加元素
        System.out.println(points);
        System.out.println("***");

        /**
         * 如果仔细研究了 EnumSet 的文档，你还会发现 of() 方法被重载了很多次，
         * 不但为可变数量参数进行了重载，而且为接收 2 至 5 个显式的参数的情况都进行了重载。这也从侧面表现了 EnumSet 对性能的关注。
         * 因为，其实只使用单独的 of() 方法解决可变参数已经可以解决整个问题了，但是对比显式的参数，会有一点性能损失。
         * 采用现在这种设计，当你只使用 2 到 5 个参数调用 of() 方法时，你可以调用对应的重载过的方法（速度稍快一点），
         * 而当你使用一个参数或多过 5 个参数时，你调用的将是使用可变参数的 of() 方法。
         * */
        //这里体现了两个方法，使用of来初始化一个EnumSet，然后全部添加的方法
        points.addAll(EnumSet.of(AlarmPoints.OFFICE1, AlarmPoints.OFFICE3, AlarmPoints.UTILITY));
        System.out.println(points);
        System.out.println("***");

        //把一整个enum加进去，注意是会不重复的
        points = EnumSet.allOf(AlarmPoints.class);
        points.removeAll(
                EnumSet.of(AlarmPoints.STAIR1, AlarmPoints.STAIR2, AlarmPoints.KITCHEN));
        System.out.println(points);
        System.out.println("***");

        points.removeAll(
                EnumSet.range(AlarmPoints.OFFICE1, AlarmPoints.OFFICE4));//从一个到一个中间的全部删除
        System.out.println(points);
        System.out.println("***");

        points = EnumSet.complementOf(points);
        System.out.println(points);


    }
}

/**
 * EnumSet 的基础是 long，一个 long 值有 64 位，而一个 enum 实例只需一位 bit 表示其是否存在。
 * 也就是说，在不超过一个 long 的表达能力的情况下，你的 EnumSet 可以应用于最多不超过 64 个元素的 enum。
 * 如果 enum 超过了 64 个元素会发生什么呢？
 * */
class BigEnumSet {
    enum Big { A0, A1, A2, A3, A4, A5, A6, A7, A8, A9,
        A10, A11, A12, A13, A14, A15, A16, A17, A18, A19,
        A20, A21, A22, A23, A24, A25, A26, A27, A28, A29,
        A30, A31, A32, A33, A34, A35, A36, A37, A38, A39,
        A40, A41, A42, A43, A44, A45, A46, A47, A48, A49,
        A50, A51, A52, A53, A54, A55, A56, A57, A58, A59,
        A60, A61, A62, A63, A64, A65, A66, A67, A68, A69,
        A70, A71, A72, A73, A74, A75 }
    public static void main(String[] args) {
        EnumSet<Big> bigEnumSet = EnumSet.allOf(Big.class);
        System.out.println(bigEnumSet);
    }
}//显然，EnumSet 可以应用于多过 64 个元素的 enum，所以我猜测，Enum 会在必要的时候增加一个 long