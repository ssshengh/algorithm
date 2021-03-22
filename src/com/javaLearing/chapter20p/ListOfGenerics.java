package com.javaLearing.chapter20p;

import java.util.ArrayList;
import java.util.List;

public class ListOfGenerics<T> {
    private List<T> array = new ArrayList<>();
    public void add(T item) {
        array.add(item);
    }

    public T get(int index) {
        return array.get(index);
    }
    //这样做可以获得数组的行为，并且还具有泛型提供的编译时类型安全性。
}

//有时，仍然会创建泛型类型的数组（例如， ArrayList 在内部使用数组）。可以通过使编译器满意的方式定义对数组的通用引用：
class Generic<T> {
}

class ArrayOfGenericReference {
    static Generic<Integer>[] gia;//泛型数组的另一层含义
}

/**
 * 编译器接受此👆操作而不产生警告。但是我们永远无法创建具有该确切类型（包括类型参数）的数组，因此有点令人困惑。
 * 由于所有数组，无论它们持有什么类型，都具有相同的结构（每个数组插槽的大小和数组布局），因此似乎可以创建一个 Object 数组并将其转换为所需的数组类型。
 * 实际上，这确实可以编译，但是会产生 ClassCastException
 * */
class ArrayOfGeneric {
    static final int SIZE = 100;
    static Generic<Integer>[] gia;//泛型数组

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        try {
            gia = (Generic<Integer>[]) new Object[SIZE];//通过创建一个Object数组，转型为该泛型类型
        } catch (ClassCastException e) {
            System.out.println(e.getMessage());
        }

        //问题在于数组会跟踪其实际类型，而该类型是在创建数组时建立的。
        // 因此，即使 gia 被强制转换为 Generic<Integer>[] ，该信息也仅在编译时存在（并且没有 @SuppressWarnings 注解，将会收到有关该强制转换的警告）。
        // 在运行时，它仍然是一个 Object 数组，这会引起问题。
        // 成功创建泛型类型的数组的唯一方法是创建一个已擦除类型的新数组，并将其强制转换。


        // Runtime type is the raw (erased) type:
        gia = (Generic<Integer>[]) new Generic[SIZE];
        System.out.println(gia.getClass().getSimpleName());
        gia[0] = new Generic<>();
        //- gia[1] = new Object(); // Compile-time error
        // Discovers type mismatch at compile time:
        //- gia[2] = new Generic<Double>();
    }
}
/* Output:
[Ljava.lang.Object; cannot be cast to [LGeneric;
Generic[]
*/