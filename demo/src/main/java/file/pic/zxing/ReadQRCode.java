package file.pic.zxing;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Author: Johnny
 * Date: 2017/7/3
 * Time: 22:26
 */
public class ReadQRCode {
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void main(String[] args) {
        MultiFormatReader formatReader = new MultiFormatReader();

        File file = new File("img.png");
        BufferedImage image;
        try {
            image = ImageIO.read(file);
            BinaryBitmap binaryBitmap =
                    new BinaryBitmap(
                            new HybridBinarizer(
                                    new BufferedImageLuminanceSource(image)));
            HashMap hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            Result result = formatReader.decode(binaryBitmap, hints);

            System.out.println("parse result: " + result.toString());
            System.out.println("format type: " + result.getBarcodeFormat());
            System.out.println("content of text: " + result.getText());
        } catch (IOException | NotFoundException e) {
            e.printStackTrace();
        }
    }
}
