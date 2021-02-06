package DPlearing.liner;

import java.util.Arrays;
import java.util.Comparator;

/**
 * LIS 是单串上最经典的问题，它的状态设计是单串动态规划最经典的状态设计。
 * 很多单串的题目。状态设计都是启发自这道题的设计，因此需要专门练习，加深印象。
 * */
public class oneArray {
    /**
     * 最大上身子序列：
     * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
     * 思路就是典型的动态规划，记得四要素：状态定义、状态转移、边界、初始条件
     * */
    public int lengthOfLIS1(int[] nums){
        if (nums.length == 0)
            return 0;
        int [] dp = new int[nums.length];   //记住：dpi存储的是以nums[i]结尾的子序列的最长子序列长度
        int max = 1;
        dp[0] = 1;
        for (int i = 1; i<nums.length; i++){
            dp[i] = 1;
            for (int j = 0; j<i; j++){
                if (nums[i] > nums[j])
                    dp[i] = Math.max(dp[i], dp[j]+1);
                //这一句是关键，在每一个子问题的时候，都需要遍历dp子序列，查找之前的结果——>穷举
                //只需要比较nums[i]和nums[j]的原因就是，是在每一个子序列中穷举，考虑过程看笔记从
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    /**
     * 顶级大佬的方法：贪心+二分查找
     * */
    public static int lengthOfLIS(int[] nums) {
        int len = nums.length;
        if (len <= 1) {
            return len;
        }

        // tail 数组的定义：长度为 i + 1 的上升子序列的末尾最小是几
        int[] tail = new int[len];
        // 遍历第 1 个数，直接放在有序数组 tail 的开头
        tail[0] = nums[0];
        // end 表示有序数组 tail 的最后一个已经赋值元素的索引
        int end = 0;

        for (int i = 1; i < len; i++) {
            // 【逻辑 1】比 tail 数组实际有效的末尾的那个元素还大
            if (nums[i] > tail[end]) {
                // 直接添加在那个元素的后面，所以 end 先加 1
                end++;
                tail[end] = nums[i];
            } else {
                // 使用二分查找法，在有序数组 tail 中
                // 找到第 1 个大于等于 nums[i] 的元素，尝试让那个元素更小
                int left = 0;
                int right = end;
                while (left < right) {
                    // 选左中位数不是偶然，而是有原因的，原因请见 LeetCode 第 35 题题解
                    // int mid = left + (right - left) / 2;
                    int mid = left + ((right - left) >>> 1);
                    //>>：带符号右移。正数右移高位补0，负数右移高位补1。
                    //>>>：无符号右移。无论是正数还是负数，高位通通补0。
                    if (tail[mid] < nums[i]) {
                        // 中位数肯定不是要找的数，把它写在分支的前面
                        left = mid + 1;
                    } else {
                        right = mid;
                    }
                }
                // 走到这里是因为 【逻辑 1】 的反面，因此一定能找到第 1 个大于等于 nums[i] 的元素
                // 因此，无需再单独判断
                tail[left] = nums[i];
            }
            // 调试方法
            // printArray(nums[i], tail);
        }
        // 此时 end 是有序数组 tail 最后一个元素的索引
        // 题目要求返回的是长度，因此 +1 后返回
        end++;
        return end;
    }

    // 调试方法，以观察是否运行正确
    private void printArray(int num, int[] tail) {
        System.out.print("当前数字：" + num);
        System.out.print("\t当前 tail 数组：");
        int len = tail.length;
        for (int i = 0; i < len; i++) {
            if (tail[i] == 0) {
                break;
            }
            System.out.print(tail[i] + ", ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] nums = new int[]{3, 5, 6, 2, 5, 4, 19, 5, 6, 7, 12};
        oneArray solution = new oneArray();
        int lengthOfLIS = oneArray.lengthOfLIS(nums);
        System.out.println("最长上升子序列的长度：" + lengthOfLIS);
    }

    /**
     *  最长递增子序列的个数(上一题的进阶，关键在于用另一个数组来存储数量信息)
     * */
    public int findNumberOfLIS(int[] nums){
        int N = nums.length;
        if (N <= 1) return N;

        int [] dp = new int[N];//和上一题的处理方式一样，dp[i]意味着以nums[i]为结尾的子序列的最大长度
        int [] count = new int[N];//count[i]意味着最长长度为dp[i]的子序列数量
        Arrays.fill(dp, 1);
        Arrays.fill(count, 1); //记住这个方法
        int max = 0;
        for (int i = 1; i<N; i++){
            for (int j = 0; j<i; j++){
                if (nums[j] < nums[i]){
                    if (dp[j] + 1 > dp[i])
                    {//和LIS一样的更新dp数组，找到一个大的，如果比当前存储的dp[i]大，则意味着找到了一个长1的最长子序列
                        dp[i] = dp[j] + 1;
                        count[i] = count[j];
                    }else if (dp[j] +1 == dp[i])
                        //这里意味着，长度为这么长的子序列我们已经找到一次了
                        count[i] += count[j];
                }
            }
            max = Math.max(max, dp[i]);
        }
        int res = 0;//储存最后的结果
        for (int i = 0; i<N; i++){
            if (dp[i] == max)
                res += count[i];
        }
        return res;
    }


    public int lengthOfLIS_1(int[] nums) {
        //使用内置二分查找的LIS算法
        int[] dp = new int[nums.length];
        int len = 0;
        for (int num : nums) {
            int i = Arrays.binarySearch(dp, 0, len, num);//内置二分查找算法
            //从0-len的dp数组中查找num数
            //返回值很关键，存在的话返回下标，不存在的话返回-((如果存在应该在的位置的下标j)+1)
            if (i < 0) {
                i = -(i + 1);//不存在，得到应该插入位置下标
                //这个很关键比如数组[1, 20, 30, 40, 50]，查找55，那么比所有的都大，j=5，返回i=-6,这里计算得到i=5，正确插入
                //查找25，j=2,返回i=-3,计算得i=2，替换30为更小的25
                //即完成了之前的逻辑
            }
            dp[i] = num;//在该位置插入
            if (i == len) {
                len++;//如果插入的位置等于len，意味着最长子序列长度加1了（最后的位置+1才是长度），更新长度
            }
        }
        return len;
    }

    public int maxEnvelopes(int[][] envelopes) {
        // sort on increasing in first dimension and decreasing in second
        Arrays.sort(envelopes, new Comparator<int[]>() {//注意这个函数参数的用法，要改变默认的排序方法，需要使用类的数据类型
            @Override
            public int compare(int[] arr1, int[] arr2) {
                if (arr1[0] == arr2[0]) {
                    return arr2[1] - arr1[1];//根据大于0小0来判定
                } else {
                    return arr1[0] - arr2[0];
                }
            }
        });
        // extract the second dimension and run LIS
        int[] secondDim = new int[envelopes.length];
        for (int i = 0; i < envelopes.length; ++i) secondDim[i] = envelopes[i][1];
        return lengthOfLIS_1(secondDim);
    }

}

