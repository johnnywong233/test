package file.pic.qrcode;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Author: Johnny
 * Date: 2017/7/3
 * Time: 22:11
 */
public class ReadQRCode {
    public static void main(String[] args) throws Exception {
        File file = new File("qrCode.png");
        BufferedImage bufferedImage = ImageIO.read(file);
        QRCodeDecoder codeDecoder = new QRCodeDecoder();
        String result = new String(codeDecoder.decode(new MyQRCodeImage(bufferedImage)), "gb2312");
        System.out.println(result);
    }
}

class MyQRCodeImage implements QRCodeImage {
    private final BufferedImage bufferedImage;

    MyQRCodeImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    @Override
    public int getHeight() {
        return bufferedImage.getHeight();
    }

    @Override
    public int getPixel(int arg0, int arg1) {
        return bufferedImage.getRGB(arg0, arg1);
    }

    @Override
    public int getWidth() {
        return bufferedImage.getWidth();
    }
}