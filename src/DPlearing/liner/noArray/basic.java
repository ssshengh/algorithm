package DPlearing.liner.noArray;

import com.javaLearing.chapter22.TrafficLight;



import java.util.*;

import java.util.stream.Stream;

public class basic {
    /**
     * 132模式
     */
    public static boolean find132pattern_bad(int[] nums){
        //中心散开法
        if (nums.length <= 2) return false;
        int n = nums.length;

        for (int i =2; i<n; i++)//第一层遍历每个元素，然后从中心散开
        {
            int first = 0, second = 0;
            for (int j =1; j<i; j++){
                first = i-j;second = i-j-1;//代表前面两个位置的指针

                while (second!= 0){
                    if (nums[first]>nums[i] && nums[second]<nums[i] && nums[first] > nums[second])
                        return true;
                    second--;
                }
                if (nums[first]>nums[i] && nums[second]<nums[i] && nums[first] > nums[second])
                    return true;

            }
        }
        return false;

    }//过于暴力，超出时间限制
    //考虑贪心的思路
    public boolean find132pattern_less(int[] nums){
        int[] dp = new int[3];//0位置储存最小，1位置储存最大，2位置为k，储存大于0小于1位置的数
        //大小顺序是021
        dp[0] = nums[0];
        for (int i = 0; i<nums.length; i++){
            dp[1] = nums[i];
            if (nums[i] > dp[1])
                dp[1] = nums[i];
            else if (nums[i] < dp[1])//如果单纯只有这个就有可能出现个问题：遇到一个更小的i，但是顺序在j后面去了[1,0,1,-4,-3]
                dp[0] = Math.min(dp[0], nums[i]);
            if (nums[i] > dp[0] && nums[i] <dp[1])
                return true;
        }
        return false;
    }

    /**
     * 只有两个键的键盘
     * 别忘了dp还可以检索前n个状态，不只是前1个
     */
    public int minSteps(int n){
        int[] dp = new int[n+1];//定义dp为i个A需要的操作数
        //这个初始化是精髓！！！因为质数必然就是和质数一样大小的次数！！当然，得加个1，作为一次copy的操作
        for (int i= 2; i<n+1; i++)//注意dp[1]是0
            dp[i] = i;

        for (int i = 2; i<=n; i++)
            for (int j = 1; j<=i/2; j++)//下取整，可以取到等号
            {
                if (i%j == 0)
                    dp[i] = Math.min(dp[i], dp[j]+i/j);//注意到这里i/j把copy算进去了，eg 9
                //这儿就是为什么所有都初始化为素数的情况，因为在调前n个状态会用到，并且，素数的操作次数不会改变！！！
            }
        return dp[n];
    }

    public static void main(String[] args) {
        int[] test = {-2,1,2,-2,1,2};
        int[] dp = new int[5];
        Arrays.fill(dp, 1);
        Arrays.stream(dp).forEach(System.out::println);
    }
}
