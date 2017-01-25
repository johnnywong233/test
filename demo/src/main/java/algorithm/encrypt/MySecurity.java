package algorithm.encrypt;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.Security;

public class MySecurity {

    private MySecurity des = new MySecurity();
    private static String strDefaultKey = "johnny";
    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;

    //TODO
    private Key getKey(byte[] arrBTmp) throws Exception {
        byte[] arrB = new byte[8];
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        return new javax.crypto.spec.SecretKeySpec(arrB, "DES");
    }

    private MySecurity() throws Exception {
        this(strDefaultKey);
    }

    private MySecurity(String strKey) throws Exception {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        Key key = getKey(strKey.getBytes());

        encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);

        decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }

    private static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        StringBuilder sb = new StringBuilder(iLen * 2);
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

    private static byte[] hexStr2ByteArr(String strIn) throws Exception {
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

    private static byte[] getByteFromFile(File file) throws IOException {
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            return null;
        }
        InputStream is = new FileInputStream(file);
        byte[] bytes = new byte[(int) length];
        int offset = 0;
        int numRead;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        if (offset < bytes.length) {
            is.close();
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

    private void encryptFile(String srcFile, String destFile) throws Exception {
        File inFile = new File(srcFile);
        byte[] fileA = getByteFromFile(inFile);
        writeBytesToFile(des.encrypt(fileA), destFile);
    }

    private void decryptFile(String srcFile, String destFile) throws Exception {
        File inFile = new File(srcFile);
        byte[] fileA = getByteFromFile(inFile);
        writeBytesToFile(des.decrypt(fileA), destFile);
    }

    public static void main(String args[]) {
        try {
            MySecurity des = new MySecurity();

            des.encryptFile("C:\\Users\\wajian\\Documents\\Test\\test.zip", "C:\\Users\\wajian\\Documents\\Test\\entest.zip");

            des.decryptFile("C:\\Users\\wajian\\Documents\\Test\\entest.zip", "C:\\Users\\wajian\\Documents\\Test\\detest.zip");

            System.out.println("string before decrypted:String==" + "123456");
            String enStr = des.encrypt("123456");
            System.out.println("string after encrypted:enStr==" + enStr);
            String deStr = des.decrypt(enStr);
            System.out.println("string after decrypted:deStr==" + deStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
