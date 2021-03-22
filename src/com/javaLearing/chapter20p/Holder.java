package com.javaLearing.chapter20p;

import com.javaLearing.chapter19.A;

import java.util.List;
import java.util.Objects;

public class Holder<T> {
    private T value;
    public Holder(){}
    public Holder(T value){this.value = value;}

    public void set(T val) {
        value = val;
    }

    public T get() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Holder && Objects.equals(value, ((Holder) o).value);
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
    public static void main(String[] args) {
        Holder<Apple> apple = new Holder<>(new Apple());
        Apple d = apple.get();
        apple.set(d);

        //如果创建了一个 Holder<Apple>，就不能将其向上转型为 Holder<Fruit>，但是可以向上转型为 Holder<? extends Fruit>。
        //因为不是Fruit，但是是Fruit的继承类
        // Holder<Fruit> fruit = apple; // Cannot upcast
        Holder<? extends Fruit> fruit = apple; // OK
        Fruit p = fruit.get();
        d = (Apple) fruit.get();
        //如果调用 get()，只能返回一个 Fruit——这就是在给定“任何扩展自 Fruit 的对象”这一边界后，它所能知道的一切了。
        /**
         * 如果你知道更多的信息，就可以将其转型到某种具体的 Fruit 而不会导致任何警告，但是存在得到 ClassCastException 的风险。
         * set() 方法不能工作在 Apple 和 Fruit 上，因为 set() 的参数也是"? extends Fruit"，意味着它可以是任何事物，
         * 编译器无法验证“任何事物”的类型安全性。
         * */
        try {
            Orange c = (Orange) fruit.get(); // No warning
        } catch (Exception e) {
            System.out.println(e);
        }
        // fruit.set(new Apple()); // Cannot call set()
        // fruit.set(new Fruit()); // Cannot call set()
        System.out.println(fruit.equals(d)); // OK


        //但是，equals() 方法可以正常工作，因为它接受的参数是 Object 而不是 T 类型。因此，编译器只关注传递进来和要返回的对象类型。它不会分析代码，以查看是否执行了任何实际的写入和读取操作。
        //
        //Java 7 引入了 java.util.Objects 库，使创建 equals() 和 hashCode() 方法变得更加容易，当然还有很多其他功能。
    }
}

class SuperTypeWildcards{
    public void wirteTo(List<? super Apple> list){
        list.add(new Apple());//可以了
        //list.add(new Fruit());//不行
        list.add(new Jonathan());//Apple继承类可以
        //list.add(new Orange());//不可以
    }
}
