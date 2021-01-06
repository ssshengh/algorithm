package BinaryTree;

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

}
