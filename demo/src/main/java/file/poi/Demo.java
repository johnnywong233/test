package file.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by johnny on 2016/10/1.
 * demo of poi on Excel
 */
public class Demo {
    //http://www.hollischuang.com/archives/31
    public static void main(String[] args) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("new sheet");
        HSSFRow row = sheet.createRow((short) 2);
        createCell(wb, row, (short) 0, HorizontalAlignment.CENTER);
        createCell(wb, row, (short) 1, HorizontalAlignment.CENTER_SELECTION);
        createCell(wb, row, (short) 2, HorizontalAlignment.FILL);
        createCell(wb, row, (short) 3, HorizontalAlignment.GENERAL);
        createCell(wb, row, (short) 4, HorizontalAlignment.JUSTIFY);
        createCell(wb, row, (short) 5, HorizontalAlignment.LEFT);
        createCell(wb, row, (short) 6, HorizontalAlignment.RIGHT);
        FileOutputStream fileOut = new FileOutputStream("D:\\Java_ex\\test\\src\\test\\resources\\workbook.xls");
        wb.write(fileOut);
        fileOut.close();
    }

    /**
     * Creates a cell and aligns it a certain way.
     *
     * @param wb     the workbook
     * @param row    the row to create the cell in
     * @param column the column number to create the cell in
     * @param align  the alignment for the cell.
     */
    private static void createCell(HSSFWorkbook wb, HSSFRow row, short column, HorizontalAlignment align) {
        HSSFCell cell = row.createCell(column);
        cell.setCellValue("Align It");
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(align);
        cell.setCellStyle(cellStyle);
    }
}
