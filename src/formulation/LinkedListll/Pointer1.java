package formulation.LinkedListll;


import formulation.ListNode;

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

}
