package useless;

import java.util.zip.CRC32;

/**
 * Author: Johnny
 * Date: 2018/6/14
 * Time: 23:06
 */
public class CRC {
    public static void main(String[] args) {
        int crc = crc32("johnny".getBytes());
        int crc1 = crc32("johnny".getBytes());
        int crc2 = crc32("johnny!".getBytes());
        System.out.println(crc == crc1);
        System.out.println(crc == crc2);
    }

    private static int crc32(byte[] array) {
        if (array != null) {
            return crc32(array, 0, array.length);
        }
        return 0;
    }

    private static int crc32(byte[] array, int offset, int length) {
        CRC32 crc32 = new CRC32();
        crc32.update(array, offset, length);
        return (int) (crc32.getValue() & 0x7FFFFFFF);
    }
}
