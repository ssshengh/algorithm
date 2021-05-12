package formulation.SortABout;

import java.util.Arrays;

public class Merge {
    /**
     * 归并排序，思路是递归的思路，一直二分下去，直到分到只有一个的时候，得到顺序
     */
    public int[] mergeSort(int[] arr){
        int[] temp = new int[arr.length];//优化！使用同一个数组操作
        helper(arr, 0, arr.length-1, temp);
        return arr;
    }

    /**
     * 这个函数负责完成二分的任务，在每一个问题中包含两个同样大小的子数组并列的子问题
     * 分别求解两个子问题后，将两个数组进行和并即可
     * @param arr：原本需要处理的数组
     * @param left：此时问题的左节点下标，注意是包含在里面的
     * @param right：右节点下标，也是包含在里面的
     */
    private void helper(int[] arr, int left, int right, int[] temp) {
//        if (left==right)
//            return;
        //OK,优化，在长度小于47的时候都可以使用插入排序
        if (right - left <= INSERTION_SORT_THRESHOLD) {
            insertSort(arr, left, right);
            return;
        }

        int mid = left+(right-left)/2;//取中点的标准写法
        helper(arr, left, mid, temp);
        helper(arr, mid+1, right, temp);
        //优化如果已经有序就不多此一举了
        if (arr[mid]<=arr[mid+1])
            return;
        //merge(arr, left, mid+1, right);
        merge_batter(arr, left, mid+1, right, temp);
    }

    /**
     * 这个函数的目的是合并两个数组，将其按照顺序排列，思路是通过复制出一个数组，然后利用三个指针来完成
     * 两个指针分别指向上面两个子问题中数组的起始节点，然后一个指针指向排序好的数组
     * @param arr：原本的数组
     * @param left：此时两个子问题合起来的数组的左节点
     * @param mid：右边子问题的起始节点
     * @param right：合并起来的子问题的右节点，用于新建数组
     */
    private void merge(int[] arr, int left, int mid, int right){
        //老遇到细节问题，写代码先画图！！！！
        int len = right-left+1;
        int[] temp = new int[len];//注意长度，空间别用太大了

        //把数组中的值复制出来
        //不能用++，修改了left大哥，可以优化为调用这个函数
        System.arraycopy(arr, left, temp, 0, len);

//        int i_left = left, j_right = mid;注意这里是复制数组中的第二个数组的开始，所以不应该是mid和left
        int i_left = 0, j_right = mid-left;
        for (int i = 0; i < len; i++) {//注意这里就直接对原本的数组进行赋值了
            if (i_left>=mid-left){
                //此时是左边数组遍历完毕来到了右边数组第一个元素开始
                arr[i+left] = temp[j_right];//注意实际数组的起始位置是left
                j_right++;
            }else if (j_right>=right-left+1){
                arr[left+i] = temp[i_left];
                i_left++;
            }else if (temp[i_left] <= temp[j_right])
            {
                arr[left+i] = temp[i_left];
                i_left++;
            }else {
                arr[left+i] = temp[j_right];
                j_right++;
            }
        }

    }
    //接下来是优化：
    /**
     * 列表大小等于或小于该大小，将优先于 mergesort 使用插入排序,47是一个计算出来的合理的大小
     * 不是我计算的哈哈哈哈
     */
    private static final int INSERTION_SORT_THRESHOLD = 47;
    private void insertSort(int[] arr, int left, int right){
        int len = right-left+1;
        for (int i = 1; i < len; i++) {
            int temp = arr[left+i];
            int j = i;
            while (j>0 && temp<=arr[left+j-1]){
                arr[j] = arr[j-1];
                j--;
            }
            arr[left+j] = temp;
        }
    }

    private void merge_batter(int[] arr, int left, int mid, int right, int[] temp){
        int len = right-left+1;
        //int[] temp = new int[right-left+1];
        System.arraycopy(arr, left, temp, left, len);//对同一个数组进行操作，减少空间消耗

        int i = left, j = mid;
        for (int k = 0; k < len; k++) {
            if (i>=mid){
                arr[k+left] = temp[j];
                j++;
            }else if (j>right){
                arr[k+left] = temp[i];
                i++;
            }else if (temp[i]<=temp[j]){
                arr[k+left] = temp[i];
                i++;
            }else {
                arr[k+left] = temp[j];
                j++;
            }
        }

    }
    public static void main(String[] args) {
        int[] arr = new int[]{7,5,4,9,1,2,3};
        Merge merge = new Merge();
        //System.out.println(Arrays.toString(merge.mergeSort(arr)));
        merge.insertSort(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }
}
