package file.poi;

import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

import java.math.BigInteger;

/**
 * Author: Johnny
 * Date: 2016/10/27
 * Time: 1:19
 */
public class POIUtils {

    static void setCellText(XWPFTableCell cell, String text, String bgcolor, int width) {
        CTTc cttc = cell.getCTTc();
        CTTcPr cellPr = cttc.addNewTcPr();
        cellPr.addNewTcW().setW(BigInteger.valueOf(width));
        //cell.setColor(bgcolor);
        CTTcPr ctPr = cttc.addNewTcPr();
        CTShd ctshd = ctPr.addNewShd();
        ctshd.setFill(bgcolor);
        ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
        cell.setText(text);
    }

    static void setText(XWPFTable table, int rowNum, int cellNum) {
        XWPFTableRow firstRow;
        XWPFTableCell firstCell;
        for (int i = 0; i < rowNum; i++) {
            firstRow = table.getRow(i);
            firstRow.setHeight(380);
            for (int j = 0; j < cellNum; j++) {
                firstCell = firstRow.getCell(j);
                setCellText(firstCell, "测试", "FFFFC9", 1600);
            }
        }
    }

}
