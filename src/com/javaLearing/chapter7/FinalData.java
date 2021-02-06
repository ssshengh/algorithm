package com.javaLearing.chapter7;

// reuse/FinalData.java
// The effect of final on fields
import java.util.*;

class Value {
    int i; // package access

    Value(int i) {
        this.i = i;
    }
}

public class FinalData {
    private static Random rand = new Random(47);
    private String id;

    public FinalData(String id) {
        this.id = id;
    }
    // Can be compile-time constants:
    //编译时常量
    private final int valueOne = 9;
    private static final int VALUE_TWO = 99;
    // Typical public constant:
    public static final int VALUE_THREE = 39;//public 意味着可以在包外访问，static 强调只有一个，final 说明是一个常量。


    // Cannot be compile-time constants:
    //非编译时常量
    //我们不能因为某数据被 final 修饰就认为在编译时可以知道它的值。
    // 由上例中的 i4 和 INT_5 可以看出，它们在运行时才会赋值随机数。
    // 示例部分也展示了将 final 值定义为 static 和非 static 的区别。
    // 此区别只有当值在运行时被初始化时才会显现，因为编译器对编译时数值一视同仁。
    // （而且编译时数值可能因优化而消失。）当运行程序时就能看到这个区别。
    // 注意到 fd1 和 fd2 的 i4 值不同，但 INT_5 的值并没有因为创建了第二个 FinalData 对象而改变，
    // 这是因为它是 static 的，在加载时已经被初始化，并不是每次创建新对象时都初始化。
    private final int i4 = rand.nextInt(20);
    static final int INT_5 = rand.nextInt(20);


    //v1 到 VAL_3 变量说明了 final 引用的意义。正如你在 main() 中所见，v2 是 final 的并不意味着你不能修改它的值。
    // 因为它是引用，所以只是说明它不能指向一个新的对象。
    // 这对于数组具有同样的意义，数组只不过是另一种引用。（我不知道有什么方法能使数组引用本身成为 final。）
    // 看起来，声明引用为 final 没有声明基本类型 final 有用。
    private Value v1 = new Value(11);
    private final Value v2 = new Value(22);
    private static final Value VAL_3 = new Value(33);
    // Arrays:
    private final int[] a = {1, 2, 3, 4, 5, 6};

    @Override
    public String toString() {
        return id + ": " + "i4 = " + i4 + ", INT_5 = " + INT_5;
    }

    public static void main(String[] args) {
        FinalData fd1 = new FinalData("fd1");
        //- fd1.valueOne++; // Error: can't change value
        fd1.v2.i++; // Object isn't constant
        fd1.v1 = new Value(9); // OK -- not final
        for (int i = 0; i < fd1.a.length; i++) {
            fd1.a[i]++; // Object isn't constant
        }
        //- fd1.v2 = new Value(0); // Error: Can't
        //- fd1.VAL_3 = new Value(1); // change reference
        //- fd1.a = new int[3];
        System.out.println(fd1);
        System.out.println("Creating new FinalData");
        FinalData fd2 = new FinalData("fd2");
        System.out.println(fd1);
        System.out.println(fd2);
    }
}
