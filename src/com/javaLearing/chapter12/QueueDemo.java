package com.javaLearing.chapter12;

// collections/QueueDemo.java
// Upcasting to a Queue from a LinkedList
import java.util.*;

public class QueueDemo {
    public static void printQ(Queue queue) {
        while(queue.peek() != null)
            System.out.print(queue.remove() + " ");
        System.out.println();
    }
    /**
     * peek() 和 element() 都返回队头元素而不删除它，但如果队列为空，则 peek() 返回 null ， 而 element() 抛出 NoSuchElementException 。
     * poll() 和 remove() 都删除并返回队头元素，但如果队列为空，则 poll() 返回 null ，而 remove() 抛出 NoSuchElementException 。
     *
     *  Queue 接口窄化了对 LinkedList 方法的访问权限，因此只有适当的方法才能使用，
     *  因此能够访问到的 LinkedList 的方法会变少（这里实际上可以将 Queue 强制转换回 LinkedList ，但至少我们不鼓励这样做）！！！！！！！！
     * */
    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        Random rand = new Random(47);
        for(int i = 0; i < 10; i++)
            queue.offer(rand.nextInt(i + 10));//offer() 是 Queue 的特有方法之一，
        // 它在允许的情况下，在队列的尾部插入一个元素，或者返回 false 。
        printQ(queue);

        Queue<Character> qc = new LinkedList<>();
        for(char c : "Brontosaurus".toCharArray())
            //自动包装机制会自动将 nextInt() 的 int 结果转换为 queue 所需的 Integer 对象，并将 char c 转换为 qc 所需的 Character 对象。
            qc.offer(c);
        printQ(qc);
    }
}
/* Output:
8 1 1 1 5 14 3 1 0 1
B r o n t o s a u r u s
*/

