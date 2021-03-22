package com.javaLearing.chapter19;

// typeinfo/SimpleDynamicProxy.java

//Java 的动态代理更进一步，不仅动态创建代理对象而且动态处理对代理方法的调用。
// 在动态代理上进行的所有调用都被重定向到单个调用处理程序，该处理程序负责发现调用的内容并决定如何处理。
import java.lang.reflect.*;

//使用动态代理更改的SimpleProxyDemo
//替代前面的SimpleProxy类成为代理
class DynamicProxyHandler implements InvocationHandler {
    private Object proxied;

    DynamicProxyHandler(Object proxied) {
        this.proxied = proxied;//这里进行代理
    }

    //invoke() 方法被传递给代理对象，以防万一你必须区分请求的来源---但是在很多情况下都无需关心。
    // 但是，在 invoke() 内的代理上调用方法时要小心，因为接口的调用是通过代理重定向的。
    @Override
    public Object
    invoke(Object proxy, Method method, Object[] args)//这个method是，comsumer里面执行代理的两个方法
            throws Throwable {
        System.out.println(
                "**** proxy: " + proxy.getClass() +
                        ", method: " + method + ", args: " + args);
        if (args != null)
            for (Object arg : args)
                System.out.println("  " + arg);
        return method.invoke(proxied, args);//通常执行代理操作，然后使用 Method.invoke() 将请求转发给被代理对象，并携带必要的参数。
    }
}

//通常执行代理操作，然后使用 Method.invoke() 将请求转发给被代理对象，并携带必要的参数。
class SimpleDynamicProxy {
    public static void consumer(Interface iface) {
        iface.doSomething();
        iface.somethingElse("bonobo");
    }

    public static void main(String[] args) {
        RealObject real = new RealObject();//
        consumer(real);

        //可以通过调用静态方法 Proxy.newProxyInstance() 来创建动态代理，该方法需要一个类加载器（通常可以从已加载的对象中获取），
        // 希望代理实现的接口列表（不是类或抽象类），以及接口  InvocationHandler 的一个实现。
        // 动态代理会将所有调用重定向到调用处理程序，因此通常为调用处理程序的构造函数提供对“真实”对象的引用，以便一旦执行中介任务便可以转发请求。
        // Insert a proxy and call again:
        //proxy为一个代理对象，
        Interface proxy = (Interface) Proxy.newProxyInstance(//创建动态代理，返回一个Object，需要转型
                Interface.class.getClassLoader(),//需要一个类加载器，调用了Interface的接口类加载器
                new Class[]{Interface.class},//希望代理实现的接口列表（不是类或抽象类）
                new DynamicProxyHandler(real));//接口  InvocationHandler 的一个实现，实际上使用这个代理类，代理了一个封装类

        System.out.println("***************************************\n");
        System.out.println(Interface.class);
        consumer(proxy);
        //代理传给consumer方法，首先调用doSomething，可以看见里面居然调用了invoke方法
        //然后调用somethingElse时，又调用了一个invoke方法
    }
}

//通常执行代理操作，然后使用 Method.invoke() 将请求转发给被代理对象，并携带必要的参数。
// 这在一开始看起来是有限制的，好像你只能执行一般的操作。但是，可以过滤某些方法调用，同时传递其他方法调用：
class MethodSelector implements InvocationHandler {
    //代理类
    private Object proxied;

    MethodSelector(Object proxied) {
        this.proxied = proxied;
    }

    @Override
    public Object
    invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        if (method.getName().equals("interesting"))
            System.out.println(
                    "Proxy detected the interesting method");
        return method.invoke(proxied, args);
    }
}

interface SomeMethods {
    void boring1();

    void boring2();

    void interesting(String arg);

    void boring3();
}

class Implementation implements SomeMethods {
    @Override
    public void boring1() {
        System.out.println("boring1");
    }

    @Override
    public void boring2() {
        System.out.println("boring2");
    }

    @Override
    public void interesting(String arg) {
        System.out.println("interesting " + arg);
    }

    @Override
    public void boring3() {
        System.out.println("boring3");
    }
}

class SelectingMethods {
    public static void main(String[] args) {
        SomeMethods proxy =
                (SomeMethods) Proxy.newProxyInstance(
                        SomeMethods.class.getClassLoader(),
                        new Class[]{ SomeMethods.class },
                        new MethodSelector(new Implementation()));
        proxy.boring1();
        proxy.boring2();
        proxy.interesting("bonobo");//利用invoke找到了代理对这个方法的调用，可以干其他的许多事
        proxy.boring3();
    }
}
//在这个示例里，我们只是在寻找方法名，但是你也可以寻找方法签名的其他方面，甚至可以搜索特定的参数值。
//
//动态代理不是你每天都会使用的工具，但是它可以很好地解决某些类型的问题。你可以在 Erich Gamma 等人的设计模式中了解有关代理和其他设计模式的更多信息。
// （Addison-Wesley，1995年），以及设计模式一章。