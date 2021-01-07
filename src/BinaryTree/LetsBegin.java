package BinaryTree;

import java.util.HashMap;

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
}
