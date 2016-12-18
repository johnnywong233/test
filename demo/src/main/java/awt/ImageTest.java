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
	/**
	 * 生成缩略图 
	 * fromFileStr:源图片路径 
	 * saveToFileStr:缩略图路径 
	 */
	public static void saveImageAsJpg(String fromFileStr, String saveToFileStr,
			int width, int height) throws Exception {
		BufferedImage srcImage;
		String imgType = "JPEG";
		if (fromFileStr.toLowerCase().endsWith(".png")){
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

	/**
	 * 将源图片的BufferedImage对象生成缩略图 
	 * source:源图片的BufferedImage对象 
	 */
	public static BufferedImage resize(BufferedImage source, int targetW,
			int targetH) {
		int type = source.getType();
		BufferedImage target;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		
		// 这里想实现在targetW,targetH范围内实现等比例的缩放，
		// 如果不需要等比例的缩放则将下面的if else语句注释掉即可。
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

	public static void main(String args[]) {
		try {
			ImageTest.saveImageAsJpg(
					"C:\\Users\\wajian\\Documents\\Test\\KatyPerry.jpg",
					"C:\\Users\\wajian\\Documents\\Test\\loveKatyPerry.jpg", 800, 600);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
