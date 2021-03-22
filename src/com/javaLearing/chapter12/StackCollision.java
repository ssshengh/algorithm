package com.javaLearing.chapter12;

// collections/StackCollision.java

public class StackCollision {
    public static void main(String[] args) {
        //注意，为了不引起冲突，需要限定名称域
        onjava.Stack<String> stack = new onjava.Stack<>();
        for(String s : "My dog has fleas".split(" "))
            stack.push(s);
        while(!stack.isEmpty())
            System.out.print(stack.pop() + " ");
        System.out.println();
        java.util.Stack<String> stack2 =
                new java.util.Stack<>();
        for(String s : "My dog has fleas".split(" "))
            stack2.push(s);
        while(!stack2.empty())
            System.out.print(stack2.pop() + " ");
    }
}
/* Output:
fleas has dog My
fleas has dog My
*/
