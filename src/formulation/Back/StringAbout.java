package formulation.Back;

import java.util.ArrayList;
import java.util.List;

public class StringAbout {
    /**
     * 括号生成,字符串类型的回溯问题
     */
    public List<String> generateParenthesis(int n){
        List<String> list = new ArrayList<>();
        if (n==0) return list;
        helper(list, new StringBuilder(), 0, n*2, 0);
        return list;
    }

    /**
     * 帮助函数，需要干的事是，递归的考虑所有的方案，然后添加括号
     * @param list：放置String的列表
     * @param sb：每一次遍历到底的时候生成的括号数组
     * @param sum：计数器，进入一个左括号++，一个右括号--，不能小于0，在等于0的时候，返回
     * @param double_n：需要的括号总数
     * @param count：现在进入的括号数
     */
    private void helper(List<String> list, StringBuilder sb, int sum, int double_n, int count){
        if (count==double_n) {
            if (sum==0){
                list.add(new String(sb));
            }
            return;
        }
        //后面添加的剪枝，时间总算完美了
        //只要剩下的数量不足以放置足够的右括号来平衡左括号就可以停了
        if (sum>double_n-count)
            return;

        //选择下一个是左括号的时候,是无条件的
        helper(list, sb.append("("), sum+1, double_n, count+1);
        sb.deleteCharAt(sb.length() - 1);

        if (sum-1>=0){
            //右括号的情况需要防止sum小于0
            helper(list, sb.append(")"), sum-1, double_n, count+1);
            sb.deleteCharAt(sb.length() - 1);
        }

    }

}
