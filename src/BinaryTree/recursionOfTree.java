package BinaryTree;

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
}//
