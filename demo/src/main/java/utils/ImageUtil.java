package utils;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/8/21
 * Time: 12:21
 */
public class ImageUtil {
    private static final String PICTRUE_FORMATE_JPG = "jpg";

    private static double screenWidth, screenHeight;  // for resizing the wallpaper

    public ImageUtil() {
    }

    /**
     * 生成组合头像
     *
     * @param paths 用户头像地址
     */
    public static void getCombinationOfHead(List<String> paths, String outputImage) throws IOException {
        List<BufferedImage> bufferedImages = new ArrayList<>();
        // 压缩图片所有的图片生成尺寸
        int imgWidth = 0;
        int imgHeight = 0;
        if (paths.size() == 1) {
            imgWidth = 180;
            imgHeight = 180;
        } else if (paths.size() >= 2 && paths.size() <= 4) {
            imgWidth = 90;
            imgHeight = 90;
        } else if (paths.size() > 4) {
            imgWidth = 60;
            imgHeight = 60;
        }
        for (String path : paths) {
            bufferedImages.add(ImageUtil.resize(path, imgHeight, imgWidth, true));
        }

        int width = 180; // 画板的宽高
        int height = 180; // 画板的高度
        // BufferedImage.TYPE_INT_RGB可以自己定义可查看API
        BufferedImage outImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 生成画布
        Graphics g = outImage.getGraphics();
        Graphics2D g2d = (Graphics2D) g;
        // 设置背景色
        g2d.setBackground(new Color(231, 231, 231));
        // 通过使用当前绘图表面的背景色进行填充来清除指定的矩形。
        g2d.clearRect(0, 0, width, height);
        // 开始拼凑 根据图片的数量判断该生成那种样式的组合头像目前为4中
        for (int i = 1; i <= bufferedImages.size(); i++) {
            if (bufferedImages.size() == 9) {
                if (i <= 3) {
                    g2d.drawImage(bufferedImages.get(i - 1), 60 * (i - 1), 0, null);
                } else if (i <= 6) {
                    g2d.drawImage(bufferedImages.get(i - 1), 60 * (i - 4), 60, null);
                } else {
                    g2d.drawImage(bufferedImages.get(i - 1), 60 * (i - 7), 120, null);
                }
            } else if (bufferedImages.size() == 8) {
                if (i <= 3) {
                    g2d.drawImage(bufferedImages.get(i - 1), 60 * (i - 1), 0, null);
                } else if (i <= 6) {
                    g2d.drawImage(bufferedImages.get(i - 1), 60 * (i - 4), 60, null);
                } else {
                    g2d.drawImage(bufferedImages.get(i - 1), 30 + 60 * (i - 7), 120, null);
                }
            } else if (bufferedImages.size() == 7) {
                if (i <= 3) {
                    g2d.drawImage(bufferedImages.get(i - 1), 60 * (i - 1), 0, null);
                } else if (i <= 6) {
                    g2d.drawImage(bufferedImages.get(i - 1), 60 * (i - 4), 60, null);
                } else {
                    g2d.drawImage(bufferedImages.get(i - 1), 60, 120, null);
                }
            } else if (bufferedImages.size() == 6) {
                if (i <= 3) {
                    g2d.drawImage(bufferedImages.get(i - 1), 60 * (i - 1), 30, null);
                } else {
                    g2d.drawImage(bufferedImages.get(i - 1), 60 * (i - 4), 90, null);
                }
            } else if (bufferedImages.size() == 5) {
                if (i <= 3) {
                    g2d.drawImage(bufferedImages.get(i - 1), 60 * (i - 1), 30, null);
                } else {
                    g2d.drawImage(bufferedImages.get(i - 1), 30 + 60 * (i - 4), 90, null);
                }
            } else if (bufferedImages.size() == 4) {
                if (i <= 2) {
                    g2d.drawImage(bufferedImages.get(i - 1), 90 * (i - 1), 0, null);
                } else {
                    g2d.drawImage(bufferedImages.get(i - 1), 90 * (i - 3), 90, null);
                }
            } else if (bufferedImages.size() == 3) {
                if (i == 3) {
                    g2d.drawImage(bufferedImages.get(i - 1), 45, 90, null);
                } else {
                    g2d.drawImage(bufferedImages.get(i - 1), 90 * (i - 1), 0, null);
                }
            } else if (bufferedImages.size() == 2) {
                g2d.drawImage(bufferedImages.get(i - 1), 90 * (i - 1), 45, null);
            } else if (bufferedImages.size() == 1) {
                g2d.drawImage(bufferedImages.get(i - 1), 0, 0, null);
            }
            // 需要改变颜色的话在这里绘上颜色。可能会用到AlphaComposite类
        }
        String outPath = "/Users/CrazyMouse/Desktop/temp/" + outputImage + ".jpg";
        String format = "JPG";
        ImageIO.write(outImage, format, new File(outPath));
    }

    /**
     * 图片缩放
     *
     * @param bb 比例不对时是否需要补白
     */
    private static BufferedImage resize(String filePath, int height, int width, boolean bb) {
        try {
            double ratio; // 缩放比例
            File f = new File(filePath);
            BufferedImage bi = ImageIO.read(f);
            Image itemp = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            // 计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (Integer.valueOf(height)).doubleValue() / bi.getHeight();
                } else {
                    ratio = (Integer.valueOf(width)).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(
                        AffineTransform.getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);
            }
            if (bb) {
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null)) {
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                } else {
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                }
                g.dispose();
                itemp = image;
            }
            return (BufferedImage) itemp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Check which image dimension (width or height) is bigger than the
     * screen, and crop it. Only one dimension, or none, will be too big.
     */
    public static BufferedImage cropImage(BufferedImage scIm) {
        int imWidth = scIm.getWidth();
        int imHeight = scIm.getHeight();

        BufferedImage croppedImage;
        if (imWidth > screenWidth) {     // image width is bigger than screen width
            // System.out.println("Cropping the width");
            croppedImage = new BufferedImage((int) screenWidth, imHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = croppedImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            int x = ((int) screenWidth - imWidth) / 2;    // crop so image center remains in the center
            g2d.drawImage(scIm, x, 0, null);
            g2d.dispose();
        } else if (imHeight > screenHeight) {
            // image height is bigger than screen height
            // System.out.println("Cropping the height");
            croppedImage = new BufferedImage(imWidth, (int) screenHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = croppedImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            int y = ((int) screenHeight - imHeight) / 2;     // crop so image center remains in the center
            g2d.drawImage(scIm, 0, y, null);
            g2d.dispose();
        } else   // do nothing
        {
            croppedImage = scIm;
        }
        // System.out.println("Cropped Image (w, h): (" + croppedImage.getWidth() + ", " + croppedImage.getHeight() + ")");
        return croppedImage;
    }

    /**
     * Scale the image either horizontally or vertically
     * depending on which screen-dimension/image-dimension ratio is larger, so the image
     * becomes as large as the screen in one dimension and maybe bigger in the other dimension.
     */
    public static BufferedImage scaleImage(BufferedImage im) {
        int imWidth = im.getWidth();
        int imHeight = im.getHeight();

        // calculate screen-dimension/image-dimension for width and height
        double widthRatio = screenWidth / (double) imWidth;
        double heightRatio = screenHeight / (double) imHeight;

        double scale = Math.max(widthRatio, heightRatio);
        // scale is the largest screen-dimension/image-dimension

        // calculate new image dimensions which fit the screen (or makes the image bigger)
        int scWidth = (int) (imWidth * scale);
        int scHeight = (int) (imHeight * scale);

        // resize the image
        BufferedImage scaledImage = new BufferedImage(scWidth, scHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = scaledImage.createGraphics();
        AffineTransform at = AffineTransform.getScaleInstance(scale, scale);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.drawImage(im, at, null);
        g2d.dispose();
        return scaledImage;
    }



}
