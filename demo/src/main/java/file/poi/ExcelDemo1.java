package file.poi;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by johnny on 2016/8/14.
 * simple use of poi to handle excel
 */
public class ExcelDemo1 {
    //http://www.jb51.net/article/84694.htm
    public static void main(String[] args) {
        try {
            FileInputStream fileInputStream = new FileInputStream(
                    "E:" + File.separator + "Java_ex" + File.separator
                            + "test" + File.separator + "src" + File.separator
                            + "test" + File.separator + "resources" + File.separator + "user.xls");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            POIFSFileSystem fileSystem = new POIFSFileSystem(bufferedInputStream);
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(fileSystem);
            //get the desired sheet according to name
            HSSFSheet sheet = hssfWorkbook.getSheet("user");
            // in fact, we can use sheet.rowIterator()
            for (int i = 1; ; i++) {
                HSSFRow row = sheet.getRow(i);
                if (row != null) {
                    String nameString1 = row.getCell(0).getStringCellValue();
                    String password = row.getCell(i).getStringCellValue();
                    System.out.println("name:" + nameString1);
                    System.out.println("password:" + password);
                    bufferedInputStream.close();
                } else {
                    bufferedInputStream.close();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
