package algorithm.search;

import static algorithm.search.BoyerMoore.boyerMooreSearch;
import static algorithm.search.BoyerMoore.getRight;
import static algorithm.search.Kmp.kmpSearch;
import static algorithm.search.Kmp.getNext;
import static algorithm.search.Sunday.sundaySearch;

/**
 * Author: Johnny
 * Date: 2017/4/5
 * Time: 13:58
 */
public class StringSearch {

    //https://juejin.im/post/58b61dbe2f301e006c45de9d
    private static int forceSearch(String txt, String pat) {
        int txtLength = txt.length();
        int patLength = pat.length();
        for (int i = 0; i <= txtLength - patLength; i++) {
            int j;
            for (j = 0; j < patLength; j++) {
                if (txt.charAt(i + j) != pat.charAt(j)) {
                    break;
                }
            }
            if (j == patLength) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        String txt = "BBC ABCDAB AACDABABCDABCDABD";
        String pat = "ABCDABD";
        System.out.println(forceSearch(txt, pat));

        //sunday
        System.out.println(sundaySearch(txt, pat));

        //KMP
        int[] next = new int[pat.length()];
        getNext(pat, next);
        System.out.println(kmpSearch(txt, pat, next));

        //BoyerMoore
        int[] right = new int[256];
        getRight(pat, right);
        System.out.println(boyerMooreSearch(txt, pat, right));

    }
}

/**
 * Knuth-Morris-Pratt
 * 关键是求 next 数组, 其长度为模式串的长度。next 数组中每个值代表模式串中当前字符前面的字符串中，有多大长度的相同前缀后缀
 */
class Kmp {
    static int kmpSearch(String txt, String pat, int[] next) {
        int txtLength = txt.length();
        int patLength = pat.length();
        int i = 0;
        int j = 0;
        while (i < txtLength && j < patLength) {
            if (j == -1 || txt.charAt(i) == pat.charAt(j)) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j == patLength) {
            return i - j;
        } else {
            return -1;
        }
    }

    static void getNext(String pat, int[] next) {
        int patLength = pat.length();
        next[0] = -1;
        int k = -1;
        int j = 0;
        while (j < patLength - 1) {
            if (k == -1 || pat.charAt(j) == pat.charAt(k)) {
                ++k;
                ++j;
                next[j] = k;
            } else {
                k = next[k];
            }
        }
    }

}

/**
 * 比 KMP 算法效率高, 该算法有“坏字符”和“好后缀”两个概念,字符串从后往前匹配。
 */
class BoyerMoore {
    static void getRight(String pat, int[] right) {
        for (int i = 0; i < 256; i++) {
            right[i] = -1;
        }
        for (int i = 0; i < pat.length(); i++) {
            right[pat.charAt(i)] = i;
        }
    }

    static int boyerMooreSearch(String txt, String pat, int[] right) {
        int txtLength = txt.length();
        int patLength = pat.length();
        int skip;
        for (int i = 0; i <= txtLength - patLength; i += skip) {
            skip = 0;
            for (int j = patLength - 1; j >= 0; j--) {
                if (pat.charAt(j) != txt.charAt(i + j)) {
                    skip = j - right[txt.charAt(i + j)];
                    if (skip < 1) {
                        skip = 1;
                    }
                    break;
                }
            }
            if (skip == 0) {
                return i;
            }
        }
        return -1;
    }

}

/**
 * sunday 算法 跟 KMP 算法一样，是从前往后匹配。在匹配失败时，关注文本串中参加匹配的最末位字符的下一位字符,
 * 如果该字符不在模式串中，则整个模式串移动到该字符之后。如果该字符在模式串中，将模式串右移使对应的字符对齐。
 */
class Sunday {
    private static int getIndex(String pat, Character c) {
        for (int i = pat.length() - 1; i >= 0; i--) {
            if (pat.charAt(i) == c) {
                return i;
            }
        }
        return -1;
    }

    static int sundaySearch(String txt, String pat) {
        int txtLength = txt.length();
        int patLength = pat.length();
        int i, j;
        int skip = -1;
        for (i = 0; i <= txtLength - patLength; i += skip) {
            for (j = 0; j < patLength; j++) {
                if (txt.charAt(i + j) != pat.charAt(j)) {
                    if (i == txtLength - patLength) {
                        break;
                    }
                    skip = patLength - getIndex(pat, txt.charAt(i + patLength));
                    break;
                }
            }
            if (j == patLength) {
                return i;
            }
        }
        return -1;
    }

}
