package algorithm.interview;

import lombok.Data;

import java.util.Stack;

/**
 * Author: Johnny
 * Date: 2018/5/13
 * Time: 1:16
 */
public class MirrorBinaryTree {

    public static void main(String[] args) {

    }

    /**
     * 翻转二叉树
     */
    private TreeNode invertTreeRecursively(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        invertTreeRecursively(root.left);
        invertTreeRecursively(root.right);
        return root;
    }

    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();
            stack.pop();
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
        }
        return root;
    }

    @Data
    static class TreeNode {
        private TreeNode left;
        private TreeNode right;
    }
}
