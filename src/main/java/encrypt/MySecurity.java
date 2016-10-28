package encrypt;

import java.awt.RenderingHints.Key;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Security;

import javax.crypto.Cipher;

public class MySecurity {
    private static String strDefaultKey = "johnny";
    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;

    //TODO
    private Key getKey(byte[] arrBTmp) throws Exception {
        byte[] arrB = new byte[8];
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
        return key;
    }

    public MySecurity() throws Exception {
        this(strDefaultKey);
    }

    public MySecurity(String strKey) throws Exception {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        Key key = getKey(strKey.getBytes());

        encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, (java.security.Key) key);

        decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, (java.security.Key) key);
    }

    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (byte anArrB : arrB) {
            int intTmp = anArrB;
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    public byte[] encrypt(byte[] arrB) throws Exception {
        return encryptCipher.doFinal(arrB);
    }

    public String encrypt(String strIn) throws Exception {
        return byteArr2HexStr(encrypt(strIn.getBytes()));
    }

    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    public byte[] decrypt(byte[] arrB) throws Exception {
        return decryptCipher.doFinal(arrB);
    }

    public String decrypt(String strIn) throws Exception {
        return new String(decrypt(hexStr2ByteArr(strIn)));
    }

    public static byte[] getByteFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();

        if (length > Integer.MAX_VALUE) {
            return null;
        }

        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file" + file.getName());
        }

        is.close();
        return bytes;
    }

    private static File writeBytesToFile(byte[] inByte, String pathAndNameString) throws IOException {
        File file = null;
        try {
            file = new File(pathAndNameString);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(inByte);
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return file;
    }

    public static void encryptFile(String srcFile, String destFile) {
        File inFile = new File(srcFile);
        byte[] myfileA = getByteFromFile(inFile);
        writeBytesToFile(des.encrypt(myfileA), destFile);
    }

    public static void decryptFile(String srcFile, String destFile) {
        File inFile = new File(srcFile);
        byte[] myfileA = getByteFromFile(inFile);
        writeBytesToFile(des.decrypt(myfileA), destFile);
    }

    public static void main(String args[]) {
        try {
            MySecurity des = new MySecurity();

            encryptFile("C:\\Users\\wajian\\Documents\\Test\\test.zip", "C:\\Users\\wajian\\Documents\\Test\\entest.zip");

            decryptFile("C:\\Users\\wajian\\Documents\\Test\\entest.zip", "C:\\Users\\wajian\\Documents\\Test\\detest.zip");

            System.out.println("解密前的String==" + "123456");
            String enStr = des.encryp("123456");
            System.out.println("加密后的enStr==" + enStr);
            String deStr = des.decrypt(enStr);
            System.out.println("解密后的deStr==" + deStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
