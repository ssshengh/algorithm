package BinaryTree;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

public class LetsBegin {

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    };


    /**
     * 根据一棵树的中序遍历与后序遍历构造二叉树。
     * 中序遍历：左中右，后序遍历：左右中
     * 可以想到的思路是中序遍历[左 中 右] 后序遍历[左 右 中]，显然，根节点的位置能够对应起来
     * 而最重要的是，二者分别的左右子树又互相对应，比如中序遍历的左子树与后序遍历右子树，排列方式也是左中右和左右中，根节点对应，那递归下去
     * 应该可以找到处理结果。
     * 但是不会实现。
     * 不过递归三部曲可以想到：1、约束为节点为空；2、每一次函数做的，就是分割左右子树；3、每一个子问题就是解决子树分配位置
     *
     * 感谢题解：https://leetcode-cn.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/solution/tu-jie-gou-zao-er-cha-shu-wei-wan-dai-xu-by-user72/
     * 参考后，解决方案关键在于引入一个map，将一个数组的所有元素以及索引存入（利用了元素不能重复）
     * */
    HashMap<Integer, Integer> inorderArrayMap = new HashMap<>(); //用以中序遍历中的元素
    int[] post; //用以放置后序遍历，便于后面调用
    public Node buildTree(int [] inorder, int [] postorder){
        for (int i = 0; i<inorder.length; i++){
            inorderArrayMap.put(inorder[i], i);//键为树中的值，值为所处的序号
        }
        post = postorder;
        return buildTree(0, inorder.length-1, 0, postorder.length-1);
    }
    private Node buildTree(int leftInorder, int rightInorder, int leftPostorder, int rightPostorder){
        //注意这里转换为了当起点大于终点的时候，作为约束条件。另外注意这个return null,思考的时候是在某一个子问题会返回null
        if (leftInorder > rightInorder || leftPostorder > rightPostorder) return null;

        int root = post[rightPostorder];//从每一棵子树的后序遍历中获得根节点
        int rootIndexInInorderArray = inorderArrayMap.get(root); //获取根节点的值
        Node node = new Node(root);
        node.left = buildTree(leftInorder, rootIndexInInorderArray-1,
                leftPostorder, leftPostorder+rootIndexInInorderArray-leftInorder-1);
        node.right = buildTree(rootIndexInInorderArray+1, rightInorder,
                leftPostorder+rootIndexInInorderArray-leftInorder, rightPostorder-1);
        return node;
    }

    /**
     * 前序+中序，一样的思路
     * */
    public Node buildTree1(int[] preorder, int[] inorder){
        for (int i=0; i<inorder.length; i++)
            inorderArrayMap.put(inorder[i], i);
        post = preorder;
        return buildTree1(0, preorder.length-1, 0, inorder.length-1);
    }
    private Node buildTree1(int ps, int pe, int is, int ie){
        if (ps>pe || is>ie)
            return null;

        int root = post[ps];
        int Index = inorderArrayMap.get(root);
        Node node = new Node(root);

        node.left = buildTree1(ps+1, ps+Index-is, is, Index-1);
        node.right = buildTree1(pe+1-ie+Index, pe, Index+1, ie);
        return node;
    }

    /**
     * 填充每个节点的右侧节点指针
     * 递归解决：不会！！！
     * 采用一个队列来存储，使用迭代解决，但是要求使用常量的辅助空间，这种方法不够好
     * 使用了队列，辅助空间就变大了
     * */
    /**
     * while(queue.size()>0) {
     * 			int size = queue.size();
     * 			//将队列中的元素串联起来
     * 			Node tmp = queue.get(0);
     * 			for(int i=1;i<size;++i) {
     * 				tmp.next = queue.get(i);
     * 				tmp = queue.get(i);
     *                        }
     * 			//遍历队列中的每个元素，将每个元素的左右节点也放入队列中
     * 			for(int i=0;i<size;++i) {
     * 				tmp = queue.remove();
     * 				if(tmp.left!=null) {
     * 					queue.add(tmp.left);
     *                }
     * 				if(tmp.right!=null) {
     * 					queue.add(tmp.right);
     *                }
     *            }* 		}
     *自己的可以改进的，本来可以不用再用一个函数的，关键是没想到可以在里面对队列进行处理
     * 把每一层的存储进去再处理
     * */
    public Node connect(Node root){
        if (root == null)
            return null;
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(root);
        Node item = root;
        while (!queue.isEmpty()){
            item = queue.remove();
            if (item.next == null)
                item.next = queue.peek();
            if (item.left != null)
                queue.add(item.left);
            if (item.right != null)
                queue.add(item.right);
        }
        root = getNull(root);//因为next全部被初始化为null，所以全部指好，再把右边置null
        return root;
   }
    private Node getNull(Node root){
        if (root == null)
            return null;
        root.next = null;
        getNull(root.right);
        return root;
    }//时间消耗与空间消耗都比较大

    //优化递归的思路：
    //其实可以不需要队列，每一行都可以看成一个链表比如第一行就是只有一个节点的链表，第二行是只有两个节点的链表（假如根节点的左右两个子节点都不为空）
    //因为第二行使用next链接起来了，所以第三行的链表构造就可以通过第二行左节点的next访问到另一个数据！！！！！
    public Node connect1(Node root){
        if (root == null)
            return null;
        Node pre = root;
        while (pre.left!=null){
              Node temp = pre;
              while (temp != null)
              {
                  temp.left.next = temp.right;
                  //这一步极为关键，俺想破脑袋没想到的一个问题是：如何只用temp完成对链表的子节点的遍历
                  //但是没有意识到，在上一层已经被串号的前提下，访问上层链表就只需要一个变量不断右移即可
                  //那么访问其子节点也就只需要一个temp和一个temp.next即可完成
                  if (temp.next != null)
                      temp.right.next = temp.next.left;
                  temp = temp.next;
              }
              pre = pre.left;
        }
        return root;
    }//内存与时间都几乎完美
    //时间O(N)，内存O(1)

    //尝试第三种方法：递归的思路，拉拉链！
    //终止条件：当前节点为空时
    //函数内：以当前节点为起始，完成从上往下的纵深串联，再递归的调用当前节点left和right
    private void dfs(Node root){
        if (root == null)
            return;
        Node left = root.left;
        Node right = root.right;
        while (left != null){
            left.next = right;
            left = left.right;
            right = right.left;
        }
        dfs(root.left);
        dfs(root.right);
    }
    public Node connect_dfs(Node root){
        dfs(root);
        return root;
    }//递归默认调用了栈，所以内存不是很好，是O(N)的

    //非完全二叉树，需要考虑的两个问题：一是最开始可能没有左节点，二是中间节点可能为空
    //所以需要另一个变量来暂存

    Node pre = null;
    //Node first = new Node(0);
    public Node connect2(Node root){
        if (root == null)
            return null;
        Node tmp = root;
        while (tmp != null){
            //之前犯了错误，把这个放在外面了，会报错，因为first.next储存着下一个左开始节点的信息
            //但是不清楚，把pre.next变成非空后，为什么会导致卡死？？？？？？？？？？？？？？？？？
            Node first = new Node(0);
            pre = first;//把pre置回一个空指针，但为了好用!=这个条件，所以随便给他置一个地址
            //解决一层的链接问题
            while (tmp != null)
            {
                if (tmp.left != null) {
                    pre.next = tmp.left;
                    //注意这一句！！！！！！把first链接到了最左边的节点！！！！！！！！这里就是第三个变量的用法！
                    pre = pre.next;//把下一个点的所有信息块赋值给pre指针
                }
                if (tmp.right != null) {
                    pre.next = tmp.right;
                    pre = pre.next;
                }
                tmp = tmp.next;
            }
            tmp = first.next;
        }
        return root;
    }

}
