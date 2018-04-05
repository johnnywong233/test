package servlet.loginValidate;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 生成验证码 的 Servlet
 */
public class CodeMakerServlet extends HttpServlet {
    private static final long serialVersionUID = 4612235776159128501L;

    private Font[] codeFont =
            {
                    new Font("Algerian", Font.BOLD, 65),
                    new Font("Vivaldi", Font.BOLD, 85),
                    new Font("Broadway", Font.BOLD, 60),
                    new Font("Forte", Font.BOLD, 75)
            };

    private Color[] color =
            {
                    Color.BLACK, Color.RED, Color.DARK_GRAY, Color.BLUE
            };

    private String codeNumbers = "";

    private int width = 250, height = 70;

    // 处理 HTTP get 请求
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 清空缓冲区 
        response.reset();

        response.setContentType("image/png");

        //设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // create a image of 250X70
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 得到图形环境对象 g
        Graphics g = image.getGraphics();

        // 填充背景
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, width, height);

        for (int i = 0; i < 4; i++) {
            drawCode(g, i);
        }

        drawNoise(g, 30);

        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);

        // 将验证码内容保存进session中
        HttpSession session = request.getSession(true);
        session.setAttribute("codeNumbers", codeNumbers);

        //重设字符串
        codeNumbers = "";

        // 利用ImageIO类的write方法对图像进行编码
        ServletOutputStream sos = response.getOutputStream();
        ImageIO.write(image, "PNG", sos);
        sos.close();
    }

    private void drawCode(Graphics graphics, int i) {
        int number = (int) (Math.random() * 10);
        graphics.setFont(codeFont[i]);
        graphics.setColor(color[i]);
        graphics.drawString("" + number, 10 + i * 60, 60);
        codeNumbers += number;
    }

    private void drawNoise(Graphics graphics, int lineNumber) {
        graphics.setColor(Color.YELLOW);
        for (int i = 0; i < lineNumber; i++) {
            int pointX1 = 1 + (int) (Math.random() * width);
            int pointY1 = 1 + (int) (Math.random() * height);
            int pointX2 = 1 + (int) (Math.random() * width);
            int pointY2 = 1 + (int) (Math.random() * height);
            graphics.drawLine(pointX1, pointY1, pointX2, pointY2);
        }
    }

    // 处理 HTTP post 请求, 和doGet一样
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}