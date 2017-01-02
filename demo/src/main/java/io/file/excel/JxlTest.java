package io.file.excel;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;

public class JxlTest {
    public static void main(String[] args) throws Exception {
        writeExcel("test.xls");
        System.out.println(readExcel("test.xls"));
    }

    public static void writeExcel(String filename) throws IOException {
        WritableWorkbook wwb;
        wwb = Workbook.createWorkbook(new File(filename));
        WritableSheet sheet = wwb.createSheet("sheet1", 0);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                Label label = new Label(j, i, "���ǵ�" + (i + 1) + "�У���" + (j + 1) + "��");
                try {
                    sheet.addCell(label);
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            wwb.write();
            wwb.close();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    public static String readExcel(String filename) {
        StringBuilder sb = new StringBuilder();
        Workbook wb = null;
        try {
            wb = Workbook.getWorkbook(new File(filename));
        } catch (BiffException | IOException e) {
            e.printStackTrace();
        }

        if (wb == null) return null;
        Sheet[] sheets = wb.getSheets();
        if (sheets != null && sheets.length > 0) {
            for (Sheet sheet : sheets) {
                int rowNum = sheet.getRows();
                for (int j = 0; j < rowNum; j++) {
                    Cell[] cells = sheet.getRow(j);
                    if (cells != null && cells.length > 0) {
                        for (Cell cell : cells) {
                            String cellValue = cell.getContents();
                            sb.append(cellValue).append("\t");
                        }
                    }
                    sb.append("\r\n");
                }
                sb.append("\r\n");
            }
        }
        wb.close();
        return sb.toString();
    }

    public static void insertImg(WritableSheet sheet, int col, int row, int width,
                                 int height, File imageFile) {
        WritableImage image = new WritableImage(col, row, width, height, imageFile);
        sheet.addImage(image);
    }
}

