package awt;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Objects;

/**
 * Author: Johnny
 * Date: 2016/12/4
 * Time: 22:35
 */
public class PadFrame extends Frame {
	private static final long serialVersionUID = -5456121244317864770L;
	private MenuItem[] miFile;
    private TextArea ta;
    final private Frame frame = this;

    /**
     * The inner class
     * Message Handle
     */
    private class EventExit implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class SystemExit extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    private class EventMenuClose implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ta.setText(null);
        }
    }

    private  class EventOpenFile implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //Create the OpenFile Dialog
            FileDialog dlg = new FileDialog(frame, "Open Files", FileDialog.LOAD);
            dlg.show();

            String strPath;
            if ((strPath = dlg.getDirectory()) != null) {
                //get the full path of the selected file
                strPath += dlg.getFile();

                //open the file
                try {
                    FileInputStream fis = new FileInputStream(strPath);
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    byte[] buf = new byte[3000];
                    int len = bis.read(buf);

                    ta.append(new String(buf, 0, len));
                    bis.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Construction Method
     * Adding Menu and TextArea components
     */
    private PadFrame(String strTitle) {
        super(strTitle);
        this.setLocation(400, 200);
        this.setSize(900, 630);

        //Create the Menu Bar
        MenuBar mb = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        miFile = new MenuItem[]{new MenuItem("Open"), new MenuItem("Close"), new MenuItem("Exit")};
        this.setMenuBar(mb);
        mb.add(menuFile);
        mb.add(menuEdit);
        for (MenuItem aMiFile : miFile) {
            menuFile.add(aMiFile);
        }
        //Add event handle
        setMenuEventHandle(new EventExit(), "File", 2);
        setMenuEventHandle(new EventOpenFile(), "File", 0);
        setMenuEventHandle(new EventMenuClose(), "File", 1);
        this.addWindowListener(new SystemExit());

        //add the TextArea component
        ta = new TextArea(30, 30);
        this.add(ta);
    }

    private void setMenuEventHandle(ActionListener al, String strMenu, int index) {
        if (Objects.equals(strMenu, "File")) {
            miFile[index].addActionListener(al);
        }
    }

    public int getMenuItemAmount(String strMenu) {
        if (Objects.equals("File", strMenu)) {
            return miFile.length;
        }
        return -1;
    }

    //http://www.jb51.net/article/64257.htm
    public static void main(String[] args) {
        PadFrame f = new PadFrame("NotePad");
        f.show();
    }
}
