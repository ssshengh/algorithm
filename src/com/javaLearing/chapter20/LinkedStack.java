package com.javaLearing.chapter20;

public class LinkedStack <T>{
    private static class Node<T>{
        T item;
        Node<T> next;//指向栈下面的元素
        Node(){item=null; next = null;}
        Node(T item, Node<T> next){
            this.item = item;
            this.next = next;
        }

        boolean end() {
            return item == null && next == null;
        }
    }

    private Node<T> top = new Node<>();//空栈顶

    public void push(T item){
        top = new Node<T>(item, top);
    }

    public T pop(){
        T result = top.item;
        if (!top.end())
            top = top.next;
        return result;
    }

    public static void main(String[] args) {
        LinkedStack<String> linkedStack = new LinkedStack<>();
        for (String s: "yeah sisi xiaokeai".split(" "))
            linkedStack.push(s);

        String s;
        while ((s = linkedStack.pop()) != null)
            System.out.println(s);
    }
}
