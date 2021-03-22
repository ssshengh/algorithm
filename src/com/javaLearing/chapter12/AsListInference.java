package com.javaLearing.chapter12;

// collections/AsListInference.java
import java.util.*;

class Snow {}
class Powder extends Snow {}
class Light extends Powder {}
class Heavy extends Powder {}
class Crusty extends Snow {}
class Slush extends Snow {}

public class AsListInference {
    public static void main(String[] args) {
        //List底层实现是数组，没法改变大小
        List<Snow> snow1 = Arrays.asList(
                new Crusty(), new Slush(), new Powder());
        //- snow1.add(new Heavy()); // Exception

        List<Snow> snow2 = Arrays.asList(
                new Light(), new Heavy());
        //- snow2.add(new Slush()); // Exception

        List<Snow> snow3 = new ArrayList<>();
        Collections.addAll(snow3,
                new Light(), new Heavy(), new Powder());
        snow3.add(new Crusty());

        // Hint with explicit type argument specification:
        //注意 Arrays.asList() 中间的“暗示”（即 <Snow> ），告诉编译器 Arrays.asList() 生成的结果 List 类型的实际目标类型是什么。这称为显式类型参数说明（
        List<Snow> snow4 = Arrays.<Snow>asList(
                new Light(), new Heavy(), new Slush());
        //- snow4.add(new Powder()); // Exception
    }
}

