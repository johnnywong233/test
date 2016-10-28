package file.poi;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

import static file.poi.POIUtils.setCellText;
import static file.poi.POIUtils.setText;

/**
 * Created by johnny on 2016/10/7.
 * set border for table in word through poi
 */
public class WordTableBorder {
    //http://53873039oycg.iteye.com/blog/2152008
    public static void main(String[] args) throws Exception {
        WordTableBorder t = new WordTableBorder();
        XWPFDocument document = new XWPFDocument();
        t.createSimpleTableWithBdColor(document);
        t.addNewPage(document, BreakType.TEXT_WRAPPING);
        t.createSimpleTableNormal(document);
        t.addNewPage(document, BreakType.TEXT_WRAPPING);
        t.createSimpleTableWithNotBd(document);
        t.saveDocument(document, "D:\\Java_ex\\test\\src\\test\\resources\\" + System.currentTimeMillis() + ".docx");
    }

    //self-defined border
    private void createSimpleTableWithBdColor(XWPFDocument doc) throws Exception {
        List<String> columnList = new ArrayList<>();
        columnList.add("ID");
        columnList.add("姓名信息|姓甚|名谁");
        columnList.add("名刺信息|籍贯|营生");
        XWPFTable table = doc.createTable(2, 5);
        CTTblBorders borders = table.getCTTbl().getTblPr().addNewTblBorders();
        CTBorder hBorder = borders.addNewInsideH();
        hBorder.setVal(STBorder.Enum.forString("dashed"));
        hBorder.setSz(new BigInteger("1"));
        hBorder.setColor("0000FF");
        CTBorder vBorder = borders.addNewInsideV();
        vBorder.setVal(STBorder.Enum.forString("dotted"));
        vBorder.setSz(new BigInteger("1"));
        vBorder.setColor("00FF00");
        CTBorder lBorder = borders.addNewLeft();
        lBorder.setVal(STBorder.Enum.forString("double"));
        lBorder.setSz(new BigInteger("1"));
        lBorder.setColor("3399FF");
        CTBorder rBorder = borders.addNewRight();
        rBorder.setVal(STBorder.Enum.forString("single"));
        rBorder.setSz(new BigInteger("1"));
        rBorder.setColor("F2B11F");
        CTBorder tBorder = borders.addNewTop();
        tBorder.setVal(STBorder.Enum.forString("thick"));
        tBorder.setSz(new BigInteger("1"));
        tBorder.setColor("C3599D");
        CTBorder bBorder = borders.addNewBottom();
        bBorder.setVal(STBorder.Enum.forString("wave"));
        bBorder.setSz(new BigInteger("1"));
        bBorder.setColor("BF6BCC");
        CTTbl ttbl = table.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        CTJc cTJc = tblPr.addNewJc();
        cTJc.setVal(STJc.Enum.forString("center"));
        tblWidth.setW(new BigInteger("8000"));
        tblWidth.setType(STTblWidth.DXA);
        XWPFTableRow firstRow;
        XWPFTableRow secondRow;
        XWPFTableCell firstCell;
        XWPFTableCell secondCell;
        setText(table, 2, 5);
        firstRow = table.insertNewTableRow(0);
        secondRow = table.insertNewTableRow(1);
        firstRow.setHeight(380);
        secondRow.setHeight(380);
        for (String str : columnList) {
            if (!str.contains("|")) {
                firstCell = firstRow.addNewTableCell();
                secondCell = secondRow.addNewTableCell();
                createVSpanCell(firstCell, str, "CCCCCC", 1600, STMerge.RESTART);
                createVSpanCell(secondCell, "", "CCCCCC", 1600, null);
            } else {
                String[] strArr = str.split("\\|");
                firstCell = firstRow.addNewTableCell();
                createHSpanCell(firstCell, strArr[0], "CCCCCC", 1600, STMerge.RESTART);
                for (int i = 1; i < strArr.length - 1; i++) {
                    firstCell = firstRow.addNewTableCell();
                    createHSpanCell(firstCell, "", "CCCCCC", 1600, null);
                }
                for (int i = 1; i < strArr.length; i++) {
                    secondCell = secondRow.addNewTableCell();
                    setCellText(secondCell, strArr[i], "CCCCCC", 1600);
                }
            }
        }
    }


    //normal border
    public void createSimpleTableNormal(XWPFDocument doc) throws Exception {
        List<String> columnList = new ArrayList<>();
        columnList.add("序号");
        columnList.add("姓名信息|姓甚|名谁");
        columnList.add("名刺信息|籍贯|营生");
        XWPFTable table = doc.createTable(2, 5);
        CTTbl ttbl = table.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        CTJc cTJc = tblPr.addNewJc();
        cTJc.setVal(STJc.Enum.forString("center"));
        tblWidth.setW(new BigInteger("8000"));
        tblWidth.setType(STTblWidth.DXA);
        XWPFTableRow firstRow;
        XWPFTableRow secondRow;
        XWPFTableCell firstCell;
        XWPFTableCell secondCell;
        setText(table, 2, 5);
        firstRow = table.insertNewTableRow(0);
        secondRow = table.insertNewTableRow(1);
        firstRow.setHeight(380);
        secondRow.setHeight(380);
        for (String str : columnList) {
            if (!str.contains("|")) {
                firstCell = firstRow.addNewTableCell();
                secondCell = secondRow.addNewTableCell();
                createVSpanCell(firstCell, str, "CCCCCC", 1600, STMerge.RESTART);
                createVSpanCell(secondCell, "", "CCCCCC", 1600, null);
            } else {
                String[] strArr = str.split("\\|");
                firstCell = firstRow.addNewTableCell();
                createHSpanCell(firstCell, strArr[0], "CCCCCC", 1600, STMerge.RESTART);
                for (int i = 1; i < strArr.length - 1; i++) {
                    firstCell = firstRow.addNewTableCell();
                    createHSpanCell(firstCell, "", "CCCCCC", 1600, null);
                }
                for (int i = 1; i < strArr.length; i++) {
                    secondCell = secondRow.addNewTableCell();
                    setCellText(secondCell, strArr[i], "CCCCCC", 1600);
                }
            }
        }
    }

    //no border for table
    public void createSimpleTableWithNotBd(XWPFDocument doc) throws Exception {
        List<String> columnList = new ArrayList<>();
        columnList.add("序号");
        columnList.add("姓名信息|姓甚|名谁");
        columnList.add("名刺信息|籍贯|营生");
        XWPFTable table = doc.createTable(2, 5);
        CTTblBorders borders = table.getCTTbl().getTblPr().addNewTblBorders();
        CTBorder hBorder = borders.addNewInsideH();
        hBorder.setVal(STBorder.Enum.forString("none"));
        hBorder.setSz(new BigInteger("1"));
        hBorder.setColor("0000FF");
        CTBorder vBorder = borders.addNewInsideV();
        vBorder.setVal(STBorder.Enum.forString("none"));
        vBorder.setSz(new BigInteger("1"));
        vBorder.setColor("00FF00");
        CTBorder lBorder = borders.addNewLeft();
        lBorder.setVal(STBorder.Enum.forString("none"));
        lBorder.setSz(new BigInteger("1"));
        lBorder.setColor("3399FF");
        CTBorder rBorder = borders.addNewRight();
        rBorder.setVal(STBorder.Enum.forString("none"));
        rBorder.setSz(new BigInteger("1"));
        rBorder.setColor("F2B11F");
        CTBorder tBorder = borders.addNewTop();
        tBorder.setVal(STBorder.Enum.forString("none"));
        tBorder.setSz(new BigInteger("1"));
        tBorder.setColor("C3599D");
        CTBorder bBorder = borders.addNewBottom();
        bBorder.setVal(STBorder.Enum.forString("none"));
        bBorder.setSz(new BigInteger("1"));
        bBorder.setColor("F7E415");
        CTTbl ttbl = table.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        CTJc cTJc = tblPr.addNewJc();
        cTJc.setVal(STJc.Enum.forString("center"));
        tblWidth.setW(new BigInteger("8000"));
        tblWidth.setType(STTblWidth.DXA);
        XWPFTableRow firstRow;
        XWPFTableRow secondRow;
        XWPFTableCell firstCell;
        XWPFTableCell secondCell;
        setText(table, 2, 5);
        firstRow = table.insertNewTableRow(0);
        secondRow = table.insertNewTableRow(1);
        firstRow.setHeight(380);
        secondRow.setHeight(380);
        for (String str : columnList) {
            if (!str.contains("|")) {
                firstCell = firstRow.addNewTableCell();
                secondCell = secondRow.addNewTableCell();
                createVSpanCell(firstCell, str, "CCCCCC", 1600, STMerge.RESTART);
                createVSpanCell(secondCell, "", "CCCCCC", 1600, null);
            } else {
                String[] strArr = str.split("\\|");
                firstCell = firstRow.addNewTableCell();
                createHSpanCell(firstCell, strArr[0], "CCCCCC", 1600, STMerge.RESTART);
                for (int i = 1; i < strArr.length - 1; i++) {
                    firstCell = firstRow.addNewTableCell();
                    createHSpanCell(firstCell, "", "CCCCCC", 1600, null);
                }
                for (int i = 1; i < strArr.length; i++) {
                    secondCell = secondRow.addNewTableCell();
                    setCellText(secondCell, strArr[i], "CCCCCC", 1600);
                }
            }
        }
    }



    public void createHSpanCell(XWPFTableCell cell, String value, String bgcolor, int width, STMerge.Enum stMerge) {
        CTTc cttc = cell.getCTTc();
        CTTcPr cellPr = cttc.addNewTcPr();
        cellPr.addNewTcW().setW(BigInteger.valueOf(width));
        cell.setColor(bgcolor);
        cellPr.addNewHMerge().setVal(stMerge);
        cellPr.addNewVAlign().setVal(STVerticalJc.CENTER);
        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
        cttc.getPList().get(0).addNewR().addNewT().setStringValue(value);
    }

    public void createVSpanCell(XWPFTableCell cell, String value, String bgcolor, int width, STMerge.Enum stMerge) {
        CTTc cttc = cell.getCTTc();
        CTTcPr cellPr = cttc.addNewTcPr();
        cellPr.addNewTcW().setW(BigInteger.valueOf(width));
        cell.setColor(bgcolor);
        cellPr.addNewVMerge().setVal(stMerge);
        cellPr.addNewVAlign().setVal(STVerticalJc.CENTER);
        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
        cttc.getPList().get(0).addNewR().addNewT().setStringValue(value);
    }

    public void addNewPage(XWPFDocument document, BreakType breakType) {
        XWPFParagraph xp = document.createParagraph();
        xp.createRun().addBreak(breakType);
    }

    public void saveDocument(XWPFDocument document, String savePath) throws Exception {
        FileOutputStream fos = new FileOutputStream(savePath);
        document.write(fos);
        fos.close();
    }
}
