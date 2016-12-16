package awt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CaptureScreen {
    /*
     * http://coolshell.cn/articles/889.html
     * 仅仅只是实现整个桌面的截屏，相当于Windows的Alt + PrintScreen 功能
     */
    //TODO
    private CaptureScreen(String fileName) throws AWTException, IOException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(screenRectangle);
        ImageIO.write(image, "png", new File(fileName));
    }

    public static void main(String args[]) throws AWTException, IOException {
        CaptureScreen cs = new CaptureScreen("C:\\Users\\wajian\\Documents\\Test\\t.png");
    }
}
