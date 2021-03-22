package com.javaLearing.chapter20p;

// generics/CaptureConversion.java

public class CaptureConversion {

    /**
     * f1() 中的类型参数都是确切的，没有通配符或边界。
     * 在 f2() 中，Holder 参数是一个无界通配符，因此它看起来是未知的。
     * 但是，在 f2() 中调用了 f1()，而 f1() 需要一个已知参数。这里所发生的是：在调用 f2() 的过程中捕获了参数类型，并在调用 f1() 时使用了这种类型。
     * */
    static <T> void f1(Holder<T> holder) {
        T t = holder.get();
        System.out.println(t.getClass().getSimpleName());
    }

    /**
     * 你可能想知道这项技术是否可以用于写入，但是这要求在传递 Holder<?> 时同时传递一个具体类型。
     * 捕获转换只有在这样的情况下可以工作：即在方法内部，你需要使用确切的类型。注意，不能从 f2() 中返回 T，因为 T 对于 f2() 来说是未知的。
     * 捕获转换十分有趣，但是非常受限。
     * */
    static void f2(Holder<?> holder) {
        f1(holder); // Call with captured type
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Holder raw = new Holder<>(1);
        f1(raw);
        // warning: [unchecked] unchecked method invocation:
        // method f1 in class CaptureConversion
        // is applied to given types
        //     f1(raw);
        //       ^
        //   required: Holder<T>
        //   found: Holder
        //   where T is a type-variable:
        //     T extends Object declared in
        //     method <T>f1(Holder<T>)
        // warning: [unchecked] unchecked conversion
        //     f1(raw);
        //        ^
        //   required: Holder<T>
        //   found:    Holder
        //   where T is a type-variable:
        //     T extends Object declared in
        //     method <T>f1(Holder<T>)
        // 2 warnings
        f2(raw); // No warnings

        Holder rawBasic = new Holder();
        rawBasic.set(new Object());
        // warning: [unchecked] unchecked call to set(T)
        // as a member of the raw type Holder
        //     rawBasic.set(new Object());
        //                 ^
        //   where T is a type-variable:
        //     T extends Object declared in class Holder
        // 1 warning
        f2(rawBasic); // No warnings

        // Upcast to Holder<?>, still figures it out:
        Holder<?> wildcarded = new Holder<>(1.0);
        f2(wildcarded);
    }
}
/* Output:
Integer
Integer
Object
Double
*/

