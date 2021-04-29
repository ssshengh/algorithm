package DPlearing.liner;

import java.util.*;
import java.util.stream.Collectors;

public class Metric {
    /**
     * 三角形最小路径和
     * */
    public int minimumTotal_ok(List<List<Integer>> triangle){
        int len = triangle.size();//层数
        if (len==0) return 0;
        //定义状态为从顶点dp[0][0]到第i层第j个的最短距离
        int [][] dp = new int[len][len];//因为j<=i+1，当然这空间还可以优化
        dp[0][0] = triangle.get(0).get(0);//初始化顶点的值

        for (int i = 1; i<len; i++){//从第一层开始
            dp[i][0] = dp[i-1][0] + triangle.get(i).get(0);//这一句初始化应该搬在这里来
            for (int j = 1; j<i+1; j++){//每一层的特性
                if (j==i)//在我这种定义下，这一步必须提取出来，比如triangle[1][1]的位置可能会取到[0][1]位置的0
                    dp[i][j] = dp[i-1][j-1] + triangle.get(i).get(j);
                else
                    dp[i][j] = Math.min(dp[i-1][j], dp[i-1][j-1])+triangle.get(i).get(j);
            }
        }
        int res = Integer.MAX_VALUE;
        for (int i =0; i<len+1; i++)
            res = Math.min(res, dp[len-1][i]);
        return res;
    }
    //优化空间，尝试用完全对应的list处理，直接在三角形上进行转移，不要额外空间
    //可能因为内部调用的问题，时间开销很大
    public static int minimumTotal_question(List<List<Integer>> triangle){
        int len = triangle.size();//层数
        int last = len + 1;//最后一层的宽度
        if (len==0) return 0;

        for (int i = 1; i<len; i++){
            triangle.get(i).set(0, triangle.get(i-1).get(0) + triangle.get(i).get(0));//注意这种设置方法
            for (int j = 1; j<i+1; j++){
                if (j==i)
                    triangle.get(i).set(j, triangle.get(i-1).get(j-1)+triangle.get(i).get(j));
                else
                    triangle.get(i).set(j,
                            Math.min(triangle.get(i-1).get(j), triangle.get(i-1).get(j-1))+triangle.get(i).get(j));
            }
        }
        int res = Integer.MAX_VALUE;
        for (int i = 0; i<len; i++)
            res = Math.min(res, triangle.get(len-1).get(i));

        return res;
    }

    //使用滚动数组优化：
    public int minimumTotal(List<List<Integer>> triangle){
        int len = triangle.size();//层数
        if (len==0) return 0;
        int [] dp = new int[len];
        dp[0] = triangle.get(0).get(0);

        for (int i = 1; i<len; i++){
            int k = dp[i-1], temp1 = dp[0], temp2 = dp[1];//需要两个临时空间存储前面的数据，不能用被修改过的数据
            dp[0] = dp[0] + triangle.get(i).get(0);
            for (int j = 1; j<=i; j++){
                if (j==i)
                    dp[j] = k + triangle.get(i).get(j);
                else
                    dp[j] = Math.min(temp1, temp2) + triangle.get(i).get(j);
                temp1 = temp2;
                temp2 = j+1>len-1? 0 : dp[j+1];
            }
        }
        int res = Integer.MAX_VALUE;
        for (int j : dp) res = Math.min(res, j);
        return res;
    }

    /**
     * 最小路径和
     * @param grid : 输入的走路矩阵
     */
    public int minPathSum_ok(int[][] grid){
        if (grid.length == 0) return 0;
        int n = grid.length;//行数
        int m = grid[0].length;//列数

        int [][] dp = new int[n][m];//定义为从[0][0]到[i][j]的最短路径
        dp[0][0] = grid[0][0];
        for (int j = 1; j<m; j++)
            dp[0][j] = grid[0][j] + dp[0][j-1];

        for (int i = 1; i<n; i++){
            dp[i][0] = dp[i-1][0] + grid[i][0];
            for (int j = 1; j<m; j++){
                dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1])+grid[i][j];
            }
        }
        return dp[n-1][m-1];
    }
    //优化空间：
    public int minPathSum(int[][] grid){
        if (grid.length == 0) return 0;
        int n = grid.length;//行数
        int m = grid[0].length;//列数

        //int [][] dp = new int[n][m];//定义为从[0][0]到[i][j]的最短路径
        int [] dp = new int[m];
        dp[0] = grid[0][0];
        for (int j = 1; j<m; j++)
            dp[j] = grid[0][j] + dp[j-1];

        for (int i = 1; i<n; i++){
            int[] temp = dp.clone();
            temp[0] = dp[0] + grid[i][0];
            for (int j = 1; j<m; j++){
                temp[j] = Math.min(dp[j], temp[j-1])+grid[i][j];
            }
            dp = temp;
        }
        return dp[m-1];
    }

    /**
     * 地下城游戏
     * @param dungeon : 矩阵
     * @return : 最小初始值
     */
    public int calculateMinimumHP(int[][] dungeon){
        if (dungeon.length == 0) return 0;
        //这一题的分析很关键，注意为什么上一题的方法有后效性了
        int n = dungeon.length;//行数
        int m = dungeon[0].length;//列数

        //定义状态为，[i][j]位置需要的最小初始值，为了无后效性，我们应该从右下到左上
        int[][] dp = new int[n][m];
        //dp[n-1][m-1] = 1 - dungeon[n-1][m-1];//救出公主至少需要1点生命,但这里有问题[[100]]初始化为-99
        dp[n-1][m-1] = Math.max(1, 1 - dungeon[n-1][m-1]);//救出公主至少需要1点生命

        //这次考虑的是，第n行[n-1]位置只需要一个前述状态，第m列[m-1]位置也是
        for (int i = m-2; i>=0; i--)
            //dp[n-1][i] = dp[n-1][i+1] - dungeon[n-1][i];有问题，如果是我遇到一个加血的地方，我初始值就会变成负的，甚至负很多
            //那么加血的地方的初始值，应该就是1，为最低.下面也一样得改
            dp[n-1][i] = Math.max(1, dp[n-1][i+1] - dungeon[n-1][i]);
        for (int i = n-2; i>=0; i--){//行从倒数第二行开始
            dp[i][m-1] = Math.max(1, dp[i+1][m-1] - dungeon[i][m-1]);//处理每一行的最后一个数，是只需要考虑一个前述位置的
            for (int j = m-2; j>=0; j--){
                //dp[i][j] = Math.min(dp[i+1][j], dp[i][j+1])-dungeon[i][j];
                dp[i][j] = Math.max(Math.min(dp[i+1][j], dp[i][j+1])-dungeon[i][j],
                        1);
            }
        }
        return dp[0][0];

    }

    /**
     * 最大正方形：注意一个特别重要的观察，如果从某个左上角到(i,j)结尾是一个正方形，边长为i的话
     * 那么大正方形中的每一个元素以其结尾都是正方形的样子，而(i,j)三个前述状态为i-1的正方形
     * @param matrix : 矩阵
     * @return : 最大正方形的值
     */
    public int maximalSquare(char[][] matrix){
        if (matrix.length == 0) return 0;
        int n = matrix.length;//行数
        int m = matrix[0].length;//列数

        int [][] dp = new int[n][m];//为某个左上角到(i,j)的最大正方形边长
        int max = 0;//存储最大边长
        //初始化：第一行和第一列无法构成正方形
//        for (int i = 0; i<m; i++)//第一行
//            if (matrix[0][i] == '1') dp[0][i] = 1;
        for (int i = 0; i<m; i++)//第一行
        {
            if (matrix[0][i] == '1') {
                dp[0][i] = 1;
                max = Math.max(max, dp[0][i]);//这里不能忘记更新，不然两行的时候就出问题了
            }

        }

        for (int i = 1; i<n; i++){
            dp[i][0] = matrix[i][0]=='0'? 0 : 1;
            max = Math.max(max, dp[i][0]);//这儿也需要，如果是2*1矩阵
            for (int j = 1; j<m; j++){
                if (matrix[i][j] == '0')
                    dp[i][j] = 0;
                else
                    dp[i][j] = Math.min(dp[i-1][j], Math.min(dp[i][j-1], dp[i-1][j-1]))+1;
                max = Math.max(max, dp[i][j]);
            }
        }
        //return (int)Math.pow(max, 2);若max为0，这里输出的会是1
        return max*max;
    }

    /**
     * 最小下降路径和，类似第一题，还更简单
     * @param matrix : 矩阵
     * @return ： 路径
     */
    public int minFallingPathSum_ok(int[][] matrix){
        if (matrix.length == 0) return 0;
        int n = matrix.length;//行数
        int m = matrix[0].length;//列数

        int[][] dp = new int[n][m];//(i,j)位置的最小下降路径
        dp[0] = Arrays.copyOfRange(matrix[0], 0, n);

        for (int i = 1; i<n; i++){
            for (int j = 0; j<m; j++){
                if (j==0)
                    dp[i][j] = Math.min(dp[i-1][j], dp[i-1][j+1])+matrix[i][j];
                else if (j==m-1)
                    dp[i][j] = Math.min(dp[i-1][j], dp[i-1][j-1])+matrix[i][j];
                else
                    dp[i][j] = Math.min(dp[i-1][j], Math.min(dp[i-1][j+1], dp[i-1][j-1]))+matrix[i][j];
            }
        }
        int res = dp[n-1][0];
        for (int j : dp[n-1])
            res = Math.min(res, j);
        return res;
    }
    //优化储存：
    public int minFallingPathSum(int[][] matrix){
        if (matrix.length == 0) return 0;
        int n = matrix.length;//行数
        int m = matrix[0].length;//列数

        int[] dp = new int[m];//(i,j)位置的最小下降路径
        dp = Arrays.copyOfRange(matrix[0], 0, n);

        for (int i = 1; i<n; i++){
            int[] temp = new int[m];
            for (int j = 0; j<m; j++){
                if (j==0)
                    temp[j] = Math.min(dp[j], dp[j+1])+matrix[i][j];
                else if (j==m-1)
                    temp[j] = Math.min(dp[j], dp[j-1])+matrix[i][j];
                else
                    temp[j] = Math.min(dp[j], Math.min(dp[j+1], dp[j-1]))+matrix[i][j];
            }
            dp = temp;
        }
        int res = dp[0];
        for (int j : dp)
            res = Math.min(res, j);
        return res;
    }//也可以原矩阵存

    public static void main(String[] args) {
        List<List<Integer>> aa = new LinkedList<>();
        aa.add(List.of(2));//List.of出来的是一个不可修改的数组
        aa.add(List.of(3,4));
        aa.add(List.of(6,5,7));
        aa.add(List.of(4,1,8,6));
        int len = aa.size();
        System.out.println("size is" + len);
        System.out.println("aa.get(len) is" + aa.get(len-1));
        List<List<Integer>> bb = new ArrayList<>();
        bb.add(new ArrayList<>(List.of(2)));
        bb.add(new ArrayList<>(List.of(3,4)));
        int[] temp = new int[]{1,2,3,3,4};
        bb.add(Arrays.stream(temp).boxed().collect(Collectors.toList()));
        //System.out.println(minimumTotal(bb));

        System.out.println("总共这么多:" + (double)171300/24/365);

    }
}
