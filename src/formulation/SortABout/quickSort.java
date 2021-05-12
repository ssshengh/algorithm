package formulation.SortABout;

import java.util.Arrays;
import java.util.Random;

public class quickSort {
    private void swap(int[] nums, int index1, int index2) {
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }
    /**
     * 快排序的基本实现，pivot选择为第一个元素
     */
    public int[] sortArray(int[] nums){
        int len = nums.length;
        helper(nums, 0, len - 1);
        return nums;

    }
    private void helper(int[] nums, int left, int right){
        if (left>=right)//注意这里是大于等于，区分于归并
            return;
        int p = pivotRandom(nums, left, right);
        helper(nums, left, p-1);//注意，取不到p，因为pivot在正确的位置了
        helper(nums, p+1, right);//这里就是为什么需要考虑大于的情况，如果pivot是最后一个的位置
    }
    //返回最后放置好的pivot的位置
    private int pivotFirst(int[] nums, int left, int right){
        int pivot = nums[left];//第一个位置作为pivot

        //循环不变量，关键所在，看我画的图
        int lt = left;
        for (int i = left+1; i<=right; i++){
            //i一直往右，到最后一个元素就可以了,注意从pivot后面第一个元素开始
            if (nums[i] <= pivot){
                //大不动，小操作,考虑相等的时候作为小于考虑，移动循环不变量
                lt++;
                swap(nums, lt, i);
            }
        }
        swap(nums, left, lt);//记得把pivot排到正确的位置
        return lt;
    }

    //第一个优化：随机选择pivot
    private final static Random rand = new Random(47);
    private int pivotRandom(int[] nums, int left, int right){
        int randIndex = left+rand.nextInt(right-left+1);//在数组索引范围内随机取一个值
        swap(nums, left, randIndex);
        int pivot = nums[left];//第一个位置作为pivot

        //循环不变量，关键所在，看我画的图
        int lt = left;
        for (int i = left+1; i<=right; i++){
            //i一直往右，到最后一个元素就可以了,注意从pivot后面第一个元素开始
            if (nums[i] <= pivot){
                //大不动，小操作,考虑相等的时候作为小于考虑，移动循环不变量
                lt++;
                swap(nums, lt, i);
            }
        }
        swap(nums, left, lt);//记得把pivot排到正确的位置
        return lt;
    }
    //三向切分法的快排序
    private void quickSort_three(int[] nums, int left, int right){
        if (left>=right)
            return;
        int randomIndex = left + rand.nextInt(right-left+1);
        swap(nums, randomIndex, left);

        int pivot = nums[left];
        int i = left+1, gt = right+1, lt = left;
        //关键点，i==gt的时候停止，因为gt是大于pivot的第一个
        //[left, lt]<pivot, (lt, i)==pivot, [gt, right]>pivot
        while (i < gt){
            if (nums[i] > pivot){
                //大于的情况，注意先把gt左移一位再交换，因为其是大于pivot的第一个
                gt--;
                swap(nums, gt, i);//i不用移动是第二个注意点，因为不知道交换过去的大还是小
            }else if (nums[i] == pivot){
                //相等的时候不做任何操作，i++即可
                i++;
            }else
            {
                //小于的时候注意，lt先右移一位，再交换，i也向后++，因为lt的位置是已经被排过的了,装满了等于的
                lt++;
                swap(nums, lt, i);
                i++;
            }
        }
        swap(nums, left, lt);//最后注意把pivot放在合适的位置,lt是小于pivot的最后一个，这样的话换过去刚好
        //这种方法剪枝了许多
        quickSort_three(nums, left, lt);
        quickSort_three(nums, gt, right);
    }
    public static void main(String[] args) {
        int[] test = {4,5,1,6,7,3,2};
        quickSort qq = new quickSort();
        qq.quickSort_three(test, 0, test.length-1);
        System.out.println(Arrays.toString(test));
        System.out.println(Arrays.toString(qq.sortArray(test)));
    }
}
