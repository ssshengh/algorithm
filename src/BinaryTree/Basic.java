package BinaryTree;

import java.util.ArrayList;
import java.util.List;

public class Basic {
    //二叉树的前中后序遍历极为重要，是二叉树的基础中的基础
    //递归法：
    //前序遍历：
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        preorderTraversal(root, list);
        return list;
    }
    private void preorderTraversal(TreeNode root, List<Integer> list){
        //输出自己，然后找到root这个节点的左右节点
        if (root == null)
            return;
        list.add(root.val);
        preorderTraversal(root.left, list);
        preorderTraversal(root.right, list);
        //根左右
    }
    //中序遍历
    private void midOrderRecur(TreeNode root, List<Integer> list){
        //关键在于函数定义实现的是什么，这个是中序遍历，详细来说就是要干这么个事：找到root这个节点的左节点，没有的话输出自己，然后找他的右节点
        if (root == null)
            return;
        midOrderRecur(root.left,list);//找到root.left的左节点输出
        list.add(root.val);
        midOrderRecur(root.right, list);//找到root.right的左节点输出
        //注意三个遍历这里的区别，体现出了递归的总体思路
        //每一个子问题就是一颗最小的二叉树，三个节点，三序遍历后作为某个节点并入更大的一棵树内
        //这里最小的一棵就是三个节点，从中间开始，看中间的左节点，然后输出左节点
    }
    //后序遍历
    private void postOrderRecur(TreeNode root, List<Integer> list){
        if (root == null)
            return;
        postOrderRecur(root.left, list);
        postOrderRecur(root.right, list);
        list.add(root.val);
    }



    private static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }
}
