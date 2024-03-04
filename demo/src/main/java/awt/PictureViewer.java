package awt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by johnny on 2016/8/28.
 */
public class PictureViewer implements ActionListener {
    private Frame frame;
    private MyCanvas mc;
    private String fpath;
    private String fname;
    private File[] files;
    private int findex;
    private FileDialog fileDialog;
    private MyFilter filter;
    private Button previous;
    private Button next;

    //http://www.phpxs.com/code/1001508/
    public static void main(String[] args) {
        new PictureViewer().init();
    }

    public void init() {
        frame = new Frame("PictureViewer");
        Panel pb = new Panel();
        Button select = new Button("choose pic");
        previous = new Button("previous");
        next = new Button("next");
        select.addActionListener(this);
        previous.addActionListener(this);
        next.addActionListener(this);
        pb.add(select);
        pb.add(previous);
        pb.add(next);
        mc = new MyCanvas();
        mc.setBackground(new Color(200, 210, 230));
        mc.addComponentListener(mc);
        frame.add(pb, "North");
        frame.add(mc, "Center");
        frame.setSize(360, 360);
        frame.setLocation(400, 200);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setVisible(true);
        this.validateButton();
        filter = new MyFilter();
        fileDialog = new FileDialog(frame, "Open file", FileDialog.LOAD);
        fileDialog.setFilenameFilter(filter);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "choose pic":
                fileDialog.setVisible(true);
                fpath = fileDialog.getDirectory();
                fname = fileDialog.getFile();
                if ((fpath != null) && (fname != null)) {
                    this.display(new File(fpath + fname));
                    files = new File(fpath).listFiles(filter);
                    this.setIndex();
                }
                break;
            case "previous":
                findex--;
                if (findex < 0) {
                    findex = 0;
                }
                this.display(files[findex]);
                break;
            case "next":
                findex++;
                if (findex >= files.length) {
                    findex = files.length - 1;
                }
                this.display(files[findex]);
                break;
            default:
        }
        this.validateButton();
    }

    private void display(File f) {
        try {
            BufferedImage bi = ImageIO.read(f);
            mc.setImage(bi);
            frame.setTitle("PictureViewer - [" + f.getName() + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        mc.repaint();
    }

    private void setIndex() {
        File current = new File(fpath + fname);
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (current.equals(files[i])) {
                    findex = i;
                }
            }
        }
    }

    private void validateButton() {
        previous.setEnabled((files != null) && (findex > 0));
        next.setEnabled((files != null) && (findex < (files.length - 1)));
    }
}

class MyCanvas extends Canvas implements ComponentListener {

    private static final long serialVersionUID = 5887356943976687289L;
    private BufferedImage bi;
    private Image im;
    private int imageWidth;
    private int imageHeight;

    public void setImage(BufferedImage bi) {
        this.bi = bi;
        this.zoom();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(im, (this.getWidth() - imageWidth) / 2, (this.getHeight() - imageHeight) / 2, this);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if (bi != null) {
            System.out.println("resize!!");

            this.zoom();
            this.repaint();
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    private void zoom() {
        if (bi == null) {
            return;
        }
        int screenWidth = this.getWidth();
        int screenHeight = this.getHeight();
        double screenProportion = 1.0 * screenHeight / screenWidth;
        System.out.println("screen: w " + screenWidth + " ,h " + screenHeight + " ,p0 " + screenProportion);

        imageWidth = bi.getWidth(this);
        imageHeight = bi.getHeight(this);
        double imageProportion = 1.0 * imageHeight / imageWidth;
        System.out.println("image: w " + imageWidth + " ,h " + imageHeight + " ,p1 " + imageProportion);

        if (imageProportion > screenProportion) {
            imageHeight = screenHeight;
            imageWidth = (int) (imageHeight / imageProportion);
            System.out.println("  p1>p0  w= " + imageWidth);
        } else {
            imageWidth = screenWidth;
            imageHeight = (int) (imageWidth * imageProportion);
            System.out.println("  p0>p1  h= " + imageHeight);
        }
        im = bi.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
    }
}

class MyFilter implements FilenameFilter {
    private final String[] extension;

    MyFilter() {
        extension = new String[]{".jpg", ".JPG", ".gif", ".GIF", ".png", ".PNG", ".jpeg", ".JPEG"};
    }

    @Override
    public boolean accept(File dir, String name) {
        for (String s : extension) {
            if (name.endsWith(s)) {
                return true;
            }
        }
        return false;
    }
}

