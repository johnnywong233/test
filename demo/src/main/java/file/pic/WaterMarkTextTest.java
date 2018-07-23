package file.pic;

import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by johnny on 2016/9/15.
 * 生成文字水印
 * http://www.phpxs.com/code/1001578/
 */
public class WaterMarkTextTest {
    public static void main(String[] args) throws IOException {

        File srcImgFile = new ClassPathResource("KatyPerry.jpg").getFile();
        String logoText = "[love Katy Perry so much]";

        File outputImageFile = new File("KatyPerry_water_text.jpg");

        File outputRotateImageFile = new File("KatyPerry_water_text_rotate.jpg");

        createWaterMarkByText(srcImgFile, logoText, outputImageFile);

        createWaterMarkByText(srcImgFile, logoText, outputRotateImageFile, 45);
    }


    private static void createWaterMarkByText(File srcImgFile, String logoText,
                                              File outputImageFile) {
        createWaterMarkByText(srcImgFile, logoText, outputImageFile, 0);
    }

    private static void createWaterMarkByText(File srcImgFile, String logoText,
                                              File outputImageFile, double degree) {
        OutputStream os = null;
        try {
            Image srcImg = ImageIO.read(srcImgFile);

            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            Graphics2D g = buffImg.createGraphics();

            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            g.drawImage(
                    srcImg.getScaledInstance(srcImg.getWidth(null),
                            srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0,
                    null);

            if (degree > 0) {
                g.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2,
                        (double) buffImg.getHeight() / 2);
            }

            g.setColor(Color.RED);

            g.setFont(new Font("宋体", Font.BOLD, 36));

            float alpha = 0.8f;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));

            g.drawString(logoText, buffImg.getWidth() / 20, buffImg.getHeight() / 20);

            g.dispose();

            os = new FileOutputStream(outputImageFile);

            //generate pic
            ImageIO.write(buffImg, "JPG", os);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
