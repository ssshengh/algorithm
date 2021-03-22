package com.javaLearing.chapter20p;

public class GenericArray<T> {
    private T[] array;//泛型数组定义

    @SuppressWarnings("unchecked")
    public GenericArray(int size){
        array = (T[]) new Object[size];//通过Object数组转型
    }
    //和以前一样，我们不能说 T[] array = new T[sz] ，所以我们创建了一个 Object 数组并将其强制转换
    public void put(int index, T item) {
        array[index] = item;
    }

    public T get(int index) {
        return array[index];
    }

    // Method that exposes the underlying representation:
    public T[] rep() {
        return array;
    }

    public static void main(String[] args) {
        GenericArray<Integer> gai = new GenericArray<>(10);

        try {
            Integer[] ia = gai.rep();
            //rep() 方法返回一个 T[] ，在主方法中它应该是 gai 的 Integer[]，
            // 但是如果调用它并尝试将结果转换为 Integer[] 引用，则会得到 ClassCastException ，这再次是因为实际的运行时类型为 Object[] 。
        } catch (ClassCastException e) {
            System.out.println(e.getMessage());
        }

        // This is OK:
        Object[] oa = gai.rep();

    }


}
