package jbarcode;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import org.jbarcode.JBarcode;
import org.jbarcode.encode.EAN8Encoder;
import org.jbarcode.paint.EAN8TextPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jbarcode.util.ImageUtil;

/**
 * Created by wajian on 2016/8/30.
 * generate all kinds of bar code with jbarcode
 * support EAN13, EAN8, UPCA, UPCE, Code 3 of 9, Codabar, Code 11, Code 93, Code 128, MSI/Plessey, Interleaved 2 of PostNet
 */
public class OneBarcodeUtil {
    //http://www.phpxs.com/code/1001958/
    public static void main(String[] paramArrayOfString) {
        try {
            JBarcode localJBarcode = new JBarcode(EAN8Encoder.getInstance(), WidthCodedPainter.getInstance(), EAN8TextPainter.getInstance());
            String str = "2219644";
            BufferedImage localBufferedImage = localJBarcode.createBarcode(str);

            saveToJPEG(localBufferedImage, "EAN8.jpg");
        }
        catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    static void saveToJPEG(BufferedImage paramBufferedImage, String paramString) {
        saveToFile(paramBufferedImage, paramString, "jpeg");
    }

    static void saveToFile(BufferedImage paramBufferedImage, String paramString1, String paramString2) {
        try {
            FileOutputStream localFileOutputStream = new FileOutputStream("C:\\work\\test\\src\\main\\resources\\" + paramString1);
            ImageUtil.encodeAndWrite(paramBufferedImage, paramString2, localFileOutputStream, 96, 96);
            localFileOutputStream.close();
        }
        catch (Exception localException) {
            localException.printStackTrace();
        }
    }
}
