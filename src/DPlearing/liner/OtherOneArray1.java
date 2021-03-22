package DPlearing.liner;

import java.util.Arrays;

public class OtherOneArray1 {
    /**
     * 最长有效括号
     * 给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
     * */
    public int longestValidParentheses_mine(String s){
        char[] ss = s.toCharArray();
        int len = ss.length;//获取字符数组的长度
        int[] dp = new int[len];//定义：dp[i]为ss[i]结尾的子数组的最长有效括号字串
        char last = ss[0];//记忆上一个状态的括号情况
        dp[0] = 0;//初始化第一个

        int max = Integer.MIN_VALUE;

        for (int i = 1; i<len; i++)
        {
            if (last == ss[i])
                dp[i] = 0;
            else if (last == '(' && ss[i] == ')')
                dp[i] = dp[i-1] +2;
            else if (last == ')' && ss[i] == '(')
                dp[i] = dp[i-1];
            max = Math.max(max, dp[i]);
            last = ss[i];
        }
        return max;
    }//没读懂题目
    public int longestValidParentheses(String s) {
        if (s.length()==0 || s.length() ==1)
            return 0;
        int[] dp = new int[s.length()];
        int max = Integer.MIN_VALUE;
        for (int i = 1; i < s.length(); i++)
        {//dp[0]==0,不用考虑了
//            if (s.charAt(i) == ')')
//                if (s.charAt(i-1) == '(')
//                    dp[i] = i<2? 2 : dp[i-2] + 2;//记住这种方法来考虑dp[-1]=0需要被考虑时候的问题
//                if (s.charAt(i-1) == ')')
//                    if (s.charAt(i - dp[i-1] -1) == '(')
//                        dp[i] = dp[i-1] + dp[i-dp[i-1]-2] +2;
            //上面老是会出现-1的索引情况，看下面的解决方法：
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                    dp[i] = dp[i - 1] + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                }

//            if (s.charAt(i) == '(')
//                dp[i] = 0;
// 可以省略，dp原本就是初始化0了，这个是唯一一个

            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }
    //贪心做法，记下这种两遍查找的思路
    public int longestValidParentheses1(String s){
        if (s.length()==0 || s.length()==1)
            return 0;
        int left = 0, right = 0, max = Integer.MIN_VALUE;
        //正向遍历
        for (int i = 0; i<s.length(); i++)
        {
            if (s.charAt(i) == '(')
                left++;
            else
                right++;

            if (left == right)
                max = Math.max(left*2, max);
            else if (right > left)
                left = right =0;

        }
        left = right =0;
        //反向遍历
        for (int i = s.length()-1; i>=0; i--){
            if (s.charAt(i) == '(')
                left++;
            else
                right++;
            if (left == right)
                max = Math.max(max,left*2 );
            else if (left>right)
                left = right = 0;
        }
        return max;
    }

    /**
     * 等差数列划分
     * 函数要返回数组 A 中所有为等差数组的子数组个数。（注意是子数组）
     * */
    public static int[] numberOfArithmeticSlices(int[] nums){
        if (nums.length <=1)
            return new int[]{0, 0};
        int k = nums[1] - nums[0];//存储等差
        int len = nums.length;
        int [] dp = new int[nums.length];//dp[i]代表nums[i]结尾的等差子数组的长度
        int[] count = new int[len];//记录下nums[i]结尾的等差子数组的长度

        dp[0] = 1;//初始化不为1的话，那么第一个字串会出错
        for (int i = 1; i<len; i++){
//            if (dp[i-1] == 0) {
//                //上一次记录的长度为0的时候，意味着为等差数列起点，记录等差
//                k = nums[i] - nums[i-1];
//                dp[i] = 1;
//                continue;
//            }//第一次错误的地方，这里是多余的，更新k下面就够了
            if (nums[i] - nums[i-1] == k)
                dp[i] = dp[i-1] +1;
            else {
                k = nums[i] - nums[i-1];
                dp[i] = 2;
            }

            if (dp[i]>=3 && dp[i]-dp[i-1]==1) {
                count[i] = dp[i];
                if (count[i-1] != 0)
                    count[i-1] = 0;
            }
        }
        int res = 0;//最后结果
        for (int i = 0; i<len; i++)
            if (count[i] != 0)
                res+=helper(count[i]);
        return dp;
    }
    private static int helper(int n){
        //贪心的从长度为n的等差序列中找出存在的序列数
        //比如长度为5，那么，顺着数从0-3记一个，0-4记一个。。。。0-n记一个
        //然后1-4，1-5。。。1-n
        int len = 0;
        for (int i = 0; i<n; i++)
            for (int j = i+2; j<n; j++)
                len++;
        return len;
    }
    //官方做法：优化dp[]为dp
    public static int numberOfArithmeticSlices1(int[] A) {
        int dp = 0;
        int sum = 0;
        for (int i = 2; i < A.length; i++) {
            if (A[i] - A[i - 1] == A[i - 1] - A[i - 2]) {
                dp = 1 + dp;
                sum += dp;
            }else
                dp = 0;
        }
        return sum;
    }
    //递归法：仔细对比一下，会发现是动态规划差不多的感觉
    public int sum = 0;
    public int numberOfArithmeticSlices_recur(int[] A){
        slice(A, A.length);
        return sum;
    }
    private int slice(int[] A, int len){
        //递归初始条件
        if (len < 2)
            return 0;
        int ap = 0;
        if (A[len] - A[len-1] == A[len-1] - A[len-2])
        {
            ap = slice(A, len-1) +1;
            sum += ap;
        }else
            slice(A, len-1);
        return ap;
    }
    /**
     * 91. 解码方法
     * */
    public int numDecodings_mine(String s) {
        char[] ss = s.toCharArray();
        int [] dp = new int[ss.length];//dp[i]定义为ss[i]结尾的子数组的解码总数
        //int max = 1;
        if (ss[0] == '0')
            return 0;
        dp[0] = 1;
        for (int i = 1; i<ss.length; i++){
            if (ss[i] == '0')
                if (ss[i-1] == '1' || ss[i-1] =='2')
                    dp[i] = i-2>=0? dp[i-2] : 1;
                else
                    return 0;

            else if (ss[i-1] == '1' || (ss[i-1] == '2' && ss[i] >= '1' && ss[i] <= '6'))
                //这里应该是else if，不是if，还是"2101"，i=2时，如果是if的话，也会被执行
                dp[i] = i-2>=0 ? (dp[i-1] + dp[i-2]) : 2;
            else
                dp[i] = dp[i-1];
            //max = Math.max(max, dp[i]);不用max才对，因为0的存在，比如"2101"这种情况
            //dp[1] = 2,但dp[2] = 0，这个0才是对的
        }
        return dp[ss.length-1];
    }
    //优化空间：
    public int numDecodings(String s){
        if (s.charAt(0) == '0')
            return 0;
        //首先不用ss去存储一个char数组了
        int first = 1; //dp[i-1]
        int second = 0;//dp[i-2]
        //int temp = 0;
        int temp = 1;//不能初始化为0，考虑一种特殊情况："1"
        for (int i = 1; i<s.length(); i++){
            if (s.charAt(i) == '0')
                if (s.charAt(i-1) == '1' || s.charAt(i-1) == '2')
                    temp = i-2>=0? second : 1;
                else
                    return 0;
            else if (s.charAt(i-1) == '1' || (s.charAt(i-1) == '2' && s.charAt(i) >='1' && s.charAt(i) <='6'))
                temp = i-2>=0? (first+second) : 2;
            else
                temp = first;
            second = first;
            first = temp;
        }
        return temp;
    }
    /**
     * 132. 分割回文串 II
     * */
    private boolean checkPalindrome(String s, int start, int end){
        //检查是不是回文串，思路很简单，双指针查找
        while (start<end){
            if (s.charAt(start) != s.charAt(end))
                return false;
            start++;
            end--;
        }
        return true;
    }
    public int minCut1(String s){
        int [] dp = new int[s.length()];//定义dp[i]为s[0:i]的最小切割次数
        //初始话考虑，dp[0] = 0;单个字符必然是回文串
        for (int i= 0; i<dp.length; i++)
            dp[i] = i;
        //为什么这么干，因为"aab"这种情况的话，得出结果是错的，因为是去最小值，如果dp[i]始终有个0的默认值，那么一定最后结果就是0
        //所以初始化为i，为必然大的值
        for (int i = 0; i<s.length(); i++){
            if (checkPalindrome(s, 0, i)){
                //本身就是回文串
                dp[i] = 0;
                continue;
            }
            for (int k = 0; k<i; k++){
                //遍历之前的情况
                if(checkPalindrome(s, k+1, i))
                    //如果s[k+1, i]为一个回文串的话，那么我只需要dp[k]代表的最小分割次数，加1就可
                    //但我需要找最小的。因此将要把dp[i]的值存储起来用就行
                    dp[i] = Math.min(dp[k]+1, dp[i]);

            }

        }
        return dp[s.length()-1];
    }
    //用最长回文串那题的dp来优化这题：
    public int minCut(String s){
        if (s.length() <= 1) return 0;
        int len = s.length();

        int [] dp = new int[s.length()];//定义dp[i]为s[0:i]的最小切割次数
        //初始话考虑，dp[0] = 0;单个字符必然是回文串
        for (int i= 0; i<dp.length; i++)
            dp[i] = i;

        boolean[][] checkPalindrome = new boolean[len][len];//储存回文串长度的dp，dp[i][j] = true意味着
        //s[i]-s[j]之间的字符串为回文串，顺序是i,j
        /**checkPalindrome[0][0] = checkPalindrome[0][1] = true;//初始化为true，boolean数组默认为false
         * 出错，因为s[0]-s[1]不一定回文串：ab
         */
        checkPalindrome[0][0] = true;
        for (int j = 1; j<len; j++){//从1开始，因为dp[0][0]单个字符当初始化就好了
            for (int i = 0; i<=j; i++){
                //这里需要考虑两个问题，一个填表，即checkPalindrome[i+1][j-1]是否被计算过，举个例子就可以了，15需要用24,显然计算过
                //另一个是i<j还是i<=j？前者节约时间，后者使得对角线为true，关键在于[1][1]这种点是否会被用，显然[0][2]会用，选择后者
                if (s.charAt(i) == s.charAt(j) && (j-i<=2 || checkPalindrome[i+1][j-1]))
                    checkPalindrome[i][j] = true;
            }
        }
        //那么我们就得到一个状态数组，s[i]-s[j]是否为回文串
        for (int i = 0; i<len; i++){
            if (checkPalindrome[0][i]){
                dp[i] = 0;
                continue;
            }
            for (int k=0; k<i; k++)
                if (checkPalindrome[k+1][i])
                    dp[i] = Math.min(dp[k]+1, dp[i]);
        }
        return dp[len-1];

    }

    public static void main(String[] args) {
        int[] test = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
        int[] test1 = {1,2,3,4,0,1,2,3,4,5};
        Arrays.stream(numberOfArithmeticSlices(test))
                .forEach(n->System.out.format("%d ", n));
        System.out.println();
        Arrays.stream(numberOfArithmeticSlices(test1))
                .forEach(n->System.out.format("%d ", n));
        System.out.println();
        System.out.println(helper(17));
        System.out.println(numberOfArithmeticSlices1(new int[]{1,2,3,4,5}));
    }
}
