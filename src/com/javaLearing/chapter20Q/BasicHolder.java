package com.javaLearing.chapter20Q;

//一个正常的泛型类
public class BasicHolder<T>{
    T element;
    void set(T arg) { element = arg; }
    T get() { return element; }
    void f() {
        System.out.println(element.getClass().getSimpleName());
    }
    //这是一个普通的泛型类型，它的一些方法将接受和产生具有其参数类型的对象，
    // 还有一个方法在其存储的域上执行操作（尽管只是在这个域上执行 Object 操作）。
}

class Subtype extends BasicHolder<Subtype> {}//导出类变成泛型基类的参数
/**
 * 新类 Subtype 接受的参数和返回的值具有 Subtype 类型而不仅仅是基类 BasicHolder 类型。
 * 这就是 CRG 的本质：基类用导出类替代其参数。
 * 这意味着泛型基类变成了一种其所有导出类的公共功能的模版，但是这些功能对于其所有参数和返回值，将使用导出类型。
 * 也就是说，在所产生的类中将使用确切类型而不是基类型。因此，在Subtype 中，传递给 set() 的参数和从 get() 返回的类型都是确切的 Subtype。
 * */
class CRGWithBasicHolder {
    public static void main(String[] args) {
        //subtype继承于BasicHolder<Subtype>，所以其中可以使用BasicHolder的字段信息等
        //然后因为基类将导出类作为了其类型，被继承过来后，相当于该类直接就把自己的类型给了BasicHolder，很奇怪
        Subtype st1 = new Subtype(), st2 = new Subtype();
        st1.set(st2);
        Subtype st3 = st1.get();
        st1.f();
    }
}
/* Output:
Subtype
*/
