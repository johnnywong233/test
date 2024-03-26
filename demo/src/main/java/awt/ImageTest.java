package awt;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;

public class ImageTest {

    private static void saveImageAsJpg(String fromFileStr, String saveToFileStr, int width, int height) throws Exception {
        BufferedImage srcImage;
        String imgType = "JPEG";
        if (fromFileStr.toLowerCase().endsWith(".png")) {
            imgType = "PNG";
        }
        File fromFile = new File(fromFileStr);
        File saveFile = new File(saveToFileStr);
        srcImage = ImageIO.read(fromFile);
        if (width > 0 || height > 0) {
            srcImage = resize(srcImage, width, height);
        }
        ImageIO.write(srcImage, imgType, saveFile);
    }

    private static BufferedImage resize(BufferedImage source, int targetW,
                                        int targetH) {
        int type = source.getType();
        BufferedImage target;
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();

        if (sx > sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());
        } else {
            sy = sx;
            targetH = (int) (sx * source.getHeight());
        }
        if (type == BufferedImage.TYPE_CUSTOM) {
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else {
            target = new BufferedImage(targetW, targetH, type);
        }
        Graphics2D g = target.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }

    public static void main(String[] args) {
        try {
            ImageTest.saveImageAsJpg(
                    "C:\\Users\\wajian\\Documents\\Test\\KatyPerry.jpg",
                    "C:\\Users\\wajian\\Documents\\Test\\loveKatyPerry.jpg", 800, 600);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
