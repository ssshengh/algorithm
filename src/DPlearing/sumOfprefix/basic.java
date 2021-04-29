package DPlearing.sumOfprefix;

import java.util.Arrays;

public class basic {

}

/**
 * 前缀和的实现——区间和
 */
class NumArray{
    private int[] sum;
    public NumArray(int [] nums){
        this.sum = new int[nums.length];
        sum[0] = nums[0];
        //前缀和数组实现
        for (int i=1; i<nums.length; i++){
            sum[i] = sum[i-1] + nums[i];
        }
    }
    public int sumRange(int left, int right) {
        return left==0?sum[right] : sum[right]-sum[left-1];
    }

    public static void main(String[] args) {
        NumArray n = new NumArray(new int[]{1, 2, 3, 4, 5, 6});
        System.out.println(Arrays.toString(n.sum));
        System.out.println(n.sumRange(1,3));
    }
}

/**
 * 矩阵区间和
 */
class NumMatrix {
    private int[][] sum;//定义为[0][0]到[i][j]结尾的矩阵的和
    //修改为前i个和前j个
    public NumMatrix(int[][] matrix) {
        if (matrix.length==0 || matrix[0].length==0) return;
        int n = matrix.length, m = matrix[0].length;
        sum = new int[n+1][m+1];
        //sum[0][0] = matrix[0][0];
        for (int i =0; i<n; i++)
            for (int j = 0; j<m; j++)
            {
                //sum[i][j] = sum[i-1][j] + sum[i][j-1] - sum[i-1][j-1] + matrix[i][j];二维经常的问题来了，因此数组扩大一维
                sum[i+1][j+1] = sum[i][j+1]+sum[i+1][j]-sum[i][j]+matrix[i][j];
            }

    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        return sum[row2+1][col2+1]+sum[row1][col1]-sum[row1][col2+1]-sum[row2+1][col1];
    }

    public static void main(String[] args) {

    }
}