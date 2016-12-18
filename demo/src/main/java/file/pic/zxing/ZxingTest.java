package file.pic.zxing;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2016/12/4
 * Time: 22:08
 */
public class ZxingTest {

    //http://www.jb51.net/article/90955.htm
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void main(String[] args) {
        //generate QR code
        try {
            //realise link redirect
            String content = "http://www.cnblogs.com/jtmjx";
            String path = "D:\\Java_ex\\test\\src\\test\\resources";

            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);
            File file1 = new File(path, "qxs.jpg");
            MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //parse QR code
        try {
            MultiFormatReader formatReader = new MultiFormatReader();
            String filePath = "D:\\Java_ex\\test\\src\\test\\resources\\QR-1479302282569KatyPerry.png";
            File file = new File(filePath);
            BufferedImage image = ImageIO.read(file);
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            Result result = formatReader.decode(binaryBitmap, hints);

            System.out.println("result = " + result.toString());
            System.out.println("resultFormat = " + result.getBarcodeFormat());
            System.out.println("resultText = " + result.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
