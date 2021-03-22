package com.javaLearing.chapter20p;

// generics/UnboundedWildcards2.java
import java.util.*;

public class UnboundedWildcards2 {
    static Map map1;
    static Map<?,?> map2;
    static Map<String,?> map3;

    static void assign1(Map map) {
        map1 = map;
    }

    static void assign2(Map<?,?> map) {
        map2 = map;
    }

    //有时允许一个参数可以是任何类型，同时为其他参数确定某种特定类型的这种能力会显得很重要
    static void assign3(Map<String,?> map) {
        map3 = map;
    }

    public static void main(String[] args) {
        assign1(new HashMap());
        assign2(new HashMap());//但是，当你拥有的全都是无界通配符时，就像在 Map<?,?> 中看到的那样，编译器看起来就无法将其与原生 Map 区分开了。
        //- assign3(new HashMap());
        // warning: [unchecked] unchecked method invocation:
        // method assign3 in class UnboundedWildcards2
        // is applied to given types
        //     assign3(new HashMap());
        //            ^
        //   required: Map<String,?>
        //   found: HashMap
        // warning: [unchecked] unchecked conversion
        //     assign3(new HashMap());
        //             ^
        //   required: Map<String,?>
        //   found:    HashMap
        // 2 warnings
        assign1(new HashMap<>());
        assign2(new HashMap<>());//但是，当你拥有的全都是无界通配符时，就像在 Map<?,?> 中看到的那样，编译器看起来就无法将其与原生 Map 区分开了。
        assign3(new HashMap<>());
    }
    /**
     * 令人困惑的是，编译器并非总是关注像 List 和 List<?> 之间的这种差异，因此它们看起来就像是相同的事物。
     * 事实上，因为泛型参数擦除到它的第一个边界，因此 List<?> 看起来等价于 List<Object> ，而 List 实际上也是 List<Object> ——除非这些语句都不为真。
     * List 实际上表示“持有任何 Object 类型的原生 List ”，而 List<?> 表示“具有某种特定类型的非原生 List ，只是我们不知道类型是什么。”
     * */
}

