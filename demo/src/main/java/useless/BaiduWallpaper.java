//package useless;
///**
// * Load a series of search words from a file supplied on the command line, and randomly choose one to  use as a query to Google's image search.
// * <p>
// * The search results are listed (description and URL), and one is randomly selected. The URL's image is downloaded and
// * saved as a local BMP file (in WALL_FNM). The image may be scaled and cropped to better fit the screen size.
// * <p>
// * Then JNA (https://jna.dev.java.net/) is used to update the Win32 registry's
// * wallpaper information, and to refresh the desktop without requiring a system reboot.
// */
//
//import com.sun.jna.Native;
//import com.sun.jna.platform.win32.Advapi32Util;
//import com.sun.jna.platform.win32.WinReg;
//import com.sun.jna.win32.StdCallLibrary;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import utils.ImageUtil;
//import utils.WebUtils;
//
//import javax.imageio.ImageIO;
//import java.awt.Dimension;
//import java.awt.Toolkit;
//import java.awt.image.BufferedImage;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.Random;
//
//public class BaiduWallpaper {
//    private static final String DEFAULT_SEARCH = "nature";
//    // used as the search word if no words file is available
//
//    private static final int MAX_TRIES = 5;     // max number of times to try download an image
//    //Change to bmp format if OS version < Windows 7
//    private static final String WALL_FORMAT = "jpg";
//    private static final String WALL_FNM = "wallpaper.jpg";    // the name of the wallpaper file
//
//    private static Random rand;    // for selecting a wallpaper at random
//    private static double screenWidth, screenHeight;  // for resizing the wallpaper
//
//    public static void main(String args[]) {
//        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//        screenWidth = dim.getWidth();
//        screenHeight = dim.getHeight();
//
//        rand = new Random();
//
//        //String searchWord = selectSearchWord(args[0]); // choose a search word
//        JSONObject json = imageSearch("美女");     // get search results for the word
//
//        BufferedImage im = selectImage(json);         // select a result and download its image
//        if (im != null) {
//            BufferedImage scaleIm = ImageUtil.scaleImage(im);     // scale
//            BufferedImage cropIm = ImageUtil.cropImage(scaleIm);  // crop
//
//            saveWallPaper(WALL_FNM, cropIm);    // save image as a BMP in WALL_FNM
//            installWallpaper(WALL_FNM);   // make WALL_FNM the new desktop wallpaper
//        }
//    }
//
//    // load the search words from wordsFnm and select one at random
//    @SuppressWarnings("unused")
//    private static String selectSearchWord(String wordsFnm) {
//        ArrayList<String> words = loadSearchWords(wordsFnm);
//        if (words.size() == 0)
//            return DEFAULT_SEARCH;
//        int idx = rand.nextInt(words.size());
//        return words.get(idx);
//    }
//
//    /**
//     * The search-words file format are lines of:
//     * <search word/phrase>
//     * and blank lines and comment lines.
//     * Return the words as a list.
//     */
//    private static ArrayList<String> loadSearchWords(String wordsFnm) {
//        ArrayList<String> words = new ArrayList<>();
//        System.out.println("Reading file: " + wordsFnm);
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(wordsFnm));
//            String line;
//            while ((line = br.readLine()) != null) {
//                line = line.toLowerCase().trim();
//                if (line.length() == 0)  // blank line
//                    continue;
//                if (line.startsWith("//"))   // comment
//                    continue;
//                words.add(line);
//            }
//            br.close();
//        } catch (IOException e) {
//            System.out.println("Error reading file: " + wordsFnm);
//        }
//        return words;
//    }
//
//    /**
//     * Query Google's image search for "phrase" (a word or words), getting back a
//     * list of image URLs in JSON format.
//     * <p>
//     * The query uses Google's AJAX Search API and its REST-based interface.
//     * For details, see
//     * http://code.google.com/apis/ajaxsearch/documentation/reference.html#_intro_fonje
//     */
//    private static JSONObject imageSearch(String phrase) {
//        System.out.println("Searching baidu Image for \"" + phrase + "\"");
//        try {
//            // Convert spaces to +, etc. to make a valid URL
//            String uePhrase = URLEncoder.encode("\"" + phrase + "\"", "UTF-8");
//            //http://image.baidu.com/channel/listjson?pn=0&rn=30&tag1=%E5%A3%81%E7%BA%B8&tag2=%E9%A3%8E%E6%99%AF&ie=utf8
//            String urlStr = "http://image.baidu.com/channel/listjson?pn=0&rn=30&tag1=%E5%A3%81%E7%BA%B8&tag2=%E9%A3%8E%E6%99%AF&ie=utf8";
//
//            String jsonStr = WebUtils.webGetString(urlStr);   // get response as a JSON string
//            //System.out.println("-------------------");
//            System.out.println(jsonStr);
//            //System.out.println("-------------------");
//            return new JSONObject(jsonStr);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * list the image URLs returned by Google, then try to download one
//     * returning it as a BufferedImage
//     */
//    private static BufferedImage selectImage(JSONObject json) {
//        try {
//            // WebUtils.saveString("temp.json", json.toString(2) );    // save, and indent the string
//            // useful for debugging
//
//            System.out.println("\nTotal no. of possible results: " +
//                    json.getString("totalNum") + "\n");
//
//            // list the search results and then download one of their images
//            int count = json.getInt("return_number");
//            JSONArray jaResults = json.getJSONArray("data");
//            showResults(count, jaResults);
//            if (jaResults.length() > 0) {
//                return tryDownloadingImage(count, jaResults);
//            }
//        } catch (JSONException e) {
//            System.out.println(e);
//            System.exit(1);
//        }
//        return null;
//    }
//
//    // for each result, list its contents title and URL
//    private static void showResults(int count, JSONArray jaResults) throws JSONException {
//        for (int i = 0; i < count; i++) {
//            System.out.print((i + 1) + ". ");
//            JSONObject j = jaResults.getJSONObject(i);
//            String content = j.getString("abs");
//            String cleanContent = content.replaceAll("\\s+", " ").
//                    trim();
//            // replace non-alphanumerics with spaces; remove multiple spaces
//            System.out.println("Content: " + cleanContent);
//            System.out.println("       URL: " + j.getString("obj_url") + "\n");
//        }
//    }
//
//    /**
//     * Download an image chosen at random from the list returned by Google.
//     * This is complicated by the possibility that a URL is unavailable. In that
//     * case the method tries again, hopefully with a different image,
//     * for up to MAX_TRIES time.
//     */
//    private static BufferedImage tryDownloadingImage(int count, JSONArray jaResults) throws JSONException {
//        BufferedImage im = null;
//        for (int i = 0; i < MAX_TRIES; i++) {
//            int idx = rand.nextInt(count);    // select an image index at random
//            System.out.println("Randomly selected no. " + (idx + 1));
//            String imUrlStr = jaResults.getJSONObject(idx).getString("obj_url");   // get its URL
//            im = getURLImage(imUrlStr);    // download the URL (maybe)
//            if (im != null)
//                return im;
//        }
//        // should not get here unless there's a problem
//        System.out.println("No suitable image found");
//        return im;
//    }
//
//    // download the image at urlStr
//    private static BufferedImage getURLImage(String urlStr) {
//        System.out.println("Downloading image at:\n\t" + urlStr);
//        BufferedImage image = null;
//        try {
//            image = ImageIO.read(new URL(urlStr));
//        } catch (IOException e) {
//            System.out.println("Problem downloading");
//        }
//        return image;
//    }
//
//    // save the image as a BMP in <fnm>
//    private static void saveWallPaper(String fnm, BufferedImage im) {
//        System.out.println("Saving image to " + fnm);
//        try {
//            ImageIO.write(im, WALL_FORMAT, new File(fnm));
//        } catch (IOException e) {
//            System.out.println("Could not save file");
//        }
//    }
//
//    /**
//     * Wallpaper installation requires three changes to thw Win32
//     * registry, and a desktop refresh. The basic idea (using Visual C# and VB)
//     * is explained in "Setting Wallpaper" by Sean Campbell:
//     * http://blogs.msdn.com/coding4fun/archive/2006/10/31/912569.aspx
//     */
//    private static void installWallpaper(String fnm) {
//        try {
//            String fullFnm = new File(".").getCanonicalPath() + "\\" + fnm;
//            // System.out.println("Full fnm: " + fullFnm);
//
//      /* 3 registry key changes to HKEY_CURRENT_USER\Control Panel\Desktop
//         These three keys (and many others) are explained at
//            http://www.virtualplastic.net/html/desk_reg.html
//
//         List of registry functions at MSDN:
//                http://msdn.microsoft.com/en-us/library/ms724875(v=VS.85).aspx
//      */
//            Advapi32Util.registrySetStringValue(WinReg.HKEY_CURRENT_USER,
//                    "Control Panel\\Desktop", "Wallpaper", fullFnm);
//            //WallpaperStyle = 10 (Fill), 6 (Fit), 2 (Stretch), 0 (Tile), 0 (Center)
//            //For windows XP, change to 0
//            Advapi32Util.registrySetStringValue(WinReg.HKEY_CURRENT_USER,
//                    "Control Panel\\Desktop", "WallpaperStyle", "10"); //fill
//            Advapi32Util.registrySetStringValue(WinReg.HKEY_CURRENT_USER,
//                    "Control Panel\\Desktop", "TileWallpaper", "0");   // no tiling
//
//            // refresh the desktop using User32.SystemParametersInfo(), so avoiding an OS reboot
//            int SPI_SETDESKWALLPAPER = 0x14;
//            int SPIF_UPDATEINIFILE = 0x01;
//            int SPIF_SENDWININICHANGE = 0x02;
//
//            boolean result = MyUser32.INSTANCE.SystemParametersInfoA(SPI_SETDESKWALLPAPER, 0,
//                    fullFnm, SPIF_UPDATEINIFILE | SPIF_SENDWININICHANGE);
//            System.out.println("Refresh desktop result: " + result);
//        } catch (IOException e) {
//            System.out.println("Could not find directory path");
//        }
//    }
//
//    /**
//     * JNA Win32 extensions includes a User32 class, but it doesn't contain
//     * SystemParametersInfo(), so it must be defined here.
//     * <p>
//     * MSDN libary docs on SystemParametersInfo() are at:
//     * http://msdn.microsoft.com/en-us/library/ms724947(VS.85).aspx
//     * <p>
//     * BOOL WINAPI SystemParametersInfo(
//     * __in     UINT uiAction,
//     * __in     UINT uiParam,
//     * __inout  PVOID pvParam,
//     * __in     UINT fWinIni );
//     * <p>
//     * When uiAction == SPI_SETDESKWALLPAPER, SystemParametersInfo() sets the desktop wallpaper.
//     * The value of the pvParam parameter determines the new wallpaper.
//     */
//    private interface MyUser32 extends StdCallLibrary {
//        MyUser32 INSTANCE = (MyUser32) Native.loadLibrary("user32", MyUser32.class);
//
//        boolean SystemParametersInfoA(int uiAction, int uiParam, String fnm, int fWinIni);
//        // SystemParametersInfoA() is the ANSI name used in User32.dll
//    }
//
//}
