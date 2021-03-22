package DPlearing.liner;

import java.util.Arrays;

public class OtherOneArray2 {
    /**
     * 最长回文字串
     * */
    //dp法
    public String longestPalindrome1(String s){
        int len = s.length();
        if (len < 2) return s;
        int begin = 0;//记录起始位置
        int maxLen = 1;//最大长度应该初始化为1，因为考虑只有两个的情况，即dp[0][1]，如果不同的话，不会下去执行
        //定义dp[i][j]为s[i]-s[j]之间的数组为回文串
        boolean [][] dp = new boolean[len][len];
        dp[0][1] = true;//这个初始化一下，虽然dp会被默认为true的
        for (int j = 1; j<len; j++){
            // i ,j的顺序,j需要从1开始，因为是把初始状态考虑进去了，调用了j-1
            for (int i = 0; i<j; i++){
                if (s.charAt(i) != s.charAt(j))
                    dp[i][j] = false;
                else
                    //两端相等，如果里面的只有一个或没有的话：j-1 - (i+1) +1 <2，显然是回文串
                    if (j - i < 3)
                        dp[i][j] = true;
                    else
                        //里面还有其他字母，那么关键就看里面的字母就行了
                        dp[i][j] = dp[i + 1][j - 1];

                if (dp[i][j] && j-i+1 > maxLen){
                    begin = i;
                    maxLen = j-i+1;
                }
            }
        }
        return s.substring(begin, begin+maxLen);

    }
    //中心扩散法：
    private String centerSpread(String s, int left, int right){
        //这一步是核心的从中心朝两边找是否是回文串，找到最长的返回
        int len = s.length();
        int start = left, end = right;
        while (start>=0 && end <len){
            if (s.charAt(start) == s.charAt(end))
            {
                start --;
                end ++;
            }else
                break;

        }
        //注意一个点，在break出来时，s.charAt(start) != s.charAt(end)，所以最后输出的字符串不包含这两个位置
        return s.substring(start+1, end);//这个方法默认取不到end
    }
    public String longestPalindrome(String s){
        int len = s.length();
        if (len < 2)    return s;
        int maxLen = 1;
        String res = s.substring(0,1);
//        if (len == 2)
//            return s.charAt(0)==s.charAt(1)? s:res;
        for (int i = 0; i<len-1; i++)//小点:枚举到len-2就够了
        {
            //从中心扩张开来的回文串可能为奇数，可能为偶数，为了方便，两种一起考虑
            //如果为奇数的时候，中间的就是i这个位置的点
            //但如果为偶数，只用i进去的话会找偏
            String oddStr = centerSpread(s, i, i);
            String evenStr = centerSpread(s, i, i+1);
            String maxLenStr = oddStr.length()>evenStr.length()? oddStr:evenStr;
            if (maxLenStr.length() > maxLen){
                maxLen = maxLenStr.length();
                res = maxLenStr;
            }

        }
        return res;
    }

    /**
     * 两个字符串的删除操作
     * */
    public int minDistance_mine(String s1, String s2){
        int [][] dp = new int[s1.length()][s2.length()];//s1[0:i]与s2[0:j]之间的最长公共子序列
        if (s1.charAt(0) == s2.charAt(0))
            dp[0][0] = 1;
        if (s1.length()>=2 && s2.length()>=2 &&
                (s1.charAt(0) == s2.charAt(1) || s1.charAt(1) == s2.charAt(0)))
            dp[0][1] = dp[1][0] =1;
        if (s1.length() == 1 && s2.length() ==2)
            if (s1.charAt(0) == s2.charAt(0) || s1.charAt(0) == s2.charAt(1))
                dp[0][0] = dp[0][1] =1;

        if (s2.length() == 1 && s1.length() ==2)
            if (s2.charAt(0) == s1.charAt(0) || s2.charAt(0) == s1.charAt(1))
                dp[1][0] = dp[0][0] =1;

        for (int i = 1; i<s1.length(); i++)
            for (int j = 1; j<s2.length(); j++){
                if (s1.charAt(i) == s2.charAt(j))
                    dp[i][j] = dp[i-1][j-1]+1;//这个显然是能够取到的上一状态
                else
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);//这两个也是被遍历过的值
            }
        return s1.length()+s2.length() - 2*dp[s1.length()-1][s2.length()-1];
    }//初始化要全部兼顾的话，变麻烦了,而且有些情况无法处理
    //思考改那个的逻辑，其实可以简单点，修改dp定义：
    public int minDistance_dp1(String s1, String s2){
        int[][] dp = new int[s1.length()+1][s2.length()+1];//为s1前i个与s2前j个的最长公共子序列
        for (int i = 0; i<=s1.length(); i++)//注意这里也要修改为=号
            for (int j = 0; j<=s2.length(); j++){
                if (i == 0|| j==0)
                    continue;//这种就完全规避了我那个烦人的初始化问题，以及初始化带来的新的问题
                if (s1.charAt(i-1) == s2.charAt(j-1))
                    dp[i][j] = dp[i-1][j-1]+1;//这个显然是能够取到的上一状态
                else
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);//这两个也是被遍历过的值
            }
        return s1.length()+s2.length() - 2*dp[s1.length()][s2.length()];
    }
    //用递归来写这个逻辑：
    public int minDistance_exceed(String s1, String s2){
        return s1.length()+s2.length() - 2*lcs(s1, s2, s1.length(), s2.length());
    }
    private int lcs (String s1, String s2, int lenS1, int lenS2){
        //函数状态定义：前lenS1与前lenS2个的最长公共子序列
        if (lenS1 == 0 || lenS2 == 0)
            return 0;//结束状态
        if (s1.charAt(lenS1-1) == s2.charAt(lenS2-1))
            return lcs(s1, s2, lenS1-1, lenS2-1) +1;
        else
            return Math.max(lcs(s1, s2, lenS1-1, lenS2),
                    lcs(s1, s2, lenS1, lenS2-1));
    }//超出时间限制了
    //优化一下：
    public int minDistance_recur(String s1, String s2) {
        int[][] memo = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 0; i<memo.length; i++)
            for (int j = 0; j<memo[1].length; j++)
                memo[i][j] = -1;//注意这个初始化，才能够正确剪枝
        return s1.length() + s2.length() - 2 * lcs(s1, s2, s1.length(), s2.length(), memo);
    }
    public int lcs(String s1, String s2, int m, int n, int[][] memo) {
        if (m == 0 || n == 0)
            return 0;
        if (memo[m][n] > 0)
            return memo[m][n];
        if (s1.charAt(m - 1) == s2.charAt(n - 1))
            memo[m][n] = 1 + lcs(s1, s2, m - 1, n - 1, memo);
        else
            memo[m][n] = Math.max(lcs(s1, s2, m, n - 1, memo), lcs(s1, s2, m - 1, n, memo));
        return memo[m][n];
    }
    //另一种dp及优化
    public int minDistance_(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];//前i，j个的最少删除次数
        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0 || j == 0)
                    dp[i][j] = i + j;//注意这里的初始化的处理，前0个和前0个显然为0，前0个和前1个显然是1
                else if (s1.charAt(i - 1) == s2.charAt(j - 1))
                    //如果相同的时候，删除次数不增
                    dp[i][j] = dp[i - 1][j - 1];
                else
                    //不同的时候，考虑删除某个数，删除的数应该是最少的一个
                    dp[i][j] = 1 + Math.min(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return dp[s1.length()][s2.length()];
    }
    //优化为一个一维数组：
    public int minDistance(String s1, String s2){
        int[] dp = new int[s2.length()+1];
        for (int i = 0; i<=s1.length(); i++){
            //这么来思考，相当于不要上一个的dp[i]了，那么不要dp[i]，就意味着，在这里，每一个i的状态被临时存储
            //dp[i-1][j]的情况,只是上一次状态的信息，也就是说，只用到过上一个状态，即i-1与j-1
            //那么如何才能把上一个状态的完全存储呢？
            //首先注意到，不论i为多少，都是每一步i填充一个完整的j，那么我这一步把完整的j记录下来，下一次i就能用
            //所以，这一题上面的解法，看似是一个二维数组，其实是s2.length()+1个一维数组！！！！！！！！！！！
            int[] temp = new int[s2.length()+1];
            for (int j = 0; j<=s2.length(); j++){
                if (i==0 || j==0)
                    temp[j] = i+j;
                else if (s1.charAt(i - 1) == s2.charAt(j - 1))
                    temp[j] = dp[j-1];
                else
                    temp[j] = 1 + Math.min(dp[j], temp[j - 1]);
            }
            dp = temp;
        }
        return dp[s2.length()];
    }
    /**
     * 位1的个数
     * */
    public int hammingWeight_basic(int n){
        //求汉明重，一个思路是利用与：1 = 00000000000001,与任何数相与，得到该位置为1
        int mask = 1, res = 0;
        for (int i = 0; i<32; i++)//题目固定是32位
        {
            if ((mask & n) != 0)
                res++;
            mask <<= 1;
        }
        return res;
    }
    //优化，很nb的思路，n与n-1一个一个的与过去，可以让最后一个1变成0，一直到第一个1，到最后变成0
    public int hammingWeight(int n){
        int res = 0;
        while (n!=0){
            res++;
            n = n&(n-1);
        }
        return res;
    }
    /**
     *比特位计数
     * */
    public int[] countBits_popCount(int num){
        //对每个数求汉明重
        int [] res = new int[num+1];
        for (int i = 0; i<=num; i++)
            res[i] = hammingWeight(i);
        return res;
    }
    //从上面的思路得到dp，引出最后设置位：
    public int[] countBits(int num){
        int[] dp = new int[num+1];
        for (int i = 1; i<=num; i++)
            dp[i] = dp[i&(i-1)] + 1;
        return dp;
    }

    //利用最高有效位的特点来做：
    public int[] countBits_high(int num){
        int[] dp = new int[num+1];
        dp[0] = 0;
        //int i = 0;
        int b = 1;//标志符号，记录每一步前移的数量，为2的倍数，每转移一次，1的个数加一
        while (b<=num){
            //b从1开始的话，dp[0]初始化了，dp[1]可以直接求出
//            while (i<b && i+b<=num) {
//                dp[i + b] = dp[i] + 1;
//                i++;
//            }
            for (int i = 0; i<b && i+b<=num; i++)
                dp[i + b] = dp[i] + 1;
            //i = 0;//每一次都要重新从0开始才能找全
            b = b<<1;
        }
        return dp;
    }
    //最低有效位
    public int[] countBits_low(int num) {
        int[] ans = new int[num + 1];
        for (int i = 1; i<=num; i++)
            ans[i] = ans[i>>2] + i&1;//i>>2等价于i/2；i&1等价于(i mod 2)
        return ans;
    }




    public static void main(String[] args) {
        String ss= "sisidabendan";
        System.out.println(ss.substring(1, 2));
        int a = 200*300*400*500;
        System.out.format("%d ", a);
    }

}
