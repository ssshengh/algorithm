package com.javaLearing.chapter10;

// interfaces/Applicator.java
import java.util.*;

class Processor {
    public String name() {
        return getClass().getSimpleName();//获取类名
    }

    public Object process(Object input) {
        return input;
    }
}

class Upcase extends Processor {
    // 返回协变类型
    //在Java1.4及以前，子类方法如果要覆盖超类的某个方法，必须具有完全相同的方法签名，包括返回值也必须完全一样。
    // Java5.0放宽了这一限制，只要子类方法与超类方法具有相同的方法签名，或者子类方法的返回值是超类方法的子类型，就可以覆盖。
    @Override
    public String process(Object input) {
        return ((String) input).toUpperCase();//变成大写
        //注意返回值是string类型
    }
}

class Downcase extends Processor {
    @Override
    public String process(Object input) {
        return ((String) input).toLowerCase();
    }
}

class Splitter extends Processor {
    @Override
    public String process(Object input) {
        // split() divides a String into pieces:
        return Arrays.toString(((String) input).split(" "));
    }
}

public class Applicator {
    public static void apply(Processor p, Object s) {
        System.out.println("Using Processor " + p.name());
        System.out.println(p.process(s));
    }

    public static void main(String[] args) {
        String s = "We are such stuff as dreams are made on";
        apply(new Upcase(), s);//这里的形参是两个很高的类型
        apply(new Downcase(), s);
        apply(new Splitter(), s);
        //可以看见尽管name方法位于普通类内，但是获取到的类名是继承类
        //执行的process方法也是各个继承类的process方法，而process的形参也是很高的object，这里可以看出
        //接口的简便性就是，可以不关注具体接口类型引用的实现，就可以调用全部一样的方法
    }
}

