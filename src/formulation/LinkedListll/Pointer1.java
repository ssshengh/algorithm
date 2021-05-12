package formulation.LinkedListll;


import formulation.ListNode;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class Pointer1 {
    /**
     * 主要是指针类型的链表问题
     * 这里题：反转链表I，反转链表II
     */
    public ListNode reverseList(ListNode head){
        if (head == null || head.next == null)
            return head;
        //和我第一次做遇到的问题一样，这样的确反转了，但是最后回溯的不再是头节点
        //解决这个问题，首先要明确，最后return的一定得是最后的尾节点才行，不能在递归过程中被改变
        /*ListNode next = reverseList(head.next);
        head.next = null;
        next.next = head;
        return head;*/
        ListNode nextNode = head.next;
        ListNode res = reverseList(nextNode);//这一句是关键，我定义为了res，因为他一定会到最尾端元素
        //所以所有的修改必然都是修改的res
        nextNode.next = head;
        head.next = null;

        return res;
    }
    //反转链表2，关键在于其中的局部反转
    /*递归过于麻烦，需要存储四个变量，因为进入递归后，head后面那一段就完全断了，比如[1,2,3,4,5]
    * 反转得到[4,3,2,5...]后面的正确了，但是头节点就没有和这个连接起来变成了[1,2,5]这样还需要
    * 存储2这个节点，但是存储了2之后，如果1离他很远，还需要存储有多少跳，过于复杂*/
    public ListNode reverseBetween_bad(ListNode head, int left, int right) {
        if (head==null || left==right)
            return head;
        ListNode temp = head;
        ListNode l = new ListNode(0), r = new ListNode(0);
        for (int i = 1; i < (right + 1); i++) {
            if (i == left-1) {
                head = temp;
                l = temp.next;
            }
            if (i == right-1) {
                r = temp.next;
                break;
            }
            temp = temp.next;
        }
        l = helper(l, r, r.next);
        head.next = l;
        return head;

    }
    private ListNode helper(ListNode left, ListNode right, ListNode rNext){
        if (left.next == null || left == right)
            return left;
        ListNode nextNode = left.next;
        ListNode res = helper(nextNode, right, rNext);
        nextNode.next = left;
        left.next = rNext;
        return res;
    }
    /**
     * 接下来使用双指针的穿针引线法以及其优化算法：草，其实就是我上面的想法，但是我没写出来
     */
    public ListNode reverseBetween(ListNode head, int left, int right){
        //第一个注意点：使用虚拟头节点--》
        ListNode virtualHead = new ListNode(-1);
        virtualHead.next = head;

        //指针：这里一定一定区分上面的定义，上面实实在在的存在一个对象，而这里只有一个引用，只是一个指针！！！！
        ListNode pre = virtualHead;
        for (int i = 0; i < left-1; i++) {
            //到达左边切断的节点的前一个,走left-1步
            pre = pre.next;//这里是指针操作
        }
        //接着走right-left+1步到达right节点
        ListNode rightNode = pre;//还是指针操作
        for (int i = 0; i < (right - left + 1); i++) {
            rightNode = rightNode.next;
        }
        //接下来是左边节点和后继节点：
        ListNode leftNode = pre.next;
        ListNode curr = rightNode.next;

        //注意，切除子链，不然会循环引用
        pre.next = null;
        rightNode.next = null;

        //这样才是完整的完成了切除子链
        reverse(leftNode);

        //接下来接上去
        leftNode.next = curr;
        pre.next = rightNode;
        return virtualHead.next;

    }
    //这里使用双指针反转：
    private void reverse(ListNode head){
        ListNode pre = null;//再次强调，指针操作！
        ListNode cur = head;//因此head没有被改变
        while (cur!=null){
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
    }

    /**
     * 更好的方法，使用三指针
     */
    public ListNode reverseBetween_batter(ListNode head, int left, int right){
        //修改链表记得有一个虚拟头节点就对了
        ListNode virtualHead = new ListNode(-1);
        virtualHead.next = head;

        ListNode pre = virtualHead;
        for (int i = 0; i < (left - 1); i++) {
            pre = pre.next;//移动到前驱节点
        }
        //子链头节点以及下一个节点的定义:
        ListNode curr = pre.next;
        ListNode next = curr.next;
        for (int i = 0; i < (right - left); i++) {
            //画一遍图就能理解了
            curr.next = next.next;
            ListNode temp = next.next;
            next.next = pre.next;
            pre.next = next;
            next = temp;//curr压根不需要改
        }
        return virtualHead.next;
    }

    /**
     * 24.两两交换链表中的节点
     * 递归求解
     */
    public ListNode swapPairs(ListNode head) {
        //结束条件，偶数时，奇数时
        if (head==null || head.next==null)
            return head;
        ListNode next = head.next;
        head.next = swapPairs(next.next);//每个子问题的第一个节点修改指向下一个子问题返回的头节点
        next.next = head;//完成反转节点
        return next;//关键，返回这个问题反转后的头节点，这样的话这个子问题的头节点的前一根链接上了
    }
    //迭代解法
    public ListNode swapPairs2(ListNode head){
        //虚拟头节点
        ListNode virtualHead = new ListNode(0);
        virtualHead.next = head;

        ListNode pre = virtualHead;//不为head因为需要前驱节点的链
        //ListNode cur = head.next;
        while (pre.next != null && pre.next.next != null){
            ListNode first = pre.next;
            ListNode second = pre.next.next;

            pre.next = second;
            first.next = second.next;
            second.next = first;
            pre = first;//注意这时候first已经变成第二个了
        }
        return virtualHead.next;
    }

    /**
     * K 个一组翻转链表
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        if (k<=1) return head;

        ListNode virtualHead = new ListNode(-1);
        virtualHead.next = head;
        //前驱节点和右节点节点
        ListNode pre = virtualHead, rightNode = head;
        //左节点和后继节点在循环中定义
        while (rightNode != null){
            ListNode leftNode = pre.next;//左节点
            for (int i=0; i<k-1; i++) {
                rightNode = rightNode.next;//右节点移动到正确的位置
                if (rightNode == null)
                    return virtualHead.next;//如果少于k的时候
            }
            ListNode curr = rightNode.next;//后继节点

            //断开连接：
            pre.next = null;
            rightNode.next = null;
            reverse(leftNode);//反转子部分
            //重新接上，注意一个点，左右节点指向的空间没有变，因此虽然在函数中反转了链，但是二者还是正确的指向了头尾，只不过反了过来
            pre.next = rightNode;
            leftNode.next = curr;

            pre = leftNode;//注意结束后，前驱节点需要来到反转后链表的最后一个作为前驱节点
            rightNode = leftNode.next;//右节点到下一个字表的开始的第一个位置才正确
        }
        return virtualHead.next;
    }

    /**
     * 删除链表的倒数第 N 个节点，关键在于先要找到到底有多少个链表节点
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        //n最大为链表长度，所以不用考虑取模
        int len = lenOfLinked(head);
        ListNode virtualHead = new ListNode(-1);
        virtualHead.next = head;

        ListNode pre = virtualHead;
        //画个图就知道了，要删除的倒数第N个节点的前驱节点需要从VH走len-N步
        for (int i = 0; i<len-n; i++)
            pre = pre.next;

        ListNode delete = pre.next;
        pre.next = delete.next;
        delete.next = null;//注意先把指针释放了，防止空空间引用
        delete = null;//释放指针，但好像没有必要

        return virtualHead.next;
    }
    private int lenOfLinked(ListNode head){
        int len = 0;
        while (head!=null){
            len++;
            head = head.next;
        }
        return len;
    }
    //快慢指针法：
    public ListNode removeNthFromEnd_fl(ListNode head, int n){
        ListNode virtualHead = new ListNode(-1);
        virtualHead.next = head;

        ListNode fast = virtualHead;
        ListNode slow = virtualHead;
        for (int i = 0; i <= n; i++) {
            fast = fast.next;//块先走n+1步，为什么不是n步，因为要确保同时走的时候它走到空
        }
        while (fast!=null){
            fast = fast.next;
            slow = slow.next;
        }

        ListNode delete = slow.next;//要删除的节点
        slow.next = delete.next;
        delete.next = null;
        return virtualHead.next;
    }

    /**
     * 找到链表的中间节点
     * @param head 头节点
     * @return 返回中间节点
     */
    public ListNode middleNode(ListNode head) {
        //可以不用虚拟头节点
        if (head==null) return null;
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 两数相加：
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2){
        ListNode virtualHead = new ListNode(0);//相加之后结果的虚拟头节点
        ListNode cur = virtualHead;//指针，从虚拟头节点开始
        int count = 0;//余数
        while (l1 != null || l2 != null){
            //这里考虑了，如果有一个链表长一些的话，那么就不断用长链表加0就可以了
            int x = l1==null? 0 : l1.val;
            int y = l2==null? 0 : l2.val;

            int sum = x + y + count;
            count = sum/10;//进位，只能进1
            sum = sum%10;//余数

            cur.next = new ListNode(sum);
            cur = cur.next;

            if (l1!=null) l1 = l1.next;
            if (l2!=null) l2 = l2.next;
        }
        //还有个忘记的，如果全部加完结束了还有一个进位
        if (count >= 1) cur.next = new ListNode(count);
        return virtualHead.next;
    }

    /**
     * 两数相加II
     */
}
