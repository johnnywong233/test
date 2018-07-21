package algorithm.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wajian on 2016/10/5.
 * compare all kinds of sort algorithm
 */
public class SortCompare {
    //http://www.codeceo.com/article/10-sort-algorithm-interview.html
    public static void main(String[] args) {
        SortCompare demo = new SortCompare();
        int[] a = {49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 56, 17, 18, 23, 34, 15, 35, 25, 53, 51};
        System.out.println("insert sort: " + Arrays.toString(demo.insertSort(a)));

        demo.shellSort(a);
        demo.selectSort(a);
        demo.bubbleSort(a);
        demo.heapSort(a);
        demo.radixSort(a);
        demo.bucketSort(a);
        demo.quickSort(a);
        demo.countSort(a);
        demo.mergeSort(a);
    }

    //计数排序算法实现
    private int[] countSort(int[] list) {
        if (list == null || list.length == 0) {
            return null;
        }

        int max = max(list);

        int[] count = new int[max + 1];
        Arrays.fill(count, 0);

        for (int aList : list) {
            count[aList]++;
        }

        int k = 0;
        for (int i = 0; i <= max; i++) {
            for (int j = 0; j < count[i]; j++) {
                list[k++] = i;
            }
        }
        return list;
    }

    public static int max(int[] list) {
        int max = Integer.MIN_VALUE;
        for (int ele : list) {
            if (ele > max) {
                max = ele;
            }
        }
        return max;
    }

    private int[] mergeSort(int[] list) {
        return mSort(list, 0, list.length - 1);
    }

    /**
     * 递归分治
     *
     * @param list  待排数组
     * @param left  左指针
     * @param right 右指针
     */
    private static int[] mSort(int[] list, int left, int right) {
        if (left >= right) {
            return null;
        }
        int mid = (left + right) / 2;
        //递归排序左边
        mSort(list, left, mid);
        //递归排序右边
        mSort(list, mid + 1, right);
        //合并
        merge(list, left, mid, right);
        return list;
    }

    /**
     * 合并两个有序数组
     *
     * @param arr   待合并数组
     * @param left  左指针
     * @param mid   中间指针
     * @param right 右指针
     */
    private static void merge(int[] arr, int left, int mid, int right) {
        //[left, mid] [mid+1, right]
        //中间数组
        int[] temp = new int[right - left + 1];

        int i = left;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        while (j <= right) {
            temp[k++] = arr[j++];
        }

        System.arraycopy(temp, 0, arr, left, temp.length);

    }

    /**
     * 快速排序
     *
     * @param list  to be sorted
     * @param left  left part
     * @param right right part
     */
    private static int[] qSort(int[] list, int left, int right) {
        if (left >= right) {
            return null;
        }
        int pivotPos = partition(list, left, right);
        qSort(list, left, pivotPos - 1);
        qSort(list, pivotPos + 1, right);
        return list;
    }

    /**
     * 划分
     */
    private static int partition(int[] list, int left, int right) {
        int pivotKey = list[left];

        while (left < right) {
            while (left < right && list[right] >= pivotKey) {
                right--;
            }
            list[left] = list[right]; //把小的移动到左边
            while (left < right && list[left] <= pivotKey) {
                left++;
            }
            list[right] = list[left]; //把大的移动到右边
        }
        list[left] = pivotKey; //最后把pivot赋值到中间
        return left;
    }

    public int[] quickSort(int[] list) {
        if (list == null || list.length == 0) {
            return null;
        }
        qSort(list, 0, list.length - 1);
        return list;
    }

    public int[] insertSort(int[] list) {
        int temp;
        for (int i = 1; i < list.length; i++) {
            int j = i - 1;
            temp = list[i];
            for (; j >= 0 && temp < list[j]; j--) {
                list[j + 1] = list[j];//将大于temp的值整体后移一个单位
            }
            list[j + 1] = temp;
        }
        for (int anA : list) {
            System.out.println(anA);
        }
        return list;
    }

    public int[] shellSort(int[] list) {
        double d1 = list.length;
        int temp;
        while (true) {
            d1 = Math.ceil(d1 / 2);
            int d = (int) d1;
            for (int x = 0; x < d; x++) {
                for (int i = x + d; i < list.length; i += d) {
                    int j = i - d;
                    temp = list[i];
                    for (; j >= 0 && temp < list[j]; j -= d) {
                        list[j + d] = list[j];
                    }
                    list[j + d] = temp;
                }
            }
            if (d == 1) {
                break;
            }
        }
        for (int aList : list) {
            System.out.println(aList);
        }
        return list;
    }

    public int[] selectSort(int[] list) {
        int position;
        for (int i = 0; i < list.length; i++) {
            int j = i + 1;
            position = i;
            int temp = list[i];
            for (; j < list.length; j++) {
                if (list[j] < temp) {
                    temp = list[j];
                    position = j;
                }
            }
            list[position] = list[i];
            list[i] = temp;
        }
        for (int aList : list) {
            System.out.println(aList);
        }
        return list;
    }

    public int[] heapSort(int[] list) {
        int arrayLength = list.length;
        //循环建堆
        for (int i = 0; i < arrayLength - 1; i++) {
            //建堆
            buildMaxHeap(list, arrayLength - 1 - i);
            //交换堆顶和最后一个元素
            swap(list, 0, arrayLength - 1 - i);
            System.out.println(Arrays.toString(list));
        }
        return list;
    }

    private void swap(int[] data, int i, int j) {
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }

    //对data数组从0到lastIndex建大顶堆
    private void buildMaxHeap(int[] data, int lastIndex) {
        //从lastIndex处节点（最后一个节点）的父节点开始
        for (int i = (lastIndex - 1) / 2; i >= 0; i--) {
            //k保存正在判断的节点
            int k = i;
            //如果当前k节点的子节点存在
            while (k * 2 + 1 <= lastIndex) {
                //k节点的左子节点的索引
                int biggerIndex = 2 * k + 1;
                //如果biggerIndex小于lastIndex，即biggerIndex+1代表的k节点的右子节点存在
                if (biggerIndex < lastIndex) {
                    //若果右子节点的值较大
                    if (data[biggerIndex] < data[biggerIndex + 1]) {
                        //biggerIndex总是记录较大子节点的索引
                        biggerIndex++;
                    }
                }
                //如果k节点的值小于其较大的子节点的值
                if (data[k] < data[biggerIndex]) {
                    //交换他们
                    swap(data, k, biggerIndex);
                    //将biggerIndex赋予k，开始while循环的下一次循环，重新保证k节点的值大于其左右子节点的值
                    k = biggerIndex;
                } else {
                    break;
                }
            }
        }
    }

    public int[] bubbleSort(int[] list) {
        int temp;
        for (int i = 0; i < list.length - 1; i++) {
            for (int j = 0; j < list.length - 1 - i; j++) {
                if (list[j] > list[j + 1]) {
                    temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                }
            }
        }
        for (int a : list) {
            System.out.println(a);
        }
        return list;
    }


    /**
     * @Description:<p>基数排序算法实现</p>
     */
    public int[] radixSort(int[] list) {

        if (list == null && list.length == 0) {
            return null;
        }

        int maxBit = getMaxBit(list);

        for (int i = 1; i <= maxBit; i++) {
            List<List<Integer>> buf = distribute(list, i); //分配
            collect(list, buf); //收集
        }
        return list;
    }

    /**
     * 分配
     *
     * @param arr  待分配数组
     * @param iBit 要分配第几位
     */
    private static List<List<Integer>> distribute(int[] arr, int iBit) {
        List<List<Integer>> buf = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            buf.add(new LinkedList<>());
        }
        for (int anArr : arr) {
            buf.get(getNBit(anArr, iBit)).add(anArr);
        }
        return buf;
    }

    /**
     * 收集
     *
     * @param arr 把分配的数据收集到arr中
     */
    private static int[] collect(int[] arr, List<List<Integer>> buf) {
        int k = 0;
        for (List<Integer> bucket : buf) {
            for (int ele : bucket) {
                arr[k++] = ele;
            }
        }
        return arr;
    }

    /**
     * 获取最大位数
     *
     */
    private static int getMaxBit(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int ele : arr) {
            int len = (ele + "").length();
            if (len > max) {
                max = len;
            }
        }
        return max;
    }

    /**
     * 获取x的第n位，如果没有则为0.
     *
     */
    private static int getNBit(int x, int n) {
        String sx = x + "";
        if (sx.length() < n) {
            return 0;
        } else {
            return sx.charAt(sx.length() - n) - '0';
        }
    }


    public int[] bucketSort(int[] list) {
        if (list == null && list.length == 0) {
            return null;
        }

        int bucketNums = 10; //这里默认为10，规定待排数[0,100)
        List<List<Integer>> buckets = new ArrayList<>(); //桶的索引

        for (int i = 0; i < 10; i++) {
            buckets.add(new LinkedList<>()); //用链表比较合适
        }

        //划分桶
        for (int a : list) {
            buckets.get(mapping(a)).add(a);
        }

        //对每个桶进行排序
        for (int i = 0; i < buckets.size(); i++) {
            if (!buckets.get(i).isEmpty()) {
                Collections.sort(buckets.get(i)); //对每个桶进行快排
            }
        }

        //还原排好序的数组
        int k = 0;
        for (List<Integer> bucket : buckets) {
            for (int ele : bucket) {
                list[k++] = ele;
            }
        }
        return list;
    }

    /**
     * 映射函数
     */
    private static int mapping(int x) {
        return x / 10;
    }
}