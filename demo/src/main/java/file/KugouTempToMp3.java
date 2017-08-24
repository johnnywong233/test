package file;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2016/11/19
 * Time: 0:29
 */
public class KugouTempToMp3 {
    private static String KGTEMP = ".kgtemp";
    private static String KRC = "krc";

    /*
     * Change(string,string) 方法只是用来测试用的。调一下字符串之类的
     * 主要使用ChangeByDir方法，参数是临时文件的文件夹和歌词文件的文件夹
     */
    //TODO
    private void change(String tempPath, String krcPath) {
        File temp = new File(tempPath);
        File krc = new File(krcPath);
        if (temp.exists() && temp.getName().endsWith(KGTEMP)) {
            String filename = temp.getName();
            String fileMd5 = filename
                    .substring(0, filename.lastIndexOf(KGTEMP));
            if (!krc.exists())
                return;
            String krcName = krc.getName();
            String krcMd5 = krcName.substring(krcName.lastIndexOf("-") + 1,
                    krcName.lastIndexOf(KRC) - 1);
            String mp3name = krcName.substring(0, krcName.lastIndexOf("-"));
            if (krcMd5.equals(fileMd5)) {
                String path = temp.getPath().substring(0,
                        temp.getPath().lastIndexOf("\\"));
                File mp3File = new File(path + "\\" + mp3name + ".mp3");
                temp.renameTo(mp3File);
            }
            System.out.println(filename + " " + fileMd5);
            System.out.println(krcName + " " + mp3name + " " + krcMd5);
        }
    }

    private void changeByDir(String tempPath, String krcPath) {
        Map<String, File> temps = fileMd5Map(tempPath);
        Map<String, String> mp3Names = krcNameMd5Map(krcPath);
        for (String key : temps.keySet()) {
            File f = temps.get(key);
            if (f.exists()) {
                String path = f.getPath().substring(0,
                        f.getPath().lastIndexOf("\\"));
                String mp3Name = mp3Names.get(key);
                File mp3File = new File(path + "\\" + mp3Name + ".mp3");
                if (f.renameTo(mp3File)) {
                    System.out.println(f.getName() + " to " + mp3File.getName() + "  success");
                }
            }
        }
    }

    private Map<String, File> fileMd5Map(String path) {
        File dirFile = new File(path);
        Map<String, File> map = null;
        if (dirFile.isDirectory()) {
            map = new HashMap<>();
            for (File f : dirFile.listFiles()) {
                if (f.exists() && f.isFile() && f.getName().endsWith(KGTEMP)) {
                    String filename = f.getName();
                    String filemd5 = filename.substring(0,
                            filename.lastIndexOf(KGTEMP));
                    map.put(filemd5, f);
                }
            }
        }
        return map;
    }

    private Map<String, String> krcNameMd5Map(String path) {
        File dirFile = new File(path);
        Map<String, String> map = null;
        if (dirFile.isDirectory()) {
            map = new HashMap<>();
            for (File f : dirFile.listFiles()) {
                if (f.exists() && f.isFile() && f.getName().endsWith(KRC)) {
                    String krcName = f.getName();
                    if (!krcName.contains("-")) continue;
                    String krcNd5 = krcName.substring(krcName.lastIndexOf("-") + 1,
                            krcName.lastIndexOf(KRC) - 1);
                    String mp3name = krcName.substring(0, krcName.lastIndexOf("-"));
                    map.put(krcNd5, mp3name);
                }
            }
        }
        return map;
    }

    //http://www.jb51.net/article/90812.htm
    public static void main(String[] args) {
        KugouTempToMp3 test = new KugouTempToMp3();
        String tempPath = "G:\\KuGou\\Temp\\ba8cf32bf3d265653cd14fb6690c2149.kgtemp";
        String krcPath = "G:\\KuGou\\Lyric\\周杰伦 - 超跑女神-ba8cf32bf3d265653cd14fb6690c2149-16053594-00000000.krc";
        test.change(tempPath, krcPath);
        String tempDir = "G:\\KuGou\\Temp";
        String krcDir = "G:\\KuGou\\Lyric";
        test.changeByDir(tempDir, krcDir);
    }
}