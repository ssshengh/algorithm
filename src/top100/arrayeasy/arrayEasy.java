package top100.arrayeasy;

import java.util.HashMap;
import java.util.Map;

public class arrayEasy {
      private static class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    /**
     * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 的那 两个 整数，并返回它们的数组下标。
     * 看见题目的第一个思路：递归的树形两边查找
     * 这个思路是比较常规的二分查找问题，指针指向第一个最后一个，计算中间的
     * 然后gg
     * 这个思路不行，他不是一个可分的问题，所以用不了递归，那么就只能想到一种是两层循环————显然太low了，一眼望见的On^2
     *
     * 去看了思路提醒，使用哈希表来简化时间到On,关键解决的问题是：target-x如何高效查找比对
     * */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i<nums.length; i++){
            if (map.containsKey(target - nums[i]))
                //注意这里用containsKey方法，不用另一个containsValue，后者是遍历所有value效率低下，前者是map的方式（原理不知道，先留这里）
                return new int[]{map.get(target - nums[i]), i};
            map.put(nums[i], i);//注意hash表的构建方法
            //思考一下这里为什么不存在hash碰撞：因为题目限定了这里只有一个解；但最重要的是，是通过寻找key——target-num[i]
            //这样就决定了，如果存在重复元素的话，两个重复元素写入一个，检查到第二个的时候就被抓出来了，两个以上的重复元素会构成冲突
            //但是因为hash碰撞不会被写入
        }
        throw new IllegalArgumentException("No two sum solution");
    }


    /**
     * 用链表实现两个数相加
     * */
    boolean index = false;//判断是否需要进位
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null)
            return null;
        //处理好第一个节点，后面只需要next就可以了
        if (l1.val+ l2.val >= 10)
            index = true;
        ListNode ll = new ListNode((l1.val+ l2.val)%10);
        ListNode ll1 = l1.next;
        ListNode ll2 = l2.next;

        ListNode lll = ll;
        while(ll1 != null || ll2 != null){

//            if (ll1 == null) {
//                int value1 = 0;
//                ll.next = new ListNode(add(value1, ll2.val));
//                ll2 = ll2.next;
//            } else if (ll2 == null){
//                int value1 = 0;
//                ll.next = new ListNode(add(value1, ll1.val));
//                ll1 = ll1.next;
//            }else {
//                ll.next = new ListNode(add(ll1.val, ll2.val));
//                ll1 = ll1.next;
//                ll2 = ll2.next;
//            }   有个大问题，这里的只会更新ll的下一个节点，不会接着连在一起，所以无论多少个相加只会有两个节点
            if (ll1 == null) {
                int value1 = 0;
                lll.next = new ListNode(add(value1, ll2.val));
                ll2 = ll2.next;
            } else if (ll2 == null){
                int value1 = 0;
                lll.next = new ListNode(add(value1, ll1.val));
                ll1 = ll1.next;
            }
            else {
                lll.next = new ListNode(add(ll1.val, ll2.val));
                ll1 = ll1.next;
                ll2 = ll2.next;

            }
            lll = lll.next;

        }
        //第一次写忽略了这种情况，在二者加完之后，还有一个进位，得到的数比最长的数长1位的情况
        if (ll1 == null && ll2 == null && index)
            lll.next = new ListNode(1);
        return ll;
    }
    //一位的加法实现，包含进位
    private int add(int a, int b){
        int k;
        if (a + b >= 10 ){
            //该位的两数相加大于10时，需要进位
            if (index)
                k = (a+b)%10 + 1;
            else
                k = (a+b)%10;
            index = true;
        }else if(a + b == 9){
            //两数相加等于9时，若有进位需要进一位，若无进位等于9且无进位
            if (index) {
                k = 0;
                index = true;
            }
            else {
                k = 9;
                index = false;
            }
        }
        else {
            //两数相加小于9时
            if (index)
                k = a+b+1;
            else
                k = a+b;
            index = false;
        }
        return k;
    }

    /**
     * 自己的解法时间不错，但是空间开销大，就从重新定义的一个加的函数就知道，接下来利用链表特征进行优化
     * 链表思路的关键在于：使用一个指针指向一个空区域，然后其next才是需要的链表头
     * */
    public ListNode addTwoNumbers1(ListNode l1, ListNode l2){
        int carry = 0;
        ListNode pre = new ListNode(0);//精髓，解决了我需要先处理第一个节点的写法
        ListNode cur = pre;//用于移动
        while (l1 != null || l2 != null){
            //第二个关键的地方：解决了我需要一个新的对象来存储的写法带来的开销
            int x = l1 == null ? 0 : l1.val;
            int y = l2 == null ? 0 : l2.val;
            int sum = x + y + carry;//把我的一个大的加法函数简化了

            carry = sum / 10;
            sum = sum % 10;
            cur.next = new ListNode(sum);

            cur = cur.next;
            if(l1 != null)
                l1 = l1.next;
            if(l2 != null)
                l2 = l2.next;

        }
        if(carry == 1) {
            cur.next = new ListNode(carry);
        }
        return pre.next;
    }

}
