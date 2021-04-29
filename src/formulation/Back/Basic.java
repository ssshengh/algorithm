package formulation.Back;

import com.javaLearing.chapter19.A;

import java.util.*;

//回溯算法的基本思路
public class Basic {
    /**
     * 全排列需要用到的返回数组
     */
    List<List<Integer>> res;
    /**
     * 实现全排列，关键是理解回溯和深度优先的运用，首先问题能够建模为一颗树，然后分解子问题求解
     * @param nums:输入的数组
     * @return : 返回一个全排列
     */
    public List<List<Integer>> permute(int[] nums){
        res = new ArrayList<>();
        if (nums.length == 0) return res;

        Deque<Integer> path = new ArrayDeque<>(nums.length);//这样初始化更好，不需要调用扩展算法
        boolean[] used = new boolean[nums.length];//初始化就全是false
        int index = 0;//从第0个开始
        dfs(nums, path, used, index, nums.length);
        return res;
    }

    /**
     * 首先明确这个函数解决的子问题：进入每一个节点，然后整合几个儿子的结果，得到一个完整的结果
     *      但是由于采用dfs从上而下，因此子问题实际是，到每一个节点，已经得到了一部分结果，将这部分结果与儿子节点的结果整和
     *      边界条件是，到达叶子节点，得到一个全排列数组，添加到结果集中
     * @param num: 需要获得全排列的数组，显然每一次子问题都需要，从其中选择
     * @param path: 记录叶子节点的结果
     * @param used: 记录使用过的节点，后面就不找它了
     * @param index: 记录当前找到了第几个节点,主要用途，判定找到了叶子节点，而不是我想的，也顺便判定目前处理过的节点
     * @param len: 数组长度
     */
    private void dfs(int[] num, Deque<Integer> path, boolean[] used, int index, int len){
        if (index == len){
            //结束条件，找到了叶子节点
            //res.add((ArrayList<Integer>) path);是不安全的，因为没有检查Deque和ArrayList说不定同是子类
            //这样就犯了，猫狗是动物，但是猫转换为狗了
            res.add(new ArrayList<>(path));//注意可以这么来初始化
            return;
        }
        //处理普通情况，每一次我们进来的节点是一个已经处理过的父节点
        for (int i = 0; i < len; i++) {
            if (used[i])
                continue;//用过的话，不用管他

            used[i] = true;//先标记这个用过了
            path.addLast(num[i]);//把其加入path中临时储存
            dfs(num, path, used, index+1, len);//否则进入下一个节点，这里注意，再往下走的时候，用的是同一个path
            //如果是++index的话会有一个很隐秘的bug，无法从左子树移动到右子树，因为，index在第二层的时候被改为3了
            // 但是我们需要叶子节点数量的path数组

            // 注意：这里的内容初学的时候很难理解，请看下面的解释
            // 在这一层递归调用结束以后，我们要回退到上一个结点，因此需要做状态重置或者说状态撤销
            // 具体的步骤就是递归之前做了什么，递归之后就是这些操作的逆向操作
            path.removeLast();//关键是这里，回溯上来的时候，我需要清空path，然后重新加入元素，这样的话，就不需要那么多path，一个就行
            used[i] = false;
        }
    }

    /**
     * 全排列II,关键是不能有重复的结果，所以必须考虑剪枝，那么在I的基础上修改看看
     * 问题的关键在于，只要两个子节点对于父节点多的是同一个元素，那么后面的字节点就可以被剪枝
     */
    public List<List<Integer>> permuteUnique(int[] nums){
        res = new ArrayList<>();
        if (nums.length == 0) return res;

        Arrays.sort(nums);
        Deque<Integer> path = new ArrayDeque<>(nums.length);
        boolean[] used = new boolean[nums.length];
        dfs1(nums, path, used, 0, nums.length);
        return res;
    }
    /**
     * 首先明确这个函数解决的子问题：进入每一个节点，然后整合几个儿子的结果，得到一个完整的结果
     *      但是由于采用dfs从上而下，因此子问题实际是，到每一个节点，已经得到了一部分结果，将这部分结果与儿子节点的结果整和
     *      边界条件是，到达叶子节点，得到一个全排列数组，添加到结果集中
     * @param num: 需要获得全排列的数组，显然每一次子问题都需要，从其中选择
     * @param path: 记录叶子节点的结果
     * @param used: 记录使用过的节点，后面就不找它了
     * @param index: 记录当前找到了第几个节点,主要用途，判定找到了叶子节点，而不是我想的，也顺便判定目前处理过的节点
     * @param len: 数组长度
     */
    private void dfs1(int[] num, Deque<Integer> path, boolean[] used, int index, int len){
        if (index == len)
        {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < len; i++) {
            if (used[i])//如果被标记找过这个数就不考虑了
                continue;

            //剪枝，如果父节点的两个字节点一样的话，就剪去右边的儿子，注意，必须先排序
            //排序确保，在k-1层的时候，其k层的儿子，相同的位置连续
            /*这里两个注意点，第一个是，在每次到这个位置其实是第k-1层，k-1层排序好了k-1个数之后，
            * 在第k层从剩下的数中顺序查找数字加入成为新的数组；第二个是，要启动这个还得确保上一个数
            * 已经被回溯过了，意味着左边第一个儿子被回溯过，但似乎不加也一样？答案是否定的，因为显然
            * 如果到k+1层的时候还是有一样的不就给误剪了，其实如果把第二个条件去掉，将会返回null*/
            if (i>0 && num[i] == num[i-1] && !used[i-1])
                continue;

            //否则开找
            used[i] = true;
            path.addLast(num[i]);
            dfs1(num, path, used, index+1, len);

            //回溯上来
            used[i] = false;
            path.removeLast();
        }
    }

    /**
     * 组合总和，难点在于剪枝的时候考虑与target比较大小，同时为了防止重复，在排序后
     * 每棵子树只需要考虑大于自己父节点值的
     * @param candidates： 数组，从中可以重复的取出元素
     * @param target： 目标值，需要取出这个和的大小
     * @return： 返回一个包含所有可能性的列表数组
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        res = new ArrayList<>();
        if (candidates.length==0) return res;
        Deque<Integer> path = new ArrayDeque<>();
        Arrays.sort(candidates);
        com_dfs(candidates, 0, path, candidates.length, target);
        return res;
    }

    /**
     * 这个是个帮助递归的函数，关键在于思考需要些什么元素：首先需要原来的数组，其次是需要考虑标识此时父节点的值
     * 每一颗子树只需要考虑大于等于父节点的值以取消重复，还有需要标识目标值的，如果大于目标值的话就需要直接剪掉
     * @param cans:原始数组
     * @param index：代表此时的父节点的值
     * @param path：代表现在形成的数组
     * @param len ：原始数组大小
     * @param sum：target减去每一层的目前元素的值
     */
    private void com_dfs(int[] cans, int index, Deque<Integer> path, int len, int sum){
        if (sum==0) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = index; i < len; i++) {
            //注意在每棵树的位置只需要考虑大于等于前一棵树的位置开始，即第二层，第一棵树的子树开始于0个儿子，第二棵树的子树开始于第一个儿子
            //对于每一层的每一颗子树成立

            sum-=cans[i];//求和
            if (sum<0){
                //这里进行剪枝，思路是排序后，如果小于0了，那么没必要在考虑后面的节点了
                break;//这一层都不用考虑了,直接回到上一层
            }

            path.addLast(cans[i]);
            com_dfs(cans, i, path, len, sum);
            path.removeLast();
            sum+=cans[i];
        }

    }

    List<List<Integer>> ans;
    /**
     * 组合总和II，关键点是，与上面一题比较的话，因为存在重复元素，在去重的时候，需要考虑排序与移位，但是移动的距离不再是i
     * 另一个是剪枝的时候，还要多减去相同元素的值
     * @param candidates：候选数组，存在重复元素
     * @param target：需要找到的总和
     * @return：得到的数组序列
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target){
        ans = new ArrayList<>();
        if (candidates.length==0) return ans;
        Arrays.sort(candidates);
        Deque<Integer> path = new ArrayDeque<>(candidates.length);
        helper(candidates, 0, target, path, candidates.length);
        return ans;
    }

    /**
     * 帮助函数，来帮助递归的
     * @param can：候选数组，需要全部传入
     * @param begin:每一层寻找应该的起始位置
     * @param target：注意这个是target减去节点值的差
     * @param path：路径函数
     * @param len：候选数组长度
     */
    private void helper(int[] can, int begin, int target, Deque<Integer> path, int len){
        if (target==0){
            ans.add(new ArrayList<>(path));
            return;
        }

        for (int i = begin; i<len; i++){
//            sum-=can[i];//目标值减去节点值
            //不能这么写，遇到了一个奇怪的bug，但是其实就是在continue的时候，下一次迭代时应该是原来的sum，但是传入的是被修改过的


            /*这里完成第一个剪枝，如果出现小于的情况的话，直接跳出这颗作为父节点的子树
            * 这里同时可以保证在多个相同的字节点的时候，如果第一个字节点被减得到的值小
            * 于0的话，不用考虑后面的所有值*/
            if (target-can[i]<0)
                break;
            /*这里完成剪枝，如果当前值等于上一个值的话，跳过这个节点的所有子树*/
            if (i>begin && can[i] == can[i-1]) {
                continue;
            }

            path.addLast(can[i]);

            /*我没想出来的点在这里，应该下一层找的位置是i+1，不能和上一层一样
            * 能够这么干的基础是，通过第二次剪枝解决了重复元素，只考虑了重复元素中的
            * 第一个，然后跳过所有的重复元素，这样的话下一层应该是从大于重复元素的点开始*/
            helper(can, i+1, target-can[i], path, len);//这个还解决了一个情况，在里面有3个1的时候那么是可以取三个1的
            path.removeLast();
            //sum+=can[i];
        }
    }


    public static void main(String[] args) {
        int[] num = new int[]{1,2,1};
        Basic bb = new Basic();
        List<List<Integer>> res1 = new ArrayList<>();
        res1 = bb.permute(num);
        System.out.println(res1);
        new Thread().start();
    }
}
