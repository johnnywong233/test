package algorithm.search;

import org.junit.Test;

import java.util.TreeSet;

/**
 * Author: Johnny
 * Date: 2017/8/29
 * Time: 18:16
 */
public class NumSearch {

    @Test
    public void test() {
        int arr[] = {9, 7, 5, 4, 2, 1, 3, 6, 8};
        System.out.println(getFirstKElements(arr, 4));
    }

    /**
     * 查找给定的int型数组的最小的k个数
     * 使用TreeSet，考虑到是有序的
     *
     * @param arr the integer array for search
     * @param k   the min k numbers desired
     */
    public static TreeSet<Integer> getFirstKElements(int arr[], int k) {
        TreeSet<Integer> set = new TreeSet<>();
        int len = arr.length;
        k = k % len;
        int i;
        int num;
        for (i = 0; i < k; i++)
            set.add(arr[i]);
        for (i = k; i < len; i++) {
            set.add(arr[i]);
            num = set.last();
            set.remove(num);
        }
        return set;
    }
}
