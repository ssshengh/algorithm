package DPlearing.backpack;

import java.util.stream.IntStream;

public class zeroOne {
    /**
     * 基本的01背包问题
     */
    public int backpack01(int W, int[] weights, int[] values){
        if (W == 0 || weights.length==0 || values.length==0) return 0;

        /*定义为dp[i][j]为第i-1个(下标为i的数)在约束条件为子条件j情况下的最优值，即最大价值
        * 初始化关键在于：dp[i][0]=0，dp[o][j]为价值中大于其子约束的值*/
        int [][]dp = new int[values.length][W+1];
        for (int i = 0; i < W+1; i++) {
            if (weights[0]<=i)
                dp[0][i] = values[0];
        }

        /*接下来根据初始化进行计算*/
        for (int i = 1; i < values.length; i++) {
            for (int j = 1; j < (W + 1); j++) {
                if (j-weights[i]>=0)
                    dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-weights[i]]+values[i]);
                else
                    dp[i][j] = dp[i-1][j];
            }
        }
        return dp[values.length-1][W];
    }

    /**
     * 空间优化
     */
    public int backpack01_better(int W, int[] weights, int[] values){
        int N = weights.length;
        if (N == 0) {
            return 0;
        }

        int [] dp = new int[W+1];
        // 注意：这里 i 从 1 开始，到 N 结束，N 可以取到
        //注意这里是倒着来的，比之前我考虑的一维的优化更方便一些，而且不用再多一个数组的空间来复制存储
        for (int i = 1; i <= N; i++) {
            for (int j = W; j >= weights[i - 1]; j--) {
                dp[j] = Math.max(dp[j], dp[j - weights[i - 1]] + values[i - 1]);
            }
        }
        return dp[W];
    }

    /**
     * 分割相同子集问题：转化为01背包的关键是，意识到该题本质上是在求，寻找一个子集其和为整个数组和的一半
     */
    public boolean canPartition_ok(int[] nums){
        if (nums.length==0) return false;
        int sum = IntStream.of(nums).sum();//整体求和
        if (sum%2 != 0) return false;//奇数必然不存在

        int target = sum/2;
        boolean[][] dp = new boolean[nums.length][target+1];//代表第[0:i]的子数组中是否存在一个子集和为j

        //dp[0][nums[0]] = true;//第一列的初始化(横i竖j)
        /*少考虑了一个问题，如果只是输入[100]的时候，那么100是大于50的，但是最大索引是51，就超出索引了*/
        if (nums[0] <= target)   dp[0][nums[0]] = true;
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < (target+1); j++) {
                if (nums[i] == j || (j >= nums[i] && dp[i - 1][j - nums[i]])) {
                    if (j == target)
                        return true;
                    dp[i][j] = true;
                }else
                    dp[i][j] = dp[i-1][j];
            }
        }
        return false;
    }
    //自己单独写一下逆序单表优化空间：
    public boolean canPartition(int[] nums){
        if (nums.length==0) return false;
        int sum = IntStream.of(nums).sum();//整体求和
        if (sum%2 != 0) return false;//奇数必然不存在

        int target = sum/2;

        boolean[] dp = new boolean[target+1];//注意每一次这个就是一行表，用到的是该表的前述数据
        if (nums[0] <= target) dp[nums[0]] = true;

        for (int i = 0; i < nums.length; i++) {
            for (int j = target; j > 0; j--) {
                if ((j-nums[i]>=0 && dp[j-nums[i]]) || j==nums[i]) {
                    if (j == target)
                        return true;
                    dp[j] = true;
                }
            }
        }
        return false;

    }

    public static void main(String[] args) {
        zeroOne aa = new zeroOne();
        int res = aa.backpack01(5, new int[]{4,3,1,1}, new int[]{300,200,150,200});
        System.out.println(res);
    }
}
