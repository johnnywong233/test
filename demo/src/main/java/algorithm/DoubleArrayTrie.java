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
 * Created by wajian on 2016/10/5.
 * 双数组Trie树
 * note: a word ends may be either of these two case:
 * 1. Base[cur_p] == pos  ( pos<0 and Tail[-pos] == 'END_CHAR' )
 * 2. Check[Base[cur_p] + Code('END_CHAR')] ==  cur_p
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
    private final int DEFAULT_LEN = 1024;//base length, the size of array grow as double of it.
    private int Base[] = new int[DEFAULT_LEN];
    private int Check[] = new int[DEFAULT_LEN];
    private char Tail[] = new char[DEFAULT_LEN];
    private int Pos = 1;
    private Map<Character, Integer> CharMap = new HashMap<>();
    private ArrayList<Character> CharList = new ArrayList<>();

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
            dat.Insert(word);
        }
        System.out.println(dat.Base.length);
        System.out.println(dat.Tail.length);
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String word = sc.next();
            System.out.println(dat.Exists(word));
            System.out.println(dat.FindAllWords(word));
        }
        System.out.println(System.currentTimeMillis() - start);
        sc.close();
        reader.close();
    }

    private DoubleArrayTrie() {
        Base[1] = 1;
        CharMap.put(END_CHAR, 1);
        CharList.add(END_CHAR);
        CharList.add(END_CHAR);
        for (int i = 0; i < 26; ++i) {
            CharMap.put((char) ('a' + i), CharMap.size() + 1);
            CharList.add((char) ('a' + i));
        }
    }

    private void Extend_Array() {
        Base = Arrays.copyOf(Base, Base.length * 2);
        Check = Arrays.copyOf(Check, Check.length * 2);
    }

    private void Extend_Tail() {
        Tail = Arrays.copyOf(Tail, Tail.length * 2);
    }

    private int GetCharCode(char c) {
        if (!CharMap.containsKey(c)) {
            CharMap.put(c, CharMap.size() + 1);
            CharList.add(c);
        }
        return CharMap.get(c);
    }

    private int CopyToTailArray(String s, int p) {
        int _Pos = Pos;
        while (s.length() - p + 1 > Tail.length - Pos) {
            Extend_Tail();
        }
        for (int i = p; i < s.length(); ++i) {
            Tail[_Pos] = s.charAt(i);
            _Pos++;
        }
        return _Pos;
    }

    private int x_check(Integer[] set) {
        for (int i = 1; ; ++i) {
            boolean flag = true;
            for (Integer aSet : set) {
                int cur_p = i + aSet;
                if (cur_p >= Base.length) {
                    Extend_Array();
                }
                if (Base[cur_p] != 0 || Check[cur_p] != 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return i;
            }
        }
    }

    private ArrayList<Integer> GetChildList(int p) {
        ArrayList<Integer> ret = new ArrayList<>();
        for (int i = 1; i <= CharMap.size(); ++i) {
            if (Base[p] + i >= Check.length) {
                break;
            }
            if (Check[Base[p] + i] == p) {
                ret.add(i);
            }
        }
        return ret;
    }

    private boolean TailContainString(int start, String s2) {
        for (int i = 0; i < s2.length(); ++i) {
            if (s2.charAt(i) != Tail[i + start]) {
                return false;
            }
        }
        return true;
    }

    private boolean TailMatchString(int start, String s2) {
        s2 += END_CHAR;
        for (int i = 0; i < s2.length(); ++i) {
            if (s2.charAt(i) != Tail[i + start]) {
                return false;
            }
        }
        return true;
    }

    private void Insert(String s) throws Exception {
        s += END_CHAR;
        int pre_p = 1;
        int cur_p;
        for (int i = 0; i < s.length(); ++i) {
            //获取状态位置
            cur_p = Base[pre_p] + GetCharCode(s.charAt(i));
            //如果长度超过现有，拓展数组
            if (cur_p >= Base.length) {
                Extend_Array();
            }
            //空闲状态
            if (Base[cur_p] == 0 && Check[cur_p] == 0) {
                Base[cur_p] = -Pos;
                Check[cur_p] = pre_p;
                Pos = CopyToTailArray(s, i + 1);
                break;
            } else
                //已存在状态
                if (Base[cur_p] > 0 && Check[cur_p] == pre_p) {
                    pre_p = cur_p;
                } else
                    //冲突 1：遇到 Base[cur_p]小于0的，即遇到一个被压缩存到Tail中的字符串
                    if (Base[cur_p] < 0 && Check[cur_p] == pre_p) {
                        int head = -Base[cur_p];
                        if (s.charAt(i + 1) == END_CHAR && Tail[head] == END_CHAR)//插入重复字符串
                        {
                            break;
                        }
                        //公共字母的情况，因为上一个判断已经排除了结束符，所以一定是2个都不是结束符
                        if (Tail[head] == s.charAt(i + 1)) {
                            int avail_base = x_check(new Integer[]{GetCharCode(s.charAt(i + 1))});
                            Base[cur_p] = avail_base;
                            Check[avail_base + GetCharCode(s.charAt(i + 1))] = cur_p;
                            Base[avail_base + GetCharCode(s.charAt(i + 1))] = -(head + 1);
                            pre_p = cur_p;
                        } else {
                            //2个字母不相同的情况，可能有一个为结束符
                            int avail_base;
                            avail_base = x_check(new Integer[]{GetCharCode(s.charAt(i + 1)), GetCharCode(Tail[head])});
                            Base[cur_p] = avail_base;
                            Check[avail_base + GetCharCode(Tail[head])] = cur_p;
                            Check[avail_base + GetCharCode(s.charAt(i + 1))] = cur_p;
                            //Tail 为END_FLAG 的情况
                            if (Tail[head] == END_CHAR) {
                                Base[avail_base + GetCharCode(Tail[head])] = 0;
                            } else {
                                Base[avail_base + GetCharCode(Tail[head])] = -(head + 1);
                            }
                            if (s.charAt(i + 1) == END_CHAR) {
                                Base[avail_base + GetCharCode(s.charAt(i + 1))] = 0;
                            } else {
                                Base[avail_base + GetCharCode(s.charAt(i + 1))] = -Pos;
                            }
                            Pos = CopyToTailArray(s, i + 2);
                            break;
                        }
                    } else
                        //冲突2：当前结点已经被占用，需要调整pre的base
                        if (Check[cur_p] != pre_p) {
                            ArrayList<Integer> list1 = GetChildList(pre_p);
                            int toBeAdjust;
                            ArrayList<Integer> list = null;
                            if (true) {
                                toBeAdjust = pre_p;
                                list = list1;
                            }
                            int origin_base = Base[toBeAdjust];
                            list.add(GetCharCode(s.charAt(i)));
                            int avail_base = x_check(list.toArray(new Integer[list.size()]));
                            list.remove(list.size() - 1);
                            Base[toBeAdjust] = avail_base;
                            for (Integer aList : list) {
                                //BUG
                                int tmp1 = origin_base + aList;
                                int tmp2 = avail_base + aList;
                                Base[tmp2] = Base[tmp1];
                                Check[tmp2] = Check[tmp1];
                                //有后续
                                if (Base[tmp1] > 0) {
                                    ArrayList<Integer> subsequence = GetChildList(tmp1);
                                    for (Integer sub : subsequence) {
                                        Check[Base[tmp1] + sub] = tmp2;
                                    }
                                }
                                Base[tmp1] = 0;
                                Check[tmp1] = 0;
                            }
                            //更新新的cur_p
                            cur_p = Base[pre_p] + GetCharCode(s.charAt(i));
                            if (s.charAt(i) == END_CHAR) {
                                Base[cur_p] = 0;
                            } else {
                                Base[cur_p] = -Pos;
                            }
                            Check[cur_p] = pre_p;
                            Pos = CopyToTailArray(s, i + 1);
                            break;
                        }
        }
    }

    private boolean Exists(String word) {
        int pre_p = 1;
        int cur_p = 0;
        for (int i = 0; i < word.length(); ++i) {
            cur_p = Base[pre_p] + GetCharCode(word.charAt(i));
            if (Check[cur_p] != pre_p) {
                return false;
            }
            if (Base[cur_p] < 0) {
                return TailMatchString(-Base[cur_p], word.substring(i + 1));
            }
            pre_p = cur_p;
        }
        return Check[Base[cur_p] + GetCharCode(END_CHAR)] == cur_p;
    }

    //内部函数，返回匹配单词的最靠后的Base index，
    private class FindStruct {
        int p;
        String prefix = "";
    }

    private FindStruct Find(String word) {
        int pre_p = 1;
        int cur_p = 0;
        FindStruct fs = new FindStruct();
        for (int i = 0; i < word.length(); ++i) {
            // BUG
            fs.prefix += word.charAt(i);
            cur_p = Base[pre_p] + GetCharCode(word.charAt(i));
            if (Check[cur_p] != pre_p) {
                fs.p = -1;
                return fs;
            }
            if (Base[cur_p] < 0) {
                if (TailContainString(-Base[cur_p], word.substring(i + 1))) {
                    fs.p = cur_p;
                    return fs;
                }
                fs.p = -1;
                return fs;
            }
            pre_p = cur_p;
        }
        fs.p = cur_p;
        return fs;
    }

    private ArrayList<String> GetAllChildWord(int index) {
        ArrayList<String> result = new ArrayList<>();
        if (Base[index] == 0) {
            result.add("");
            return result;
        }
        if (Base[index] < 0) {
            String r = "";
            for (int i = -Base[index]; Tail[i] != END_CHAR; ++i) {
                r += Tail[i];
            }
            result.add(r);
            return result;
        }
        for (int i = 1; i <= CharMap.size(); ++i) {
            if (Check[Base[index] + i] == index) {
                for (String s : GetAllChildWord(Base[index] + i)) {
                    result.add(CharList.get(i) + s);
                }
                //result.addAll(GetAllChildWord(Base[index]+i));
            }
        }
        return result;
    }

    private ArrayList<String> FindAllWords(String word) {
        ArrayList<String> result = new ArrayList<>();
        FindStruct fs = Find(word);
        int p = fs.p;
        if (p == -1) {
            return result;
        }
        if (Base[p] < 0) {
            String r = "";
            for (int i = -Base[p]; Tail[i] != END_CHAR; ++i) {
                r += Tail[i];
            }
            result.add(fs.prefix + r);
            return result;
        }
        if (Base[p] > 0) {
            ArrayList<String> r = GetAllChildWord(p);
            for (int i = 0; i < r.size(); ++i) {
                r.set(i, fs.prefix + r.get(i));
            }
            return r;
        }
        return result;
    }
}
