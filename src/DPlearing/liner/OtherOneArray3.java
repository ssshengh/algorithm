package DPlearing.liner;

public class OtherOneArray3 {
    /**
     * 使序列递增的最小交换次数
     * */
    public int minSwap_bad(int[] A, int[] B){
        if (A.length <= 1 || A.length != B.length)
            return 0;
        //A的长度等于B的长度
        int[] dp = new int[A.length];

        for (int i = 0; i < A.length; i++){
            boolean ai1 = i-1>=0? A[i]>=A[i-1] : A[i] >= 0;
            boolean ai2 = i+1<=A.length-1? A[i]<=A[i+1] : A[i] < Integer.MAX_VALUE;
            boolean bi1 = i-1>=0? B[i]>=B[i-1] : B[i] >= 0;
            boolean bi2 = i+1<=A.length-1? B[i]<=B[i+1] : B[i] < Integer.MAX_VALUE;
            if (!ai1 || !ai2 || !bi1 || !bi2)
            {
                int temp = A[i];
                A[i] = B[i];
                B[i] = temp;
                dp[i] = i-1>=0? dp[i-1] +1 : 1;
            }else
                dp[i] = i-1>=0? dp[i-1] : 0;
        }
        return dp[A.length-1];
    }//这一版代码能解决不是严格递增的情况[3,3,8,9,10] [1,7,4,6,8]，但是不能解决一个不是严格递增一个是的情况如何处理[0,4,4,5,9][0,1,6,8,10]
    //感觉应该改一下dp定义

    //这一题值得关注一下，他的思路是反过来的，往常我们找dp的时候，是正着去考虑，比前面小或者大，那么加一减一
    //这一题是反过来，我只有两种情况，那么我都尝试去加一看看，取最小
    public int minSwap(int[] A, int[] B){
        //我们用 n1 表示数组 A 和 B 满足前 i - 1 个元素分别严格递增，并且 A[i - 1] 和 B[i - 1] 未被交换的最小交换次数，
        // 用 s1 表示 A[i - 1] 和 B[i - 1] 被交换的最小交换次数。
        int s1 = 1, n1 = 0;//注意初始化，s1交换过，必然是1
        for (int i = 1; i<A.length; i++){
            int n2 = Integer.MAX_VALUE, s2 = Integer.MAX_VALUE;
            if (A[i-1] < A[i] && B[i-1] < B[i]){
                n2 = Math.min(n2, n1);//前一个没交换，后一对肯定不交换
                s2 = Math.min(s2, s1 + 1);//前一个交换，后一个就必须交换
            }
            if (A[i-1] < B[i] && B[i-1] < A[i]){
                //必须交换一对才行
                n2 = Math.min(n2, s1);//前一对交换了，后一对不交换
                s2 = Math.min(n1+1, s2);//前一对交换了，所以n1+1，后一对没交换，看谁大
            }
            s1 = s2;
            n1 = n2;
        }
        return Math.min(s1, n1);//只有一个数的时候，返回0，没问题
        //这一题显然是从两个dp数组优化过来的，给的新的思路就是，我们可以代替dp[i]和dp[i-1]，用一种明确的定义
    }

}
