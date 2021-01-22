package BinarySearchTree;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

public class basic {
    //二叉搜索树，是左子树小根节点，右子树大于根节点的树

    public class TreeNode {
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

    //验证二叉搜索树，关键在于利用其性质，所有左子树小于根节点，小于右子树
    //因此，从这个过程可以看做，从上到下的递归，然后回溯信息
    public boolean isValidBST1(TreeNode root){
        if (root == null)
            return true;
        if (root.left != null && root.left.val >= root.val)
            return false;
        if (root.right != null && root.right.val <= root.val)
            return false;
        return isValidBST1(root.left) &&
                isValidBST1(root.right);
    }//这版代码能够保证每个小的二叉树里满足二叉搜索树的要求，但是不能满足最大的子树满足要求比如[5,4,6,null,null,3,7]
    long pre = Long.MIN_VALUE;
    public boolean isValidBST(TreeNode root){
        if (root == null)
            return true;
        if (!isValidBST(root.left))
            return false;
        if (root.val <= pre)
            return false;
        pre = root.val;
        return isValidBST(root.right);
    }

    //实现一个二叉搜索树的迭代器
    //迭代器的思路不要想得很复杂，一种就是把东西全取出来，存在某个数组里，利用数组的规律来查找
    //另一种就是模拟，中序遍历
    ArrayList<Integer> array;
    int index;
    public void BSTIterator1(TreeNode root){
        this.array = new ArrayList<>();
        index = 0;
        this.inorder(root);
    }
    private void inorder(TreeNode root){
        if (root == null)
            return;
        inorder(root.left);
        this.array.add(root.val);
        inorder(root.right);
    }
    public int next1(){
        return this.array.get(this.index++);
    }
    public boolean hasNext1(){
        return this.index < this.array.size();
    }
    //法2：注意理解拆开后的，利用堆栈实现的中序遍历：把左子树全压进去，出栈，然后判定，若有右节点，则把右节点与其左子树压进去，即可遍历完所有
    Stack<TreeNode> stack;
    public void BSTIterator(TreeNode root){
        this.stack = new Stack<>();
        leftMostInorder(root);
    }
    private void leftMostInorder(TreeNode root){
        while (root != null){
            this.stack.push(root);
            root = root.left;
        }
    }
    public int next(){
        TreeNode node = stack.pop();
        if (node.right != null)
            leftMostInorder(node.right);
        return node.val;
    }
    public boolean hasNext(){
        return !this.stack.isEmpty();
    }

    //二叉搜索树的搜索
    public TreeNode searchBST_dfs(TreeNode root, int val){
        if (root == null)
            return null;
        if (root.val == val)
            return root;
        else if (val < root.val)
            return searchBST(root.left, val);
        else
            return searchBST(root.right, val);
    }//时间复杂度O(H),H为树高，平均为logN，最坏N
    //空间复杂度：O(H)，平均为logN，最坏N
    //优化一下：迭代
    public TreeNode searchBST(TreeNode root, int val){
        while (root != null && val != root.val)
            root = val < root.val ? root.left : root.right;//注意这种写法
        return root;
    }//空间复杂度优化为O(1)，恒定额外空间

    //二叉搜索树的插入，关键在于，插入之后，其他的子树怎么处理
    //递归法
    public TreeNode insertIntoBST(TreeNode root, int val) {
        //递归注意：是无法插入除叶节点以外的节点的。
        //但是！！！最开始没做出来，没意识到，二叉搜索树插入，一定可以插入最下面的叶子
        if (root == null)
            return new TreeNode(val);
        if (root.val > val)
            root.left = insertIntoBST(root.left, val);
        else
            root.right = insertIntoBST(root.right, val);
        return root;
    }
    //这道题难的是，插入中间的实现，通过迭代
    //看了题解是，这道题压根不用考虑。。。。插入中间的情况，只需要插入叶子就可以了，然后重构重构
    public TreeNode insertIntoBST1(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        TreeNode pos = root;
        while (pos != null) {
            if (val < pos.val) {
                if (pos.left == null) {
                    pos.left = new TreeNode(val);
                    break;
                } else {
                    pos = pos.left;
                }
            } else {
                if (pos.right == null) {
                    pos.right = new TreeNode(val);
                    break;
                } else {
                    pos = pos.right;
                }
            }
        }
        return root;
    }
    //基本思路有了，模拟递归下去，找到之后再处理，但是处理有问题，没有前驱和后继节点的思路，所以没有处理好
    public TreeNode deleteNode1(TreeNode root, int key){
        if (root == null)
            return null;
        TreeNode node = root;
        while (node != null){
            if (key < node.val){
                node = node.left;
            }else if (key > node.val)
            {
                node = node.right;
            }else {//找到这个点和一个node的值一样了
                if (node.right != null){
                    TreeNode item = node;
                    node = node.right;
                    while (node.left != null)
                        node = node.left;
                    node.left = item.left;
                    item = null;
                    break;
                }else {
                    TreeNode item = node;
                    node = node.left;
                    item = null;
                    break;
                }
            }
        }
        return root;
    }
    //对一个树进行中序遍历，某个节点前一个就叫前驱，后一个叫后继
    //这一题要处理的难点在于，我需要将移除后的数据的子节点移上来，那么就是需要处理 ！！有子树情况下的前驱和后继节点！！（不然没有子树，前驱为根）
    public int rightMin(TreeNode root) {//1.找到以某个结点为根节点的右子树最小值。
        root = root.right;
        while (root.left != null) root = root.left;
        return root.val;
    }

    public int leftMax(TreeNode root) {//2.找到以某个结点为根节点的左子树最大值。
        root = root.left;
        while (root.right != null) root = root.right;
        return root.val;
    }
    public TreeNode deleteNode(TreeNode root, int key){
        if (root == null)
            return null;
        if (key > root.val)
            root.right = deleteNode(root.right, key);
        else if (key < root.val)
            root.left = deleteNode(root.left, key);
        else {//关键处
            if (root.right == null && root.left == null)
                root=null;//找到这个点，没有左右节点，直接删除
            else if (root.right != null){
                root.val = rightMin(root);//这里是很巧妙的地方：如果这个被删除的节点存在右子树，那么从其右子树抓出最大的节点，把值带过来
                root.right = deleteNode(root.right, root.val);//而这个最大的节点必然在右子树的叶子上，所以即使现在存在两个相同的节点
                //也只需要递归下去，通过第一个条件删除即可
            }else {
                root.val = leftMax(root);
                root.left = deleteNode(root.left, root.val);
            }
        }
        return root;
    }

    //探寻第k个最大的元素，就不用二叉搜索树实现了，怪别扭的，明明堆、快排各种算法都可以用哈哈哈哈哈
    //后面有兴趣可以看看二叉搜索树的办法
    /**
     * 关于 Java 的 PriorityQueue 优先级队列
     * 1 是线程不安全的队列
     * 2 存储使用数组实现
     * 3 利用比较器做优先级比较
     * PriorityQueue不是定长数组，超过size时会grow（initialCapability + 1)。
     * */
    private PriorityQueue<Integer> queue;
    private int limit;
    public void KthLargest(int k, int[] nums) {
        limit = k;
        queue = new PriorityQueue<>(k);
        for (int num : nums) {
            add(num);
        }
    }
    //始终在里面就存储k个元素，那么顶上的就是第k大的要求
    public int add(int val) {
        if (queue.size() < limit) {
            queue.add(val);
        } else if (val > queue.peek()) {
            queue.poll();
            queue.add(val);
        }

        return queue.peek();
    }

    //二叉搜索树的公共祖先：不要管什么，只要是二叉树，这个问题的办法就是一样的
    //递归下去
    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q){
        if (root == null || root == p || root == q)
            return root;
        TreeNode left = lowestCommonAncestor1(root.left, p, q);
        TreeNode right = lowestCommonAncestor1(root.right, p, q);

        if (left == null)
            return right;
        if (right == null)
            return left;
        return root;
    }
    //使用二叉搜索树的性质再来一遍
    //递归的方法：
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q){
         if (root.val < p.val && root.val < q.val)
             return lowestCommonAncestor(root.right,p,q);
         if (root.val > p.val && root.val > q.val)
             return lowestCommonAncestor(root.left, p, q);
         return root;
    }
    //迭代，递归的显式版本：关键在于三种情况，要么两个在左或者右，要么就两边，找到两个在两边的时候必然是最小公共祖先
    public TreeNode lowestCommonAncestor_1(TreeNode root, TreeNode p, TreeNode q){
        TreeNode ancetor = root;
        while (true){
            if (ancetor.val < p.val && ancetor.val < q.val)
                ancetor = ancetor.right;
            else if (ancetor.val > p.val && ancetor.val > q.val)
                ancetor = ancetor.left;
            else
                break;
        }
        return ancetor;
    }

    /**
     * 通常，有两种最广泛使用的集合：散列集合（Hash Set）和 树集合（Tree Set）。
     *
     * 树集合，Java 中的 Treeset 或者 C++ 中的 set ，是由高度平衡的二叉搜索树实现的。因此，搜索、插入和删除的时间复杂度都是 O(logN) 。
     *
     * 散列集合，Java 中的 HashSet 或者 C++ 中的 unordered_set ，是由哈希实现的，但是平衡二叉搜索树也起到了至关重要的作用。当存在具有相同哈希键的元素过多时，将花费 O(N) 时间复杂度来查找特定元素，其中N是具有相同哈希键的元素的数量。 通常情况下，使用高度平衡的二叉搜索树将把时间复杂度从 O(N) 改善到 O(logN) 。
     *
     * 哈希集和树集之间的本质区别在于树集中的键是有序的。
     * */

    /**
     * 给定一个二叉树，判断它是否是高度平衡的二叉树：一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1 。
     * 显然基本思路是，找一个变量存储每一层的高度，然后比较左右子树的高度
     * 考虑递归
     * 这道题难点在于怎么把高度差回溯上来，同时还保证原来的高度不被改变
     * */
    //没做出来，对比一下别人的思路看看，怎么把高度回溯上来的
//    boolean isTrue = true;
//    public boolean isBalanced(TreeNode root){
//        if (root == null)
//            return false;
//        int high = 0;
//        high = isBalanced(root, high, isTrue);
//        return isTrue;
//    }
//    private int isBalanced(TreeNode root, int high, boolean isTrue){
//        if (root == null || !isTrue)
//            return high;
//        high = high + 1;
//        int left = isBalanced(root.left, high, isTrue);
//        int right = isBalanced(root.right, high, isTrue);
//        if (Math.abs(left - right) > 1)
//            isTrue = false;
//        return Math.max(left, right);
//    }
    public boolean isBalanced(TreeNode root) {
        return recur(root) != -1;
    }
    //大佬的办法是通过一个特定高度阻断，直接回溯上去
    //我想要采用一个bool变量来进行阻断
    //还有在高度的处理上我用一个外部变量来处理，这个搞麻烦了许多，我模仿其高度的迭代方法，使用额外bool阻断看看
    private int recur(TreeNode root) {
        if (root == null) return 0;
        int left = recur(root.left);
        if(left == -1) return -1;
        int right = recur(root.right);
        if(right == -1) return -1;
        return Math.abs(left - right) < 2 ? Math.max(left, right) + 1 : -1;
    }

    //第二版自己写的：
    //对比得到两个问题与不足：1、全局变量像错误的那种写的话，那么会被作用域屏蔽，所以传递不出来
    //                      2、自底而上的高度的处理方法，应该记住
    boolean isTrue = true;
    public boolean isBalanced1(TreeNode root){
        if (root == null)
            return true;
        int high = 0;
        high = isBalanced_in(root);
        return isTrue;
    }
    private int isBalanced_in(TreeNode root){
        if (root == null || !isTrue)
            return 0;
        int left = isBalanced_in(root.left);
        int right = isBalanced_in(root.right);
        if (Math.abs(left - right) > 1)
            isTrue = false;
        return Math.max(left, right) + 1;
    }

    //从一个升序数组恢复二叉搜索平衡树，注意要点：升序==二叉搜索树的中序遍历
    //平衡：两边节点数目一样，排序方法一样，就不用考虑二叉树的平衡了，也不用考虑上滤下滤了
    public TreeNode sortedArrayToBST(int[] nums){
        return dfs(nums, 0, nums.length-1);
    }
    private TreeNode dfs(int[] nums, int left,  int right){
        if (left > right)
            return null;
        int mid = left + (right-left)/2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = dfs(nums, left, mid-1);
        root.right = dfs(nums, mid+1, right);
        return root;
    }

    public static void main(String[] args) {
        int a = (1-0)/2;
        System.out.println(a);
    }

}
