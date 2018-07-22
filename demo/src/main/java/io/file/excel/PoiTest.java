package io.file.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PoiTest  {
	public static void main(String[] args) throws Exception {
		PoiTest t = new PoiTest();
		t.writeExcel("test.xls");
		t.readExcel("test.xls");
	}
	
	@SuppressWarnings("resource")
	public void writeExcel(String filename) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("JSP");
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellType(CellType.STRING);
		cell.setCellValue("����");
		
		cell = row.createCell(1);
		cell.setCellType(CellType.STRING);
		cell.setCellValue("�༭");
		
		FileOutputStream out = new FileOutputStream(filename);
		workbook.write(out);
		out.close();
	}
	
	@SuppressWarnings("resource")
	public void readExcel(String filename) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
		HSSFSheet sheet = workbook.getSheet("JSP");
//		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFRow row = sheet.getRow(0);
		HSSFCell cell = row.getCell(0);
		System.out.println(cell.getStringCellValue());
		cell = row.getCell(1);
		System.out.println(cell.getStringCellValue());
	}
}
