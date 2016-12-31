package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2016/11/17
 * Time: 23:45
 */
public class CsvUtil {
    private BufferedReader br = null;
    private List<String> list = new ArrayList<>();

    public CsvUtil(String fileName) throws Exception {
        br = new BufferedReader(new FileReader(fileName));
        String temp;
        while ((temp = br.readLine()) != null) {
            list.add(temp);
        }
    }

    public List<String> getList() {
        return list;
    }

    /**
     * 获取行数
     */
    public int getRowNum() {
        return list.size();
    }

    /**
     * 获取列数
     */
    public int getColNum() {
        if (!list.toString().equals("[]")) {
            if (list.get(0).contains(",")) {
                return list.get(0).split(",").length;
            } else if (list.get(0).trim().length() != 0) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    /**
     * 获取指定行
     */
    public String getRow(int index) {
        if (this.list.size() != 0) {
            return list.get(index);
        } else {
            return null;
        }
    }

    /**
     * 获取指定列
     */
    public String getCol(int index) {
        if (this.getColNum() == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        int column = this.getColNum();
        if (column > 1) {
            for (String aList : list) {
                sb = sb.append(aList.split(",")[index]).append(",");
            }
        } else {
            for (String aList : list) {
                sb = sb.append(aList).append(",");
            }
        }
        String str = sb.toString();
        str = str.substring(0, str.length() - 1);
        return str;
    }

    /**
     * 获取某个单元格
     */
    public String getString(int row, int col) {
        String temp;
        int column = this.getColNum();
        if (column > 1) {
            temp = list.get(row).split(",")[col];
        } else if (column == 1) {
            temp = list.get(row);
        } else {
            temp = null;
        }
        return temp;
    }

    public void CsvClose() throws Exception {
        this.br.close();
    }

    public static void main(String[] args) throws Exception {
        CsvUtil util = new CsvUtil("D:\\demo.csv");
        int rowNum = util.getRowNum();
        int colNum = util.getColNum();
        String x = util.getRow(2);
        String y = util.getCol(2);
        System.out.println("rowNum:" + rowNum);
        System.out.println("colNum:" + colNum);
        System.out.println("x:" + x);
        System.out.println("y:" + y);

        for (int i = 1; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                System.out.println("result[" + i + "|" + j + "]:" + util.getString(i, j));
            }
        }

    }
}
