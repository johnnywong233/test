package io.file;

import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletResponse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import java.util.Map.Entry;

/**
 * Created by wajian on 2016/8/17.
 * java generate CSV file
 * http://www.jb51.net/article/52724.htm
 */
public class CSVUtilsDemo{
    /**
     * Test data
     */
    public static void main(String[] args) {
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

        String path = "C:/work/test/src/test/resources/";
        String fileName = "export file";
        File file = CSVUtils.createCSVFile(exportData, map, path, fileName);
        String fileName2 = file.getName();
        System.out.println("filename:" + fileName2);
        
        //TODO
        //another example, wrong
//        String name = "银行退款数据";
//        List exportData = new ArrayList();
//        LinkedHashMap datamMap = null;
//        for (Iterator iterator = refundList.iterator(); iterator.hasNext();) {
//           HashMap map = (HashMap) iterator.next();
//           datamMap = new LinkedHashMap();
//           datamMap.put("1", map.get("merOrderId"));
//           datamMap.put("2", DateUtil.convertDateToString("yyyyMMdd", (Date) map.get("orderTime")));
//           BigDecimal amount = (BigDecimal) map.get("amount");
//           String amountString = amount.divide(new BigDecimal(10)).toPlainString();
//           datamMap.put("3", amountString);
//           datamMap.put("4", map.get("remark") != null ? map.get("remark") : "");
//           exportData.add(datamMap);
//        }
//         LinkedHashMap map = new LinkedHashMap();
//         map.put("1", "订单号");
//         map.put("2", "支付日期");
//         map.put("3", "退货现金金额（整数金额 单位：分）");
//         map.put("4", "退货原因");
//         String filePath = "C:/work/test/src/test/resources/";
//         String fileName = "export file";
//         File file = CSVUtils.createCSVFile(exportData, map, filePath, name);//生成CSV文件
//         fileName = file.getName();
//         CSVUtils.exportFile(response, filePath + fileName, fileName);//下载生成的CSV文件
        
    }
}



class CSVUtils {
    /**
     * generate CVS file
     * @param exportData
     *       源数据List
     * @param map
     *       csv文件的列表头map
     * @param outPutPath
     *       文件路径
     * @param fileName
     *       文件名称
     * @return
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
            System.out.println("csvFile：" + csvFile);
            // UTF-8使正确读取分隔符","
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    csvFile), "UTF-8"), 1024);
            System.out.println("csvFileOutputStream:" + csvFileOutputStream);
            //TODO 文件头部有乱码
            // 写入文件头部
            for (Iterator<Entry<String, String>> propertyIterator = map.entrySet().iterator(); propertyIterator.hasNext();) {
                Entry<String, String> propertyEntry = propertyIterator.next();
                csvFileOutputStream.write(propertyEntry.getValue());
                if (propertyIterator.hasNext()) {
                    csvFileOutputStream.write(",");
                }
            }
            csvFileOutputStream.newLine();
            // 写入文件内容
            for (Iterator<Map<String, String>> iterator = exportData.iterator(); iterator.hasNext();) {
                Object row = iterator.next();
                for (Iterator<Entry<String, String>> propertyIterator = map.entrySet().iterator(); propertyIterator
                        .hasNext();) {
                    Entry<String, String> propertyEntry = propertyIterator.next();
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
     * @param response
     * @param csvFilePath
     *       文件路径
     * @param fileName
     *       文件名称
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
                out.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });
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
     * @param filePath
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
     * @param filePath
     *     文件目录路径
     * @param fileName
     *     文件名称
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
}
