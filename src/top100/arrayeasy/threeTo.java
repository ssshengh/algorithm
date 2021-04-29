package top100.arrayeasy;

import com.javaLearing.chapter24.Nap;
import com.javaLearing.chapter24P.IDChecker;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class ListNode{
    //链表元素定义
    int val;
    ListNode next;
    ListNode(){}
    ListNode(int val){this.val = val;}
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class threeTo {
    /**
     * 3. 无重复字符的最长子串
     * 考虑思路是，类似dp，dp[i]代表目前nums[0:i]的最长不重复子序列长度，需要一个map来存储之前的包含的元素
     * 那么如果nums[i+1]存在于map中，dp[i+1] = 1
     * 否则，放入map，然后dp[i+1] = dp[i]+1
     */
    public int lengthOfLongestSubstring_ok(String s){
        int len = s.length();
        if (len == 0) return 0;

        int [] dp = new int[len];//dp[i]代表，s[0:i]的中s[i]结尾的最长不重复子序列长度
        dp[0] = 1;

        int res = 1;

        Map<Character, Integer> map = new HashMap<>();//<此时找的子序列中的元素，所在位置>
        map.put(s.charAt(0), 0);
        int k = Integer.MIN_VALUE;
        for (int i = 1; i<len; i++){
            if (map.containsKey(s.charAt(i)) && map.get(s.charAt(i))>k) {
                dp[i] = i-map.get(s.charAt(i));
                //map = new HashMap<>();
                map.put(s.charAt(i), i);//这个比较关键："bbbbbbb"情况下，没有这句，第二个b的话，将会因为清除使得第三个b跳到else执行
                k = map.get(s.charAt(i));
            }
            else {
                dp[i] = dp[i - 1] + 1;
                res = Math.max(res, dp[i]);
                map.put(s.charAt(i), i);
            }
        }
        return res;
    }
    public int lengthOfLongestSubstring(String s){
        //上面的思路是滑动窗口，但是写法不是那么的易懂，换做真正的滑动窗口哦来写：
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>();

        for (int start = 0, end = 0; end < n; end++){
            //[start , end]的一个窗口
            char alpha = s.charAt(end);//获取每次新加入的元素
            if (map.containsKey(alpha))//如果窗口里存在新加入的元素
                start = Math.max(start, map.get(alpha));//那么就把起点放在重复元素那里
            map.put(alpha, end + 1);//然后更新这个元素位置,注意这个加1,不知道是因为哪种特殊情况
            ans = Math.max(ans, end - start + 1);//获取此时窗口大小
        }
        return ans;
    }
    /**
     * 15. 三数之和
     * 先试用map把时间降到n^2来看，map来自思路提示：如果用0减去每个数组的元素，存储，那么就只需要找两个数相加等于这个数
     */
    public List<List<Integer>> threeSum_a(int[] nums){
        if (nums.length <= 2) return null;
        int len = nums.length;
        Map<Integer, Integer> map = new HashMap<>();//<0-目前元素的值，其位置>,如果遇见相同元素，那么其实在哪个位置并不重要

        for (int i =0 ;i<len; i++)
            map.put(-nums[i], i);
        List<List<Integer>> lists = new ArrayList<>();

        for (int i = 0; i<len; i++)
            for (int j = 0; j<len; j++){
                if (map.containsKey(nums[i]+nums[j]) && map.get(nums[i]+nums[j]) != -1) {
                    lists.add(List.of(nums[i], nums[j], -nums[i] - nums[j]));
                    map.put(nums[i]+nums[j], -1);//代表已经找到过这个组合了
                }
            }

        //感觉有个难以处理的问题就是，如果是同样的nums[i]+nums[j]的值，但是完全不同的ij的话，救难判断
        return lists;
    }
    public List<List<Integer>> threeSum(int[] nums){
        List<List<Integer>> lists = new ArrayList<>();
        int len = nums.length;
        if (len<=2 ) return lists;//注意要返回这个

        Arrays.sort(nums);//排序是这个思路的关键
        for (int i=0; i<len; i++){
            if (nums[i] > 0) continue;//如果这个数大于0的话，那么必然不会得到等于0的情况
            //这里会想，如果前两个小于0呢，这时候必然已经被考虑过了，不用重复考虑，而后面必然不可能有一个和她相加再和前面一个数相加为0
            if (i>0 && nums[i] == nums[i-1]) continue;//如果有与第一个元素相同的，必然是在前面，已经排好序了

            int L = i+1, R = len-1;//L的定义也是关键，前面的更小的数，必然被考虑过了
            while (L < R){
                int sum = nums[L] + nums[R] + nums[i];
                if (sum == 0){
                    lists.add(Arrays.asList(nums[L], nums[R], nums[i]));
                    while (L<R && nums[L] == nums[L+1]) L++;//如果左边遇到相同元素就把现在的元素当作最后的一个
                    while (L<R && nums[R] == nums[R-1]) R--;//关键是细节：L<R必须时刻满足
                    L++;
                    R--;
                }
                else if (sum<0) L++;//说明加的左边小了一些
                else R--;//说明右边假的大了一些，因为L不可能左移
            }

        }
        return lists;
    }

    /**
     * 206. 反转链表
     */
    public ListNode reverseList_ok(ListNode head){
        if (head == null || head.next==null)//前一个是递归边界，后一个是特殊情况
            return head;
        ListNode p = reverseList(head.next);//每次回溯的是后面的节点，最关键没想清楚的来了：回溯的是已经完成排列的链表
        //p.next = head;
        head.next.next = head;//为什么不能上面这么写，关键还是回溯的问题没有想清，画一画就知道了
        //因为p，从始至终都是最后一个节点！！！！怪不得我下面做不下去
        //我一直在想如何把最后一个节点，不修改的回溯上去，这个给了我一个思路，就是一直保存它，然后修改其他的不管它，只管回溯就行
        head.next = null;
        return p;
    }
//    //从上往下的递归
//    private ListNode Helper(ListNode first){
//        //使用一个递归的函数，帮助处理两个元素的反转，子问题为两个节点的情况
//        //函数的作用就是，在所有两个节点中，把后一个节点的指针指向前一个，前一个的指向空
//        if (first.next == null)//结束条件，找到最后一个元素的时候
//            return first;//此时作为头节点返还出去
//
//        ListNode head = Helper(first.next);//一直找到最底
//        head.next = first;
//        first.next = null;
//        return first;//回溯信息

    //这个思路比较接近，但是细节问题没有考虑清楚
//    }

    //双指针
    public ListNode reverseList(ListNode head){
        if (head == null || head.next == null)
            return head;
        ListNode pre = head, cur = head.next;
        pre.next = null;//第一个元素先直接给指空
        ListNode temp = null;//这个用来暂存cur后面的元素，因为会给断掉
        while (cur != null){
            //当后面节点没有到最后一个节点时：
            temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;
    }
    /**
     * 7. 整数反转
     */
    public int reverse_ok(int x){
        String s = String.valueOf(x);//转换为String

        int len = s.length();//获取长度
        StringBuilder ans = new StringBuilder(len);
        int k = 0;
        if (s.charAt(0) == '-') {
            ans.append('-');
            k = 1;
        }

        for (int i = len-1; i>=k; i--)
            ans.append(s.charAt(i));

        try {
            return Integer.parseInt(String.valueOf(ans));
        }catch (Exception e){
            return 0;
        }

    }
    public int reverse(int x){
        int res = 0;
        while (x!=0){
            int first = x % 10;//得到最后一位数
            x = x / 10;//得到前面的数
            if (res > 214748364 || (res == 214748364 && first > 7))
                return 0;
            if (res < -214748364 || (res==-214748364 && first < -8))
                return 0;

            res = res * 10 + first;
        }
        return res;
    }

    /**
     * 70. 爬楼梯
     */
    public int climbStairs_ok(int n){
        if (n <= 1) return n;
        int [] dp = new int[n];//dp[i]代表第i-1层需要的方法数
        dp[0] = 1;dp[1] = 2;
        for (int i = 2; i<n; i++){
            dp[i] = dp[i-1]+dp[i-2];
        }
        return dp[n-1];
    }
    //优化一下空间：
    public int climbStairs_better(int n){
        if (n <= 1) return n;
        int first = 1, second = 2;
        int temp;
//        int [] dp = new int[n];//dp[i]代表第i-1层需要的方法数
//        dp[0] = 1;dp[1] = 2;
        for (int i = 2; i<n; i++){
            temp = first + second;
            first = second;
            second = temp;
        }
        return second;
    }
    //用流编程：
    int x = 1;
    public int climbStairs(int n){
        if (n <= 1) return n;
        //final int x = 1;
        int a = IntStream.iterate(2, i->
            {
                int result = x+i;
                x = i;
                return result;
            }).skip(n-1).limit(1).sum();
        return a;
    }
    /**
     * 20.有效的括号
     */
    public boolean isValid(String s){
        if (s.length()<=1 || s.length()%2!=0)//奇数个括号一定失败
            return false;
        Map<Character, Character> map = new HashMap<>(){
            {put(')', '('); put(']', '['); put('}', '{');}//注意这种初始化方法
        };//<右括号，左括号>很关键，不能反过来，因为每次输入的是右括号来进行判断
        Deque<Character> deque = new ArrayDeque<>();//栈只会存储左括号
        deque.add(s.charAt(0));
        for (int i = 1; i<s.length(); i++){
            //首先，我得匹配到右括号，即输入为右括号，找到其左括号
            if (map.containsKey(s.charAt(i))){
                //然后确立失败条件：如果栈顶的存入的左括号，对应的不是自己的右括号，一定出错
                if (deque.peekLast()!=map.get(s.charAt(i)))
                    return false;
                //否则就是找到合适的了，pop出去
                deque.pollLast();
            }
            else
                deque.addLast(s.charAt(i));//否则把左括号放入即可
        }
        return deque.isEmpty();
    }
    /**
     * 21. 合并两个有序链表
     */
    public ListNode mergeTwoLists_ok(ListNode l1, ListNode l2){
        if (l1 == null && l2 == null) return null;
        if (l1 == null && l2 != null) return l2;
        if (l1 != null && l2 == null) return l1;
        //用两个指针分别指向其第一个
        ListNode first = l1, second = l2;
        while (first!=null){//只要对第一个链表没有找到最后一个就继续

            if (first.val >= second.val && (second.next == null || first.val < second.next.val ))
                //如果第一个链表中找到的元素在第二个链表的某个元素中间
            {
                ListNode temp = new ListNode(first.val);
                temp.next = second.next;
                second.next = temp;
                first = first.next;//只有放进去后才会更新第一个链表的指针
            }else if (first.val < second.val){
                //这是一种特殊情况，l1中的所有元素都小于l2的
                ListNode temp = new ListNode(first.val);
                temp.next = second;
                second = temp;l2 = temp;
                first = first.next;
            }
            else
                second = second.next;//如果放不进去，就往后找第二个链表的元素
        }
        return l2;
    }
    //写得好复杂：
    public ListNode mergeTwoLists_better(ListNode l1, ListNode l2){
        ListNode preHead = new ListNode(-1);
        ListNode prv = preHead;

        while (l1!=null && l2!=null){
            if (l1.val <= l2.val){
                prv.next = l1;//比较关键，在前面引用相等的时候，二者就是完全一样的了，但我来写可能会用preHead
                l1 = l1.next;
            }else {
                prv.next = l2;
                l2 = l2.next;
            }
            prv = prv.next;
        }
        // 合并后 l1 和 l2 最多只有一个还未被合并完，我们直接将链表末尾指向未合并完的链表即可
        prv.next = l1 == null ? l2 : l1;
        return preHead.next;
    }
    //可以使用递归
    public ListNode mergeTwoLists(ListNode l1, ListNode l2){
        if (l1==null)
            return l2;//如果l1提前被找完，那么直接把l2剩下的回溯上去为当前较小节点的下一节点
        else if (l2 == null)
            return l1;
        else if (l1.val < l2.val)//选出较小当前节点，作为第一个节点
        {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;//回溯组合好的节点上去
        }else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }
}
