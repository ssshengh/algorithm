package formulation.SortABout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 排序类的题目：
 */
public class test {
    /**
     * 合并两个有序数组
     */
    public void merge_ms(int[] nums1, int m, int[] nums2, int n) {
        if (n==0)
            return;
        //与归并的区别是，这里是合并到nums1里面去，里面的元素只会是m个，包含了0元素
        int[] help = new int[m];//把nums1中的非零元素全部提取出来
        int j = 0;
        for (int k : nums1) {
            if (k != 0) {
                help[j] = k;
                j++;
            }
        }
        j = 0;//helper的指针
        int k = 0;//n2的指针
        for (int i = 0; i < (m + n); i++) {
            if (j==m){
                nums1[i] = nums2[k];
                k++;
            }else if (k>n){
                nums1[i] = help[j];
                j++;
            }else if (help[j]>=nums2[k]){
                nums1[i] = nums2[k];
                k++;
            }else {
                nums1[i] = help[j];
                j++;
            }
        }
    }
    //因为这一题的特点，还可以使用另一种双指针的办法：
    public void merge(int[] nums1, int m, int[] nums2, int n){
        //思路是，两个指针分别指向两个数组有效数的最后一个数，然后比较，从nums1的最后一个位置开始赋值
        //注意一个关键点，如果nums2的某个元素(前面还有元素)比nums1有效元素第一个还小，那么只需要把它整个复制到最前面即可
        int point_1 = m-1, point_2 = n-1;
        int len = m+n-1;
        while (point_1>=0 && point_2>=0){
            nums1[len--] = nums1[point_1]<=nums2[point_2]? nums2[point_2--]:nums1[point_1--];
        }
        System.arraycopy(nums2, 0, nums1, 0, point_2+1);
    }

    /**
     * 旋转数组
     */
    public void rotate(int[] nums, int k) {
        if (nums.length==1) return;
        int len = nums.length;
        k = k%len;//千万记住，一定要求个mod，不然的话如果k大于len就很麻烦
        int[] helper = new int[k];
        System.arraycopy(nums, len-k, helper, 0, k);
        for (int i = (len - k - 1); i >= 0; i--) {
            nums[len-1] = nums[i];
            len--;
        }
        System.arraycopy(helper, 0, nums, 0, k);
    }
    //注意负负得正的方法
    public void rotate_batter(int[] nums, int k){
        if (nums.length==1) return;
        k = k%nums.length;
        rotateAll(nums, 0, nums.length-1);
        rotateAll(nums, 0, k-1);
        rotateAll(nums, k, nums.length-1);
    }
    private void rotateAll(int[] arr, int left, int right){
        while (left<right){//等于的时候也可以停止了
            int temp = arr[right];
            arr[right] = arr[left];
            arr[left] = temp;
            left++;
            right--;
        }
    }

    /**
     * 数组中的逆序对：归并的思想
     */
    public int reversePairs(int[] nums) {
        if (nums.length<=1) return 0;
        int[] temp = Arrays.copyOf(nums, nums.length);
        //System.out.println(Arrays.toString(temp));
        return helper(temp, 0, nums.length-1, new int[nums.length]);
    }

    /**
     * 排序是从小到大的排序
     */
    private int helper(int[] nums, int left, int right, int[] temp){
        if (left==right)
            return 0;
        int mid = left+(right-left)/2;
        int left_count = helper(nums, left, mid, temp);//区间：[left:mid]
        int right_count = helper(nums, mid+1, right, temp);//区间：[mid+1:right]
        //一个小优化，如果左边的最后一个值大于右边第一个值，那么就可以不再合并，关键是注意到，此时的逆序数合并后增加的是前面一半的所有
        if (nums[mid] <= nums[mid+1])
            return left_count+right_count;
        int merge_count = merge(nums, left, right, mid+1, temp);//直接传入mid+1作为后面右区间的第一个元素下标
        return left_count+right_count+merge_count;
    }
    private int merge(int[] nums, int left, int right, int mid, int[] temp){
        //左区间是[left:mid-1],右区间是[mid:right]是对应的nums的位置
        //直接采用优化后的，使用一个全局数组
        if (right + 1 - left >= 0)
            System.arraycopy(nums, left, temp, left, right + 1 - left);


        //注意指针指向的是temp
        int temp_left = left, temp_right = mid;
        int count = 0;//计算这次合并的逆序对数量
        for (int i = left; i <= right; i++) {//注意这里指的也是temp，nums需要加上个left
            //注意这个等于符号，写少了就出了问题，少了一次,而且不能是len，这个是个隐秘的bug
            //注意，这两种边界情况本质上只是操作一半的数组，而在上一个问题中已经被计算过逆序对了，因此不用再次计算
            if (temp_left >= mid) {
                nums[i] = temp[temp_right];
                temp_right++;
            }
            else if (temp_right > right) {
                nums[i] = temp[temp_left];
                temp_left++;
            }
            //这里的常规情况才是跨越两个子数组的逆序对计算
            else if (temp[temp_left] <= temp[temp_right]) {
                nums[i] = temp[temp_left];
                temp_left++;
            }
            else {
                nums[i] = temp[temp_right];
                temp_right++;
                count += mid-temp_left;//注意这里加的是右边的指针
            }
        }
        return count;
    }


    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
//        strings.add("aaa");
//        strings.add("BB");
        System.out.println(strings.hashCode());
//        System.out.println(strings.get(0).hashCode());
//        System.out.println(strings.get(1).hashCode());
    }
}
