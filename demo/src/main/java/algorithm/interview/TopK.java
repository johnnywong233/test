package algorithm.interview;

import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2018/5/23
 * Time: 22:16
 */
public class TopK {
    //一堆数的个数
    private static final Integer M = 5;
    //有多少堆
    private static final Integer N = 5;


    public static void main(String[] args) {
        int[] a = {2, 20, 3, 7, 9, 1, 17, 18, 0, 4, 34, 9, 23, 98, 23, 29,
                1111, 54, 333, 76, 98, 72, 12, 26, 99};
        int k = 6;
        //guava API Ints.asList 的使用
        //方法一：归并排序，不推荐，麻烦
        System.out.println(generateData(Ints.asList(a)));
        for (int i = 0; i < N; i++) {
            //guava API
            quickSort(Ints.toArray(generateData(Ints.asList(a)).get(i)));
        }
//        System.out.println(Arrays.toString(merge(quickSort(Ints.toArray(generateData(Ints.asList(a)).get(i))), k)));

        //方法二：快速排序
        new TopK().getTopKMinBySort(a, 0, a.length - 1, k);
        for (int i = 0; i < k; i++) {
            System.out.print(a[i] + " ");
        }

        System.out.println("\n------");
        //方法二：求最大值使用最小堆
        int[] result = new TopK().getTopKByHeap(a, k);
        for (int temp : result) {
            System.out.print(temp + " ");
        }
    }

    public static void quickSort(int[] list) {
        if (list != null && list.length == 0) {
            qSort(list, 0, list.length - 1);
        }
    }

    /**
     * 快速排序
     *
     * @param list  to be sorted
     * @param left  left part
     * @param right right part
     */
    private static void qSort(int[] list, int left, int right) {
        if (left >= right){
            return;
        }
        int pivotPos = partition(list, left, right);
        qSort(list, left, pivotPos - 1);
        qSort(list, pivotPos + 1, right);
    }

    private static int partition(int[] a, int first, int end) {
        int i = first;
        int main = a[end];
        for (int j = first; j < end; j++) {
            if (a[j] < main) {
                int temp = a[j];
                a[j] = a[i];
                a[i] = temp;
                i++;
            }
        }
        a[end] = a[i];
        a[i] = main;
        return i;
    }

    //快速排序的过程来求Top k.平均时间复杂度为(O(n)).适用于无序单个数组
    private void getTopKMinBySort(int[] a, int first, int end, int k) {
        if (first < end) {
            int partitionIndex = partition(a, first, end);
            if (partitionIndex == k - 1) {
                return;
            } else if (partitionIndex > k - 1) {
                getTopKMinBySort(a, first, partitionIndex - 1, k);
            } else {
                getTopKMinBySort(a, partitionIndex + 1, end, k);
            }
        }
    }

    /**
     * 创建k个节点的小根堆
     */
    private int[] createHeap(int[] a, int k) {
        int[] result = new int[k];
        System.arraycopy(a, 0, result, 0, k);
        for (int i = 1; i < k; i++) {
            int child = i;
            int parent = (i - 1) / 2;
            int temp = a[i];
            while (parent >= 0 && child != 0 && result[parent] > temp) {
                result[child] = result[parent];
                child = parent;
                parent = (parent - 1) / 2;
            }
            result[child] = temp;
        }
        return result;
    }

    private void insert(int[] a, int value) {
        a[0] = value;
        int parent = 0;

        while (parent < a.length) {
            int lChild = 2 * parent + 1;
            int rChild = 2 * parent + 2;
            int minIndex = parent;
            if (lChild < a.length && a[parent] > a[lChild]) {
                minIndex = lChild;
            }
            if (rChild < a.length && a[minIndex] > a[rChild]) {
                minIndex = rChild;
            }
            if (minIndex == parent) {
                break;
            } else {
                int temp = a[parent];
                a[parent] = a[minIndex];
                a[minIndex] = temp;
                parent = minIndex;
            }
        }
    }

    /**
     * 采用小根堆或者大根堆;
     * 求最大K个采用小根堆，而求最小K个采用大根堆。
     * 适用于数据量较大（特别是大到内存不可以容纳）时，偏向于采用堆
     * 求最大K个的步奏：
     * <p>
     * 根据数据前K个建立K个节点的小根堆。在后面的N-K的数据的扫描中，
     * 如果数据大于小根堆的根节点，则根节点的值覆为该数据，并调节节点至小根堆。
     * 如果数据小于或等于小根堆的根节点，小根堆无变化。
     * 时间复杂度O(nlogK)(n:数据的长度)。
     */
    private int[] getTopKByHeap(int[] input, int k) {
        int[] heap = this.createHeap(input, k);
        for (int i = k; i < input.length; i++) {
            if (input[i] > heap[0]) {
                this.insert(heap, input[i]);
            }
        }
        return heap;
    }

    /**
     * 已知几个递减有序的m个数组，求这几个数据前k大的数
     * 适合采用Merge的方法,时间复杂度(O(k*m);
     */
    public static int[] merge(List<List<Integer>> input, int k) {
        int[] index = new int[input.size()];//保存每个数组下标扫描的位置;
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            int max = Integer.MIN_VALUE;
            int maxIndex = 0;
            for (int j = 0; j < input.size(); j++) {
                if (index[j] < input.get(j).size()) {
                    if (max < input.get(j).get(index[j])) {
                        max = input.get(j).get(index[j]);
                        maxIndex = j;
                    }
                }
            }
            if (max == Integer.MIN_VALUE) {
                return result;
            }
            result[i] = max;
            index[maxIndex] += 1;

        }
        return result;
    }

    private static <T> List<List<T>> generateData(List<T> data) {
        List<List<T>> result = new ArrayList<>();
        int remainder = data.size() % N; //余数
        int number = data.size() / N; //商
        int offset = 0;//偏移量
        for (int i = 0; i < N; i++) {
            List<T> value;
            if (remainder > 0) {
                value = data.subList(i * number + offset, (i + 1) * number + offset + 1);
                remainder--;
                offset++;
            } else {
                value = data.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }

}

