package BinaryTree;

import com.sun.source.tree.Tree;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

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
        //进一步的，每一步，这个函数干的就是返回自己的值
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


    //迭代解法：核心是用一个显式堆栈来表示递归中的隐式堆栈
    //前序遍历
    public static void preOrderIteration(TreeNode head){
        if (head == null)
            return;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(head);
        while (!stack.isEmpty())
        {
            TreeNode node = stack.pop();
            System.out.println(node.val + " ");
            if (node.right != null)
                stack.push(node.right);
            if (node.left != null)
                stack.push(node.left);
        }
    }
    //中序遍历
    public static void midOrderIteration(TreeNode head){
        if (head == null)
            return;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = head;
        while (!stack.isEmpty() || cur != null){
            //完成一直向左遍历到最后,根节点存储在了stack最下面，在输出整个左子树后输出
            while (cur != null){
                stack.push(cur);
                cur = cur.left;
            }
            TreeNode node = stack.pop();//输出栈中的左节点，左到不能再左了,其根节点就是父节点
            System.out.println(node.val + " ");
            if (node.right != null)
            {//从右子树重新开始一遍找左
                cur = node.right;
                //stack.push(cur);不能在这里push，重复了
            }

        }
    }
    //后序遍历
    public static void postOrderIteration(TreeNode head){
        if (head == null)
            return;
        //注意到两个栈的设计模式
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        stack1.push(head);
        while (!stack1.isEmpty())
        {
            //根节点是最后被pop出来的，所以直接放下面
            TreeNode node = stack1.pop();
            //stack2相当于把树按顺序排好了
            stack2.push(node);
            //注意是先放左再放右，与前序遍历区分开,因为反过来了，画个图
            if (node.left != null)
                stack1.push(node.left);
            if (node.right != null)
                stack1.push(node.right);
        }
    }

    //层序遍历，标准解法是用二维数组，把每一层的都给输出
    //先用一维数组，完全遍历二叉树
    //层序遍历核心在于利用了一个队列
    private static void bfs(TreeNode head){
        if (head == null)
            return;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(head);
        while (!queue.isEmpty()){
            TreeNode node = queue.poll();
            System.out.println(node.val + " ");
            //先入左，再入右，先出左，先进左的左右，因此再出队的时候，队列中同时存在两层的元素
            if (head.left != null)
                queue.add(head.left);
            if (head.right != null)
                queue.add(head.right);
        }
    }
    //把bfs改为层序遍历
    public static void bfsTree(TreeNode head){
        if (head == null)
            return;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(head);
        while (!queue.isEmpty()){
            int size = queue.size();
            for (int i=0; i<size; i++){
                //  bfs与层序遍历的关键在于这一行，在每一次循环的时候，可以在一个while中把一层的节点全部输出
                //然后把其子节点入队，比如第二层满的时候，第二层最开始是先入左后入右，到下一个while里
                //通过for，将左节点出队，入队其子节点在右节点后面，然后出右节点，入其子节点在左子节点后面
                TreeNode node = queue.poll();
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }
        }
    }
    //二维数组存储每一层元素，顺着堆下来
    public static @NotNull
    List<List<Integer>> bfsTreeFull(TreeNode head){
        List<List<Integer>> list = new ArrayList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        if (head != null)
            queue.add(head);
        while (!queue.isEmpty()){
            int size = queue.size();
            //区别在于这里用一个二维数组来进行存储
            List<Integer> level = new ArrayList<>();
            for (int i=0; i<size; i++){
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }
            list.add(level);
        }
        return list;
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

