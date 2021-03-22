package com.javaLearing.chapter19;

// typeinfo/SweetShop.java
// 检查类加载器工作方式
class Cookie {
    static { System.out.println("Loading Cookie"); }
}

class Gum {
    static { System.out.println("Loading Gum"); }
}

class Candy {
    static { System.out.println("Loading Candy"); }
}
//上面的代码中，Candy、Gum 和 Cookie 这几个类都有一个 static{...} 静态初始化块，这些静态初始化块在类第一次被加载的时候就会执行。
//也就是说，静态初始化块会打印出相应的信息，告诉我们这些类分别是什么时候被加载了

public class SweetShop {
    public static void main(String[] args) {
        System.out.println("inside main");
        new Candy();
        System.out.println("After creating Candy");

        /**
         * 所有 Class 对象都属于 Class 类，而且它跟其他普通对象一样，我们可以获取和操控它的引用(这也是类加载器的工作)。
         * forName() 是 Class 类的一个静态方法，我们可以使用 forName() 根据目标类的类名（String）得到该类的 Class 对象。
         * 上面的代码忽略了 forName() 的返回值，因为那个调用是为了得到它产生的“副作用”。
         * 从结果可以看出，forName() 执行的副作用是如果 Gum 类没有被加载就加载它，而在加载的过程中，Gum 的 static 初始化块被执行了。
         * */
        try {
            Class.forName("Gum");//没有返回值，因此不会真的加载类Gum
        } catch(ClassNotFoundException e) {
            System.out.println("Couldn't find Gum");
        }//还需要注意的是，如果 Class.forName() 找不到要加载的类，它就会抛出异常 ClassNotFoundException。

        System.out.println("After Class.forName(\"Gum\")");
        new Cookie();
        System.out.println("After creating Cookie");
    }//从输出中可以看到，Class 对象仅在需要的时候才会被加载，static 初始化是在类加载时进行的。
}

