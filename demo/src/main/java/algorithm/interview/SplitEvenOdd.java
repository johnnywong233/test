package algorithm.interview;

import java.util.Arrays;

/**
 * Author: Johnny
 * Date: 2018/5/14
 * Time: 21:11
 */
public class SplitEvenOdd {
    public static void main(String[] args) {
        int[] arrays = {1, 10, 4, 88, 5, 6, 71, 8, 9, 2};
        System.out.println(Arrays.toString(splitOddAndEven(arrays)));
    }

    public static int[] splitOddAndEven(int[] inputArray) {
        int count = 0;
        int lastIndex = inputArray.length - 1;

        for (int firstIndex = 0; firstIndex < lastIndex; firstIndex++) {
            count++;
            System.out.println(" firstIndex: " + firstIndex + " - lastIndex: " + lastIndex);

            int last = inputArray[lastIndex];
            int first = inputArray[firstIndex];
            // 情况1：如果前面是偶数，后面是奇数，则交换位置
            if (first % 2 == 0 && last % 2 != 0) {
                inputArray[lastIndex] = first;
                inputArray[firstIndex] = last;
                lastIndex--;// 此处注意后面的下标位置往前移，而前面的下标位置会在for循环中first_index++往后移动
            } else if (first % 2 == 0) {
                // 情况2：只有一种情况，前面偶数，后面也是偶数，需要前面下标不变，而后面需要往前移动
                lastIndex--;
                firstIndex--;
            } else if (last % 2 == 0) {
                // 情况3：前面是奇数 后面是偶数，这已经是我们需要的,后面下标往前面走，前面下标在for循环中往后移动
                lastIndex--;
            } else {
                // 情况4：前面是奇数，后面也是奇数,后面不做任何操作，前面下标位置在for中会往后移动
            }
        }
        System.out.println("总共循环次数：" + count);
        return inputArray;
    }




}
