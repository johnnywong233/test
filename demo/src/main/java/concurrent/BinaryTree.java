package concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class BinaryTree {
    public static class Node {
        Node leftChild;
        Node rightChild;
        public String content;

        public Node(String ct) {
            content = ct;
        }
    }

    public static class NodeCopyTask extends RecursiveTask<Node> {
        private static final long serialVersionUID = 8974245402260284582L;
        Node mNode;

        NodeCopyTask(Node node) {
            mNode = node;
        }

        @Override
        protected Node compute() {
            if (mNode == null) {
                return null;
            }

            NodeCopyTask taskLeft = new NodeCopyTask(mNode.leftChild);
            taskLeft.fork();
            NodeCopyTask taskRight = new NodeCopyTask(mNode.rightChild);
            taskRight.fork();

            Node node = new Node(mNode.content);
            node.leftChild = taskLeft.join();
            node.rightChild = taskRight.join();
            return node;
        }
    }

    // http://m.blog.csdn.net/article/details?id=50673797
    public static void main(String[] args) {
        Node node = new Node("Hello, Fork-Join");
        node.leftChild = new Node("Left");
        node.rightChild = new Node("Right");

        Node parent = new Node("parent node");
        parent.leftChild = node;

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        NodeCopyTask task = new NodeCopyTask(node);
        Future<Node> future = forkJoinPool.submit(task);
        try {
            Node nodeNew = future.get();
            System.out.println("nodeNew: " + nodeNew.content); //add
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println(node.content);//add
    }

}
