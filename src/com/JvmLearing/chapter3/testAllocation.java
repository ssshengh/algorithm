package com.JvmLearing.chapter3;

public class testAllocation {
    private static final int _1MB = 1024*1024;

    /**
     * 测试对象优先在Eden分配，尝试分配3个2M大小的对象和一个4M大小的对象，设置Java堆大小为20M
     * 不可扩展，其中10M新生代，10M老年代。大多数情况下，对象在新生代Eden区进行分配，如果没有
     * 足够空间的话，就会发生一次MinorGC
     * <P>
     *     参数：-Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     * </P>
     * <P>
     *     另一种适合JDK9后的：-XX:+UseSerialGC  -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -Xlog:gc+heap=debug
     * </P>
     */
    public void EdenPriority(){
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];//这里会出现一次Minor GC
    }

    /**
     * 大对象直接安排到老年代
     * <P>
     *     参数：-XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -Xlog:gc+heap=debug
     *      -XX:PretenureSizeThreshold=3145728
     * </P>
     */
    public void testPreTenuredAllocation(){
        byte[] allocation;
        allocation = new byte[4*_1MB];
    }

    /**
     * 测试设定长期存活对象进入老年代
     * <P>
     *     参数：-XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -Xlog:gc*
     *     -XX:MaxTenuringThreshold=1 -Xlog:gc+age=trace
     * </P>
     */
    public static void testTenuringThreshold(){
        byte[] allocation1 = new byte[_1MB/4];//256MB，什么时候进入老年代取决于阈值的设置
        byte[] allocation2 = new byte[_1MB*4];
        byte[] allocation3 = new byte[4*_1MB];
        allocation3 = null;
        allocation3 = new byte[_1MB*4];
    }

    public static void main(String[] args) {
        testAllocation.testTenuringThreshold();
    }
}
