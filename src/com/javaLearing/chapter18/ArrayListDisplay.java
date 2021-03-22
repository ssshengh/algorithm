package com.javaLearing.chapter18;

// strings/ArrayListDisplay.java
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.*;

class  Coffee implements Supplier<String> {
    private int id = 0;
    private String ak;
    @Override
    public String get(){
        ak = id + ":" + "a";
        id +=1;
        return ak;
    }
    @Override
    public String toString(){
        return "String";
    }

}

public class ArrayListDisplay {
    public static void main(String[] args) {
        List<String> coffees =
                Stream.generate(new Coffee())//默认调用了get方法
                        .limit(10)
                        .collect(Collectors.toList());
        System.out.println(coffees);
    }
}
/* Output:
[Americano 0, Latte 1, Americano 2, Mocha 3, Mocha 4,
Breve 5, Americano 6, Latte 7, Cappuccino 8, Cappuccino 9]
*/

