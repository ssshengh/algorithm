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
            int mn = min, mx = max;
            max = Math.max(mx*nums[i], Math.max(mn*nums[i], nums[i]));
            min = Math.min(mx*nums[i], Math.min(mn*nums[i], nums[i]));
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
    //有种写法，记录起始位置的
    public int[] maxSubarray(int[] B){
        int max = Integer.MIN_VALUE;
        int dp = B[0];
        int[] ans = new int[2];//记录起始位置
        int begin = 0;

        for (int i = 1; i<B.length; i++){
            if (dp >0 )
                dp+=B[i];
            else {
                dp = B[i];
                begin = i;
            }//关键在这里，他把最大子数组和的求和分裂成了，大于0与小于等于0两种情况
            if (dp > max){
                max = dp;
                ans[0] = begin;
                ans[1] = i;
            }
        }
        return ans;
    }

    //怎么写都不太对，看看大佬的思路：
    public int maxSubarraySumCircular_2(int[] A) {
        ArrayList<Integer> list = new ArrayList<>();//储存两倍的数组
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
     * 求最大子矩阵和：
     * 注意点：降维、以及如何暂存元素
     * 参照一维的存储位置的情况：dp大小变化(dp替换为新的起点时更新起点终点，正常加一个时更新终点)
     * */
    public int[] getMaxMatrix(int[][] matrix){
        int[] ans = new int[4];//结果储存数组
        int size_row = matrix.length;
        int size_column = matrix[0].length;
        int[] dim1_array = new int[size_column];
        int c_begin = 0;//用以记录即时的起始点的列数(行数由循环的i与j标出)
        int r_begin = 0;//记录即时起始点行
        int c_end = 0;//记录即时的终点的列数
        int max = Integer.MIN_VALUE;
        int dp = 0;//动态规划状态

        for (int i = 0; i<size_row; i++){
            for (int j = 0; j<size_column; j++)
                dim1_array[j] = 0;//每一次先要把暂存的一维数组清空

            for (int j = i; j < size_row; j++){
                dp = 0;//每一次应该先被重置，然后再计算
                for (int k=0; k<size_column; k++) {
                    dim1_array[k] += matrix[j][k];
                    //我们只是不断增加其高，也就是下移矩阵下边，所有这个矩阵每列的和只需要加上新加的哪一行的元素！！！！！！！
                    //因为我们求dp[i]的时候只需要dp[i-1]和nums[i],所有在我们不断更新一维数组时就可以求出当前位置的dp!!!!!
                    if (dp > 0)
                        dp += dim1_array[k];
                    else {
                        dp = dim1_array[k];
                        c_begin = k;
                    }
                    if (dp > max){
                        max = dp;
                        ans[0] = i;
                        ans[1] = c_begin;
                        ans[2] = j;
                        ans[3] = k;

                    }

                }
            }
        }
        return ans;

    }

    /**
     * 给定一个非空二维矩阵 matrix 和一个整数 k，找到这个矩阵内部不大于 k 的最大矩形和。
     * */
    public int maxSumSubmatrix1(int[][] matrix, int k){
        int dp = 0;
        int max = Integer.MIN_VALUE;
        int size_row = matrix.length;
        int size_column = matrix[0].length;
        int[] dim1_array = new int[size_column];
        int max_lowOfk = Integer.MIN_VALUE;

        for (int i = 0; i<size_row; i++){
            for (int j = 0; j<size_column; j++)
                dim1_array[j] = 0;//每一次先要把暂存的一维数组清空

            for (int j = i; j<size_row; j++ ){
                dp = 0;
                for (int n = 0; n<size_column; n++){
                    dim1_array[n] += matrix[j][n];
                    dp = Math.max(dp+dim1_array[n], dim1_array[n]);
//                    if (dp >k)
//                        return max;
                    //这种有个问题是[2,2,1]，3时，求到4就跳出来了，无法得到正确答案3，还需要一个元素来记下小于等于3的值
                    if (dp > max)
                        max = dp;
                    if (max <= k && max > max_lowOfk)
                        max_lowOfk = max;//这种可以解决上述问题，但无法解决这个问题，只要k小于任何一个数组中的数，就失败了

                }
            }
        }
        return max_lowOfk;
    }

    public int maxSumSubmatrix(int[][] matrix, int k){
        int dp = 0;
        int max = Integer.MIN_VALUE;
        int size_row = matrix.length;
        int size_column = matrix[0].length;
        int[] dim1_array = new int[size_column];
        int max_lowOfk = Integer.MIN_VALUE;

        for (int i = 0; i<size_row; i++){
            for (int j = 0; j<size_column; j++)
                dim1_array[j] = 0;//每一次先要把暂存的一维数组清空

            for (int j = i; j<size_row; j++ ){
                dp = 0;
                for (int n = 0; n<size_column; n++){
                    dim1_array[n] += matrix[j][n];
                }
                //改思路，在每一次把压缩数组求出来再求解
                //暴力求最大值
                max = Math.max(max, dpmax(dim1_array, k));
                if (max == k)
                    return k; // 尽量提前
            }
        }
        return max;
    }
    // 在数组 arr 中，求不超过 k 的最大值，考虑[2,2,-1],0
    private int dpmax(int[] arr, int k) {
        int rollSum = arr[0], rollMax = rollSum;
        // O(rows) 常规求最大和
        for (int i = 1; i < arr.length; i++) {
            if (rollSum > 0)
                rollSum += arr[i];
            else
                rollSum = arr[i];
            if (rollSum > rollMax)
                rollMax = rollSum;
        }
        if (rollMax <= k)
            return rollMax; //失败用例时，这儿的结果是2
        // O(rows ^ 2)这一步为了解决我遇到的第二个问题，k很小的时候怎么办，这里是暴力法解决的
        int max = Integer.MIN_VALUE;
        for (int l = 0; l < arr.length; l++) {
            int sum = 0;
            for (int r = l; r < arr.length; r++) {
                sum += arr[r];
                if (sum > max && sum <= k)
                    max = sum;//失败用例时，这儿在取到-1的时候才会赋值
                if (max == k)
                    return k; // 尽量提前
            }
        }
        return max;
    }
    //对暴力解法的优化，动态规划的求最大和优化，依然考虑[2,2,-1],0
    //这个方法有问题，错误的
    private int helper3(int[] nums, int k) {
        int[] dp = new int[nums.length + 1];
        dp[0] = nums[0];
        int res = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
            if (dp[i] == k) {
                return dp[i];
            }
            if (res < dp[i] && dp[i] < k) {
                res = dp[i];
            }
            // res = Math.max(dp[i], res);
        }
        return res;
    }


}
