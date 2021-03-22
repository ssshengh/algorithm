package com.javaLearing.chapter20p;

// generics/Wildcards.java
// Exploring the meaning of wildcards

public class Wildcards {

    /**
     * 在 rawArgs() 中，编译器知道 Holder 是一个泛型类型，
     * 因此即使它在这里被表示成一个原生类型，编译器仍旧知道向 set() 传递一个 Object 是不安全的。
     * 由于它是原生类型，你可以将任何类型的对象传递给 set() ，而这个对象将被向上转型为 Object 。
     * */
    // Raw argument:传递了一个特别大范围的，不确定泛型是否这么大就无法set进去，但是可以取出，只不过丢失类型信息
    static void rawArgs(Holder holder, Object arg) {
        //- holder.set(arg);
        // warning: [unchecked] unchecked call to set(T)
        // as a member of the raw type Holder
        //     holder.set(arg);
        //               ^
        //   where T is a type-variable:
        //     T extends Object declared in class Holder
        // 1 warning

        // Can't do this; don't have any 'T':
        // T t = holder.get();

        //无论何时，只要使用了原生类型，都会放弃编译期检查。对 get() 的调用说明了相同的问题：没有任何 T 类型的对象，因此结果只能是一个 Object。
        // OK, but type information is lost:
        Object obj = holder.get();
    }

    /**
     *  人们很自然地会开始考虑原生 Holder 与 Holder<?> 是大致相同的事物。
     *  但是 unboundedArg() 强调它们是不同的——它揭示了相同的问题，但是它将这些问题作为错误而不是警告报告，
     *  因为原生 Holder 将持有任何类型的组合，而 Holder<?> 将持有具有某种具体类型的同构集合，因此不能只是向其中传递 Object 。
     * */
    // Like rawArgs(), but errors instead of warnings:
    static void unboundedArg(Holder<?> holder, Object arg) {
        //- holder.set(arg);
        // error: method set in class Holder<T>
        // cannot be applied to given types;
        //     holder.set(arg);
        //           ^
        //   required: CAP#1
        //   found: Object
        //   reason: argument mismatch;
        //     Object cannot be converted to CAP#1
        //   where T is a type-variable:
        //     T extends Object declared in class Holder
        //   where CAP#1 is a fresh type-variable:
        //     CAP#1 extends Object from capture of ?
        // 1 error

        // Can't do this; don't have any 'T':
        // T t = holder.get();

        // OK, but type information is lost:
        Object obj = holder.get();
    }

    /**
     * 在 exact1() 和 exact2() 中，你可以看到使用了确切的泛型参数——没有任何通配符。
     * 你将看到，exact2()与 exact1() 具有不同的限制，因为它有额外的参数。
     * */
    static <T> T exact1(Holder<T> holder) {
        return holder.get();
    }

    static <T> T exact2(Holder<T> holder, T arg) {
        holder.set(arg);
        return holder.get();
    }

    /**
     *  在 wildSubtype() 中，在 Holder 类型上的限制被放松为包括持有任何扩展自 T 的对象的 Holder 。
     *  这还是意味着如果 T 是 Fruit ，那么 holder 可以是 Holder<Apple> ，这是合法的。
     *  为了防止将 Orange 放置到 Holder<Apple> 中，对 set() 的调用（或者对任何接受这个类型参数为参数的方法的调用）都是不允许的。
     *  但是，你仍旧知道任何来自 Holder<？extends Fruit> 的对象至少是 Fruit ，因此 get() （或者任何将产生具有这个类型参数的返回值的方法）都是允许的。
     * */
    static <T> T wildSubtype(Holder<? extends T> holder, T arg) {
        //- holder.set(arg);
        // error: method set in class Holder<T#2>
        // cannot be applied to given types;
        //     holder.set(arg);
        //           ^
        //   required: CAP#1
        //   found: T#1
        //   reason: argument mismatch;
        //     T#1 cannot be converted to CAP#1
        //   where T#1,T#2 are type-variables:
        //     T#1 extends Object declared in method
        //     <T#1>wildSubtype(Holder<? extends T#1>,T#1)
        //     T#2 extends Object declared in class Holder
        //   where CAP#1 is a fresh type-variable:
        //     CAP#1 extends T#1 from
        //       capture of ? extends T#1
        // 1 error
        return holder.get();
    }

    /**
     *  wildSupertype() 展示了超类型通配符，这个方法展示了与 wildSubtype() 相反的行为：holder 可以是持有任何 T 的基类型的容器。
     *  因此， set() 可以接受 T ，因为任何可以工作于基类的对象都可以多态地作用于导出类（这里就是 T ）。
     *  但是，尝试着调用 get() 是没有用的，因为由 holder 持有的类型可以是任何超类型，因此唯一安全的类型就是 Object 。
     *  这个示例还展示了对于在 unbounded() 中使用无界通配符能够做什么不能做什么所做出的限制：因为你没有 T，所以你不能将 set() 或 get() 作用于 T 上。
     * */
    static <T> void wildSupertype(Holder<? super T> holder, T arg) {
        holder.set(arg);
        //- T t = holder.get();
        // error: incompatible types:
        // CAP#1 cannot be converted to T
        //     T t = holder.get();
        //                     ^
        //   where T is a type-variable:
        //     T extends Object declared in method
        //       <T>wildSupertype(Holder<? super T>,T)
        //   where CAP#1 is a fresh type-variable:
        //     CAP#1 extends Object super:
        //       T from capture of ? super T
        // 1 error

        // OK, but type information is lost:
        Object obj = holder.get();
    }

    public static void main(String[] args) {
        Holder raw = new Holder<>();
        // Or:
        raw = new Holder();
        Holder<Long> qualified = new Holder<>();
        Holder<?> unbounded = new Holder<>();
        Holder<? extends Long> bounded = new Holder<>();
        Long lng = 1L;

        //为了迁移兼容性，rawArgs() 将接受所有 Holder 的不同变体，而不会产生警告。
        // unboundedArg() 方法也可以接受相同的所有类型，尽管如前所述，它在方法体内部处理这些类型的方式并不相同。
        rawArgs(raw, lng);
        rawArgs(qualified, lng);
        rawArgs(unbounded, lng);
        rawArgs(bounded, lng);

        unboundedArg(raw, lng);
        unboundedArg(qualified, lng);
        unboundedArg(unbounded, lng);
        unboundedArg(bounded, lng);


        /**
         * 如果向接受“确切”泛型类型（没有通配符）的方法传递一个原生 Holder 引用，就会得到一个警告，因为确切的参数期望得到在原生类型中并不存在的信息。
         * 如果向 exact1() 传递一个无界引用，就不会有任何可以确定返回类型的类型信息。
         * 可以看到，exact2() 具有最多的限制，因为它希望精确地得到一个 Holder<T> ，以及一个具有类型 T 的参数，
         * 正由于此，它将产生错误或警告，除非提供确切的参数。
         * */
        //- Object r1 = exact1(raw);
        // warning: [unchecked] unchecked method invocation:
        // method exact1 in class Wildcards is applied
        // to given types
        //      Object r1 = exact1(raw);
        //                        ^
        //   required: Holder<T>
        //   found: Holder
        //   where T is a type-variable:
        //     T extends Object declared in
        //     method <T>exact1(Holder<T>)
        // warning: [unchecked] unchecked conversion
        //      Object r1 = exact1(raw);
        //                         ^
        //   required: Holder<T>
        //   found:    Holder
        //   where T is a type-variable:
        //     T extends Object declared in
        //     method <T>exact1(Holder<T>)
        // 2 warnings

        Long r2 = exact1(qualified);
        Object r3 = exact1(unbounded); // Must return Object
        Long r4 = exact1(bounded);

        //- Long r5 = exact2(raw, lng);
        // warning: [unchecked] unchecked method invocation:
        // method exact2 in class Wildcards is
        // applied to given types
        //     Long r5 = exact2(raw, lng);
        //                     ^
        //   required: Holder<T>,T
        //   found: Holder,Long
        //   where T is a type-variable:
        //     T extends Object declared in
        //       method <T>exact2(Holder<T>,T)
        // warning: [unchecked] unchecked conversion
        //     Long r5 = exact2(raw, lng);
        //                      ^
        //   required: Holder<T>
        //   found:    Holder
        //   where T is a type-variable:
        //     T extends Object declared in
        //       method <T>exact2(Holder<T>,T)
        // 2 warnings

        Long r6 = exact2(qualified, lng);

        //- Long r7 = exact2(unbounded, lng);
        // error: method exact2 in class Wildcards
        // cannot be applied to given types;
        //     Long r7 = exact2(unbounded, lng);
        //               ^
        //   required: Holder<T>,T
        //   found: Holder<CAP#1>,Long
        //   reason: inference variable T has
        //     incompatible bounds
        //     equality constraints: CAP#1
        //     lower bounds: Long
        //   where T is a type-variable:
        //     T extends Object declared in
        //       method <T>exact2(Holder<T>,T)
        //   where CAP#1 is a fresh type-variable:
        //     CAP#1 extends Object from capture of ?
        // 1 error

        //- Long r8 = exact2(bounded, lng);
        // error: method exact2 in class Wildcards
        // cannot be applied to given types;
        //      Long r8 = exact2(bounded, lng);
        //                ^
        //   required: Holder<T>,T
        //   found: Holder<CAP#1>,Long
        //   reason: inference variable T
        //     has incompatible bounds
        //     equality constraints: CAP#1
        //     lower bounds: Long
        //   where T is a type-variable:
        //     T extends Object declared in
        //       method <T>exact2(Holder<T>,T)
        //   where CAP#1 is a fresh type-variable:
        //     CAP#1 extends Long from
        //       capture of ? extends Long
        // 1 error

        /**
         * 有时，这样做很好，但是如果它过于受限，那么就可以使用通配符，这取决于是否想要从泛型参数中返回类型确定的返回值（就像在 wildSubtype() 中看到的那样），
         * 或者是否想要向泛型参数传递类型确定的参数（就像在 wildSupertype() 中看到的那样）。
         * 因此，使用确切类型来替代通配符类型的好处是，可以用泛型参数来做更多的事，但是使用通配符使得你必须接受范围更宽的参数化类型作为参数。
         * 因此，必须逐个情况地权衡利弊，找到更适合你的需求的方法。
         * */
        //- Long r9 = wildSubtype(raw, lng);
        // warning: [unchecked] unchecked method invocation:
        // method wildSubtype in class Wildcards
        // is applied to given types
        //     Long r9 = wildSubtype(raw, lng);
        //                          ^
        //   required: Holder<? extends T>,T
        //   found: Holder,Long
        //   where T is a type-variable:
        //     T extends Object declared in
        //     method <T>wildSubtype(Holder<? extends T>,T)
        // warning: [unchecked] unchecked conversion
        //     Long r9 = wildSubtype(raw, lng);
        //                           ^
        //   required: Holder<? extends T>
        //   found:    Holder
        //   where T is a type-variable:
        //     T extends Object declared in
        //     method <T>wildSubtype(Holder<? extends T>,T)
        // 2 warnings

        Long r10 = wildSubtype(qualified, lng);
        // OK, but can only return Object:
        Object r11 = wildSubtype(unbounded, lng);
        Long r12 = wildSubtype(bounded, lng);

        //- wildSupertype(raw, lng);
        // warning: [unchecked] unchecked method invocation:
        //   method wildSupertype in class Wildcards
        //   is applied to given types
        //     wildSupertype(raw, lng);
        //                  ^
        //   required: Holder<? super T>,T
        //   found: Holder,Long
        //   where T is a type-variable:
        //     T extends Object declared in
        //     method <T>wildSupertype(Holder<? super T>,T)
        // warning: [unchecked] unchecked conversion
        //     wildSupertype(raw, lng);
        //                   ^
        //   required: Holder<? super T>
        //   found:    Holder
        //   where T is a type-variable:
        //     T extends Object declared in
        //     method <T>wildSupertype(Holder<? super T>,T)
        // 2 warnings

        wildSupertype(qualified, lng);

        //- wildSupertype(unbounded, lng);
        // error: method wildSupertype in class Wildcards
        // cannot be applied to given types;
        //     wildSupertype(unbounded, lng);
        //     ^
        //   required: Holder<? super T>,T
        //   found: Holder<CAP#1>,Long
        //   reason: cannot infer type-variable(s) T
        //     (argument mismatch; Holder<CAP#1>
        //     cannot be converted to Holder<? super T>)
        //   where T is a type-variable:
        //     T extends Object declared in
        //     method <T>wildSupertype(Holder<? super T>,T)
        //   where CAP#1 is a fresh type-variable:
        //     CAP#1 extends Object from capture of ?
        // 1 error

        //- wildSupertype(bounded, lng);
        // error: method wildSupertype in class Wildcards
        // cannot be applied to given types;
        //     wildSupertype(bounded, lng);
        //     ^
        //   required: Holder<? super T>,T
        //   found: Holder<CAP#1>,Long
        //   reason: cannot infer type-variable(s) T
        //     (argument mismatch; Holder<CAP#1>
        //     cannot be converted to Holder<? super T>)
        //   where T is a type-variable:
        //     T extends Object declared in
        //     method <T>wildSupertype(Holder<? super T>,T)
        //   where CAP#1 is a fresh type-variable:
        //     CAP#1 extends Long from capture of
        //     ? extends Long
        // 1 error
    }
}

