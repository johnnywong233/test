package io.file.excel;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class JxlTest  {
	public static void main(String[] args) throws Exception {
		writeExcel("test.xls");
		System.out.println(readExcel("test.xls"));
	}

	public static void writeExcel(String filename) throws IOException {
		WritableWorkbook wwb = null;
		wwb = Workbook.createWorkbook(new File(filename));
		WritableSheet sheet = wwb.createSheet("sheet1", 0);
		
		for(int i=0;i<10;i++){
			for(int j=0;j<5;j++){
				Label label = new Label(j,i,"���ǵ�"+(i+1)+"�У���"+(j+1)+"��");
				try {
					sheet.addCell(label);
				} catch (RowsExceededException e) {
					e.printStackTrace();
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
	
	public static String readExcel(String filename){
		StringBuffer sb = new StringBuffer();
		Workbook wb =null;

		try {
			wb=Workbook.getWorkbook(new File(filename));
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(wb==null) return null;
		
		Sheet[] sheets = wb.getSheets();
		if(sheets!=null && sheets.length>0){
			for(int i=0;i<sheets.length;i++){
				int rowNum = sheets[i].getRows();
				for(int j=0;j<rowNum;j++){
					Cell[] cells = sheets[i].getRow(j);
					if(cells!=null && cells.length>0){
						for(int k=0;k<cells.length;k++){
							String cellValue = cells[k].getContents();
							sb.append(cellValue + "\t");
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

	/**
	 * ��excel�в���ͼƬ
	 * @param sheet ������ı�
	 * @param col	ͼƬ�Ӹ��п�ʼ
	 * @param row	ͼƬ�Ӹ��п�ʼ
	 * @param width	ͼƬ��ռ������
	 * @param height	ͼƬ��ռ������
	 * @param imageFile Ҫ�����ͼƬ�ļ�
	 */
	public static void insertImg(WritableSheet sheet,int col,int row,int width,
			int height,File imageFile){
		WritableImage image = new WritableImage(col,row,width,height,imageFile);
		sheet.addImage(image);
	}
}

