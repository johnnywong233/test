package concurrent;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * https://www.ibm.com/developerworks/cn/java/j-lo-forkjoin/
 */
public class ForkJoinSimple {
    private static final int NARRAY = 16;
    private final long[] array = new long[NARRAY];
    private final Random rand = new Random();

    @Before
    public void setUp() {
        for (int i = 0; i < array.length; i++) {
            array[i] = rand.nextLong() % 100;
        }
        System.out.println("Initial Array: " + Arrays.toString(array));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    public void testSort() throws Exception {
        ForkJoinTask sort = new SortTask(array);
        ForkJoinPool pool = new ForkJoinPool();
        pool.submit(sort);
        pool.shutdown();

        pool.awaitTermination(30, TimeUnit.SECONDS);

        assertTrue(checkSorted(array));
    }

    private boolean checkSorted(long[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > (a[i + 1])) {
                return false;
            }
        }
        return true;
    }
}

@SuppressWarnings("serial")
class SortTask extends RecursiveAction {
    private final long[] array;
    private final int lo;
    private final int hi;

    public SortTask(long[] array) {
        this.array = array;
        this.lo = 0;
        this.hi = array.length - 1;
    }

    public SortTask(long[] array, int lo, int hi) {
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }

    @Override
    protected void compute() {
        int threshold = 0;
        if (hi - lo < threshold) {
            sequentiallySort(array, lo, hi);
        } else {
            int pivot = partition(array, lo, hi);
            System.out.println("\npivot = " + pivot + ", low = " + lo + ", high = " + hi);
            System.out.println("array" + Arrays.toString(array));

            //RecursiveAction �����沢û��coInvoke����
//            coInvoke(new SortTask(array, lo, pivot - 1), new SortTask(array, pivot + 1, hi));
        }
    }

    private int partition(long[] array, int lo, int hi) {
        long x = array[hi];
        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            if (array[j] <= x) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, hi);
        return i + 1;
    }

    private void swap(long[] array, int i, int j) {
        if (i != j) {
            long temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    private void sequentiallySort(long[] array, int lo, int hi) {
        Arrays.sort(array, lo, hi + 1);
    }
}
