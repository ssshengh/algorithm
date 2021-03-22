package com.javaLearing.chapter22;

enum Shrubbery { GROUND, CRAWLING, HANGING }
public class EnumClass {
    public static void main(String[] args) {
        for (Shrubbery s : Shrubbery.values())
        {
            System.out.println(
                    s + " ordinal: " + s.ordinal());
            System.out.print("比较，compare："+
                    s.compareTo(Shrubbery.CRAWLING) + " ");//有小于大于
            System.out.print("比较，equals："+
                    s.equals(Shrubbery.CRAWLING) + " ");//只判断是否相等
            System.out.println(s == Shrubbery.CRAWLING);
            System.out.println(s.getDeclaringClass());//获取类型信息
            System.out.println(s.name());//获取枚举变量名称
            System.out.println("********************");
        }
        System.out.println("注意看：");
        // Produce an enum value from a String name:
        for(String s :
                "HANGING CRAWLING GROUND".split(" ")) {
            Shrubbery shrub =
                    Enum.valueOf(Shrubbery.class, s);//valueOf() 是在 Enum 中定义的 static 方法，
            // 它根据给定的名字返回相应的 enum 实例，如果不存在给定名字的实例，将会抛出异常
            System.out.println(shrub);
        }
    }

}
