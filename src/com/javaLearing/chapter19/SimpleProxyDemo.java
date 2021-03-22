package com.javaLearing.chapter19;

// typeinfo/SimpleProxyDemo.java

interface Interface {
    void doSomething();

    void somethingElse(String arg);
}

class RealObject implements Interface {
    @Override
    public void doSomething() {
        System.out.println("doSomething");
    }

    @Override
    public void somethingElse(String arg) {
        System.out.println("somethingElse " + arg);
    }
}

//这个对象就是代理对象
class SimpleProxy implements Interface {
    private Interface proxied;

    SimpleProxy(Interface proxied) {
        this.proxied = proxied;
    }

    @Override
    public void doSomething() {
        System.out.println("SimpleProxy doSomething");
        proxied.doSomething();//代替其做一些其他的操作
    }

    @Override
    public void somethingElse(String arg) {
        System.out.println(
                "SimpleProxy somethingElse " + arg);
        proxied.somethingElse(arg);
    }
}

//一个对象封装真实对象，代替其提供其他或不同的操作---这些操作通常涉及到与“真实”对象的通信，因此代理通常充当中间对象
class SimpleProxyDemo {
    //因为 consumer() 接受 Interface，所以它不知道获得的是 RealObject 还是 SimpleProxy，因为两者都实现了 Interface。
    // 但是，在客户端和 RealObject 之间插入的 SimpleProxy 执行操作，然后在 RealObject 上调用相同的方法。
    public static void consumer(Interface iface) {
        iface.doSomething();
        iface.somethingElse("bonobo");
    }

    public static void main(String[] args) {
        consumer(new RealObject());
        consumer(new SimpleProxy(new RealObject()));
    }
}

