package algorithm.search;

/**
 * Created by Johnny on 2017/12/19.
 */
public class HeapSelect {

    public static void main(String[] args) {
        // 假设数组arrs中存储10亿个数字，（这里用100个数代替）
        int[] arrs = new int[100];
        for (int i = 0; i < arrs.length; i++) {
            arrs[i] = (int) (Math.random() * 1000);
        }

        // 先取出10个数字，构建最小堆
        int[] arr = new int[10];
        System.arraycopy(arrs, 0, arr, 0, arr.length);

        HeapSelect heapSelect = new HeapSelect();
        heapSelect.heapSort(arr, arr.length - 1);

        for (int i = 10; i < arrs.length; i++) {
            if (arrs[i] > arr[0]) {
                arr[0] = arrs[i];
                heapSelect.heapSort(arr, arr.length - 1);
            }
        }
    }

    private void heapSort(int[] arrays, int e) {
        if (e > 0) {
            // 初始化堆，找出最大的放在堆顶
            initSort(arrays, e);
            // snp(arrays);
            arrays[0] = arrays[e] + arrays[0];
            arrays[e] = arrays[0] - arrays[e];
            arrays[0] = arrays[0] - arrays[e];
            // snp(arrays);
            heapSort(arrays, e - 1);
        } else {
            // snp(arrays);
        }
    }

    private void initSort(int[] arrays, int e) {
        int m = (e + 1) / 2;
        for (int i = 0; i < m; i++) {
            boolean flag = buildHeap(arrays, e, i);
            // 如果孩子之间有交换，就要重新开始
            if (flag) {
                i = -1;
            }
        }
    }

    /**
     * 返回一个标记，如果有根与孩子交换就要重新从顶根开始查找不满足最大堆树结构
     */
    private boolean buildHeap(int[] arrays, int e, int i) {
        // 左孩子
        int leftChild = 2 * i + 1;
        // 右孩子
        int rightChild = 2 * i + 2;
        // 判断是否有右孩子，没有右孩子的话直接比较左孩子，小于左孩子则交换
        if (rightChild > e) {
            if (arrays[i] < arrays[leftChild]) {
                arrays[i] = arrays[i] + arrays[leftChild];
                arrays[leftChild] = arrays[i] - arrays[leftChild];
                arrays[i] = arrays[i] - arrays[leftChild];
                return true;
            } else {
                return false;
            }
        }
        // 在根与两个孩子之间找出最大的那个值进行交换
        if (arrays[i] < arrays[leftChild]) {
            if (arrays[leftChild] > arrays[rightChild]) {
                // 交换根与左孩子的值
                arrays[i] = arrays[i] + arrays[leftChild];
                arrays[leftChild] = arrays[i] - arrays[leftChild];
                arrays[i] = arrays[i] - arrays[leftChild];
                return true;
            } else {
                // 交换根与右孩子的值
                arrays[i] = arrays[i] + arrays[rightChild];
                arrays[rightChild] = arrays[i] - arrays[rightChild];
                arrays[i] = arrays[i] - arrays[rightChild];
                return true;
            }
        } else if (arrays[i] < arrays[rightChild]) {
            // 交换根与右孩子的值
            arrays[i] = arrays[i] + arrays[rightChild];
            arrays[rightChild] = arrays[i] - arrays[rightChild];
            arrays[i] = arrays[i] - arrays[rightChild];
            return true;
        }
        return false;
    }// buildHeap
}
