package algorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/5/18
 * Time: 23:44
 */
public class BloomFilter {
    private BitSet bitSet;
    private int bitSetSize;
    private int addedElements;
    private int hashFunctionNumber;

    /**
     * 构造一个布隆过滤器，过滤器的容量为c * n 个bit.
     *
     * @param c 当前过滤器预先开辟的最大包含记录,通常要比预计存入的记录多一倍.
     * @param n 当前过滤器预计所要包含的记录.
     * @param k 哈希函数的个数，等同每条记录要占用的bit数.
     */
    private BloomFilter(int c, int n, int k) {
        this.hashFunctionNumber = k;
        this.bitSetSize = (int) Math.ceil(c * k);
        this.addedElements = n;
        this.bitSet = new BitSet(this.bitSetSize);
    }

    /**
     * 通过文件初始化过滤器.
     */
    public void init(String file) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null && line.length() > 0) {
                this.put(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void put(String str) {
        int[] positions = createHashes(str.getBytes(), hashFunctionNumber);
        for (int position1 : positions) {
            int position = Math.abs(position1 % bitSetSize);
            bitSet.set(position, true);
        }
    }

    public boolean contains(String str) {
        byte[] bytes = str.getBytes();
        int[] positions = createHashes(bytes, hashFunctionNumber);
        for (int i : positions) {
            int position = Math.abs(i % bitSetSize);
            if (!bitSet.get(position)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 得到当前过滤器的错误率.
     */
    private double getFalsePositiveProbability() {
        // (1 - e^(-k * n / m)) ^ k
        return Math.pow((1 - Math.exp(-hashFunctionNumber * (double) addedElements / bitSetSize)),
                hashFunctionNumber);
    }

    /**
     * 将字符串的字节表示进行多哈希编码.
     *
     * @param bytes      待添加进过滤器的字符串字节表示.
     * @param hashNumber 要经过的哈希个数.
     * @return 各个哈希的结果数组.
     */
    private static int[] createHashes(byte[] bytes, int hashNumber) {
        int[] result = new int[hashNumber];
        int k = 0;
        while (k < hashNumber) {
            result[k] = HashFunctions.hash(bytes, k);
            k++;
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        BloomFilter bloomfilter = new BloomFilter(30000000, 10000000, 8);
        System.out.println("Bloom Filter Initialize ... ");
        //TODO
        bloomfilter.init("data/base.txt");
        System.out.println("Bloom Filter Ready");
        System.out.println("False Positive Probability : "
                + bloomfilter.getFalsePositiveProbability());
        // 查找新数据
        List<String> result = new ArrayList<>();
        long t1 = System.currentTimeMillis();
        BufferedReader reader = new BufferedReader(new FileReader("data/input.txt"));
        String line = reader.readLine();
        while (line != null && line.length() > 0) {
            if (!bloomfilter.contains(line)) {
                result.add(line);
            }
            line = reader.readLine();
        }
        reader.close();
        long t2 = System.currentTimeMillis();
        System.out.println("Parse 9900000 items, Time : " + (t2 - t1) + "ms , find "
                + result.size() + " new items.");
        System.out.println("Average : " + 9900000 / ((t2 - t1) / 1000) + " items/second");
    }
}

class HashFunctions {
    static int hash(byte[] bytes, int k) {
        switch (k) {
            case 0:
                return RSHash(bytes);
            case 1:
                return JSHash(bytes);
            case 2:
                return ELFHash(bytes);
            case 3:
                return BKDRHash(bytes);
            case 4:
                return APHash(bytes);
            case 5:
                return DJBHash(bytes);
            case 6:
                return SDBMHash(bytes);
            case 7:
                return PJWHash(bytes);
        }
        return 0;
    }

    private static int RSHash(byte[] bytes) {
        int hash = 0;
        int magic = 63689;
        for (byte aByte : bytes) {
            hash = hash * magic + aByte;
            magic = magic * 378551;
        }
        return hash;
    }

    private static int JSHash(byte[] bytes) {
        int hash = 1315423911;
        for (byte aByte : bytes) {
            hash ^= ((hash << 5) + aByte + (hash >> 2));
        }
        return hash;
    }

    private static int ELFHash(byte[] bytes) {
        int hash = 0;
        int x;
        for (byte aByte : bytes) {
            hash = (hash << 4) + aByte;
            if ((x = hash & 0xF0000000) != 0) {
                hash ^= (x >> 24);
                hash &= ~x;
            }
        }
        return hash;
    }

    private static int BKDRHash(byte[] bytes) {
        int seed = 131;
        int hash = 0;
        for (byte aByte : bytes) {
            hash = (hash * seed) + aByte;
        }
        return hash;
    }

    private static int APHash(byte[] bytes) {
        int hash = 0;
        int len = bytes.length;
        for (int i = 0; i < len; i++) {
            if ((i & 1) == 0) {
                hash ^= ((hash << 7) ^ bytes[i] ^ (hash >> 3));
            } else {
                hash ^= (~((hash << 11) ^ bytes[i] ^ (hash >> 5)));
            }
        }
        return hash;
    }

    private static int DJBHash(byte[] bytes) {
        int hash = 5381;
        for (byte aByte : bytes) {
            hash = ((hash << 5) + hash) + aByte;
        }
        return hash;
    }

    private static int SDBMHash(byte[] bytes) {
        int hash = 0;
        for (byte aByte : bytes) {
            hash = aByte + (hash << 6) + (hash << 16) - hash;
        }
        return hash;
    }

    private static int PJWHash(byte[] bytes) {
        long BitsInUnsignedInt = (4 * 8);
        long ThreeQuarters = ((BitsInUnsignedInt * 3) / 4);
        long OneEighth = (BitsInUnsignedInt / 8);
        long HighBits = (long) (0xFFFFFFFF) << (BitsInUnsignedInt - OneEighth);
        int hash = 0;
        long test;
        for (byte aByte : bytes) {
            hash = (hash << OneEighth) + aByte;
            if ((test = hash & HighBits) != 0) {
                hash = (int) ((hash ^ (test >> ThreeQuarters)) & (~HighBits));
            }
        }
        return hash;
    }
}
