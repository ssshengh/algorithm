package com.javaLearing.chapter20p;

import com.javaLearing.chapter19.A;

import java.util.Arrays;
import java.util.List;

public class GenericReading {
    static List<Apple> apples = Arrays.asList(new Apple());
    static List<Fruit> fruit = Arrays.asList(new Fruit());

    /**
     * readExact() 方法使用了精确的类型。如果使用这个没有任何通配符的精确类型，就可以向 List 中写入和读取这个精确类型。
     * 另外，对于返回值，静态的泛型方法 readExact() 可以有效地“适应”每个方法调用，
     * 并能够从 List<Apple> 中返回一个 Apple ，从 List<Fruit> 中返回一个 Fruit ，就像在 f1() 中看到的那样。
     * 因此，如果可以摆脱静态泛型方法，那么在读取时就不需要协变类型了。
     * */
    static <T> T readExact (List<T> list){//关键在于，这里的元素，写入或者读取的都是准确的
        return list.get(0);
    }

    // A static method adapts to each call:
    static void f1() {
        Apple a = readExact(apples);
        Fruit f = readExact(fruit);
        f = readExact(apples);
    }

    /**
     * 然而对于泛型类来说，当你创建这个类的实例时，就要为这个类确定参数。
     * 就像在 f2() 中看到的，fruitReader 实例可以从 List<Fruit> 中读取一个 Fruit ，因为这就是它的确切类型。
     * 但是 List<Apple> 也应该产生 Fruit 对象，而 fruitReader 不允许这么做。
     * */
    static class Reader<T>{
        T readExact(List<T> list){return list.get(0);}
    }

    static void f2(){//按道理说应该是可以的，因为泛型类也确定了取出的类型就是Fruit
        Reader<Fruit> fruitReader = new Reader<>();
        Fruit f = fruitReader.readExact(fruit);
        //注意这样不行，不能转型为Fruit
        //- Fruit a = fruitReader.readExact(apples);
        // error: incompatible types: List<Apple>
        // cannot be converted to List<Fruit>
    }

    /**
     * 为了修正这个问题，CovariantReader.readCovariant() 方法将接受 List<？extends T> ，
     * 因此，从这个列表中读取一个 T 是安全的（你知道在这个列表中的所有对象至少是一个 T ，并且可能是从 T 导出的某种对象）。
     * 在 f3() 中，你可以看到现在可以从 List<Apple> 中读取 Fruit 了。
     * */
    static class CovariantReader<T> {
        T readCovariant(List<? extends T> list) {
            return list.get(0);
        }
    }

    static void f3() {
        CovariantReader<Fruit> fruitReader = new CovariantReader<>();
        Fruit f = fruitReader.readCovariant(fruit);
        Fruit a = fruitReader.readCovariant(apples);//可以进行向上转型
    }
    public static void main(String[] args) {
        f1();
        f2();
        f3();
    }

}
