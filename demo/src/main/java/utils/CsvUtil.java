package utils;

import org.apache.commons.beanutils.BeanUtils;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    public CsvUtil() {

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

    /**
     * generate CVS file
     *
     * @param exportData 源数据List
     * @param map        csv文件的列表头map
     * @param outPutPath 文件路径
     * @param fileName   文件名称
     * @return CVS file
     */
    public static File createCSVFile(List<Map<String, String>> exportData, LinkedHashMap<String, String> map, String outPutPath,
                                     String fileName) {
        File csvFile = null;
        BufferedWriter csvFileOutputStream = null;
        try {
            File file = new File(outPutPath);
            if (!file.exists()) {
                file.mkdir();
            }
            //定义文件名格式并创建
            csvFile = File.createTempFile(fileName, ".csv", new File(outPutPath));
            // UTF-8使正确读取分隔符","
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    csvFile), "UTF-8"), 1024);
            // 写入文件头部
            for (Iterator<Map.Entry<String, String>> propertyIterator = map.entrySet().iterator(); propertyIterator.hasNext(); ) {
                Map.Entry<String, String> propertyEntry = propertyIterator.next();
                csvFileOutputStream.write(propertyEntry.getValue());
                if (propertyIterator.hasNext()) {
                    csvFileOutputStream.write(",");
                }
            }
            csvFileOutputStream.newLine();
            // 写入文件内容
            for (Iterator<Map<String, String>> iterator = exportData.iterator(); iterator.hasNext(); ) {
                Object row = iterator.next();
                for (Iterator<Map.Entry<String, String>> propertyIterator = map.entrySet().iterator(); propertyIterator
                        .hasNext(); ) {
                    Map.Entry<String, String> propertyEntry = propertyIterator.next();
                    csvFileOutputStream.write(BeanUtils.getProperty(row,
                            propertyEntry.getKey()));
                    if (propertyIterator.hasNext()) {
                        csvFileOutputStream.write(",");
                    }
                }
                if (iterator.hasNext()) {
                    csvFileOutputStream.newLine();
                }
            }
            csvFileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (csvFileOutputStream != null) {
                    csvFileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }

    /**
     * download file
     *
     * @param csvFilePath 文件路径
     * @param fileName    文件名称
     * @throws IOException
     */
    public static void exportFile(HttpServletResponse response, String csvFilePath, String fileName)
            throws IOException {
        response.setContentType("application/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

        InputStream in = null;
        try {
            in = new FileInputStream(csvFilePath);
            int len;
            byte[] buffer = new byte[1024];
            response.setCharacterEncoding("UTF-8");
            OutputStream out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});
                out.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * delete all files under the directory filePath
     */
    public static void deleteFiles(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                if (file1.isFile()) {
                    file1.delete();
                }
            }
        }
    }

    /**
     * delete a single file
     *
     * @param filePath 文件目录路径
     * @param fileName 文件名称
     */
    public static void deleteFile(String filePath, String fileName) {
        File file = new File(filePath);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                if (file1.isFile()) {
                    if (file1.getName().equals(fileName)) {
                        file1.delete();
                        return;
                    }
                }
            }
        }
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

    @Test
    public void test() {
        List<Map<String, String>> exportData = new ArrayList<>();
        Map<String, String> row1 = new LinkedHashMap<>();
        row1.put("1", "11");
        row1.put("2", "12");
        row1.put("3", "13");
        row1.put("4", "14");
        exportData.add(row1);
        row1 = new LinkedHashMap<>();
        row1.put("1", "21");
        row1.put("2", "22");
        row1.put("3", "23");
        row1.put("4", "24");
        exportData.add(row1);
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("1", "row 1");
        map.put("2", "row 2");
        map.put("3", "row 3");
        map.put("4", "row 4");

        String path = "C:\\work\\test_git\\test\\demo\\src\\test\\resources";
        String fileName = "export file";
        File file = createCSVFile(exportData, map, path, fileName);
        String fileName2 = file.getName();
        System.out.println("filename:" + fileName2);
    }

}
