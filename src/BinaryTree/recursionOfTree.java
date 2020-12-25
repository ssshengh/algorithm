package BinaryTree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class recursionOfTree {
    private static class treeNode {
        int val;
        treeNode left;
        treeNode right;
        treeNode() {}
        treeNode(int val) { this.val = val; }
        treeNode(int val, treeNode left, treeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    //从一个问题说起，来谈递归的自顶而下和自底而上，查找一颗树的最大深度
    //自顶而下的思路是：在每个递归层级，我们将首先访问节点来计算一些值，并在递归调用函数时将这些值传递到子节点。
    //很像是前序遍历，中左右
    //我们知道根节点的深度是1。 对于每个节点，如果我们知道某节点的深度，那我们将知道它子节点的深度。
    //因此，在调用递归函数的时候，将节点的深度传递为一个参数，那么所有的节点都知道它们自身的深度。
    //而对于叶节点，我们可以通过更新深度从而获取最终答案。

    private int answer = 1;//根节点，深度初始化为1
    //函数干个什么事：查找root，并有可能的话跟新深度
    private void  Max_depth(treeNode root, int depth){
        //递归结束条件
        if (root == null)
            return;
        //更新深度，关键在于在每一次遍历到叶子的时候更新
        if (root.left == null && root.right == null)
            answer = Math.max(answer, depth);
        Max_depth(root.left, depth+1);//函数子问题，遍历子节点，若可能，更新深度
        Max_depth(root.right, depth+1);
    }

    //自底而上的思路：在每个递归层次上，我们首先对所有子节点递归地调用函数，然后根据返回值和根节点本身的值得到答案。
    //这个过程可以看作是后序遍历的一种。
    //如果不是树结构，很难实现递归，因为树很好理解为"相同问题的重复"，大问题有着清晰的脉络变成字问题，然后所有子问题结构一样
    private int Max_depthD(treeNode root){
        //函数作用：返回root的深度
        if (root == null)
            return 0;
        int left_depth = Max_depthD(root.left);//返回左子树的深度
        int right_depth = Max_depthD(root.right);//返回右子树的深度
        return Math.max(left_depth, right_depth)+1;
    }


    //解决对称二叉树问题，分递归和迭代
    private boolean a = true;
    public boolean isSymmetric(treeNode head){
        //先想递归三要素：函数干嘛，约束条件，递推式
        //函数干的事：两颗三节点子树是否镜像对称。  如何判断镜像对称：从根节点下来，同时检查左边的左子树和右边的右子树
        //递推：从左右同时下来，每一个子问题是同一层左边和右边的一棵树，进行两次，进入下一个子问题
        //约束：头节点为空的时候，无法进行
        if (head == null)
            return true;
        isSymmetric(head.left, head.right);
        return a;
    }

    private void isSymmetric(treeNode left, treeNode right){
        //切记，别陷进去了，子问题就是查左查右是否相等
        if (left == null && right == null)
            return;
        else if ( left == null || right == null)
        {//这个一定要加上，不然空指针异常了，不能解决一边为空，一边有的情况
            a = false;
            return;
        }
        else if (left != right){
            a = false;
        }
        isSymmetric(left.left, right.right);
        isSymmetric(left.right, right.left);
    }
//自己写的这一版，内存开销很大，时间开销不错
    //官方答案，相对于我的简洁一些，但理解性也差了点，内存消耗稍低
    public boolean isSymmetric1(treeNode root) {
        return check(root, root);
    }

    public boolean check(treeNode p, treeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        return p.val == q.val && check(p.left, q.right) && check(p.right, q.left);
    }
    /*
    * 时间复杂度：这里遍历了这棵树，渐进时间复杂度为 O(n)。
    * 空间复杂度：这里的空间复杂度和递归使用的栈空间有关，这里递归层数不超过n，故渐进空间复杂度为O(n)
    * */

    //迭代方法：从递归来实现，迭代的本质都是把递归里隐藏的栈或者队列显式陈列了
    //思路很简单，关键是巧妙的利用continue实现
    public boolean isSymmetric_D (treeNode root){
        if (root == null || (root.left ==null && root.right==null))
            return true;
        LinkedList<treeNode> queue = new LinkedList<>();
        queue.add(root.left);
        queue.add(root.right);
        while (queue.size()>0){
            treeNode left = queue.removeFirst();
            treeNode right = queue.removeFirst();

            if (left == null && right == null)
                continue;
            else if (left == null || right == null)
                return false;
            else if (left.val != right.val)
                return false;

            queue.add(left.left);
            queue.add(right.right);

            queue.add(left.right);
            queue.add(right.left);
        }
        return true;
    }//可以比较得到，迭代思路的时间开销和空间开销都比较大



    //路经总和问题
    //首先分析问题：问题是每个叶子对应一条路径，对应一个值，每个节点的路经长本质上是，其根节点路径长度加1
    //所以可以划分为子问题，每个子问题为一颗三节点树==》递归
    //函数干嘛：通过根节点路径加1计算叶节点
    //结束过程，节点为空或者节点的儿子都为空
    //子问题，往下遍历，计算路径长，在某个儿子为空的时候比较sum

    public boolean hasPathSum(treeNode root, int sum){
        if (root == null)
            return false;
        int sum_tree = root.val;
        hasPathSum(root, sum, sum_tree);
        return a;
    }
    private void hasPathSum(treeNode root, int sum, int sum_tree){
        //boolean下，难以解决的一个问题是，如何在某一个子问题查到了相等，把这个结果返还回来
        if (root == null)
            return;
        if (a)
            return;
        if (root.right == null && root.left == null){
            if (sum == sum_tree)
                a = true;
            return;
        }

        hasPathSum(root.left, sum, sum_tree+=root.left.val);
        hasPathSum(root.right, sum, sum_tree+=root.right.val);
//        if (root == null)
//            return;
//        if (a)
//            return;
//        if (root.right == null && root.left == null){
//            if (sum == sum_tree)
//                a = true;
//            return;
//        }else if (root.left != null && root.right == null){
//            hasPathSum(root.left, sum, sum_tree+=root.left.val);
//
//        }else if (root.left == null && root.right != null){
//            hasPathSum(root.right, sum, sum_tree+=root.right.val);
//        }else{
//            hasPathSum(root.left, sum, sum_tree+=root.left.val);
//            hasPathSum(root.right, sum, sum_tree+=root.right.val);
//        }
    }
    //压根没做出来，这一版有着两个巨大的问题
    //一、返回值为boolean的问题，如何把布尔值返回回去而不被其他子问题修改？
    //二、我尝试使用全局变量来储存bool值，但是如何去划分情况使得问题清晰，这里按照子问题的四种情况进行划分的话：两个子节点，一个或者无
    //      就会报错，最后一个if必为true。很奇怪，确实，如果前面的都执行了，判断失败后，最后一个是必对的，那么写不写并没用
    //      如果最后一个是只有右节点的话，递归右节点将会一直存在。
    //      如果是if ；else if实现这种划分的话，逻辑正确，但是全局bool并没有返回过来，至今不知道原因

    //看一看标准的答案如何去处理这个问题
    //DFS算法
    public boolean hasPathSum1(treeNode root, int sum){
        if (root == null)
            return false;
        //这个if不是约束！！！千万区分
        if (root.left == null && root.right == null)
            return sum == root.val;
        return hasPathSum1(root.left, sum - root.val) || hasPathSum1(root.right, sum - root.val);
    }
    //很简洁，那么比对一下，根本矛盾在于，我没思考到如何把bool从子问题返回到父问题，导致使用了一堆if else
    //现在这个问题其实很简单，本质是子问题没有分清
    //子问题，也就是函数要干个什么事，就是该节点空了错，节点儿子空了判断，否则查左右节点，若有一个找到了就找到
    //约束条件已经包含全了，所以子问题必然会返回true或者false，所以我就直接分析一个问题就可以了
    //并不是陷阱了细节，而是！！！！！最重要的，在这个问题中，其实是把其当做了完全二叉树看待，在只有一个儿子的时候，也看作了两个节点
    //而这两个节点是否都存在，已经被约束处理了
    //「如果需要遍历整颗树，递归函数就不能有返回值。如果需要遍历某一条固定路线，递归函数就一定要有返回值！」

    //这种方法内存消耗巨大

    //BFS，这里揭示了我的另一个问题，在对问题建模的时候，建模成树，但是没有把树的四种遍历全考虑进去
    //使用两个队列来分别存储，容易理解
    public boolean hasPathSumBfs(treeNode root, int sum) {
        if (root == null) {
            return false;
        }
        Queue<treeNode> queNode = new LinkedList<treeNode>();
        Queue<Integer> queVal = new LinkedList<Integer>();
        queNode.offer(root);
        queVal.offer(root.val);
        while (!queNode.isEmpty()) {
            treeNode now = queNode.poll();//把队列第一个弹出
            int temp = queVal.poll();
            if (now.left == null && now.right == null) {
                if (temp == sum) {
                    return true;
                }
                continue;
            }
            if (now.left != null) {
                queNode.offer(now.left);
                queVal.offer(now.left.val + temp);
            }
            if (now.right != null) {
                queNode.offer(now.right);
                queVal.offer(now.right.val + temp);
            }
        }
        return false;
    }



}//
