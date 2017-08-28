package awt;

import javax.imageio.ImageIO;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
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
 * Created by wajian on 2016/8/28.
 */

public class PictureViewer implements ActionListener {
    private Frame frame;
    private MyCanvas mc;
    private String fpath;
    private String fname;
    private File[] files;
    private int findex;
    private FileDialog fd_load;
    private MyFilter filter;
    private Button previous;
    private Button next;

    //http://www.phpxs.com/code/1001508/
    public static void main(String args[]) throws Exception {
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
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setVisible(true);
        this.validateButton();
        filter = new MyFilter();
        fd_load = new FileDialog(frame, "Open file", FileDialog.LOAD);
        fd_load.setFilenameFilter(filter);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "choose pic":
                fd_load.setVisible(true);
                fpath = fd_load.getDirectory();
                fname = fd_load.getFile();
                if ((fpath != null) && (fname != null)) {
                    this.display(new File(fpath + fname));
                    files = new File(fpath).listFiles(filter);
                    this.setIndex();
                }
                break;
            case "previous":
                findex--;
                if (findex < 0)
                    findex = 0;
                this.display(files[findex]);
                break;
            case "next":
                findex++;
                if (findex >= files.length)
                    findex = files.length - 1;
                this.display(files[findex]);
                break;
        }
        this.validateButton();
    }

    public void display(File f) {
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
    private int image_width;
    private int image_height;

    public void setImage(BufferedImage bi) {
        this.bi = bi;
        this.zoom();
    }

    public void paint(Graphics g) {
        g.drawImage(im, (this.getWidth() - image_width) / 2, (this.getHeight() - image_height) / 2, this);
    }

    public void componentResized(ComponentEvent e) {
        if (bi != null) {
            System.out.println("resize!!");

            this.zoom();
            this.repaint();
        }
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    private void zoom() {
        if (bi == null)
            return;
        int screen_width = this.getWidth();
        int screen_height = this.getHeight();
        double screen_proportion = 1.0 * screen_height / screen_width;
        System.out.println("screen: w " + screen_width + " ,h " + screen_height + " ,p0 " + screen_proportion);

        image_width = bi.getWidth(this);
        image_height = bi.getHeight(this);
        double image_proportion = 1.0 * image_height / image_width;
        System.out.println("image: w " + image_width + " ,h " + image_height + " ,p1 " + image_proportion);

        if (image_proportion > screen_proportion) {
            image_height = screen_height;
            image_width = (int) (image_height / image_proportion);
            System.out.println("  p1>p0  w= " + image_width);
        } else {
            image_width = screen_width;
            image_height = (int) (image_width * image_proportion);
            System.out.println("  p0>p1  h= " + image_height);
        }
        im = bi.getScaledInstance(image_width, image_height, Image.SCALE_SMOOTH);
    }
}

class MyFilter implements FilenameFilter {
    private String[] extension;

    MyFilter() {
        extension = new String[]{".jpg", ".JPG", ".gif", ".GIF", ".png", ".PNG", ".jpeg", ".JPEG"};
    }

    public MyFilter(String[] extension) {
        this.extension = extension;
    }

    public boolean accept(File dir, String name) {
        for (String s : extension) {
            if (name.endsWith(s)) {
                return true;
            }
        }
        return false;
    }
}

