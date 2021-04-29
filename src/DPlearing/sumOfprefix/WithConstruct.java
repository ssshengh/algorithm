package DPlearing.sumOfprefix;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WithConstruct {
    /**
     * 和等于k的最长子数组长度
     */
    public int maxSubArrayLen_ok(int[] nums, int k){
        if (nums.length == 0) return 0;

        int [] sum = new int[nums.length];//前缀和数组
        sum[0] = nums[0];
        for (int i = 1; i<nums.length; i++)
            sum[i] = sum[i-1] + nums[i];//sum[i] : nums[0:i]

        //int[][] dp = new int[sum.length][sum.length];//i到j的和的大小
        int len = 0;//返回最大长度
        if (sum[0] == k) len = 1;
        for (int i = 1; i<nums.length; i++) {
            //把j等与0的情况放这里
            if (sum[i] == k) len = Math.max(len, i+1);
            for (int j = 1; j <= i; j++) {
                //j -> i的数组和，其中考虑了为单个数的情况
                if (sum[i] - sum[j-1] == k)
                    len = Math.max(len, i-j+1);
            }
        }
        return len;
    }
    public int maxSubArrayLen(int[] nums, int k){
        if (nums.length == 0) return 0;

        int [] sum = new int[nums.length+1];//前缀和数组,这里我用另一种前缀和，操作方便一些，sum[i]为i个数的和
        Map<Integer, Integer> map = new HashMap<>();//<前缀和， 位置>
        map.put(0, 0);//0位置，意味着前0个和为0
        for (int i=1; i<sum.length; i++)
        {
            sum[i] = sum[i-1] + nums[i-1];
            if (!map.containsKey(sum[i]))//暂时不知道为什么流下小的位置
                map.put(sum[i], i);
        }
        int len = nums.length;
        int max = 0;

        for (int i = len; i>max; i--){
            if (map.containsKey(sum[i] - k))//因为是从后往前遍历，所以如果存在，那必然能找到和为k的子数组的右边最后一个元素，
                //而这个元素对应的前缀和减去k，必然为左边第一个元素值
                max = Math.max(max, i-map.get(sum[i] - k));
        }//这种修改循环条件有个前提，小于max的必然不是我们的结果，而且我们不需要遍历到0
        return max;
    }
    /**
     * 连续数组
     */
    //显然这种数组比较难理解，我们其实用map的话，思路就很简单，不更新位置就行了
    public int findMaxLength_ok(int[] nums){
        //这题思路比较经典，记一记
        //这个数组的定义是最巧妙的，因为遇到0减一遇到1加一，那么最小值和最大值都是nums的长度
        //如果我们需要不断地得到找到相同值时的位置时，就需要在arr[求得的值]里面存储下位置，那么下一次遇到相同的值的时候，就直接减去上一次的位置
        int[] arr = new int[nums.length*2 + 1];
        int count = 0, maxLen = 0;//最关键的思路
        Arrays.fill(arr, -2);//根据下面的思路，其他位置必然不可能存储count为0的时候，所以不会为-1，而存储的是位置，更新时会从0开始
        //取为-2是为了后面好判断
        arr[nums.length] = -1;//arr其他位置存储的数组位置的映射必然是大于等于0的，为了与存为0的位置区分开的特殊情况

        for (int i =0; i<nums.length; i++){
            count = count + (nums[i]==0? -1 : 1);//更新算法
            //不用想太多细节，关键就是找，count相同的时候，它一定被存在了同一个位置，count从-n到n取，所以需要考虑为0时
            //此外，在找到后只更新maxLen而不更新arr[count + nums.length]，意味着存储的必然是同一count的第一个位置
            if (arr[count + nums.length] >= -1)
                maxLen = Math.max(maxLen, i - arr[count + nums.length]);
            else //在我们每求得一个值的时候，就存在值加上长度的位置上，这样数组确保能够全部存下，并且由于得到的count是离散的
            //如果后面找到同样的值，那么一定会在上一次存入的位置找到，而且一定大于等于-1
            //等于号考虑了这种情况：0和1数目相同时，存在了arr[nums.length]里
                arr[count+nums.length] = i;
        }
        return maxLen;
    }
    public int findMaxLength(int[] nums){
        Map<Integer, Integer> map = new HashMap<>();
        //<count值， 第一次count值出现的位置>
        map.put(0, -1);
        int count = 0, maxLen = 0;
        for (int i = 0; i<nums.length; i++){
            count = count + (nums[i] == 0? -1 : 1);
            if (map.containsKey(count))
                maxLen = Math.max(maxLen, i - map.get(count));//左开右闭
            else //这种是刚好，对着图看
                map.put(count, i);
            //那么关键就是为什么要初始化一个count为0的时候，其实就是，第一次为0一定是没开始计算count的时候开始，到下一次为0了，即数组从第一个到那个位置
            //其实另一个角度，count为0，不就意味着，一直加过来，到这个位置，0和1数目一样嘛～
        }
        return maxLen;
    }
}
