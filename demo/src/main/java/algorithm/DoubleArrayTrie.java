package algorithm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by johnny on 2016/10/5.
 * 双数组Trie树
 * note: a word ends may be either of these two case:
 * 1. Base[curP] == pos  ( pos<0 and tail[-pos] == 'END_CHAR' )
 * 2. check[Base[curP] + Code('END_CHAR')] ==  curP
 * 传统的Trie实现简单，但是占用的空间实在是难以接受，特别是当字符集不仅限于英文26个字符的时候，爆炸起来的空间根本无法接受。
 * 双数组Trie就是优化了空间的Trie树，原理本文就不讲了，请参考An Efficient Implementation of Trie Structures，本程序的编写也是参考这篇论文的。
 * 关于几点论文没有提及的细节和与论文不一一致的实现：
 * 1.对于插入字符串，如果有一个字符串是另一个字符串的子串的话，我是将结束符也作为一条边，产生一个新的结点，这个结点新节点的Base我置为0
 * 所以一个字符串结束也有2中情况：一个是Base值为负，存储剩余字符(可能只有一个结束符)到Tail数组；另一个是Base为0。
 * 所以在查询的时候要考虑一下这两种情况
 * 2.对于第一种冲突（论文中的Case 3），可能要将Tail中的字符串取出一部分，作为边放到索引中。论文是使用将尾串左移的方式，我的方式直接修改Base值，而不是移动尾串。
 */
public class DoubleArrayTrie {

    private final char END_CHAR = '\0';
    //base length, the size of array grow as double of it.
    private final int DEFAULT_LEN = 1024;
    private final Map<Character, Integer> charMap = new HashMap<>();
    private final ArrayList<Character> charList = new ArrayList<>();
    private int[] base = new int[DEFAULT_LEN];
    private int[] check = new int[DEFAULT_LEN];
    private char[] tail = new char[DEFAULT_LEN];
    private int pos = 1;

    private DoubleArrayTrie() {
        base[1] = 1;
        charMap.put(END_CHAR, 1);
        charList.add(END_CHAR);
        charList.add(END_CHAR);
        for (int i = 0; i < 26; ++i) {
            charMap.put((char) ('a' + i), charMap.size() + 1);
            charList.add((char) ('a' + i));
        }
    }

    //http://www.mincoder.com/article/3855.shtml
    public static void main(String[] args) throws Exception {
        ArrayList<String> words = new ArrayList<>();
        long start = System.currentTimeMillis();
        //en-US IMJPCH hyph_en_CA
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\work\\test\\src\\main\\resources\\IMJPCH.dic")));
        String s;
        int num = 0;
        while ((s = reader.readLine()) != null) {
            words.add(s);
            num++;
        }
        DoubleArrayTrie dat = new DoubleArrayTrie();
        for (String word : words) {
            dat.insert(word);
        }
        System.out.println(dat.base.length);
        System.out.println(dat.tail.length);
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String word = sc.next();
            System.out.println(dat.exists(word));
            System.out.println(dat.findAllWords(word));
        }
        System.out.println(System.currentTimeMillis() - start);
        sc.close();
        reader.close();
    }

    private void extendArray() {
        base = Arrays.copyOf(base, base.length * 2);
        check = Arrays.copyOf(check, check.length * 2);
    }

    private void extendTail() {
        tail = Arrays.copyOf(tail, tail.length * 2);
    }

    private int getCharCode(char c) {
        if (!charMap.containsKey(c)) {
            charMap.put(c, charMap.size() + 1);
            charList.add(c);
        }
        return charMap.get(c);
    }

    private int copyToTailArray(String s, int p) {
        int index = pos;
        while (s.length() - p + 1 > tail.length - pos) {
            extendTail();
        }
        for (int i = p; i < s.length(); ++i) {
            tail[index] = s.charAt(i);
            index++;
        }
        return index;
    }

    private int xCheck(Integer[] set) {
        for (int i = 1; ; ++i) {
            boolean flag = true;
            for (Integer aSet : set) {
                int curP = i + aSet;
                if (curP >= base.length) {
                    extendArray();
                }
                if (base[curP] != 0 || check[curP] != 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return i;
            }
        }
    }

    private ArrayList<Integer> getChildList(int p) {
        ArrayList<Integer> ret = new ArrayList<>();
        for (int i = 1; i <= charMap.size(); ++i) {
            if (base[p] + i >= check.length) {
                break;
            }
            if (check[base[p] + i] == p) {
                ret.add(i);
            }
        }
        return ret;
    }

    private boolean tailContainString(int start, String s2) {
        for (int i = 0; i < s2.length(); ++i) {
            if (s2.charAt(i) != tail[i + start]) {
                return false;
            }
        }
        return true;
    }

    private boolean tailMatchString(int start, String s2) {
        s2 += END_CHAR;
        for (int i = 0; i < s2.length(); ++i) {
            if (s2.charAt(i) != tail[i + start]) {
                return false;
            }
        }
        return true;
    }

    private void insert(String s) {
        s += END_CHAR;
        int preP = 1;
        int curP;
        for (int i = 0; i < s.length(); ++i) {
            //获取状态位置
            curP = base[preP] + getCharCode(s.charAt(i));
            //如果长度超过现有，拓展数组
            if (curP >= base.length) {
                extendArray();
            }
            //空闲状态
            if (base[curP] == 0 && check[curP] == 0) {
                base[curP] = -pos;
                check[curP] = preP;
                pos = copyToTailArray(s, i + 1);
                break;
            } else
                //已存在状态
                if (base[curP] > 0 && check[curP] == preP) {
                    preP = curP;
                } else
                    //冲突 1：遇到 base[curP]小于0的，即遇到一个被压缩存到Tail中的字符串
                    if (base[curP] < 0 && check[curP] == preP) {
                        int head = -base[curP];
                        if (s.charAt(i + 1) == END_CHAR && tail[head] == END_CHAR)//插入重复字符串
                        {
                            break;
                        }
                        //公共字母的情况，因为上一个判断已经排除了结束符，所以一定是2个都不是结束符
                        if (tail[head] == s.charAt(i + 1)) {
                            int availBase = xCheck(new Integer[]{getCharCode(s.charAt(i + 1))});
                            base[curP] = availBase;
                            check[availBase + getCharCode(s.charAt(i + 1))] = curP;
                            base[availBase + getCharCode(s.charAt(i + 1))] = -(head + 1);
                            preP = curP;
                        } else {
                            //2个字母不相同的情况，可能有一个为结束符
                            int availBase;
                            availBase = xCheck(new Integer[]{getCharCode(s.charAt(i + 1)), getCharCode(tail[head])});
                            base[curP] = availBase;
                            check[availBase + getCharCode(tail[head])] = curP;
                            check[availBase + getCharCode(s.charAt(i + 1))] = curP;
                            //tail 为END_FLAG 的情况
                            if (tail[head] == END_CHAR) {
                                base[availBase + getCharCode(tail[head])] = 0;
                            } else {
                                base[availBase + getCharCode(tail[head])] = -(head + 1);
                            }
                            if (s.charAt(i + 1) == END_CHAR) {
                                base[availBase + getCharCode(s.charAt(i + 1))] = 0;
                            } else {
                                base[availBase + getCharCode(s.charAt(i + 1))] = -pos;
                            }
                            pos = copyToTailArray(s, i + 2);
                            break;
                        }
                    } else
                        //冲突2：当前结点已经被占用，需要调整pre的base
                        if (check[curP] != preP) {
                            ArrayList<Integer> list1 = getChildList(preP);
                            int toBeAdjust;
                            ArrayList<Integer> list;
                            if (true) {
                                toBeAdjust = preP;
                                list = list1;
                            }
                            int originBase = base[toBeAdjust];
                            list.add(getCharCode(s.charAt(i)));
                            int availBase = xCheck(list.toArray(new Integer[0]));
                            list.remove(list.size() - 1);
                            base[toBeAdjust] = availBase;
                            for (Integer aList : list) {
                                //BUG
                                int tmp1 = originBase + aList;
                                int tmp2 = availBase + aList;
                                base[tmp2] = base[tmp1];
                                check[tmp2] = check[tmp1];
                                //有后续
                                if (base[tmp1] > 0) {
                                    ArrayList<Integer> subsequence = getChildList(tmp1);
                                    for (Integer sub : subsequence) {
                                        check[base[tmp1] + sub] = tmp2;
                                    }
                                }
                                base[tmp1] = 0;
                                check[tmp1] = 0;
                            }
                            //更新新的cur_p
                            curP = base[preP] + getCharCode(s.charAt(i));
                            if (s.charAt(i) == END_CHAR) {
                                base[curP] = 0;
                            } else {
                                base[curP] = -pos;
                            }
                            check[curP] = preP;
                            pos = copyToTailArray(s, i + 1);
                            break;
                        }
        }
    }

    private boolean exists(String word) {
        int preP = 1;
        int curP = 0;
        for (int i = 0; i < word.length(); ++i) {
            curP = base[preP] + getCharCode(word.charAt(i));
            if (check[curP] != preP) {
                return false;
            }
            if (base[curP] < 0) {
                return tailMatchString(-base[curP], word.substring(i + 1));
            }
            preP = curP;
        }
        return check[base[curP] + getCharCode(END_CHAR)] == curP;
    }

    private FindStruct find(String word) {
        int preP = 1;
        int curP = 0;
        FindStruct fs = new FindStruct();
        for (int i = 0; i < word.length(); ++i) {
            // BUG
            fs.prefix += word.charAt(i);
            curP = base[preP] + getCharCode(word.charAt(i));
            if (check[curP] != preP) {
                fs.p = -1;
                return fs;
            }
            if (base[curP] < 0) {
                if (tailContainString(-base[curP], word.substring(i + 1))) {
                    fs.p = curP;
                    return fs;
                }
                fs.p = -1;
                return fs;
            }
            preP = curP;
        }
        fs.p = curP;
        return fs;
    }

    private ArrayList<String> getAllChildWord(int index) {
        ArrayList<String> result = new ArrayList<>();
        if (base[index] == 0) {
            result.add("");
            return result;
        }
        if (base[index] < 0) {
            StringBuilder r = new StringBuilder();
            for (int i = -base[index]; tail[i] != END_CHAR; ++i) {
                r.append(tail[i]);
            }
            result.add(r.toString());
            return result;
        }
        for (int i = 1; i <= charMap.size(); ++i) {
            if (check[base[index] + i] == index) {
                for (String s : getAllChildWord(base[index] + i)) {
                    result.add(charList.get(i) + s);
                }
                //result.addAll(getAllChildWord(base[index]+i));
            }
        }
        return result;
    }

    private ArrayList<String> findAllWords(String word) {
        ArrayList<String> result = new ArrayList<>();
        FindStruct fs = find(word);
        int p = fs.p;
        if (p == -1) {
            return result;
        }
        if (base[p] < 0) {
            StringBuilder r = new StringBuilder();
            for (int i = -base[p]; tail[i] != END_CHAR; ++i) {
                r.append(tail[i]);
            }
            result.add(fs.prefix + r);
            return result;
        }
        if (base[p] > 0) {
            ArrayList<String> r = getAllChildWord(p);
            r.replaceAll(s -> fs.prefix + s);
            return r;
        }
        return result;
    }

    //内部函数，返回匹配单词的最靠后的Base index，
    private static class FindStruct {
        int p;
        String prefix = "";
    }
}
