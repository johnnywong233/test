package file.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Created by johnny on 2016/8/14.
 */
public class ExcelDemo {
    /**
     * java create excel file then write into disk
     * http://www.jb51.net/article/84694.htm
     */
    public static void main(String[] args) {
        //windows和Linux的斜杠不一样，而且java对于“/”需要转义处理，File.separator可以实现跨平台
    	//E:\Java_ex\test\src\test\resources
        File file = new File("E:" + File.separator + "Java_ex" + File.separator
                + "test" + File.separator + "src" + File.separator
                + "test" + File.separator + "resources" + File.separator + "user.xls");
        try {
            OutputStream outputStream = new FileOutputStream(file);
            //create an excel file, HSSF works for lower than excel 2007
            //for newer than excel 2007, use XSSF
            HSSFWorkbook workbook = new HSSFWorkbook();
            //create an excel sheet
            HSSFSheet sheet = workbook.createSheet("user");
            HSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("name");
            row.createCell(1).setCellValue("password");
            workbook.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
