package formulation.SortABout;

import java.util.*;

public class SelectAndInsert {
    /**
     * 选择排序：基本思路是每一轮从其中找到最小的一个元素，然后放在第一个
     */
    public int[] sortArray(int[] nums) {
        int len = nums.length;
        if (len <= 1) return nums;

        for (int i = 0; i < (len - 1); i++) {
            int minIndex = i;
            for (int j = i+1; j<len; j++)
                if (nums[j]<nums[minIndex])
                    minIndex = j;
            swap(nums, minIndex, i);
        }
        return nums;
    }
    //工具类，用来帮助交换的
    private void swap(int[] num, int i, int j){
        int temp = num[i];
        num[i] = num[j];
        num[j] = temp;
    }

    /**
     * 插入排序：思想是最后需要排序的元素逐渐与前一个元素比较，如果小于前一个元素则交换位置直到到最合适的位置
     */
    public int[] InsertSort(int[] nums){
        int len = nums.length;
        if (len <= 1) return nums;
//        for (int i = 1; i<nums.length; i++){
//            关键思路是会交换多次的，如果第一个和i交换了，那么接下来就是交换过去的i和第二个比较
//            for (int j = 0; j < i; j++) {
//                if (nums[j]>nums[i])
//                    swap(nums, i, j);
//            }
        //从后往前可以节约一些时间
        for (int i = 1; i < len; i++) {
            for (int j = i; j > 0; j--)
                if (nums[j - 1] > nums[j]) {
                    swap(nums, j - 1, j);
                } else {
                    break;
                }

        }

        return nums;
    }
    //第二种方法：使用一个临时变量暂存其中的需要判断的元素，然后从后往前将元素不断赋值
    public int[] InsertSort_batter(int[] nums){
        int len = nums.length;
        for (int i = 1; i < len; i++) {
            int temp = nums[i];
            int j = i;
            while (j>0 && temp<nums[j-1]){
                nums[j] = nums[j-1];
                j--;
            }
            nums[j] = temp;
        }
        return nums;
    }

    public static void main(String[] args) {
        int[] test = new int[]{1,5,4,2,8,9};
        int[] test2 = new int[]{5,2,3,1};
        SelectAndInsert ss = new SelectAndInsert();
        int[]res = ss.InsertSort_batter(test);
        System.out.println(Arrays.toString(res));
    }
}
