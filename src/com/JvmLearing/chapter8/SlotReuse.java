package com.JvmLearing.chapter8;
//JVM参数：-verbose:gc
public class SlotReuse {
    public static void main(String[] args) {
        //这种是无法清理掉bytes的空间的，因为还在这个方法的作用域里
//        byte[] bytes = new byte[64*1024*1024];
//        System.gc();
        {//这种会清理掉的
            byte[] bytes = new byte[64*1024*1024];
        }
        int a = 0;//但是清理的前提是这里必须有这一句话
        //本质是，虽然超出了作用域，变量槽可以复用里，但是如果之后再也没有任何对局部变量表的读或写操作的话
        //他的变量槽就不会被复用，因此不会被释放。在没有被复用的情况下GC Roots还有着对他的关联
        //在对象占用内存大、栈帧长时间没有被回收、方法调用次数达不到即时编译要求的时候的奇技来用
        System.gc();
    }
}
