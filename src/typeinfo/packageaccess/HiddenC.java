package typeinfo.packageaccess;

// typeinfo/packageaccess/HiddenC.java

import com.javaLearing.chapter19.*;
class C implements A {
    @Override
    public void f() {
        System.out.println("public C.f()");
    }

    public void g() {
        System.out.println("public C.g()");
    }

    void u() {
        System.out.println("package C.u()");
    }

    protected void v() {
        System.out.println("protected C.v()");
    }

    private void w() {
        System.out.println("private C.w()");
    }
}

//这个文件中，只有HiddenC有public，其他的是包管理权限，不可见
public class HiddenC {
    public static A makeA() {
        return new C();
    }
}

