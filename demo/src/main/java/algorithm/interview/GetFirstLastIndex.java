package algorithm.interview;

/**
 * Author: Johnny
 * Date: 2018/5/15
 * Time: 22:02
 * 给定一个升序的整数数组，查找某一个值在数组中出现的索引号，例如，输入数组2，3，3，4，4，5；查找的数是3，则返回1,2。时间复杂度要求为O（logN）
 */
public class GetFirstLastIndex {

    public static void main(String[] args) {
        int[] sortedArray = {1, 1, 2, 3, 3, 6, 6, 6, 6, 9, 9, 10};
        int length = sortedArray.length;
        int target = 6;
        System.out.println(getFirstTarget(sortedArray, length, target, 0, length - 1));
        System.out.println(getSecondTarget(sortedArray, length, target, 0, length - 1));
    }

    private static int getFirstTarget(int[] input, int n, int target, int nStart, int nEnd) {
        if (nStart > nEnd) {
            //返回-1表示没有待查询的元素
            return -1;
        }
        //中间索引
        int nMid = nStart + ((nEnd - nStart) >> 1);
        int nMidData = input[nMid];

        while (nStart <= nEnd) {
            if (target > nMidData) {
                nStart = nMid + 1;
            } else if (target < nMidData) {
                nEnd = nMid - 1;
            } else {
                if ((target != input[nMid - 1] && nMid > 0) || nMid == 0) {
                    return nMid;
                } else {
                    nEnd = nMid - 1;
                }
            }
            //更新中间值得索引和值
            nMid = nStart + ((nEnd - nStart) >> 1);
            //数组越界判断
            if (nMid < 0) {
                return -1;
            }
            nMidData = input[nMid];
        }
        return -1;
    }

    //寻找结束索引
    private static int getSecondTarget(int[] input, int n, int target, int nStart, int nEnd) {
        if (nStart > nEnd) {
            return -1;
        }
        //中间索引
        int nMid = nStart + ((nEnd - nStart) >> 1);
        int nMidData = input[nMid];

        while (nStart <= nEnd) {
            if (target > nMidData) {
                nStart = nMid + 1;
            } else if (target < nMidData) {
                nEnd = nMid - 1;
            } else {
                if ((target != input[nMid + 1] && nMid < n) || nMid == n - 1) {
                    return nMid;
                } else {
                    nStart = nMid + 1;
                }
            }
            //更新中间值得索引和值
            nMid = nStart + ((nEnd - nStart) >> 1);
            if (nMid < 0) {
                return -1;
            }
            nMidData = input[nMid];
        }
        return -1;
    }
}
