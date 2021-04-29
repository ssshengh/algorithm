package com.JvmLearing.chapter3;

/**
 * 讨论引用计数器的循环引用问题
 *                  -XX:+PrintGCDetails出现垃圾回收时打印内存回收日志
 *                  -Xlog:gc*更新为了这个
 */
public class ReferenceCounterGC {
    public Object instance = null;
    private static final int _1MB = 1024;

    /**
     * 这个成员属性的唯一意义是占一点内存，以便在GC内存中查看是否被回收过
     */
    private byte[] bigSize = new byte[2*_1MB];

    public static void testGC(){
        ReferenceCounterGC objA = new ReferenceCounterGC();
        ReferenceCounterGC objB = new ReferenceCounterGC();
        objA.instance = objB;//objA对象里引用了objB
        objB.instance = objA;//反之

        objA = null;
        objB = null;

        //假设在这里GC，objA&objB是否能够被正常回收
        System.gc();
    }

    public static void main(String[] args) {
        ReferenceCounterGC.testGC();
    }
}
