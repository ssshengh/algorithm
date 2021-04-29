package com.JvmLearing.chapter2;

import java.util.ArrayList;
import java.util.List;

/**
 * VM Args：-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapHomePath=/Users/shenheng/opt/dumpFile
 * 把堆的大小设置为20m，把最小值和最大值都设置为这个，可以避免自动扩展
 * 第三个参数允许在VM出现了问题的时候Dump出当前的堆存储块照用于分析
 */
public class HeapOOM {
    static class OOMObject{}

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while (true)
            list.add(new OOMObject());
    }
}
