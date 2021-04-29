package DPlearing.liner;

import java.util.Arrays;
import java.util.regex.MatchResult;

public class dimensionArray {
    public double largestSumOfAverages_mine(int[] A, int K){
        int len = A.length;
        double[] P = new double[A.length+1];//注意多1
        //首先用前缀和便于求平均值
        for (int i = 0; i<A.length; i++)
            P[i+1] = P[i] + A[i];//Pi等价于，sum of A[0:i-1]

        //这个是我的未考虑的特殊情况：不分或者只分一个的，下面考虑的分一个的，没有考虑不分的
        if (K<=1)
            return P[A.length]/A.length;

        //涉及到除法，必须double
        double[][] dp = new double[A.length][K];//定义dp：A[0:i]分为k份的最大和
        for (int i = 1; i<len; i++)
            dp[i][0] = P[i+1]/(i+1);
        dp[0][1] = dp[0][0] =  A[0];
        for (int k = 1; k<K; k++)//k从1开始，分0个没啥意义
            for (int i = 1; i<len; i++)
                for (int j = 0; j<i; j++){
                    dp[i][k] = Math.max(dp[i][k], dp[j][k-1] + (P[i+1]-P[j+1])/(i-j));
                }
        return dp[A.length-1][K-1];
    }
    //考虑优化：把k优化掉，并且从后往前的方法：
    public double largestSumOfAverages(int[] A, int K){
        int len = A.length;
        double[] P = new double[A.length+1];
        for (int i = 0; i<len; i++)
            P[i+1] = P[i] + A[i];

        double[] dp = new double[len];
        for (int i = 0; i<len; i++)
            dp[i] = (P[len]-P[i])/(len-i);//A[i:len-1]的均值

        for (int k = 0; k<K-1; k++)
            // 先把i固定，j变化作为分隔点
            // 分隔一次i~(N - 1)变为2个连续数组
            // 分隔两次i~N-1变为3个连续数组
            // 分隔K- 1次i~(N -1)变为K个连续数组
            // 这里有一点很好证明:一个数组分割后，他的xxx值一定比不分割要大，这是这里dp能求到正确值的前提条件
            for (int i = 0; i<len; i++)
                for (int j = i+1; j<len; j++)//注意，这里是从后往前的反向规划，因此用到的dp[j]这种未求状态是反向求平均
                    dp[i] = Math.max(dp[i], dp[j] + (P[j]-P[i]) / (j-i) );
        return dp[0];
    }

    /**
     * 粉刷房子1
     * */
    public int minCost_mine(int[][] costs){
        if (costs.length == 0)
            return 0;
        int HouseSum = costs.length;
        int ColorSum = 3;
        int[][] dp = new int[HouseSum][ColorSum];//定义为第i栋房子涂第j种颜色的最低开销

        //初始化
        System.arraycopy(costs[0], 0, dp[0], 0, ColorSum);

        for (int i = 1; i < HouseSum; i++)
            for (int j = 0; j<ColorSum; j++){
                if (j == 0)
                    dp[i][j] = Math.min((dp[i-1][1] + costs[i][0]), (dp[i-1][2] + costs[i-1][0]));
                else if (j == 1)
                    dp[i][j] = Math.min((dp[i-1][0] + costs[i][1]), (dp[i-1][2] + costs[i-1][1]));
                else
                    dp[i][j] = Math.min((dp[i-1][0] + costs[i][2]), (dp[i-1][1] + costs[i-1][2]));
            }

        int res = Integer.MAX_VALUE;
        for (int i = 0; i<3; i++)
            res = Math.min(res, dp[HouseSum-1][i]);
        return res;
    }
    //优化，因为每个中间值都需要被用到，所以存储法不太合适，这里提供了另一种思路：滚动数组
    //注意到我们的代码中，只用到了i-1的状态的所有值，那么我就用cost来存储，不重新开辟空间
    public int minCost(int[][] costs){
        if (costs.length == 0)
            return 0;
        //注意到，始终只用了当前状态和前一个状态，那么用一个3维数组就足够了
        int[] first = new int[3];
        System.arraycopy(costs[0], 0, first, 0, 3);
        for (int i = 1; i< costs.length; i++)
        {
            int[] second = first.clone();
            second[0] = Math.min(first[1]+costs[i][0], first[2]+costs[i][0]);
            second[1] = Math.min(first[0]+costs[i][1], first[2]+costs[i][1]);
            second[2] = Math.min(first[1]+costs[i][2], first[0]+costs[i][2]);
            first = second;
        }
        return Math.min(Math.min(first[0], first[1]), first[2]);
    }

    /**
     * 粉刷房子 II
     * */
    public int minCostII_mine(int[][] costs){
        //上面优化后算法延展到k
        if (costs.length == 0)
            return 0;
        int K = costs[0].length;
        int[] first = new int[costs[0].length];//k种颜色
        System.arraycopy(costs[0], 0, first, 0, K);
        for (int i = 1; i<costs.length; i++){//选择房子,遇到的问题[2]
            int [] second = first.clone();
            int item = Integer.MAX_VALUE;//用于中间储存状态，找最小值
//            for (int j = 0; j<K; j++){//上一栋房子选择的颜色
//                for (int k = 0; k<K; k++)//这一栋房子选择的颜色
//                {
//                    if (k==j)
//                        continue;
//                    item = Math.min(first[j]+costs[i][k], item);
//                    second[k] = item;
//                }
//
//            }逻辑有问题，这样的话，不断优化的是second整个数组，而我是要second一个元素一个元素的求最优，遇到的问题[1]
            for (int j = 0; j<K; j++){//这一栋房子选择的颜色
                for (int k = 0; k<K; k++){//上一栋房子选择的颜色
                    if (j == k)
                        continue;
                    item = Math.min(first[k]+costs[i][j], item);
                }
                second[j] = item;
                item = Integer.MAX_VALUE;//遇到的问题[3]，应该重制一下，不然会被上一次最小的item影响
            }

            first = second;
        }
        int res = Integer.MAX_VALUE;
        for (int item : first)
            res = Math.min(res, item);
        return res;
    }//时间消耗比较大：nk^2
    //考虑优化
    public int minCostII(int[][] costs) {
        // n个房子
        int n = costs.length;
        if(n==0)return 0;
        // k种颜色的油漆
        int k = costs[0].length;
        if(k==0)return 0;
        // 至少1种颜色，1间房子
        if(k==1) {
            if(n==1) {
                return costs[0][0];
            } else {
                // 不可能完成任务
                return 0;
            }
        }

        // 至此，至少有2种颜色
        // 相邻两个房子的颜色不同，求最小花费
        // f[i][j]表示将房子[0..i]刷完，并且i号房子是颜色j的最小花费
        int[][] f = new int[n][k];

        int preColorMin = 0;
        int preColorMin2 = 0;
        for (int i = 0; i < n; i++) {
            int costMin = Integer.MAX_VALUE;//记录当前轮，粉刷完[0..i]的最小花费
            int colorMin = k-1;//记录最小花费下，i号房的颜色
            int costMin2 = Integer.MAX_VALUE;//记录当前轮，粉刷完[0..i-1]的次最小花费
            int colorMin2 = k-1;//记录次最小花费下，i号房的颜色

            for (int j = 0; j < k; j++) {
                if(i==0) {
                    f[i][j] = costs[i][j];
                } else {
                    // 当前颜色j不是前一轮的最小颜色，就让前一个房间取最小颜色
                    // 否则，让前一个房间粉刷为次最小颜色
                    f[i][j] = costs[i][j] + (j!=preColorMin?f[i-1][preColorMin]:f[i-1][preColorMin2]);
                }

                if(f[i][j] < costMin) {
                    // 最小变次最小
                    colorMin2 = colorMin;
                    costMin2 = costMin;
                    // 更新最小
                    colorMin = j;
                    costMin = f[i][j];
                } else if(f[i][j] < costMin2) {
                    // 更新次最小
                    colorMin2 = j;
                    costMin2 = f[i][j];
                }
            }
            preColorMin = colorMin;
            preColorMin2 = colorMin2;
        }
        return f[n-1][preColorMin];
    }

    /**
     * 买卖股票最佳时机
     * */
    public int maxProfit_bad(int[] prices){
        if (prices.length <= 1)
            return 0;
        int len = prices.length;
        int [] sum = new int[prices.length];//sum[i]为第i天卖出的最高收益
        int item = 0;
        int res = 0;//收入
        for (int i = 0; i<len; i++)//卖出天为i
        {
            for (int j = 0; j < i; j++) {//第j天买入
                item = Math.max(prices[i] - prices[j], item);
            }
            sum[i] = item;
            res = Math.max(res, sum[i]);
            item = 0;
        }
        return res;

    }//超出时间限制，那么我换个思路
    public int maxProfit_better(int[] prices){
        if (prices.length <= 1)
            return 0;
        int len = prices.length;
        int res = 0;//收入
        for (int i = 0; i<len-1; i++)//买入天为i
        {
            for (int j = i+1; j < len; j++) //第j天卖出
                res = Math.max(prices[j]-prices[i], res);
        }
        return res;
    }//还是不行，暴力法时间太久了
    //借鉴官方方法进行优化，动态规划的一个比较难想到的状态转移
    public int maxProfit_ok(int[] prices){
        int len = prices.length;
        if (len <= 1)
            return 0;
        int[] dp = new int[len];//dp[i]为第i天卖出的最高收入
        int res = 0;//最后结果，有可能不卖不买，所以最小为0
        for (int i =1; i<len; i++)//从第一天开始考虑卖
        {
            int subtract = prices[i] - prices[i-1];//利润差，关键的思考在于，不用在乎哪天买入
            //不管前面哪天买入，那么第j天的最高收入都是这么多，因为如果j+1的价格高于j天，肯定等一天卖，这样的话，卖出的价格就是
            //j之前某天买入某天卖出的最大值改过来，变成那天买入，j+1天卖出
            //那么就有一种情况，第j天价格特别低，低于暗暗记录的买入那天，j+1天特别特别高，这样的话，就不行
            //dp[i] = Math.max(dp[i-1]+subtract, dp[i-1]);所以这种不行，那么改为0的话，收入低于0的时候，换买的天
            //这下就和最大子数组和联系起来了，一直加，加到nums[i]的时候，dp[i-1]+nums[i]<nums[i]小，意味着nums[i]拉不起来了
            //就算后面有更大的也拉不起来，那么就另开新路
            dp[i] = Math.max(dp[i-1]+subtract, 0);//这是和最大子数组不太一样的地方，在第i个值太低了，都拉不上来，那么最好的不就是这个最低点重新开买嘛
            res = Math.max(dp[i], res);
        }
        return res;
    }
    //例行优化空间
    public int maxProfitI(int[] prices){
        int len = prices.length;
        if (len <= 1)
            return 0;
        int dp = 0;
        int res = 0;
        for (int i = 1; i<len; i++){
            int subtract = prices[i] - prices[i-1];
            dp = Math.max(dp+subtract, 0);
            res = Math.max(dp, res);
        }
        return res;
    }


    /**
     * 买卖股票的最佳时机 II
     * */
    private int res = 0;
    public int maxProfitII(int[] prices){
        if (prices.length <=1)
            return 0;
        int len = prices.length;
        dfs(prices, 0, len, 0, res);
        return this.res;
    }
    /**
     * 递归三要素一：这个函数解决的问题——计算第index天的收益profit
     * @param prices 股价数组
     * @param index  当前是第几天，从 0 开始
     * @param status 0 表示不持有股票，1表示持有股票，
     * @param profit 当前收益
     */
    private void dfs(int[] prices, int index, int len, int status, int profit){
        if (index == len)//终止条件，从0增加到最后一天[2]
        {
            this.res = Math.max(this.res, profit);//在最后一天的时候
            return;
        }
        dfs(prices, index+1, len, status, profit);//不卖或者不买过一天

        if (status == 0)//另一种情况就是没有股票，选择买，另一个分支
            dfs(prices, index+1, len, 1, profit-prices[index]);
        else//这种情况持有，可以选择卖出
            dfs(prices, index+1, len, 0, profit+prices[index]);
    }
    //动态规划法：关键是记录状态
    //可以看见，其实思路和递归是一样的，存储每一天持有或者没有持有的情况
    public int maxProfit_dp1(int[] prices){
        int len = prices.length;
        if (len<=1)
            return 0;

        int [][] dp = new int[len][2];//dp[i][j]是指在第i天，持股状态为j时，手里的最大现金数，现金！！
        dp[0][1] = -prices[0];
        for(int i = 1; i<len; i++){
            //很难顺着去考虑这个过程，但动态规划不应该是从头来考虑的
            //而是去思考其中的状态以及转移如何满足的条件：不管如何我这次现金就应该前述不管怎么买怎么卖都是最大的
            //因此，分类考虑就行
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + prices[i]);//上一次没持股或者持股卖了
            dp[i][1] = Math.max(dp[i-1][1], dp[i-1][0] - prices[i]);//上一次持股或者买

//            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
//            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
            //区分第一次dp的思路，第一次是只能交易一次，那么对应到状态转移上，在买的时候：就是没有上一状态的，上一状态必须是刚开始
            //而可以重复买的话，是"再"买的时候，上一次上一次必须没持股

        }
        return dp[len-1][0];

    }
    //使用滚动数组优化：注意到只用到了dp[i-1]和dp[i]，因此用first和now定义这两个值
    //但是dp[i-1]有0和1的情况，dp[i]也有，因此用firstCash(0,没有)和firstHold(1，持有)
    public int maxProfit_dp2(int[] prices){
        int len = prices.length;
        if (len<=1)
            return 0;

        //初始化，dp[i-1]的两个状态
        int firstCash = 0;
        int firstHold = -prices[0];

        //dp[i]的情况
        int nowCash = 0;
        int nowHold = 0;
        for (int i = 1; i<len; i++){
            nowCash = Math.max(firstCash, firstHold+prices[i]);
            nowHold = Math.max(firstHold, firstCash-prices[i]);
            firstCash = nowCash;
            firstHold = nowHold;
        }
        return firstCash;
    }
    public int maxProfit_go(int[] prices){
        int len = prices.length;
        if (len<=1)
            return 0;

        //贪心的核心，不用如同dp一样考虑前面状态，也不用考虑会不会影响后面状态
        //只需要考虑现在
        //因此首先得有贪心的可能：相加不管是连一起还是分开都是每一个单元相加起来最优
        int res = 0;
        for (int i = 1; i<len; i++){
            int diff= prices[i] - prices[i-1];
            if (diff>0)
                res+=diff;
        }
        return res;
    }
    /**
     * III
     * */
    public int maxProfit(int[] prices){
        int len = prices.length;
        if (len<=1)
            return 0;

        int [][][]dp = new int[len][3][2];//dp[i][j][k]第i天最大现金(利润)，卖出过j次，持股状态k
        dp[0][0][0] = 0;
        dp[0][0][1] = -prices[0];

        dp[0][1][0] = -1000;//如果是最小值，溢出了！！！！
        dp[0][1][1] = Integer.MIN_VALUE;

        dp[0][2][0] = Integer.MIN_VALUE;
        dp[0][2][1] = Integer.MIN_VALUE;//不定义这几个，默认为0的话，求解出来有问题
        int res = 0;
        for(int i = 1; i<len; i++)
        {
            //未卖出过，j=0,不持股，持股
            dp[i][0][0] = 0;
            dp[i][0][1] = Math.max(dp[i-1][0][1], dp[i-1][0][0] - prices[i]);//上一次未卖出过未持股的状态买入，或者保持未卖出持股
            //卖出过一次
            dp[i][1][0] = Math.max(dp[i-1][1][0], dp[i-1][0][1] + prices[i]);//保持卖出过一次未持股，或者上一次一次没卖出过持股状态，卖出了
            dp[i][1][1] = Math.max(dp[i-1][1][1], dp[i-1][1][0] - prices[i]);//保持卖出过一次持股，或者上一次买的，即上一次卖出过一次，买入
            //卖出过两次
            dp[i][2][0] = Math.max(dp[i-1][2][0], dp[i-1][1][1] + prices[i]);//保持卖出过两次未持有，或者上一次卖出过一次，持有，又卖出了
            dp[i][2][1] = Integer.MIN_VALUE;//不存在卖出过两次，又买入持股的

        }
        return Math.max(dp[len-1][1][0], Math.max(res, dp[len-1][2][0]));
    }

    /**
     * 编辑距离：关键是把操作简化，并最终联系起来其转换方式
     * */
    public int minDistance_ok(String word1, String word2){
        int len1 = word1.length(), len2 = word2.length();
        if (len1==0 && len2==0)
            return 0;
        //定义状态为前i个前j个的编辑距离，即修改为一样的需要多少操作
        int[][] dp = new int[len1+1][len2+1];
        //初始化，很重要，但是后面考虑可不可以融进另一个循环中
        for (int i = 0; i<len1+1; i++)
            dp[i][0] = i;
        for (int i = 0; i<len2+1; i++)
            dp[0][i] = i;

        //注意加一减一，这种定义下老是忘记
        for (int i = 1; i<len1+1; i++){
            for (int j = 1; j<len2+1; j++){
                //这里看笔记，思路太nb了
                if (word1.charAt(i-1) == word2.charAt(j-1))
                    dp[i][j] = Math.min(dp[i][j-1], Math.min(dp[i-1][j], dp[i-1][j-1]-1))+1;
                else
                    dp[i][j] = Math.min(dp[i][j-1], Math.min(dp[i-1][j], dp[i-1][j-1]))+1;
            }
        }
        return dp[len1][len2];

    }
    //优化一下,其实时间没有太多变化，毕竟一个量级的
    public int minDistance(String word1, String word2){
        int len1 = word1.length(), len2 = word2.length();
        if (len1==0 && len2==0)
            return 0;
        //定义状态为前i个前j个的编辑距离，即修改为一样的需要多少操作
        int[][] dp = new int[len1+1][len2+1];

        //注意加一减一，这种定义下老是忘记
        for (int i = 0; i<len1+1; i++){
            for (int j = 0; j<len2+1; j++){
                if (i==0 || j==0)
                {
                    dp[i][0] = i;
                    dp[0][j] = j;
                    continue;
                }
                //这里看笔记，思路太nb了
                if (word1.charAt(i-1) == word2.charAt(j-1))
                    dp[i][j] = Math.min(dp[i][j-1], Math.min(dp[i-1][j], dp[i-1][j-1]-1))+1;
                else
                    dp[i][j] = Math.min(dp[i][j-1], Math.min(dp[i-1][j], dp[i-1][j-1]))+1;
            }
        }
        return dp[len1][len2];

    }


}
