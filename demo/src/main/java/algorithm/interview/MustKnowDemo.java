package algorithm.interview;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * 关于String、数组的若干算法题
 */
public class MustKnowDemo {
    static int[][] d = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public static void main(String[] args) throws Exception {
        int[] nums = new int[]{7, 5, 6, 4};
        int[] nums2 = new int[]{8, 9, 0, 2};
        int[] nums3 = new int[]{1, -2, 3, 10, -4, 7, 2, -5};
//        getMaxLetter("go go good");
//        testDateSplit();
//        System.out.println(Arrays.toString(twoSum(nums, 7)));
//        System.out.println(allSum(nums, 7));
//        System.out.println(Arrays.toString(maxNumber(nums, nums2, 7)));
//        System.out.println(groupAnagrams(new String[]{"ear", "era", "bra", "bar", "rab"}));
//        System.out.println(groupAnagrams2(new String[]{"ear", "era", "bra", "bar", "rab"}));
//        System.out.println(findMinInRotatedSortedArray(nums2));
//        System.out.println(firstOnce("avcvcs"));
//        System.out.println(getNthUglyNumber(10));
//        System.out.println(getReversePairNum(nums));
//        System.out.println(sum(20));
//        System.out.println(add(122,1));
//        System.out.println(minNumber(nums));
//        System.out.println(countDigitOne1(12));
//        System.out.println(countDigitOne2(12));
//        System.out.println(countDigitOne3(12));
//        System.out.println(maxSubArray2(nums3));
//        System.out.println(maxSubArray1(nums3));
//        System.out.println(Arrays.toString(findKSmallest1(nums3, 3)));
//        System.out.println(findNumberInSnakeMatrix(5, 1));
//        findThisNumber();
//        System.out.println(fourNumber());
//        System.out.println(judge2(1021, 1131));
        long num = 59084709587505L;
        System.out.println(find1(num));
    }

    /**
     * <a href="https://edu.csdn.net/skill/algorithm/algorithm-a3532059f6854b5abac0d07821a7457f?category=692">...</a>
     * TODO
     */
    public static int find1(long max) {
        int[] a = {3, 5, 7};
        List<Long> set = new LinkedList<>();
        long t = 1L;
        do {
            for (int i = 0; i < 3; ++i) {
                long tt = t * a[i];
                if (tt <= max) {
                    set.add(tt);
                }
            }
            t = set.getLast();
        } while (t <= max);
        return set.size();
    }

    /**
     * <a href="https://edu.csdn.net/skill/algorithm/algorithm-d98c791357f2416389a36896d0186317?category=692">...</a>
     * 由4个不同的数字组成乘法算式，其乘积仍由这4个数字组成
     * 如：210 x 6 = 1260
     * 求这样的算式有多少个？
     */
    public static int fourNumber() {
        int i, j;
        int ans = 0;
        // 1位数 * 3位数
        for (i = 1; i < 10; i++) {
            for (j = 123; j < 1000; j++) {
                if (judge1(i * j) && judge2(i, j) && judge3(i, j)) {
                    ans++;
                }
            }
        }
        // 2位数 * 2位数
        for (i = 10; i < 100; i++) {
            for (j = i + 1; j < 100; j++) {
                if (judge1(i * j) && judge2(i, j) && judge3(i, j)) {
                    ans++;
                }
            }
        }
        return ans;
    }

    /**
     * 是否是4位数
     */
    private static boolean judge1(int n) {
        int ans = 0;
        while (n >= 1) {
            ans++;
            n /= 10;
        }
        return ans == 4;
    }

    private static boolean judge2(int i, int j) {
        int s1 = 0, s2 = 0;
        int ss1 = 1, ss2 = 1;
        int tmp = i * j;
        boolean flag1 = true, flag2 = true;
        while (tmp >= 1) {
            s1 += tmp % 10;
            if (tmp % 10 == 0) {
                flag1 = false;
            } else {
                ss1 *= tmp % 10;
            }
            tmp /= 10;
        }
        while (j >= 1) {
            s2 += j % 10;
            if (j % 10 != 0) {
                ss2 *= j % 10;
            } else {
                flag2 = false;
            }
            j /= 10;
        }
        while (i >= 1) {
            s2 += i % 10;
            if (i % 10 != 0) {
                ss2 *= i % 10;
            } else {
                flag2 = false;
            }
            i /= 10;
        }
        if (s1 == s2 && ss1 == ss2 && flag1 && flag2) {
            return true;
        } else {
            return (!flag1 && !flag2) && s1 == s2 && ss1 == ss2;
        }
    }

    private static boolean judge3(int i, int j) {
        // 方法调用方确保传入的两个数的各位刚好可以存到长度为4的数组里
        int[] a = new int[4];
        int ans = 0;
        while (i >= 1) {
            a[ans] = i % 10;
            i /= 10;
            ans++;
        }
        while (j >= 1) {
            a[ans] = j % 10;
            j /= 10;
            ans++;
        }
        for (i = 0; i < 3; i++) {
            for (j = i + 1; j < 4; j++) {
                // 判断两个数的各个位上的数字是否存在相同的
                if (a[i] == a[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * <a href="https://edu.csdn.net/skill/algorithm/algorithm-f4874378183445fb9edcf1cb372399f7">...</a>
     */
    public static int findNumberInSnakeMatrix(int n, int m) {
        // 行、列索引
        int i = 0, j = 0;
        int cnt = 2;
        // 初始化一个【足够】用的矩阵
        int[][] a = new int[250][250];
        a[0][0] = 1;
        // 设置一个够用的最大自然数
        while (cnt < 1000) {
            j++;
            while (i != -1 && j != -1) {
                a[i][j] = cnt++;
                if (j == 0) {
                    break;
                }
                i++;
                j--;
            }
            i++;
            while (i != -1 && j != -1) {
                a[i][j] = cnt++;
                if (i == 0) {
                    break;
                }
                i--;
                j++;
            }
        }
        return a[n - 1][m - 1];
    }

    /**
     * <a href="https://edu.csdn.net/skill/algorithm/algorithm-ef16a8876b2446c0981c5b9cf28f278d?category=188">...</a>
     */
    public static void findThisNumber() {
        for (int i = 123456; i <= 987654; i++) {
            if (i % 10 == 0 || i % 10 == 1 || i % 10 == 5 || i % 10 == 6) {
                continue;
            }
            if (meet(i, (long) i * i)) {
                System.out.println(i);
            }
        }
    }

    private static boolean meet(int n, long square) {
        Set<Long> set1 = new HashSet<>();
        Set<Long> set2 = new HashSet<>();

        int a = digitSize(set1, n);
        if (a != 6) {
            return false;
        }
        int b = digitSize(set2, square);
        int c = digitSize(set1, square);
        return a + b == c;
    }

    public static int digitSize(Set<Long> set, long n) {
        set.add(n % 10);
        while (n / 10 != 0) {
            n /= 10;
            set.add(n % 10);
        }
        return set.size();
    }

    /**
     * 给定数组nums和数字k，输出该数组里最小的k个数
     */
    public static int[] findKSmallest1(int[] arr, int k) {
        // 创建一个最大堆（默认是最大堆）
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        // 将数组中的元素添加到最大堆中
        for (int num : arr) {
            maxHeap.offer(num);
            // 如果堆的大小超过k，则移除堆顶元素，保持堆的大小为k
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }
        // 将最小的k个数存储到结果数组中
        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            result[i] = maxHeap.poll();
        }
        return result;
    }

    public static int[] findKSmallest2(int[] arr, int k) {
        int[] result = new int[k];
        findKSmallestUtil(arr, 0, arr.length - 1, k - 1); // k-1 因为索引从0开始
        System.arraycopy(arr, 0, result, 0, k);
        return result;
    }

    private static void findKSmallestUtil(int[] arr, int start, int end, int k) {
        if (start >= end) {
            return;
        }
        int pivotIndex = partition(arr, start, end);
        if (pivotIndex != k) {
            if (pivotIndex < k) {
                findKSmallestUtil(arr, pivotIndex + 1, end, k);
            } else {
                findKSmallestUtil(arr, start, pivotIndex - 1, k);
            }
        }
    }

    private static int partition(int[] arr, int start, int end) {
        int pivot = arr[end];
        int left = start;
        int right = end - 1;
        while (left <= right) {
            while (left <= right && arr[left] < pivot) {
                left++;
            }
            while (left <= right && arr[right] > pivot) {
                right--;
            }
            if (left < right) {
                swap(arr, left, right);
                left++;
                right--;
            }
        }
        swap(arr, left, end);
        return left;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 求1+2+…+n，要求：不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句(A?B:C)
     * 公式 n*(n+1)/2 不能使用
     */
    public static int sum(int n) {
        // 定义临时变量初始值为1，用于保存递归过程中的累加和
        int temp = 1;
        return sumHelper(n, temp);
    }

    private static int sumHelper(int n, int temp) {
        // 利用逻辑与短路特性实现递归终止条件，累加结果存储到temp
        boolean flag = (n > 0) && ((temp += sumHelper(n - 1, temp)) > 0);
        return n + temp - 1;
    }

    /**
     * 写一个函数，求两个整数之和，要求在函数体内不得使用+、-、×、÷四则运算符号。
     */
    public static int add(int a, int b) {
        while (b != 0) {
            int carry = a & b;  // 计算进位
            a = a ^ b;          // 计算不带进位的和
            b = carry << 1;     // 将进位左移
        }
        return a;
    }

    /**
     * 第一个只出现一次的字符
     */
    public static char firstOnce(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Map<Character, Integer> map = new HashMap<>();
        for (char c : str.toCharArray()) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }
        for (char c : str.toCharArray()) {
            if (map.get(c) == 1) {
                return c;
            }
        }
        return ' ';
    }

    /**
     * 给定字符串，求出现次数最多的字符
     */
    public static void getMax(String str) {
        Map<Character, Integer> result = new HashMap<>();
        for (char c : str.toCharArray()) {
            if (result.containsKey(c)) {
                result.put(c, result.get(c) + 1);
            } else {
                result.put(c, 1);
            }
        }
        Character maxChar = null;
        Integer maxCount = 0;
        for (Map.Entry<Character, Integer> entry : result.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxChar = entry.getKey();
                maxCount = entry.getValue();
            }
        }
        System.out.println("字符: " + maxChar + " 出现的字数最多,为 " + maxCount + " 次");
    }

    /**
     * 把只包含因子2、3和5的数称作丑数（Ugly Number）。另外，习惯上把1当做第一个丑数。
     * 求按从小到大的顺序的第1500个丑数。
     */
    public static int getNthUglyNumber(int n) {
        if (n <= 0) {
            return 0;
        }
        int[] uglyNumbers = new int[n];
        uglyNumbers[0] = 1;
        int index2 = 0, index3 = 0, index5 = 0;
        int factor2 = 2, factor3 = 3, factor5 = 5;
        for (int i = 1; i < n; i++) {
            int nextUglyNumber = Math.min(factor2, Math.min(factor3, factor5));
            uglyNumbers[i] = nextUglyNumber;
            if (nextUglyNumber == factor2) {
                index2++;
                factor2 = uglyNumbers[index2] * 2;
            }
            if (nextUglyNumber == factor3) {
                index3++;
                factor3 = uglyNumbers[index3] * 3;
            }
            if (nextUglyNumber == factor5) {
                index5++;
                factor5 = uglyNumbers[index5] * 5;
            }
        }
        return uglyNumbers[n - 1];
    }

    /**
     * 给定一个数组，若前面的数字大于后面的数字，则两个数字组成一个逆序对。求给定的输入数组中的逆序对的总数
     */
    public static int getReversePairNum(int[] array) {
        if (array == null || array.length < 2) {
            return 0;
        }
        int[] temp = new int[array.length];
        return mergeSortAndCount(array, temp, 0, array.length - 1);
    }

    private static int mergeSortAndCount(int[] array, int[] temp, int left, int right) {
        if (left >= right) {
            return 0;
        }
        int mid = (left + right) / 2;
        int res = 0;
        res += mergeSortAndCount(array, temp, left, mid);
        res += mergeSortAndCount(array, temp, mid + 1, right);
        res += mergeAndCount(array, temp, left, mid, right);
        return res;
    }

    private static int mergeAndCount(int[] array, int[] temp, int left, int mid, int right) {
        int i = left;    // Starting index for left subarray
        int j = mid + 1; // Starting index for right subarray
        int k = left;    // Starting index to be sorted
        int res = 0;
        while (i <= mid && j <= right) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
                res += (mid + 1 - i); // All remaining elements in the left subarray are greater than array[j]
            }
        }
        while (i <= mid) {
            temp[k++] = array[i++];
        }
        while (j <= right) {
            temp[k++] = array[j++];
        }
        for (i = left; i <= right; i++) {
            array[i] = temp[i];
        }
        return res;
    }

    public static void testDateSplit() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdf.parse("2015-4-20");
        Date end = sdf.parse("2015-5-2");
        List<Date> lists = dateSplit(start, end);
        if (!lists.isEmpty()) {
            for (Date date : lists) {
                System.out.println(sdf.format(date));
            }
        }
    }

    /**
     * 对给定的两个日期之间的日期进行遍历
     */
    public static List<Date> dateSplit(Date startDate, Date endDate) throws Exception {
        if (!startDate.before(endDate)) {
            throw new Exception("开始时间应该在结束时间之后");
        }
        long spi = endDate.getTime() - startDate.getTime();
        long step = spi / (24 * 60 * 60 * 1000);
        List<Date> dateList = new ArrayList<>();
        dateList.add(endDate);
        for (int i = 1; i <= step; i++) {
            dateList.add(new Date(dateList.get(i - 1).getTime() - (24 * 60 * 60 * 1000)));
        }
        return dateList;
    }

    /**
     * 给定字符串，求出现次数最多的单词
     */
    public static void getMaxLetter(String str) {
        Map<String, Integer> result = new HashMap<>();
        for (String c : str.split(" ")) {
            if (result.containsKey(c)) {
                result.put(c, result.get(c) + 1);
            } else {
                result.put(c, 1);
            }
        }
        String maxChar = null;
        Integer maxCount = 0;
        for (Map.Entry<String, Integer> entry : result.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxChar = entry.getKey();
                maxCount = entry.getValue();
            }
        }
        System.out.println("单次:" + maxChar + " 出现的字数最多,为 " + maxCount + " 次");
    }

    /**
     * 两数之和：给定数组和目标数，求所有的两数组合，要求两数加和为给定的目标数
     * 返回索引
     */
    public static int[] twoSum(int[] a, int target) {
        int i = 0, j = a.length - 1;
        while (true) {
            if (target == a[i] + a[j]) {
                return new int[]{i, j};
            }
            if (a[i] + a[j] < target) {
                ++i;
            } else {
                --j;
            }
            if (i == j) {
                return null;
            }
        }
    }

    /**
     * 给定数组和目标数，求所有的组合情况，加和为给定的目标数，数组元素可以重复使用
     * 返回索引
     */
    public static List<List<Integer>> allSum(int[] candidates, int target) {
        List<List<Integer>> resultList = new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        Arrays.sort(candidates);
        dfs(candidates, resultList, result, 0, target);
        return resultList;
    }

    private static void dfs(int[] candidates, List<List<Integer>> resultList, List<Integer> result, int start, int target) {
        if (target >= 0) {
            if (target == 0) {
                resultList.add(new ArrayList<>(result));
            } else {
                for (int i = start; i < candidates.length; i++) {
                    result.add(candidates[i]);
                    dfs(candidates, resultList, result, i, target - candidates[i]);
                    result.removeLast();
                }
            }
        }
    }

    /**
     * 三数之和：给定数组和目标数，求所有的两数组合，要求三个数加和为给定的目标数
     */
    public static List<List<Integer>> threeSum(int[] num) {
        Arrays.sort(num);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < num.length - 2; ++i) {
            if (i > 0 && num[i] == num[i - 1]) {
                continue;
            }
            int lo = i + 1, hi = num.length - 1, sum = -num[i];
            while (lo < hi) {
                // 有序数组找特定和的两元素，双指针算法
                if (num[lo] + num[hi] == sum) {
                    res.add(Arrays.asList(num[i], num[lo], num[hi]));
                    while (lo < hi && num[lo] == num[lo + 1]) {
                        lo++;
                    }
                    while (lo < hi && num[hi] == num[hi - 1]) {
                        hi--;
                    }
                    lo++;
                    hi--;
                } else if (num[lo] + num[hi] < sum) {
                    lo++;
                } else {
                    hi--;
                }
            }
        }
        return res;
    }

    /**
     * 连续子数组的最大和
     * 输入一个整型数组，数组元素有正有负。数组中连续的多（包括一）个整数组成一个子数组。求所有子数组的和的最大值，要求时间复杂度为O（n）
     */
    public static int maxSubArray1(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = dp[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    public static int maxSubArray2(int[] nums) {
        int maxSum = nums[0];
        int curSum = 0;
        for (int n : nums) {
            curSum += n;
            if (curSum > maxSum) {
                maxSum = curSum;
            }
            if (curSum < 0) {
                curSum = 0;
            }
        }
        return maxSum;
    }

    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> res = new ArrayList<>();
        if (candidates.length == 0 || target < candidates[0]) {
            return res;
        }
        List<Integer> tmp = new ArrayList<>();
        helper(candidates, target, 0, tmp, res);
        return res;
    }

    public static void helper(int[] a, int target, int start, List<Integer> tmp, List<List<Integer>> res) {
        if (target < 0) {
            return;
        }
        if (target == 0) {
            res.add(new ArrayList<>(tmp));
            return;
        }
        for (int i = start; i < a.length; i++) {
            tmp.add(a[i]);
            int newTarget = target - a[i];
            helper(a, newTarget, i + 1, tmp, res);
            tmp.remove(tmp.size() - 1);
            if (newTarget <= 0) {
                break;
            }
            while (i + 1 < a.length && a[i] == a[i + 1]) {
                // 组合中有重复元素，不要重复开头
                i++;
            }
        }
    }

    /**
     * 实现一个求开平方根的函数
     */
    public static int mySqrt(int x) {
        int left = 0, right = 46340;
        while (left < right) {
            int mid = (left + right) / 2;
            if (mid * mid < x) {
                left = mid + 1;
            } else if (mid * mid > x) {
                if ((mid - 1) * (mid - 1) <= x) {
                    return mid - 1;
                } else {
                    right = mid - 1;
                }
            } else {
                return mid;
            }
        }
        if (left * left > x) {
            return left - 1;
        }
        return left;
    }

    /**
     * 石头剪刀布游戏，输入为p1 p2形式，p_i候选项：Rock、Scissors、Paper，求输出（候选项为：Tie、Player1、Player2）
     * 示例：Rock Rock，输出：Tie
     */
    public static void output(List<String> inputs) {
        for (String item : inputs) {
            String item1 = (String) Arrays.stream(item.split(" ")).toArray()[0];
            String item2 = (String) Arrays.stream(item.split(" ")).toArray()[1];
            if (item1.equals(item2)) {
                System.out.println("Tie");
            } else if ((item1.equals("Rock") && item2.equals("Scissors")) || (item1.equals("Scissors") && item2.equals("Paper")) || (item1.equals("Paper") && item2.equals("Rock"))) {
                System.out.println("Player1");
            } else {
                System.out.println("Player2");
            }
        }
    }

    /**
     * 求给定的二进制数字中1的个数
     */
    public static int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            if ((n & 1) == 1) {
                count++;
            }
            n >>>= 1;
        }
        return count;
    }

    /**
     * 输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个
     */
    public static String minNumber(int[] nums) {
        // 将整数数组转换为字符串数组
        String[] strArray = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strArray[i] = String.valueOf(nums[i]);
        }
        // 自定义比较器，比较两个字符串拼接后的大小
        Comparator<String> comparator = (s1, s2) -> {
            String str1 = s1 + s2;
            String str2 = s2 + s1;
            return str1.compareTo(str2);
        };
        // 使用自定义比较器对字符串数组进行排序
        Arrays.sort(strArray, comparator);
        // 将排序后的字符串数组拼接成一个字符串
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * 输入一个整数n，求从1到n这n个整数的十进制表示中1出现的次数
     * 最基础版本，for循环逐一累加
     */
    public static int countDigitOne1(int n) {
        int count = 0;
        for (int i = 1; i <= n; i++) {
            count += numOfOne(i);
        }
        return count;
    }

    private static int numOfOne(int n) {
        int count = 0;
        // 关键条件
        while (n >= 1) {
            if (n % 10 == 1) {
                count++;
            }
            n /= 10;
        }
        return count;
    }

    public static int countDigitOne2(int n) {
        int count = 0;
        for (long i = 1; i <= n; i *= 10) {
            long div = i * 10;
            count += (int) ((n / div) * i + Math.min(Math.max(n % div - i + 1, 0), i));
        }
        return count;
    }

    /**
     * TODO：百度智障大模型给出的算法
     */
    public static int countDigitOne3(int n) {
        int count = 0;
        for (long i = 1; i <= n; i *= 10) {
            long div = i * 10;
            long low = n / div * div;
            long high = (n / div + 1) * div - 1;
            if (low > 0) {
                count += (int) ((n / div) * i + Math.min(Math.max(low % div - i + 1, 0), i));
            }
            if (high < n) {
                count += (int) Math.min(high % div + 1, i);
            }
        }
        return count;
    }

    /**
     * 复原 IP 地址
     * <a href="https://edu.csdn.net/practice/24632351">...</a>
     */
    public static List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<>(Collections.emptyList());
        int ls = s.length();
        if (ls == 0 || ls > 12) {
            return res;
        }
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                for (int k = 1; k <= 4; k++) {
                    int m = ls - i - j - k;
                    if (m > 0 && m <= 3) {
                        String add1 = s.substring(0, i);
                        String add2 = s.substring(i, i + j);
                        String add3 = s.substring(i + j, i + j + k);
                        String add4 = s.substring(i + j + k);
                        if (isValid(add1) && isValid(add2) && isValid(add3) && isValid(add4)) {
                            res.add(add1.concat(".").concat(add2).concat(".").concat(add3).concat(".").concat(add4));
                        }
                    }
                }
            }
        }
        return res;
    }

    private static boolean isValid(String str) {
        if (str.length() == 1) {
            return true;
        }
        if (str.charAt(0) == '0') {
            return false;
        }
        return Integer.parseInt(str) <= 255;
    }

    /**
     * <a href="https://edu.csdn.net/practice/24761839"></a>
     *
     * @param nums 给定的可能存在重复元素的数组
     * @return 求数组元素递增的最大长度
     */
    public static int longestConsecutive(int[] nums) {
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }
        int longestStreak = 0;
        for (int num : numSet) {
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;
                while (numSet.contains(currentNum + 1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }
                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }
        return longestStreak;
    }

    /**
     * <a href="https://edu.csdn.net/practice/27336825">...</a>
     * 搜索二维矩阵,每行中的整数从左到右按升序排列, 每行的第一个整数大于前一行的最后一个整数。
     *
     * @param matrix 二维数组
     * @param target 目标值
     * @return 目标值是否存在于数组中
     */
    public static boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        int begin, mid, end;
        begin = 0;
        int len1 = matrix.length, len2 = matrix[0].length;
        end = len1 * len2 - 1;
        while (begin < end) {
            mid = (begin + end) / 2;
            if (matrix[mid / len2][mid % len2] < target) {
                begin = mid + 1;
            } else {
                end = mid;
            }
        }
        return matrix[begin / len2][begin % len2] == target;
    }

    /**
     * <a href="https://edu.csdn.net/practice/27476116">...</a>
     * 给定单词（字符串）列表words，以及 m x n 二维字符网格board，找出所有同时在二维网格和字典中出现的单词。
     * 单词必须按照字母顺序，通过 相邻的单元格 内的字母构成，相邻单元格是那些水平相邻或垂直相邻的单元格。
     * 同一个单元格内的字母在一个单词中不允许被重复使用
     */
    public static List<String> findWords(char[][] board, String[] words) {
        List<String> ans = new ArrayList<>();
        Arrays.sort(words);
        Map<String, Boolean> notExistWords = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (i > 0 && word.equals(words[i - 1])) {
                continue;
            }
            boolean notExistFlag = false;
            for (int j = 1; j < word.length(); j++) {
                if (notExistWords.containsKey(word.substring(0, j + 1))) {
                    notExistFlag = true;
                    break;
                }
            }
            if (notExistFlag) {
                continue;
            }
            if (exist(board, word)) {
                ans.add(word);
            } else {
                notExistWords.put(word, false);
            }
        }
        return ans;
    }

    private static boolean exist(char[][] board, String word) {
        int m = board.length;
        if (m == 0) {
            return false;
        }
        int n = board[0].length;
        if (n == 0) {
            return false;
        }
        if (word.isEmpty()) {
            return true;
        }
        boolean[][] f = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (word.charAt(0) == board[i][j]) {
                    f[i][j] = true;
                    boolean flag = dfs(i, j, 1, board, word, m, n, f);
                    if (flag) {
                        return true;
                    }
                    f[i][j] = false;
                }
            }
        }
        return false;
    }

    private static boolean dfs(int i, int j, int k, char[][] board, String word, int m, int n, boolean[][] f) {
        if (k == word.length()) {
            return true;
        }
        for (int l = 0; l < 4; l++) {
            if (i + d[l][0] < m && j + d[l][1] < n && i + d[l][0] >= 0 && j + d[l][1] >= 0 && board[i + d[l][0]][j + d[l][1]] == word.charAt(k) && !f[i + d[l][0]][j + d[l][1]]) {
                f[i + d[l][0]][j + d[l][1]] = true;
                boolean flag = dfs(i + d[l][0], j + d[l][1], k + 1, board, word, m, n, f);
                if (flag) {
                    return true;
                }
                f[i + d[l][0]][j + d[l][1]] = false;
            }
        }
        return false;
    }

    /**
     * <a href="https://edu.csdn.net/practice/28395430">...</a>
     * 环形公路
     *
     * @param gas  加油站数组,每个加油站可加油量
     * @param cost 消耗数组,到下一个加油站消耗油量
     * @return -1 or 环形公路加油站台(索引从0开始)
     */
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        int sum = 0, cur = 0, start = 0;
        for (int i = 0; i < n; i++) {
            sum = sum + gas[i] - cost[i];
            if (cur < 0) {
                start = i;
                cur = gas[i] - cost[i];
            } else cur = cur + gas[i] - cost[i];
        }
        if (sum < 0) return -1;
        return start;
    }

    /**
     * <a href="https://edu.csdn.net/practice/28865708">...</a>
     * 给定n个非负整数表示每个宽度为1的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水
     */
    public static int trap(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int len = height.length;
        int res = 0;
        int[] leftMax = new int[len];
        int[] rightMax = new int[len];
        leftMax[0] = height[0];
        for (int i = 1; i < len; i++) {
            leftMax[i] = Math.max(height[i], leftMax[i - 1]);
        }
        rightMax[len - 1] = height[len - 1];
        for (int i = len - 2; i >= 0; i--) {
            rightMax[i] = Math.max(height[i], rightMax[i + 1]);
        }
        for (int i = 1; i < len - 1; i++) {
            res += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return res;
    }

    /**
     * 给定两个数组nums1和nums2，数组元素表示两个自然数各位上的数字。现从两个数组中选择出k(k<=m+n)个数字拼接成新数字，要求从任一个数组取数时保持数字在该数组中的相对顺序。
     * 输出结果为长度为k的数组，且能表示最大数
     */
    public static int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int[] res = null;
        for (int i = Math.max(k - nums2.length, 0); i <= Math.min(nums1.length, k); ++i) {
            int[] merge = merge(maxInNums(nums1, i), maxInNums(nums2, k - i));
            res = (res == null || greater(merge, 0, res, 0)) ? merge : res;
        }
        return res;
    }

    private static int[] maxInNums(int[] nums, int k) {
        int[] max = new int[k];
        int len = nums.length;
        for (int i = 0, j = 0; i < len; ++i) {
            while (j > 0 && k - j < len - i && max[j - 1] < nums[i]) {
                --j;
            }
            if (j < k) {
                max[j++] = nums[i];
            }
        }
        return max;
    }

    private static boolean greater(int[] nums1Max, int i, int[] nums2Max, int j) {
        int lenNums1Max = nums1Max.length;
        int lenNums2Max = nums2Max.length;
        while (i < lenNums1Max && j < lenNums2Max && nums1Max[i] == nums2Max[j]) {
            ++i;
            ++j;
        }
        return j == lenNums2Max || (i < lenNums1Max && nums1Max[i] > nums2Max[j]);
    }

    private static int[] merge(int[] nums1Max, int[] nums2Max) {
        int lenCurRes = nums1Max.length + nums2Max.length;
        int[] curRes = new int[lenCurRes];
        for (int i = 0, j = 0, m = 0; m < lenCurRes; ++m) {
            curRes[m] = greater(nums1Max, i, nums2Max, j) ? nums1Max[i++] : nums2Max[j++];
        }
        return curRes;
    }

    public static List<List<String>> groupAnagrams(String[] arrays) {
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        for (String str : arrays) {
            char[] cs = str.toCharArray();
            Arrays.sort(cs);
            String key = String.valueOf(cs);
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(str);
        }
        return new ArrayList<>(map.values());
    }

    public static List<List<String>> groupAnagrams2(String[] arrays) {
        if (arrays.length == 0) {
            return new ArrayList<>();
        }
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        for (String str : arrays) {
            char[] cs = str.toCharArray();
            int[] count = new int[26];
            for (char c : cs) {
                ++count[c - 'a'];
            }
            StringBuilder s = new StringBuilder();
            for (int num : count) {
                s.append(num);
            }
            String key = String.valueOf(s);
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(str);
        }
        return new ArrayList<>(map.values());
    }

    /**
     * 把一个数组最开始的若干个元素搬到数组的末尾，称之为数组的旋转。输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。
     * 如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为1。
     */
    public static int findMinInRotatedSortedArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Array is empty or null");
        }
        int left = 0;
        int right = nums.length - 1;
        // 如果数组没有被旋转，直接返回第一个元素
        if (nums[left] < nums[right]) {
            return nums[left];
        }
        while (left < right) {
            int mid = left + (right - left) / 2;
            // 如果中间元素大于右边元素，说明最小值在右半部分
            if (nums[mid] > nums[right]) {
                left = mid + 1;
                // 如果中间元素小于右边元素，说明最小值在左半部分或就是mid
            } else if (nums[mid] < nums[right]) {
                right = mid;
            } else { // 如果中间元素等于右边元素，无法确定最小值在哪个部分，缩小范围
                right--;
            }
        }
        return nums[left];
    }

    /**
     * <a href="https://edu.csdn.net/practice/24632345">...</a>
     *
     * @param nums 数组
     * @return 数组元素可组成的最大数
     */
    public static String largestNumber(int[] nums) {
        String[] str = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            str[i] = String.valueOf(nums[i]);
        }
        Arrays.parallelSort(str);
        for (int i = 1; i < str.length; i++) {
            for (int j = 0; j < i; j++) {
                if (str[i].length() > str[j].length() && str[i].startsWith(str[j])) {
                    StringBuilder str1 = new StringBuilder();
                    StringBuilder str2 = new StringBuilder();
                    str1.append(str[i]).append(str[j]);
                    str2.append(str[j]).append(str[i]);
                    if (str2.toString().compareTo(str1.toString()) > 0) {
                        String tmp = str[i];
                        str[i] = str[j];
                        str[j] = tmp;
                    }
                }
            }
        }
        StringBuilder ans = new StringBuilder();
        for (int i = str.length - 1; i >= 0; i--) {
            ans.append(str[i]);
        }
        return ans.charAt(0) == '0' ? "0" : ans.toString();
    }

    /**
     * 股票交易的最大利润
     */
    public static int maxProfit(int[] prices) {
        int result = 0;
        if (prices.length == 0) {
            return result;
        }
        int firstDealSell;
        int secondDealSell;
        for (secondDealSell = prices.length - 1; secondDealSell > 0; secondDealSell--) {
            if (prices[secondDealSell - 1] < prices[secondDealSell]) {
                break;
            }
        }
        for (firstDealSell = 1; firstDealSell < prices.length; firstDealSell++) {
            while (firstDealSell + 1 < prices.length && prices[firstDealSell + 1] >= prices[firstDealSell]) {
                firstDealSell++;
            }
            int result1 = maxProfit(prices, 0, firstDealSell);
            int result2 = maxProfit(prices, firstDealSell + 1, secondDealSell);
            if (result1 + result2 > result) {
                result = result1 + result2;
            }
        }
        return result;
    }

    private static int maxProfit(int[] prices, int left, int right) {
        int result = 0;
        if (right - left < 1) {
            return result;
        }
        int minPrice = prices[left];
        for (int i = left + 1; i <= right; i++) {
            result = Math.max(result, prices[i] - minPrice);
            minPrice = Math.min(minPrice, prices[i]);
        }
        return result;
    }

    /**
     * <a href="https://edu.csdn.net/practice/28865710">...</a>
     * <p>
     * Your MedianFinder object will be instantiated and called as such:
     * MedianFinder obj = new MedianFinder();
     * obj.addNum(num);
     * double param_2 = obj.findMedian();
     */
    private static class MedianFinder {
        private final PriorityQueue<Integer> min;
        private final PriorityQueue<Integer> max;

        /**
         * initialize your data structure here.
         */
        public MedianFinder() {
            min = new PriorityQueue<>();
            max = new PriorityQueue<>((a, b) -> b - a);
        }

        public void addNum(int num) {
            max.add(num);
            min.add(max.remove());
            if (min.size() > max.size()) max.add(min.remove());
        }

        public double findMedian() {
            if (max.size() == min.size()) {
                return (max.peek() + min.peek()) / 2.0;
            } else {
                return max.peek();
            }
        }
    }

}
