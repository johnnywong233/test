package algorithm.tree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Stack;

/**
 * Created by johnny on 2016/8/30.
 * 二叉树遍历
 */
/*遍历是对树的一种最基本的运算，所谓遍历二叉树，就是按一定的规则和顺序走遍二叉树的所有结点，使每一个结点都被访问一次，
  而且只被访问一次。由于二叉树是非线性结构，因此，树的遍历实质上是将二叉树的各个结点转换成为一个线性序列来表示。
　　设L、D、R分别表示遍历左子树、访问根结点和遍历右子树， 则对一棵二叉树的遍历有三种情况：DLR（称为先根次序遍历），
    LDR（称为中根次序遍历），LRD （称为后根次序遍历）。
　　(1)先序遍历
　　访问根；按先序遍历左子树；按先序遍历右子树
　　(2)中序遍历
　　按中序遍历左子树；访问根；按中序遍历右子树
　　(3)后序遍历
　　按后序遍历左子树；按后序遍历右子树；访问根
　　（4）层次遍历
　　即按照层次访问，通常用队列来做。访问根，访问子女，再访问子女的子女（越往后的层次越低）（两个子女的级别相同）
*/
@Getter
@AllArgsConstructor
public class BinTree {
    protected TreeNode root;

    /**
     * 构造树
     */
    public static TreeNode init() {
        TreeNode a = new TreeNode('A');
        TreeNode b = new TreeNode('B', null, a);
        TreeNode c = new TreeNode('C');
        TreeNode d = new TreeNode('D', b, c);
        TreeNode e = new TreeNode('E');
        TreeNode f = new TreeNode('F', e, null);
        TreeNode g = new TreeNode('G', null, f);
        return new TreeNode('H', d, g);// root
    }

    /**
     * 访问节点
     */
    private static void visit(TreeNode p) {
        System.out.print(p.getVal() + " ");
    }

    /**
     * 递归实现前序遍历
     */
    private static void preOrder(TreeNode p) {
        if (p != null) {
            visit(p);
            preOrder(p.getLeft());
            preOrder(p.getRight());
        }
    }

    /**
     * 递归实现中序遍历
     */
    private static void inorder(TreeNode p) {
        if (p != null) {
            inorder(p.getLeft());
            visit(p);
            inorder(p.getRight());
        }
    }

    /**
     * 递归实现后序遍历
     */
    private static void postorder(TreeNode p) {
        if (p != null) {
            postorder(p.getLeft());
            postorder(p.getRight());
            visit(p);
        }
    }

    /**
     * 非递归实现前序遍历
     */
    private static void iterativePreorder(TreeNode p) {
        Stack<TreeNode> stack = new Stack<>();
        if (p != null) {
            stack.push(p);
            while (!stack.empty()) {
                p = stack.pop();
                visit(p);
                if (p.getRight() != null) {
                    stack.push(p.getRight());
                }
                if (p.getLeft() != null) {
                    stack.push(p.getLeft());
                }
            }
        }
    }

    /**
     * 非递归实现后序遍历
     */
    private static void iterativePostorder(TreeNode p) {
        TreeNode q = p;
        Stack<TreeNode> stack = new Stack<>();
        while (p != null) {
            // 左子树入栈
            for (; p.getLeft() != null; p = p.getLeft()) {
                stack.push(p);
            }
            // 当前节点无右子或右子已经输出
            while (p != null && (p.getRight() == null || p.getRight() == q)) {
                visit(p);
                q = p;// 记录上一个已输出节点
                if (stack.empty()) {
                    return;
                }
                p = stack.pop();
            }
            // 处理右子
            stack.push(p);
            assert p != null;
            p = p.getRight();
        }
    }

    /**
     * 非递归实现中序遍历
     */
    private static void iterativeInorder(TreeNode p) {
        Stack<TreeNode> stack = new Stack<>();
        while (p != null) {
            while (p != null) {
                if (p.getRight() != null) {
                    stack.push(p.getRight());// 当前节点右子入栈
                }
                stack.push(p);// 当前节点入栈
                p = p.getLeft();
            }
            p = stack.pop();
            while (!stack.empty() && p.getRight() == null) {
                visit(p);
                p = stack.pop();
            }
            visit(p);
            if (!stack.empty()) {
                p = stack.pop();
            } else {
                p = null;
            }
        }
    }

    /**
     * 判断二叉树B是不是二叉树A的子结构
     */
    public static boolean isSubStructure(TreeNode A, TreeNode B) {
        if (A == null || B == null) {
            return false;
        }
        // 判断B是否是A的子结构
        return check(A, B) || isSubStructure(A.getLeft(), B) || isSubStructure(A.getRight(), B);
    }

    private static boolean check(TreeNode A, TreeNode B) {
        // 如果B为空，说明B已完全匹配
        if (B == null) {
            return true;
        }
        // 如果A为空或A的值不等于B的值，说明不匹配
        if (A == null || A.getVal() != B.getVal()) {
            return false;
        }
        // 继续判断左子树和右子树是否匹配
        return check(A.getLeft(), B.getLeft()) && check(A.getRight(), B.getRight());
    }

    //http://www.phpxs.com/code/1001693/
    public static void main(String[] args) {
        BinTree tree = new BinTree(init());
        System.out.print(" Pre-Order:");
        preOrder(tree.getRoot());
        System.out.println();
        System.out.print("　In-Order:");
        inorder(tree.getRoot());
        System.out.println();
        System.out.print("Post-Order:");
        postorder(tree.getRoot());
        System.out.println();
        System.out.print(" Pre-Order:");
        iterativePreorder(tree.getRoot());
        System.out.println();
        System.out.print("　In-Order:");
        iterativeInorder(tree.getRoot());
        System.out.println();
        System.out.print("Post-Order:");
        iterativePostorder(tree.getRoot());
        System.out.println();
    }

    /**
     * 翻转二叉树，借助于栈来实现
     */
    public static TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();
            stack.pop();
            if (node.getLeft() != null) {
                stack.push(node.getLeft());
            }
            if (node.getRight() != null) {
                stack.push(node.getRight());
            }
            TreeNode temp = node.getLeft();
            node.setLeft(node.getRight());
            node.setRight(temp);
        }
        return root;
    }

    /**
     * 翻转二叉树，递归方式实现
     */
    public static TreeNode invertTreeRecursively(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode temp = root.getLeft();
        root.setLeft(root.getRight());
        root.setRight(temp);
        invertTreeRecursively(root.getLeft());
        invertTreeRecursively(root.getRight());
        return root;
    }

    /*二叉树节点*/
    @Data
    @AllArgsConstructor
    private static class TreeNode {
        private int val;
        private TreeNode left, right;

        public TreeNode(char val) {
            this(val, null, null);
        }
    }
}