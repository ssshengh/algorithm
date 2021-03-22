package com.javaLearing.chapter20p;

import java.lang.reflect.Array;

public class GenericArray2<T>{
    private Object[] array;

    public GenericArray2(int sz) {
        array = new Object[sz];
    }


    public void put(int index, T item) {
        array[index] = item;
    }
    //这两个方法的意思是，数组类型被擦除为Object了，其中的元素的（数组内部）表示，也是Object的
    @SuppressWarnings("unchecked")
    public T get(int index) {
        return (T) array[index];
        //调用 get() 时，它将对象强制转换为 T ，实际上这是正确的类型，因此很安全。
    }

    /**
     * 但是，如果调用 rep() ，它将再次尝试将 Object[] 强制转换为 T[] ，但仍然不正确，并在编译时生成警告，并在运行时生成异常。
     * 因此，无法破坏基础数组的类型，该基础数组只能是 Object[] 。
     * 在内部将数组视为 Object[] 而不是 T[] 的优点是，我们不太可能会忘记数组的运行时类型并意外地引入了bug，
     * 尽管大多数（也许是全部）此类错误会在运行时被迅速检测到。
     * */
    @SuppressWarnings("unchecked")
    public T[] rep() {
        return (T[]) array; // Unchecked cast
    }

    public static void main(String[] args) {
        GenericArray2<Integer> gai =
                new GenericArray2<>(10);
        for (int i = 0; i < 10; i++)
            gai.put(i, i);
        for (int i = 0; i < 10; i++)
            System.out.print(gai.get(i) + " ");
        System.out.println();
        try {
            Integer[] ia = gai.rep();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

/**
 * 对于新代码，请传入类型标记。在这种情况下，GenericArray 如下所示：
 * */
class GenericArrayWithTypeToken<T> {
    private T[] array;

    //类型标记 Class<T> 被传递到构造函数中以从擦除中恢复，因此尽管必须使用 @SuppressWarnings 关闭来自强制类型转换的警告，
    // 但我们仍可以创建所需的实际数组类型。一旦获得了实际的类型，就可以返回它并产生所需的结果，如在主方法中看到的那样。
    // 数组的运行时类型是确切的类型 T[] 。
    @SuppressWarnings("unchecked")
    public GenericArrayWithTypeToken(Class<T> type, int sz) {
        array = (T[]) Array.newInstance(type, sz);//传入了类型标签，这样就使得数组在运行时类型不会被擦除
    }

    public void put(int index, T item) {
        array[index] = item;
    }

    public T get(int index) {
        return array[index];
    }

    // Expose the underlying representation:
    public T[] rep() {
        return array;
    }

    public static void main(String[] args) {
        GenericArrayWithTypeToken<Integer> gai =
                new GenericArrayWithTypeToken<>(
                        Integer.class, 10);
        // This now works:
        Integer[] ia = gai.rep();
    }
}