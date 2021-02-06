package com.javaLearing.chapter7;

// reuse/Detergent.java
// (c)2017 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://OnJava8.com for more book information.
// Inheritance syntax & properties
/**
 * 注意修饰符：
 * Cleanser 中的所有方法都是公开的。请记住，如果不使用任何访问修饰符，则成员默认为包访问权限，这只允许包内成员访问。
 * 因此，如果没有访问修饰符，那么包内的任何人都可以使用这些方法。例如，Detergent 就没有问题。
 * 但是，如果其他包中的类继承 Cleanser，则该类只能访问 Cleanser 的公共成员。
 * 因此，为了允许继承，一般规则是所有字段为私有，所有方法为公共。
 * (protected成员也允许派生类访问;你以后会知道的。)在特定的情况下，你必须进行调整，但这是一个有用的指南。
 * */
class Cleanser {
    private String s = "Cleanser";
    //注意，这里利用了+来处理String，思考可不可以重载运算符——不可以
    public void append(String a) { s += a; }

    public void dilute() { append(" dilute()"); }
    public void apply() { append(" apply()"); }
    public void scrub() { append(" scrub()"); }
    @Override
    public String toString() { return s; }
    public static void main(String[] args) {
        Cleanser x = new Cleanser();
        x.dilute(); x.apply(); x.scrub();
        System.out.println(x);
    }
}
/**
 * 注意main方法的使用：
 * Cleanser 和 Detergent 都包含一个 main() 方法。
 * 你可以为每个类创建一个 main() ; 这允许对每个类进行简单的测试。
 * 当你完成测试时，不需要删除 main(); 你可以将其留在以后的测试中。
 * 即使程序中有很多类都有 main() 方法，惟一运行的只有在命令行上调用的 main()。
 * 这里，当你使用 java Detergent 时候，就调用了 Detergent.main()。
 * 但是你也可以使用 java Cleanser 来调用 Cleanser.main()，即使 Cleanser 不是一个公共类。
 * 即使类只具有包访问权，也可以访问 public main()。
 * */

public class Detergent extends Cleanser {
    // Change a method:
    //重要的是一点：继承过来之后相当于继承了所有的可继承方法，即使没有看见显示定义，也是可以调用的
    //对基类的继承，可以把继承看作是复用类。如在 scrub() 中所见，可以使用基类中定义的方法并修改它。
    // 在这里，你可以在新类中调用基类的该方法。但是在 scrub() 内部，不能简单地调用 scrub()，因为这会产生递归调用。
    @Override
    public void scrub() {
        append(" Detergent.scrub()");
        super.scrub(); // Call base-class version
    }
    // Add methods to the interface:
    public void foam() { append(" foam()"); }
    // Test the new class:
    public static void main(String[] args) {
        Detergent x = new Detergent();
        //调用既使没有被显示定义的方法
        x.dilute();
        x.apply();
        x.scrub();
        x.foam();
        System.out.println(x);
        System.out.println("Testing base class:");

        //显示调用基类的main方法：
        Cleanser.main(args);
    }
}
/* Output:
Cleanser dilute() apply() Detergent.scrub() scrub() foam()
Testing base class:
Cleanser dilute() apply() scrub()
*/

