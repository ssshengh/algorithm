package com.javaLearing.chapter7;

// reuse/Hide.java
// (c)2017 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://OnJava8.com for more book information.
// Overloading a base-class method name in a derived
// class does not hide the base-class versions

class Homer {
    char doh(char c) {
        System.out.println("doh(char)");
        return 'd';
    }
    float doh(float f) {
        System.out.println("doh(float)");
        return 1.0f;
    }
}
//class Lisa extends Homer {
//    @Override 这里就是把重写当重载，报错
//    void doh(Milhouse m) {
//        System.out.println("doh(Milhouse)");
//    }
//}
class Milhouse {}

class Bart extends Homer {
    //注意：这里引入了一种新的重载方法，因为继承的两个doh方法均对其可见
    void doh(Milhouse m) {
        System.out.println("doh(Milhouse)");
    }
}

public class Hide {
    public static void main(String[] args) {
        Bart b = new Bart();
        b.doh(1);
        b.doh('x');
        b.doh(1.0f);
        b.doh(new Milhouse());
    }
}
/* Output:
doh(float)
doh(char)
doh(float)
doh(Milhouse)
*/

