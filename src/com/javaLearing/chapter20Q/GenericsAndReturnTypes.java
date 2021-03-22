package com.javaLearing.chapter20Q;

// generics/GenericsAndReturnTypes.java

interface GenericGetter<T extends GenericGetter<T>> {
    T get();
}

interface Getter extends GenericGetter<Getter> {}

public class GenericsAndReturnTypes {
    void test(Getter g) {
        Getter result = g.get();
        GenericGetter gg = g.get(); // Also the base type
    }
}


/**
 * 上面的自限定的方法，实现了返回值的自协变
 * 下面是非泛型方法，参数类型不能随子类型变化而变化
 * */
class OrdinarySetter {
    void set(Base base) {
        System.out.println("OrdinarySetter.set(Base)");
    }
}

class DerivedSetter extends OrdinarySetter {
    void set(Derived derived) {
        System.out.println("DerivedSetter.set(Derived)");
    }
}

class OrdinaryArguments {
    public static void main(String[] args) {
        Base base = new Base();
        Derived derived = new Derived();
        DerivedSetter ds = new DerivedSetter();
        ds.set(derived);
        // Compiles--overloaded, not overridden!:
        ds.set(base);
        //set(derived) 和 set(base) 都是合法的，因此 DerivedSetter.set() 没有覆盖 OrdinarySetter.set() ，而是重载了这个方法。
    }
}
/* Output:
DerivedSetter.set(Derived)
OrdinarySetter.set(Base)
*/
