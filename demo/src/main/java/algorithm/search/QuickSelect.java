package algorithm.search;

/**
 * Created by Johnny on 2017/12/19.
 */
public class QuickSelect {

    public static void main(String[] args) {
        int[] array = new int[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 1000);
        }

        quickSort(array, 0, array.length - 1);

        for (int i = array.length - 10; i < array.length; i++) {// 数组后10位数既是最大的10位数
            System.out.print(array[i] + " ");
        }
    }

    private static int len = 10;

    /**
     * 多次调用快排，将数组中最大的10位数移动至最右边。
     */
    private static void quickSort(int[] array, int left, int right) {
        int pivot;
        if (left < right) {
            pivot = partition(array, left, right);//取出枢轴
            if (right - pivot + 1 > len) {//如果pivot右边的数大于10个，再次重复操作（1）
                quickSort(array, pivot + 1, right);
            } else if (right - pivot + 1 < len) {//pivot右边的数小于10个，执行操作（2）
                len = len - (right - pivot + 1);
                quickSort(array, left, pivot - 1);
            }
            // 对左右数组递归调用快速排序，直到顺序完全正确
        }
    }

    /**
     * pivotValue作为枢轴，较之小的元素排序后在其左，较之大的元素排序后在其右
     */
    private static int partition(int[] array, int left, int right) {
        int pivot = array[left];
        while (left < right) {
            while (left < right && array[right] >= pivot) {
                --right;
            }
            // 将比枢轴小的元素移到低端，此时right位相当于空，等待低位比pivot大的数补上
            array[left] = array[right];
            while (left < right && array[left] <= pivot) {
                ++left;
            }
            // 将比枢轴大的元素移到高端，此时left位相当于空，等待高位比pivot小的数补上
            array[right] = array[left];
        }
        // 当left == right，完成一趟快速排序，此时left位相当于空，等待pivotkey补上
        array[left] = pivot;
        return left;
    }
}
