package grammar.collection;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/2/19
 * Time: 18:08
 */
public class ArraysTest {
    @Test
    public void test() {
        String[] array = new String[]{"a", "c", "2", "1", "b", "6", "m", "z", "l"};
        Integer[] ints = new Integer[]{5, 1, 4, 3, 2};
        List<String> lists = Arrays.asList(array);
        System.out.println(lists);

        Arrays.sort(array);
        for (String str : array) {
            System.out.println(str);
        }

        Arrays.parallelSort(array);//large data

        Arrays.sort(array, 2, 5);//add sort rules
        System.out.println(Arrays.deepToString(array));//[a, c, 1, 2, b]

        //binarySearch需要保证是排好序的
        System.out.println(Arrays.binarySearch(array, "c"));
        Arrays.sort(array);
        System.out.println(Arrays.binarySearch(array, "c"));

        //demo of copyOf
        //如果位数不够，需要补位
        Integer[] result = Arrays.copyOf(ints, 10);
        for (int i : result) {
            System.out.println(i);
        }
        System.out.println("----------------------------------------->");
        //如果位数够，就取最小的数组
        result = Arrays.copyOf(ints, 3);
        for (int i : result) {
            System.out.println(i);
        }
        System.out.println("----------------------------------------->");
        //
        result = Arrays.copyOfRange(ints, 2, 4);
        for (int i : result) {
            System.out.println(i);
        }

        //deepEquals深度比较、deepHashCode生成hashcode、deepToString深度打印
        String[] array2 = new String[]{"a", "c", "2", "1", "b"};
        System.out.println(Arrays.deepEquals(array, array2));//深度比较两个数组是否相同

        System.out.println(Arrays.deepHashCode(array));
        System.out.println(Arrays.deepHashCode(array2));//如果两个数组deepEquals，那么他们的hashcode一定相同

        //格式化输出数组
        System.out.println(Arrays.deepToString(array));

        //demo of equals
        String[] array3 = new String[]{"a", "c", "2", "1", "b"};
        //1 对比引用是否相同
        //2 对比是否存在null
        //3 对比长度是否相同
        //4 挨个元素对比
        System.out.println(Arrays.equals(array, array3));

        //fill: 基于目标元素填充数组
        Arrays.fill(array, "test");
        System.out.println(Arrays.deepToString(array));//[test, test, test, test, test]

        //把数组转换成stream,然后可以使用java8的stream特性了。
        Arrays.stream(array).forEach(System.out::println);

        //有点像spark的reduceByKey，即根据传入的方法一次计算：
        Arrays.parallelPrefix(ints, (x, y) -> (x + y));
        System.out.println(Arrays.deepToString(ints));//[5, 6, 10, 13, 15]

        //相当于stream.map会挨个元素遍历执行方法
        Arrays.parallelSetAll(ints, x -> x * x);
        System.out.println(Arrays.toString(ints));//[0, 1, 4, 9, 16]

        //与上面类似, not parallel
        Arrays.setAll(ints, x -> x * 2);
        System.out.println(Arrays.toString(ints));
    }
}
