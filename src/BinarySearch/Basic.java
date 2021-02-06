package BinarySearch;

public class Basic {
    /**
     * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
     * 你可以假设数组中无重复元素。
     * 标准的二分查找的实现
     * */
    public int search(int[] nums, int target){
        //三分支二分查找，思路简单，关键是解决确定该数应该在哪个区间的问题
        int len = nums.length;
        int left = 0;
        int right = len-1;
        while (left <= right){
            int mid = (left + right)>>>1;//防止整数溢出（为正时）
            if (nums[mid] == target)
                return nums[mid];
            else if (nums[mid] < target)
            {
                left = mid + 1;
            }else
                right = mid - 1;//注意这种方法是不包含mid的，因为判断过了，是要在里面找存在
        }
        return -1;
    }

    public int search_1(int[] nums, int target){
        //这种方法更关键，两个分支，思路是排除不存在的，既然上面找==target，这里就找!=target，即大于小于的
        int len = nums.length;
        int left = 0;
        int right = len-1;
        while (left < right){
            //第一个注意点，这里是小于号，最后是得到一个两个数的区间，然后左元素就可能是
            int mid = left + (right-left)/2;
            //第二个注意点，mid在右边就需要上取整，这种方法区间是包含mid的
            if (nums[mid] < target)//这说明不在左边
                left = mid + 1;
            else
                right = mid;
        }
        if (nums[left] == target)
            return left;
        return -1;
    }
}
