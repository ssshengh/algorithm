package com.javaLearing.chapter20;

abstract class GenericWithCreate<T>{
    final T element;
    GenericWithCreate(){
        this.element = creat();
    }

    abstract T creat();//create() 是模板方法
}

class X{}

class XCreator extends GenericWithCreate<X>{//注意这里确定了模版工厂的类型
    //通过继承模版，将模版固定为一个特定的参数类型，那么就可以通过模版来创建特定类型对象
    @Override
    X creat() {
        return new X();
    }
    void f(){
        System.out.println(element.getClass().getSimpleName());
    }
}

//GenericWithCreate 包含 element 字段，并通过无参构造函数强制其初始化，该构造函数又调用抽象的 create() 方法。
// 这种创建方式可以在子类中定义，同时建立 T 的类型。
public class CreatorGeneric {
    public static void main(String[] args) {
        XCreator xc = new XCreator();
        xc.f();
    }
}
