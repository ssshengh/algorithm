package DPlearing.liner;

import java.util.*;

public class SomeString {
    /**
     * 392、判断子序列
     * */
    //自己的方法：单指针加队列
    public boolean isSubsequence_mine(String s, String t){
        char [] char_s = s.toCharArray();
        char [] char_t = t.toCharArray();
        if (char_s.length > char_t.length)
            return false;

        Deque<Character> deque = new ArrayDeque<>();
        for (char char_ : char_s) deque.add(char_);//先进先出的把char_s放入队列中

        for (char c : char_t) {
            //队列出完就为对
            if (deque.isEmpty())
                return true;
            char temp = deque.getFirst();
            if (c == temp) {
                deque.removeFirst();
            }

        }
        return deque.isEmpty();
    }
    //官方解法优化：将队列优化为另一个指针
    public boolean isSubsequence1(String s, String t){
        int n = s.length(), m = t.length();
        int i = 0, j = 0;
        while (i<n && j<m){
            if (s.charAt(i) == t.charAt(j))//注意这个方法，把String当数组用
                i++;
            j++;
        }
        return i==n;
    }
    //动态规划法：优化思路，想办法把t中的字母进行编码，对应上其位置，方便跳跃查询
    public boolean isSubsequence(String s, String t){
        int n = s.length();
        int m = t.length();//二者的长度

        int [][]dp = new int[m+1][26];//dp[i][j]意味着位置i后面字母j出现的第一个位置
        //为什么这么初始化呢？因为我找到s中第一个字母对应的t中字母j的第一个位置dp[i][j]后我可以直接在这个位置后找其他字母

        //先初始化dp，把初始状态令为m,这样的话，状态转移一定会成功，而且可以作为最后的查找条件
        for (int i = 0; i<26; i++)
            dp[m][i] = m;

        //把t中的26个字母全部编码
        for (int i = m-1; i>=0; i--)//每个i前面可能都有26个字母的位置
            for (int j=0; j < 26; j++){
                if (t.charAt(i) == j+'a')//意味着我找到i前面的j这个字母的位置了
                    dp[i][j] = i;
                else
                    dp[i][j] = dp[i+1][j];//否则就说明j这个字母不在i，而在i+1前面一些
            }

        //编码完成，接下来就是跳跃式查找，注意dp定义
        int loc = 0;
        for (int i = 0; i<n; i++){
            //我只需要对s遍历就行了，顺序看s的元素位于t中的位置
            //loc = dp[i][s.charAt(i)];//第一次进来意味着从位置0开始，s第一个元素所处的位置
            if (dp[loc][s.charAt(i)-'a'] == m)//说明该字母，t中无
                return false;
            loc = dp[loc][s.charAt(i)-'a'] +1;//这一句是最开始没想到的，loc这么来更新，才能做到下一次检索的位置
            //是这一次找到的第一个元素位置之后，找第二个元素的第一个位置
        }
        //return loc == m;
        return true;//这样更适配，s为空的时候，因为此时直接不走for,main里面的小例子

    }
    /**
     * 28,实现strStr()
     * */
    public int strStr(String haystack, String needle){
        //注意初始条件
        if (needle.length() == 0)
            return 0;//是可以匹配到的
        if (haystack.length() ==0)
            return -1;//这种就不行
        char[] original = haystack.toCharArray();
        char[] model = needle.toCharArray();
        int orlen = original.length;
        int molen = model.length;
        return kmp(original, orlen, model, molen);
    }
    //kmp算法：
    private int [] next (char[] needle, int len){
        //本质上是对自己进行模式匹配，next[i]的意思是，char[i]结尾的字串的最长公共前后缀中前缀最后一个值下标
        int [] next = new int[len];
        int k = -1;//用于指示作为模式串的needle，与原串needle匹配到的位置-1，这样可以保证，右移模式串时，有可能从第0个位置重新找一样字串
        next[0] = -1;//第一个数初始化为-1，没有最长公共前后缀

        for (int i = 1; i<len; i++){
            //从1开始，因为不用考虑第一个数
            //这一步的意思是，作为模式串的needle，在k+1这个点不和原串的i位置匹配了，需要移动到后面(坏字符)
            while (k!=-1 && needle[k+1] != needle[i])
                k = next[k];//这里不能k直接等于-1，我找不到例子，但是多半是好后缀原则

            //这一步意思是，模式needle的第一个数开始和自己的第i个数匹配
            //一直匹配到不匹配的时候，到上面的if
            if (needle[k+1] == needle[i])
                k++;
            next[i] = k;
            //啥时候忘记了简单，bcbcbe 对应 [-1,-1,0,1,2,-1]
        }
        return next;
    }
    private int kmp (char[] origin, int orlen, char[] model, int molen){
        //首先获得模式串next数组：
        int[] next = next(model, molen);
        int j = 0;//模式串的位置指针

        for (int i = 0; i<orlen; i++){
            //遍历一遍原串进行比较
            while (j>0 && origin[i] != model[j]){//注意是while，一个前缀一个前缀的找过去
                //如果在匹配过程中，找到了和模式串不一样的点
                j = next[j-1] + 1;//找到匹配的前j-1个点，找到其最长公共前后缀的前缀下标，加一接着匹配
                //找到最小的那个前缀，使得i和j对上
                if (molen - j + i > orlen)//molen - (j-1)为模式串剩下的长度，i+1为匹配过的长度
                    //超出长度时，可以直接返回不存在
                    return -1;
            }
            if (origin[i] == model[j])
                ++j;//匹配成功，继续往后匹配
            //遍历完毕，返回模式串匹配的原串的起点下标
            if (j == molen)//不是-1，因为上一个if在最后一位匹配后，j++了
                return i - molen + 1;
        }
        return -1;

    }
    /**
     * 368.最大整除子集
     * 贪心和二分没写出来，用dp试试
     * */
    public List<Integer> largestDivisibleSubset(int[] nums){
        //特殊情况
        int n = nums.length;
        if (n == 0) return new ArrayList<Integer>();
        //定义状态方程：
        List<ArrayList<Integer>> EDG = new ArrayList<>();
        for (int num: nums) EDG.add(new ArrayList<Integer>());//别忘了先初始化

        Arrays.sort(nums);//将原数组排序

        for (int i = 0; i<n; i++){
            List<Integer> subList = new ArrayList<>();//临时变量，储存nums[] 的i位置之前的最大的整除数集
            for (int j = 0; j<i; j++)
                //如果nums[i]能够整除之前的第一个值，那么将这个值对应的整数数集存入临时变量
                //如果后面还能再整除一个，假设i+3，那么EDG.get(i+3)必然包含了EDG.get(i)的值
                //因此只需要，判断新的这个，更大的数的整数数集比先前存的更大，并且一样可以整除就可以赋值过来
                if (nums[i] % nums[j] == 0 && subList.size() < EDG.get(j).size())
                    subList = EDG.get(j);

            EDG.get(i).addAll(subList);//把前述的整数数集存入
            EDG.get(i).add(nums[i]);//把自己存入
        }
        //找到EDG中的最大长度返回该list
        List<Integer> ret = new ArrayList<>();
        for (ArrayList<Integer> integers : EDG) {
            if (ret.size() < integers.size()) ret = integers;
        }
        return ret;

    }
    //双On^2
    //优化空间，主要逻辑一样
    public List<Integer> largestDivisibleSubset1(int[] nums){
        int len = nums.length;
        if (len == 0) return new ArrayList<>();

        int [] dp = new int[len];//此时的dp[i]定义为以nums[i]结尾的子数组的，最长整除数集
        Arrays.sort(nums);//排序nums

        int maxSubsetSize = Integer.MIN_VALUE, maxSubsetIndex = Integer.MIN_VALUE;
        for (int i = 0; i<len; i++){
            int subSet = 0;
            for (int j = 0; j<i; j++)
                if (nums[i] % nums[j] ==0 && subSet < dp[j])//只要j位置的最长整数集比我们目前统计的subSet大的话就替换
                    subSet = dp[j];

            dp[i] = subSet +1;
            //接下来需要取出最大长度以及最大长度索引
            if (dp[i] > maxSubsetSize)
            {
                maxSubsetIndex = i;
                maxSubsetSize = dp[i];
            }
        }

        //找到最大长度了开始重构最长整数集，消耗时间换空间：
        LinkedList<Integer> res = new LinkedList<>();
        int currIndex = maxSubsetSize;//储存最大长度,便于后面递减，可以优化for
        int currData = nums[maxSubsetIndex];//储存临时的数据，从最长整数集对应的元素开始除下去
        for (int i = maxSubsetIndex; i>=0; i--){
            if (currIndex == 0) break;

            if (currData % nums[i] == 0 && currIndex == dp[i] ){
                //这里考虑了最大的元素放进去
                //只要找到一个最大元素，那么就一个一个除，找到一个能整除的，并且最长整数集长度在递减的
                //然后把次大的赋值给临时变量，继续除，在某个位置会提前dp[i]=1，所以上面提前跳出
                res.addFirst(nums[i]);
                currData = nums[i];
                currIndex --;
            }
        }
        return res;
    }

    //核心优化：存储中间状态,极致时间
    //优化关键点在于，上述求子最长整数集的内部循环中不断调用之前的数据，因此对内部循环优化

    //存储中间状态，key：下表；value：nums[key]对应的最长子整除数集
    HashMap<Integer, List<Integer>> eds = new HashMap<>();
    //储存整理过的nums
    int[] array = {};//这么来初始化空数组
    private List<Integer> Eds(int i){
        //这里是抽象出来的内部循环，一方面完成之前的对i+1前面找到的子最长整除数集查找，一边储存
        if (this.eds.containsKey(i)) return this.eds.get(i);

        List<Integer> maxSubList = new ArrayList<>();//为何需要，因为需要比较长度大小

        //完成之前的内部for的工作
        for (int j = 0; j<i; j++) {
            //原本是需要前面的子序列的长度大于临时值时才加进来，考虑了可能公因数的情况,到了下面
            if (this.array[i] % this.array[j] == 0){
                List<Integer> subList = Eds(j);
                if (subList.size() > maxSubList.size()) maxSubList = subList;
            }

        }
        //把当前值加进去
        //把当前位置结果存储起来
//        maxSubList.add(this.array[i]);
//        this.eds.put(i, maxSubList);
//        return maxSubList;
        //这个会出错，因为232行这一句，sublist调用的Eds，Eds返回的是全局变量eds的引用
        //所以eds引用最后给了maxSubList，所以相当于对eds的某一个元素(是一个列表)加了一个数
        List<Integer> res = new ArrayList<>(maxSubList);
        res.add(this.array[i]);
        this.eds.put(i, res);
        return res;
    }
    public List<Integer> largestDivisibleSubset3(int[] nums){
        int len = nums.length;
        if (len == 0) return new ArrayList<Integer>();

        Arrays.sort(nums);
        this.array = nums;

        //别忘了初始化！！！
        this.eds = new HashMap<>();
        List<Integer> res = new ArrayList<>();//存储最后的值

        for (int i = 0; i<len; i++){
            List<Integer> subList = Eds(i);
            if (res.size() < subList.size()) res = subList;
        }

        return res;

    }




    public static void main(String[] args) {
        int n = 0;
        for (int i = 0; i<n; i++)
            System.out.print(i);
        System.out.println(n);

    }

}
class aa{
    public static void main(String[] args) {
        int [] nums = {1,2,3};
        System.out.println(new SomeString().largestDivisibleSubset3(nums)
        );
    }
}
