package algorithm.encrypt.demo1;

/**
 * Author: Johnny
 * Date: 2017/2/9
 * Time: 10:01
 */
public enum BlowfishManager {
    BRIDGELI_CN("bridgeli_cn!@#$abc123_");

    BlowfishManager(String secret) {
        this.blowfish = new Blowfish(secret);
    }

    private Blowfish blowfish;

    public Blowfish getBlowfish() {
        return blowfish;
    }

    public String decryptString(String sCipherText) {
        return this.getBlowfish().decryptString(sCipherText);
    }

    public String encryptString(String sPlainText) {
        return this.getBlowfish().encryptString(sPlainText);
    }

    //http://www.codeceo.com/article/blowfish-encryption-algorithm.html
    public static void main(String[] args) {
        String encryptString = BlowfishManager.BRIDGELI_CN.encryptString(10 + "");
        System.out.println(encryptString);
        String decryptString = BlowfishManager.BRIDGELI_CN.decryptString(encryptString);
        System.out.println(decryptString);
    }
}
