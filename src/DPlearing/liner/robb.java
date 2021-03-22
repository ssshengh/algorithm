package DPlearing.liner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//打家劫舍系列
public class robb {
    /**
     * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，
     * 影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
     * */
    public int rob2(int[] nums) {
        int len = nums.length;
        if (len == 0)
            return 0;
        int[] dp = new int[nums.length+1];//关键是这里加1，因为dp[i]意味着前i个房子，对应的是nums[i-1]
        //int max = Integer.MIN_VALUE;

        //初始化：
        dp[0] = 0;
        dp[1] = nums[0];
        //int max = dp[1];//应对[0]的情况
        for(int i = 2; i<len+1; i++){
            dp[i] = Math.max(nums[i-1] + dp[i-2], dp[i-1]);
            //max = Math.max(dp[i], max);可以简单优化为没有另一个变量max，额外空间少1
        }
        return dp[len];
    }
    public int rob1(int[] nums){
        if (nums.length == 0)
            return 0;
        int first = 0;//dp[i-2]
        int second = 0;//dp[i-1]

        for (int num:nums){
            int temp = Math.max(first + num, second);
            first = second;
            second = temp;
        }
        return second;
    }

    /**
     * 第一个问题加一个条件：环状
     * */
    private int myRob(int[] nums){
        int first = 0, second = 0, temp;

        for (int num:nums){
            temp = Math.max(first + num, second);
            first = second;
            second = temp;
        }
        return second;
    }
    public int rob(int[] nums) {
        if (nums.length==0) return 0;
        if (nums.length==1) return nums[0];

        return Math.max(myRob(Arrays.copyOfRange(nums, 0, nums.length-1)),
                myRob(Arrays.copyOfRange(nums, 1, nums.length)));//注意这个的使用
    }

    /**
     * 变式：删除与获得点数
     * 每次操作中，选择任意一个nums[i]，删除它并获得nums[i]的点数。之后，你必须删除每个等于nums[i] - 1或nums[i] + 1的元素。
     * */
    public int deleteAndEarn(int[] nums){
        if (nums.length == 0)
            return 0;
        if (nums.length ==1)
            return nums[0];

        int len = nums.length;
        int max = Integer.MIN_VALUE;
        //先找到数组中最大的值
        for (int num:nums)
            max = Math.max(num, max);
        //构造新数组all
        int [] all = new int[max+1];
        for (int num:nums)
            all[num]++;

        //打家劫舍
        int first = 0, second = 0;//dp[i-1],dp[i-2]
        int temp = 0;//dp[i]
        for (int i = 0; i<all.length; i++){
            temp = Math.max(first, second+i*all[i]);
            second = first;
            first = temp;
        }
        return temp;
    }

    /**
     * 打家劫舍二变式：3n快披萨
     * 问题关键在于区别打家劫舍2，
     * */
    public int maxSizeSlices(int[] slices){
        if (slices.length == 0)
            return 0;
        else if (slices.length ==1)
            return slices[0];
        return Math.max(maxSizeSlice(Arrays.copyOfRange(slices, 1, slices.length)),
                maxSizeSlice(Arrays.copyOfRange(slices, 0, slices.length-1)));
    }
    private int maxSizeSlice(int [] slices){
        int len = slices.length;
        int choose = (len+1)/3;
        int[][] dp = new int[len+1][choose+1];//注意加1，状态定义

        for (int i = 1; i<len; i++)
            for (int j = 1; j<choose; j++)
                dp[i][j] = Math.max(dp[i-1][j], (i-2<=0? 0 : dp[i-2][j-1]) + slices[i]);

        return dp[len][choose];
    }

    /**
     * 变式1：斐波那契子序列
     * 先自己写一版，试着实现状态方程
     * */
    public static int lenLongestFibSubseq(int[] arr){
        int len = arr.length;
        int [][] dp = new int[len][len];//状态方程，dp[i][j]表示由arr[i]arr[j]结尾的最长斐波那契子序列长度
        int max = Integer.MIN_VALUE;

        System.out.println("最开始dp为：");
        printDp(dp);

        //需要填充2，不然跑出来结果少二，很简单：只要dp[i][j]存在，那么就意味着至少存在两个数的子序列
        System.out.println("dp初始化为：");
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                dp[i][j] = 2;//因为在我们的算法中i永远小于j，所以不需要全部填充，这也导致了下面的不同
            }
        }
        printDp(dp);
        //本质上是为了遍历整个数组，比较每一个状态并更新，所以需要两层循环
        //这里就可以看出为什么称动态规划为穷举法了
        //这个是我一直没想出来的循环写法，如何在数组内找到k、i、j这三个数
        for (int i = 0; i < len; i++)
            for (int j = i+1; j<len; j++){
                //i在k和j之间
                for (int k = i-1; k>=0; k--){
                    if (arr[k] + arr[i] == arr[j])
                        //dp[i][j] = dp[k][i]+1;
                        dp[i][j] = Math.max(dp[k][i]+1, dp[i][j]);//因为k在i前面，可能遇到不是2而是0的位置，这样算出来就少2

                    max = Math.max(max, dp[i][j]);
                }
            }
        System.out.println("dp值为：");
        printDp(dp);
        return max >=2? max:0;
    }//这种方法是超时的
    private static void printDp(int [][] dp){
        int n = dp.length;//行
        int m = dp[0].length;//列
        for (int[] ints : dp) {
            for (int j = 0; j < m; j++)
                System.out.print(ints[j]);
            System.out.println();
        }
    }

    public int lenLongestFibSubseq1(int[] A) {
        int N = A.length;
        Map<Integer, Integer> index = new HashMap();
        for (int i = 0; i < N; ++i)
            index.put(A[i], i);

        Map<Integer, Integer> longest = new HashMap();
        int ans = 0;

        //排序是j、k，与上一题的上三角遍历相比，这一题是下三角矩阵，但是都一样，两层循环都能把数组完全遍历
        for (int k = 0; k < N; ++k)
            for (int j = 0; j < k; ++j) {
                //接下来两行的意思就是，找到了A[i]+a[j]=a[k]，并且i满足就在j前面的条件
                //与上面的方法相比，其实就是不用遍历i，关键只要A[i]满足条件并且满足位置关系就可以了
                int i = index.getOrDefault(A[k] - A[j], -1);//如果存在A[i]+a[j]=a[k]得到i
                if (i >= 0 && i < j) {
                    //在i和j之间：i、j、k
                    // Encoding tuple (i, j) as integer (i * N + j)
                    int cand = longest.getOrDefault(i * N + j, 2) + 1;
                    longest.put(j * N + k, cand);//注意这个键值对，把j，k与现在斐波那契子序列的值map在一起，利用j*N+k把jk关联
                    ans = Math.max(ans, cand);
                }
            }

        return ans >= 3 ? ans : 0;
    }

    public int lenLongestFibSubseq_mine(int[] B){
        int len = B.length;
        Map<Integer, Integer> index = new HashMap<>();
        for (int i = 0; i<len; i++)
            index.put(B[i], i);//把位置和值对应起来，后面用
        Map<Integer, Integer> longest = new HashMap<>();//用来储存最大值，关联起i和j
        int max = Integer.MIN_VALUE;//储存最大值
        //顺序是k，i，j
        for (int i = 0; i<len; i++)
            for (int j = i+1; j<len; j++){
                //注意不用遍历k，index的用处就是用来找k,
                int k = index.getOrDefault(B[j] - B[i], -1);
                if (k>=0 && k<i){//必须在i前面，不能取等
                    int temp = longest.getOrDefault(k*len+i, 2);//编码k和i共同映射为与ki对应的最长子序列长度
                    //意味着，(k,i)这个状态是否存在，不存在的话，就用2来替代，因为现在找到j了，k和i就是2个
                    //这时我找到了新的路径:从(k,i)到(i,j)，需要写入longest中
                    longest.put(i*len+j, temp+1);
                    max = Math.max(max, temp+1);
                }
            }
        return max;
    }
    /**
     * 这一题的变式：最长等差数列子序列
     * 这一版有问题，没注意到一点，数组内可能有重复元素，因此index数组就出错了
     * */
    public int longestArithSeqLength1(int[] B){
        if (B.length == 0 )
            return 0;
        else if (B.length == 1)
            return 1;
        int len = B.length;
        //有重复元素，这样就会导致最后找到的位置出错，循环内部的k出问题
        Map<Integer, Integer> index = new HashMap<>();
        for (int i = 0; i<len; i++)
            index.put(B[i], i);

        Map<Integer, Integer> longest = new HashMap<>();
        int max = Integer.MIN_VALUE;
        //k,i,j
        for (int i = 0; i<len; i++)
            for (int j = i+1; j<len; j++){
                //B[j] - B[i] = B[i] - B[k]
                int k = index.getOrDefault(2*B[i]-B[j], -1);
                if (k>=0 && k<i){
                    int temp = longest.getOrDefault(k*len+i, 2);
                    longest.put(i*len+j, temp+1);
                    max = Math.max(temp+1, max);
                }
            }
        return Math.max(max, 2);
    }
    //如何看错误的话就看：[1,3,3,1,7,5,7]，按照目前的方式，本来应该是1，3，5，7的变成了1，5，7
    //因为最开始存储的位置就是最后一个，这是一个难以发现的bug
    //但是下面放在后面就可以确保，先考虑了前面的重复元素，再考虑后面的。
    //修改后：
    public int longestArithSeqLength(int[] B){
        int len = B.length;
        int [][] dp = new int[len][len];
        for (int i = 0; i<len; i++){
            for (int j = i+1; j<len; j++)
                dp[i][j] = 2;
        }
        //优化思路和之前一样，还是k这个值，不能再多一重循环，那么就想办法，把k和等差联系起来
        Map<Integer, Integer> map = new HashMap<>();
        int max = Integer.MIN_VALUE;
        //k、i、j
        for (int i = 0; i<len; i++) {
            for (int j = i + 1; j < len; j++) {
                int value_k = 2 * B[i] - B[j];
                if (map.containsKey(value_k))
                    dp[i][j] = Math.max(dp[map.get(value_k)][i] + 1, dp[i][j]);
                max = Math.max(dp[i][j], max);
            }
            map.put(B[i], i);//把所有的数组值存储进去
        }
        return max;
    }

    public static void main(String[] args) {
//        int [] array1 = new int[] {1,3,7,11,12,14,18};
//        int [] array2 = new int[] {1,2,3,4,5,6,7,8};
//        System.out.println(lenLongestFibSubseq(array1));
//        System.out.println(lenLongestFibSubseq(array2));
        Map<Integer, Integer> map = new HashMap<>();
        int [] test = new int[]{1,1,3,3,4,4};
        for (int i = 0; i<test.length; i++)
            map.put(test[i], i);
        System.out.println(map);
    }
}
