package algorithm;

/**
 * Created by wajian on 2016/8/28.
 */
public class SortDemo {


}

//快速排序，希尔排序，插入排序，堆排序，归并排序
class Sort {
    public static <AnyType extends Comparable<? super AnyType>> void insertionSort(AnyType[] a) {
        insertionSort(a, 0, a.length - 1);
    }

    private static <AnyType extends Comparable<? super AnyType>> void insertionSort(AnyType[] a, int left, int right) {
        int j;  // 记录第一个比tmp小的元素的后边一位的位置

        for (int p = left; p <= right; p++) {
            AnyType tmp = a[p];
            for (j = p; j > left && tmp.compareTo(a[j - 1]) < 0; j--) {
                a[j] = a[j - 1];
            }
            a[j] = tmp;
        }
    }

    public static <AnyType extends Comparable<? super AnyType>> void shellSort(AnyType[] arr) {
        int j;

        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                AnyType tmp = arr[i];
                for (j = i; j >= gap && tmp.compareTo(arr[j - gap]) < 0; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = tmp;
            }
        }
    }

    private static int leftChild(int i) {
        return i * 2 + 1;
    }

    private static <AnyType extends Comparable<? super AnyType>> void perculateDown(AnyType[] arr, int i, int size) {
        AnyType tmp = arr[i];

        for (int child; (child = leftChild(i)) < size; i = child) {
            if (child != size - 1 && arr[child].compareTo(arr[child + 1]) < 0) {
                child++;
            }
            if (tmp.compareTo(arr[child]) < 0) {
                arr[i] = arr[child];
            } else {
                break;
            }
        }
        arr[i] = tmp;
    }

    public static <AnyType extends Comparable<? super AnyType>> void heapSort(AnyType[] arr) {
        for (int i = arr.length / 2; i >= 0; i--) {
            perculateDown(arr, i, arr.length);
        }
        for (int i = arr.length - 1; i >= 0; i--) {
            swapReferences(arr, 0, i);
            perculateDown(arr, 0, i);
        }
    }

    private static <AnyType extends Comparable<? super AnyType>> void swapReferences(AnyType[] arr, int i, int j) {
        AnyType tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    @SuppressWarnings("unchecked")
	public static <AnyType extends Comparable<? super AnyType>> void mergeSort(AnyType[] arr) {
        AnyType[] tmp = (AnyType[]) new Comparable[arr.length];
        mergeSort(arr, 0, arr.length - 1, tmp);
    }

    private static <AnyType extends Comparable<? super AnyType>> void mergeSort(AnyType[] arr, int start, int end, AnyType[] tmp) {
        if (start < end) {
            int mid = (start + end) >> 1;
            mergeSort(arr, start, mid, tmp);
            mergeSort(arr, mid + 1, end, tmp);
            merge(arr, start, mid, end, tmp);
        }
    }

    private static <AnyType extends Comparable<? super AnyType>> void merge(AnyType[] arr, int start, int mid, int end, AnyType[] tmp) {
        int i = start, j = mid + 1, k = start;
        while (i <= mid && j <= end) {
            if (arr[i].compareTo(arr[j]) < 0) {
                tmp[k++] = arr[i++];
            } else {
                tmp[k++] = arr[j++];
            }
        }

        while (i <= mid) {
            tmp[k++] = arr[i++];
        }

        while (j <= end) {
            tmp[k++] = arr[j++];
        }

        for (int m = start; m <= end; m++) {
            arr[m] = tmp[m];
        }
    }

    public static <AnyType extends Comparable<? super AnyType>> void quickSort(AnyType[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static <AnyType extends Comparable<? super AnyType>> void quickSort(AnyType[] arr, int left, int right) {
        if (left + LENGTH_DIFF <= right) {

            AnyType pivot = medium(arr, left, right);

            int i = left, j = right;
            while (true) {
                while (arr[++i].compareTo(pivot) < 0);
                while (arr[--j].compareTo(pivot) > 0);

                if (i < j) {
                    swapReferences(arr, i, j);
                } else {
                    break;
                }
            }

            swapReferences(arr, i, right);
            quickSort(arr, left, i - 1);
            quickSort(arr, i + 1, right);
        } else {
            insertionSort(arr, left, right);
        }
    }

    private static <AnyType extends Comparable<? super AnyType>> AnyType medium(AnyType[] arr, int left,
                                                                                int right) {
        int center = (left + right) / 2;
        if (arr[center].compareTo(arr[left]) < 0) {
            swapReferences(arr, center, left);
        }
        if (arr[left].compareTo(arr[right]) > 0) {
            swapReferences(arr, left, right);
        }
        if (arr[center].compareTo(arr[right]) < 0) {
            swapReferences(arr, center, right);
        }
        return arr[right];
    }
    private final static int LENGTH_DIFF = 20;
}