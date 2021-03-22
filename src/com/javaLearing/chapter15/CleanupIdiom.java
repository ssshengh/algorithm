package com.javaLearing.chapter15;

// exceptions/CleanupIdiom.java
// Disposable objects must be followed by a try-finally
class NeedsCleanup { // Construction can't fail
    private static long counter = 1;
    private final long id = counter++;
    public void dispose() {
        System.out.println(
                "NeedsCleanup " + id + " disposed");
    }
}


class ConstructionException extends Exception {}

class NeedsCleanup2 extends NeedsCleanup {
    // Construction can fail:
    NeedsCleanup2() throws ConstructionException {}
}
public class CleanupIdiom {
    public static void main(String[] args) {
        // [1]:相当简单，遵循了在可去除对象之后紧跟 try-finally 的原则。如果对象构造不会失败，就不需要任何 catch。
        NeedsCleanup nc1 = new NeedsCleanup();
        try {
            // ...
        } finally {
            nc1.dispose();
        }
        // [2]:为了构造和清理，可以看到将具有不能失败的构造器的对象分组在一起。
        // If construction cannot fail,
        // you can group objects:
        NeedsCleanup nc2 = new NeedsCleanup();
        NeedsCleanup nc3 = new NeedsCleanup();
        try {
            // ...
        } finally {
            nc3.dispose(); // Reverse order of construction
            nc2.dispose();
        }
        // [3]:展示了如何处理那些具有可以失败的构造器，且需要清理的对象。
        // 为了正确处理这种情况，事情变得很棘手，因为对于每一个构造，都必须包含在其自己的 try-finally 语句块中，
        // 并且每一个对象构造必须都跟随一个 try-finally 语句块以确保清理。
        // If construction can fail you must guard each one:
        try {
            NeedsCleanup2 nc4 = new NeedsCleanup2();
            try {
                NeedsCleanup2 nc5 = new NeedsCleanup2();
                try {
                    // ...
                } finally {
                    nc5.dispose();
                }
            } catch(ConstructionException e) { // nc5 const.
                System.out.println(e);
            } finally {
                nc4.dispose();
            }
        } catch(ConstructionException e) { // nc4 const.
            System.out.println(e);
        }
    }
    //本例中异常处理的混乱情形，有力的论证了应该创建不会抛出异常的构造器，尽管这并不总会实现。
    //
    //注意，如果 dispose() 可以抛出异常，那么你可能需要额外的 try 语句块。基本上，你应该仔细考虑所有的可能性，并确保正确处理每一种情况。
}

