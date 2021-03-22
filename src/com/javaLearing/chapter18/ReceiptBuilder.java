package com.javaLearing.chapter18;

// strings/ReceiptBuilder.java
import java.util.*;
//%[argument_index$][flags][width][.precision]conversion
public class ReceiptBuilder {
    private double total = 0;
    private Formatter f =
            new Formatter(new StringBuilder());
    public ReceiptBuilder() {
        f.format(//第一个左对齐，最小长度15，少的加空格补
                "%-15s %5s %10s%n", "Item", "Qty", "Price");
        f.format(
                "%-15s %5s %10s%n", "----", "---", "-----");
    }
    public void add(String name, int qty, double price) {
        f.format("%-15.15s %5d %10.2f%n", name, qty, price);
        //最小宽度不论其对哪种数据类型起作用，都是得到最小长度
        //最小宽度接一个.后面是最大长度
        //当应用于不同类型的数据转换时，precision 的意义也不同。在将 precision 应用于 String 时，它表示打印 string 时输出字符的最大数量。
        // 而在将 precision 应用于浮点数时，它表示小数部分要显示出来的位数（默认是 6 位小数），如果小数位数过多则舍入，太少则在尾部补零。
        total += price * qty;
    }
    public String build() {
        f.format("%-15s %5s %10.2f%n", "Tax", "",
                total * 0.06);
        f.format("%-15s %5s %10s%n", "", "", "-----");
        f.format("%-15s %5s %10.2f%n", "Total", "",
                total * 1.06);
        return f.toString();
    }
    public static void main(String[] args) {
        ReceiptBuilder receiptBuilder =
                new ReceiptBuilder();
        receiptBuilder.add("Jack's Magic Beans", 4, 4.25);
        receiptBuilder.add("Princess Peas", 3, 5.1);
        receiptBuilder.add(
                "Three Bears Porridge", 1, 14.29);
        System.out.println(receiptBuilder.build());
    }
}
/* Output:
Item              Qty      Price
----              ---      -----
Jack's Magic Be     4       4.25
Princess Peas       3       5.10
Three Bears Por     1      14.29
Tax                         2.80
                           -----
Total                      49.39
*/

