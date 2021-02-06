package DPlearing.liner;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class sumOfsubArray {
    //最大连续子序列和
    public int maxSubArray(int[] nums){
        int dp = 0, max = nums[0];
        for (int item : nums){
            dp = Math.max(dp + item, item);
            max = Math.max(max, dp);
        }
        return max;
    }//注意内含了滚动数组的思路：即不用一个显式的数组来处理，用时间换空间

    //最大连续子序列乘积：关键在于多个负数的处理，特别巧妙
    public int maxProduct_1(int[] nums){
        //非滚动数组思想
        int length = nums.length;
        int[] maxF = new int[length];
        int[] minF = new int[length];
        System.arraycopy(nums, 0, maxF, 0, length);
        System.arraycopy(nums, 0, minF, 0, length);
        for (int i = 1; i < length; ++i) {
            maxF[i] = Math.max(maxF[i - 1] * nums[i], Math.max(nums[i], minF[i - 1] * nums[i]));
            minF[i] = Math.min(minF[i - 1] * nums[i], Math.min(nums[i], maxF[i - 1] * nums[i]));
        }
        int ans = maxF[0];
        for (int i = 1; i < length; ++i) {
            ans = Math.max(ans, maxF[i]);
        }
        return ans;
    }
    public int maxProduct(int[] nums){
        //滚动数组思想
        int max = nums[0], min = nums[0], ans = nums[0];
//        for (int item : nums){
//            max = Math.max(max * item, Math.max(min * item, item));
//            min = Math.min(max * item, Math.min(min * item, item));
//            ans = Math.max(max, ans);
//        }不能这么写，第一个不为1时会被乘两次
        for (int i = 1; i<nums.length; i++){
            max = Math.max(max*nums[i], Math.max(min*nums[i], nums[i]));
            min = Math.min(max*nums[i], Math.min(min*nums[i], nums[i]));
            ans = Math.max(max, ans);
        }
        return ans;
    }


    /**
     * 给定一个由整数数组 A 表示的环形数组 C，求 C 的非空子数组的最大可能和。
     * 环形问题：拼接、分类
     * */
    //分类法，关键思路是：拼接起来的时候，中间最大等价于，单个的中间最小
    public int maxSubarraySumCircular(int[] B){
        int max = 0, min = 0;
        int sum_max = B[0], sum_min = B[0];//储存最大子数组和和最小子数组和
        int len = B.length;
        int sum_array = 0;//储存整个数组和
        for (int item : B){
            sum_array += item;
            max = Math.max(max+item, item);
            sum_max = Math.max(sum_max, max);
            //反过来求最小是直觉性的，但是不会证明
            min = Math.min(min+item, item);
            sum_min = Math.min(sum_min, min);
        }
//        return Math.max(sum_max, (sum_array - sum_min));
        //这种情况有问题，当全为负的时候[-2,-3,-1]，会出现sum_min为整个数组和,此时最大值sum_max为-1
        //但会判定sum_array - sum_min = 0 > sum_max
        if ((sum_array - sum_min) > sum_max && (sum_array - sum_min)!= 0){
            return (sum_array - sum_min);
        }
        return sum_max;
    }
    //连接法：找最大，关键在于找到的最大子数组的最后一个元素不能为第一个元素
    public int maxSubarraySumCircular_1(int[] B){
        int[] sums = new int[B.length * 2];
        for (int i = 0; i<sums.length; i++){
            if (i < B.length)
                sums[i] = B[i];
            else
                sums[i] = B[i % B.length];
        }//合为一个数组
        int start = 0, end = 0;
        int max = 0;//记录数组最大值
        int dp = 0;//作为状态序列

        for (int i=0; i<sums.length; i++) {
            int mx = max;

            if (end > B.length -1 && start == end%B.length) {
                break;
            }
            dp = Math.max(dp + sums[i], sums[i]);
            max = Math.max(max, dp);
            if (max != mx){
                end = i;
            }
            if (dp + sums[i] < sums[i])
            {
                start = i;
                end = i;
            }
        }
        return max;
    }

    //怎么写都不太对，看看大佬的思路：
    public int maxSubarraySumCircular_2(int[] A) {
        ArrayList<Integer> list = new ArrayList<>();
        int n = A.length;
        for (int i = 0; i < 2; i++) {
            for (int i1 : A) list.add(i1);
        }
        int[] preSum = new int[2 * n + 1];

        for (int i = 1; i <= 2 * n; i++) {
            preSum[i] = preSum[i - 1] + list.get(i-1);
        }
        int ans = Integer.MIN_VALUE;
        Deque<Integer> q = new LinkedList<>();
        q.offerLast(0);
        for (int i = 1; i <= 2 * n; i++) {
            if (!q.isEmpty() && i - n > q.peekFirst()) q.pollFirst();
            if (!q.isEmpty()) ans = Math.max(ans, preSum[i] - preSum[q.peekFirst()]);
            while (!q.isEmpty() && preSum[q.peekLast()] >= preSum[i]) q.pollLast();

            q.offerLast(i);
        }
        return ans;
    }


    /**
     *
     * */

}
