package com.javaLearing.chapter20;

// generics/Store.java
// Building a complex model using generic collections


import java.util.ArrayList;
import java.util.Random;
import java.util.function.Supplier;

//在这里，该模型是一个具有过道，货架和产品的零售商店

class Product {//产品
    private final int id;
    private String description;
    private double price;

    Product(int idNumber, String descr, double price) {
        id = idNumber;
        description = descr;
        this.price = price;
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return id + ": " + description +
                ", price: $" + price;
    }

    public void priceChange(double change) {
        price += change;
    }

    public static Supplier<Product> generator =
            new Supplier<Product>() {
                private Random rand = new Random(47);

                @Override
                public Product get() {
                    return new Product(rand.nextInt(1000), "Test",
                            Math.round(
                                    rand.nextDouble() * 1000.0) + 0.99);
                }
            };
}

//Shelf 使用 Suppliers.fill() 这个实用程序，
// 该实用程序接受 Collection （第一个参数），并使用 Supplier （第二个参数），以元素的数量为 n （第三个参数）来填充它。
// Suppliers 类将会在本章末尾定义，其中的方法都是在执行某种填充操作，并在本章的其他示例中使用。
class Shelf extends ArrayList<Product> {//架子
    Shelf(int nProducts) {
        Suppliers.fill(this, Product.generator, nProducts);
    }
}

class Aisle extends ArrayList<Shelf> {//过道
    Aisle(int nShelves, int nProducts) {
        for (int i = 0; i < nShelves; i++)
            add(new Shelf(nProducts));
    }
}

class CheckoutStand {//柜台
}

class Office {//办公室
}

public class Store extends ArrayList<Aisle> {//零售店
    private ArrayList<CheckoutStand> checkouts =
            new ArrayList<>();
    private Office office = new Office();

    public Store(
            int nAisles, int nShelves, int nProducts) {
        for (int i = 0; i < nAisles; i++)
            add(new Aisle(nShelves, nProducts));
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Aisle a : this)
            for (Shelf s : a)
                for (Product p : s) {
                    result.append(p);
                    result.append("\n");
                }
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(new Store(5, 4, 3));
        //调用了toString方法
        //尽管有复杂的层次结构，但多层的集合仍然是类型安全的和可管理的。令人印象深刻的是，组装这样的模型并不需要耗费过多精力。
    }
}
/* Output: (First 8 Lines)
258: Test, price: $400.99
861: Test, price: $160.99
868: Test, price: $417.99
207: Test, price: $268.99
551: Test, price: $114.99
278: Test, price: $804.99
520: Test, price: $554.99
140: Test, price: $530.99
                  ...
*/

