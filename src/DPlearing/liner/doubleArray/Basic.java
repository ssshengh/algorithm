package DPlearing.liner.doubleArray;

public class Basic {
    /**
     * 最长公共子序列
     * */
    public int longestCommonSubsequence_ok(String text1, String text2){
        int len1 = text1.length();
        int len2 = text2.length();
        if (len1==0 || len2==0)
            return 0;
        int[][] dp = new int[len1+1][len2+1];//定义dp[i][j]表示text1前i个和text2前j个
        //这样定义，在二维状态数组中便于初始化

        for (int i = 0; i<len1; i++)
            for (int j = 0; j<len2; j++){
                if (text1.charAt(i) == text2.charAt(j))
                    dp[i+1][j+1] = dp[i][j] +1;
                else
                    dp[i+1][j+1] = Math.max(dp[i][j+1], dp[i+1][j]);
            }
        return dp[len1][len2];
    }
    //优化下空间
    public int longestCommonSubsequence(String text1, String text2){
        int len1 = text1.length();
        int len2 = text2.length();
        if (len1==0 || len2==0)
            return 0;
        int[] dp = new int[len2+1];//第一维为i,上一次的状态

        for (int i = 0; i<len1; i++)
        {
            int [] temp = dp.clone();//这一步比较关键，需要拷贝下上一次的状态，不过随着更新，好像也没啥用
            //new一个也一样的
            for (int j = 0; j<len2; j++)
            {
                //第一维为i+1
                if (text1.charAt(i) == text2.charAt(j))
                    temp[j+1] = dp[j]+1;
                else
                    temp[j+1] = Math.max(temp[j], dp[j+1]);
            }
            dp = temp;
        }
        return dp[len2];
    }

    /**
     * 712. 两个字符串的最小ASCII删除和
     */
    public int minimumDeleteSum(String s1, String s2){
        //从上一题引申出来思路，最短的ASCII删除和===最大的最长子序列ASCII和
        int len1 = s1.length();
        int len2 = s2.length();
        if (len1==0 || len2==0)
            return 0;
        int sum = 0;//两个数组的总ASCII和
        for (int i = 0; i<len1; i++) sum+=s1.charAt(i);
        for (int i = 0; i<len2; i++) sum+=s2.charAt(i);

        //接下来和上面的思路一样，求解最长子序列的，最大ASCII和
        int[][] dp = new int[len1+1][len2+1];//定义dp[i][j]表示s1前i个和s2前j个的最长子序列的最大和
        //这样定义，在二维状态数组中便于初始化
        for (int i = 0; i<len1; i++)
            for (int j = 0; j<len2; j++){
                if (s1.charAt(i) == s2.charAt(j))
                    dp[i+1][j+1] = dp[i][j] + (int) s1.charAt(i);
                else
                    dp[i+1][j+1] = Math.max(dp[i][j+1], dp[i+1][j]);
            }
        return sum - dp[len1][len2]*2;
    }

    /**
     * 最长重复子数组，给两个整数数组 A 和 B ，返回两个数组中公共的、长度最长的子数组的长度。
     * 双指针暴力法(太麻烦，感觉有问题),用dp做
     * @param A 数组A
     * @param B 数组B
     * @return 返回最大的子数组长度
     */
    public int findLength_dp(int[] A, int[] B){
        int len1 = A.length, len2 = B.length;
        int sum = 0;//总结果
        if (len1==0 || len2==0)
            return 0;

        int[][] dp = new int[len1+1][len2+1];//dp[i][j]意味着以第i个和第j个结尾的子数组的最大长度
        for (int i = 0; i<len1; i++)
        {
            for (int j = 0; j<len2; j++){
                if (B[j] == A[i]) {
                    dp[i+1][j+1] = dp[i][j] >= 1 ?
                            dp[i][j] + 1 : 1;
                    sum = Math.max(dp[i+1][j+1], sum);
                }

            }
        }
        return sum;
    }
    //滑动窗口算法
    public int findLength(int[] A, int[] B){
        int n = A.length, m = B.length;
        int sum = 0;
        //固定A，B从左向右移动匹配窗口，解决B比A短的时候
        for (int i = 0; i<n; i++){
            int len = Math.min(m, n-i);//比较B的长度和A[i:]的数组的长度，i不断增加，A余下的窗口越来越小，选取最小的作为窗口
            int maxLen = maxLength(A, B, i, 0, len);//这种情况，B就是从第一个开始，而A是从窗口的第1个开始，即i
            sum = Math.max(sum, maxLen);
        }
        //固定B，A从左往右移动，为了确保，A比B短的时候
        for (int i = 0; i<m; i++){
            int len = Math.min(n, m-i);
            int maxLen = maxLength(A, B, 0, i, len);
            sum = Math.max(sum, maxLen);
        }
        return sum;
    }

    /**
     * 这个完成窗口内部的最长匹配长度
     * @param A 第一个数组
     * @param B 第二个数组
     * @param addA 窗口对其的A的第一个位置
     * @param addB 窗口对其的B的第一个位置
     * @param len 窗口长度
     * @return 窗口最长匹配长度
     */
    public int maxLength(int[] A, int[] B, int addA, int addB, int len){
        int sum = 0, temp = 0;
        for (int i = 0; i<len; i++){
            if (A[addA+i] == B[addB+i])//从第一个开始比对，相同就加一，如果不同的话，就回归0就可以了，不用考虑前一个是否一样
                temp++;
            else
                temp = 0;
            sum = Math.max(temp, sum);
        }
        return sum;
    }
}
