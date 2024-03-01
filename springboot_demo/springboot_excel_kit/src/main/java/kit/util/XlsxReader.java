package kit.util;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * *.xlsx文件解析器
 */
public class XlsxReader extends DefaultHandler {
    private final DataFormatter mFormatter = new DataFormatter();
    private SharedStringsTable mSharedStringsTable;
    /**
     * 上一次的内容
     */
    private String mLastContents;
    /**
     * 字符串标识
     */
    private boolean mNextIsString;
    private int mSheetIndex = -1;
    private int mCurrentRowIndex = 0;
    private int mCurrentColumnIndex = 0;
    private boolean mIsTElement;
    private final ReadHandler mReadHandler;
    private short mFormatIndex;
    private String mFormatString;
    private StylesTable mStylesTable;
    private CellValueType mNextDataType = CellValueType.STRING;
    private final List<String> mRowData = new ArrayList<>();

    /**
     * 定义前一个元素和当前元素的位置，用来计算其中空的单元格数量，如A6和A8等
     */
    private String mPreviousRef = null, mCurrentRef = null;
    /**
     * 定义该文档一行最大的单元格数，用来补全一行最后可能缺失的单元格
     */
    private String mMaxRef = null;
    /**
     * 空单元格的默认值
     */
    private String mEmptyCellValue = null;

    XlsxReader(ReadHandler handler) {
        this.mReadHandler = handler;
    }

    XlsxReader setEmptyCellValue(String ecv) {
        this.mEmptyCellValue = ecv;
        return this;
    }

    /**
     * 处理所有sheet
     *
     * @param fileName excel文件
     * @throws Exception IOException, OpenXML4JException, SAXException
     */
    void process(String fileName) throws Exception {
        POIUtils.checkExcelFile(fileName);
        processAll(OPCPackage.open(fileName));
    }

    private void processAll(OPCPackage pkg) throws IOException, OpenXML4JException, SAXException, ParserConfigurationException {
        XSSFReader xssfReader = new XSSFReader(pkg);
        mStylesTable = xssfReader.getStylesTable();
        SharedStringsTable sst = (SharedStringsTable) xssfReader.getSharedStringsTable();
        XMLReader parser = this.fetchSheetParser(sst);
        Iterator<InputStream> sheets = xssfReader.getSheetsData();
        while (sheets.hasNext()) {
            mCurrentRowIndex = 0;
            mSheetIndex++;
            InputStream sheet = sheets.next();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            sheet.close();
        }
        pkg.close();
    }

    /**
     * 处理所有sheet
     *
     * @param fileName excel文件
     * @throws Exception IOException, OpenXML4JException, SAXException
     */
    void process(InputStream is, String fileName) throws Exception {
        POIUtils.checkExcelFile(fileName);
        processAll(OPCPackage.open(is));
    }

    /**
     * 处理指定sheet
     *
     * @param fileName   excel文件
     * @param sheetIndex 从0开始
     * @throws Exception IOException, OpenXML4JException, SAXException
     */
    void process(String fileName, int sheetIndex) throws Exception {
        POIUtils.checkExcelFile(fileName);
        processBySheet(sheetIndex, OPCPackage.open(fileName));
    }

    private void processBySheet(int sheetIndex, OPCPackage pkg) throws IOException, OpenXML4JException, SAXException, ParserConfigurationException {
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = (SharedStringsTable) r.getSharedStringsTable();

        XMLReader parser = fetchSheetParser(sst);

        // rId2 found by processing the Workbook
        // 根据 rId# 或 rSheet# 查找sheet
        InputStream sheet = r.getSheet("rId" + (sheetIndex + 1));
        mSheetIndex++;
        InputSource sheetSource = new InputSource(sheet);
        parser.parse(sheetSource);
        sheet.close();
        pkg.close();
    }

    /**
     * 处理指定sheet
     *
     * @param fileName   excel文件
     * @param sheetIndex 从0开始
     * @throws Exception IOException, OpenXML4JException, SAXException
     */
    void process(InputStream is, String fileName, int sheetIndex) throws Exception {
        POIUtils.checkExcelFile(fileName);
        processBySheet(sheetIndex, OPCPackage.open(is));
    }

    private XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException, ParserConfigurationException {
        // jdk9 之后 XMLReaderFactory 废弃
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();
        XMLReader reader = parser.getXMLReader();
        this.mSharedStringsTable = sst;
        reader.setContentHandler(this);
        return reader;
    }

    /**
     * 处理数据类型
     *
     * @param attributes attributes
     */
    private void setNextDataType(Attributes attributes) {
        mNextDataType = CellValueType.STRING;
        mFormatIndex = -1;
        mFormatString = null;
        String cellType = attributes.getValue("t");
        String cellStyleStr = attributes.getValue("s");

        if ("b".equals(cellType)) {
            mNextDataType = CellValueType.BOOL;
        } else if ("e".equals(cellType)) {
            mNextDataType = CellValueType.ERROR;
        } else if ("inlineStr".equals(cellType)) {
            mNextDataType = CellValueType.INLINESTR;
        } else if ("s".equals(cellType)) {
            mNextDataType = CellValueType.STRING;
        } else if ("str".equals(cellType)) {
            mNextDataType = CellValueType.FORMULA;
        }
        //TODO: 日期类型的判断

        if (cellStyleStr != null) {
            int styleIndex = Integer.parseInt(cellStyleStr);
            XSSFCellStyle style = mStylesTable.getStyleAt(styleIndex);
            mFormatIndex = style.getDataFormat();
            mFormatString = style.getDataFormatString();

            if (mFormatString == null) {
                mNextDataType = CellValueType.NULL;
                mFormatString = BuiltinFormats.getBuiltinFormat(mFormatIndex);
            }
        }
    }

    /**
     * 对解析出来的数据进行类型处理
     *
     * @param value 单元格的值（这时候是一串数字）
     * @return string
     */
    private String getDataValue(String value) {
        String newValue;
        switch (mNextDataType) {
            // 这几个的顺序不能随便交换，交换了很可能会导致数据错误
            case BOOL:
                char first = value.charAt(0);
                newValue = first == '0' ? "FALSE" : "TRUE";
                break;
            case ERROR:
                newValue = "\"ERROR:" + value + '"';
                break;
            case FORMULA:
                newValue = '"' + value + '"';
                break;
            case INLINESTR:
                newValue = new XSSFRichTextString(value).toString();
                break;
            case STRING:
                newValue = String.valueOf(value);
                break;
            case NUMBER:
                if (mFormatString != null) {
                    try {
                        newValue = mFormatter.formatRawCellContents(Double.parseDouble(value), mFormatIndex, mFormatString).trim();
                    } catch (NumberFormatException e) {
                        newValue = mEmptyCellValue;
                    }
                } else {
                    newValue = value;
                }
                newValue = newValue != null ? newValue.replace("_", "").trim() : null;
                break;
            case DATE:
                // TODO:对日期字符串作特殊处理
                newValue = mFormatter.formatRawCellContents(Double.parseDouble(value), mFormatIndex, mFormatString);
                newValue = newValue.replace(" ", "T");
                break;
            default:
                newValue = mEmptyCellValue;
                break;
        }
        return newValue;
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) {
        // c => 单元格
        if ("c".equals(name)) {
            // 设定单元格类型
            this.setNextDataType(attributes);

            // 前一个单元格的位置
            mPreviousRef = mPreviousRef == null ? attributes.getValue("r") : mCurrentRef;
            // 当前单元格的位置
            mCurrentRef = attributes.getValue("r");
            // Figure out if the value is an index in the SST
            String cellType = attributes.getValue("t");
            mNextIsString = ("s".equals(cellType));
        }
        mIsTElement = "t".equals(name);

        mLastContents = "";
    }

    @Override
    public void endElement(String uri, String localName, String name) {
        // Process the last contents as required.
        // Do now, as characters() may be called more than once
        if (mNextIsString) {
            int idx = Integer.parseInt(mLastContents);
            mLastContents = mSharedStringsTable.getItemAt(idx).getString();
            mNextIsString = false;
        }

        // t元素也包含字符串
        if (mIsTElement) {
            String value = mLastContents.trim();
            mRowData.add(mCurrentColumnIndex, value);
            mCurrentColumnIndex++;
            mIsTElement = false;
        } else if ("c".equals(name)) {
            String value = this.getDataValue(mLastContents.trim());

            // 补全单元格之间的空单元格 mCurrentRef 和 mPreviousRef 差距超过2
            if (!mCurrentRef.equals(mPreviousRef)) {
                for (int i = 0; i < countNullCell(mCurrentRef, mPreviousRef); i++) {
                    mRowData.add(mCurrentColumnIndex, mEmptyCellValue);
                    mCurrentColumnIndex++;
                }
            }

            mRowData.add(mCurrentColumnIndex, value);
            mCurrentColumnIndex++;
        }
        // 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
        else if ("row".equals(name)) {
            // 默认第一行为表头，以该行单元格数目为最大数目
            if (mCurrentRowIndex == 0) {
                mMaxRef = mCurrentRef;
            }
            // 补全一行尾部可能缺失的单元格
            if (mMaxRef != null) {
                for (int i = 0; i <= countNullCell(mMaxRef, mCurrentRef); i++) {
                    mRowData.add(mCurrentColumnIndex, mEmptyCellValue);
                    mCurrentColumnIndex++;
                }
            }

            if (!mRowData.isEmpty()) {
                mReadHandler.handler(mSheetIndex, mCurrentRowIndex, mRowData);
            }
            mRowData.clear();
            mCurrentRowIndex++;
            mCurrentColumnIndex = 0;
            mPreviousRef = null;
            mCurrentRef = null;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        // 得到单元格内容的值
        mLastContents += new String(ch, start, length);
    }

    /**
     * 计算两个单元格之间的单元格数目(同一行)
     *
     * @param ref  单元格1
     * @param ref2 单元格1
     * @return 单元格间隔
     */
    private int countNullCell(String ref, String ref2) {
        // excel2007最大行数是1048576，最大列数是16384，最后一列列名是XFD
        String xfd = ref.replaceAll("\\d+", "");
        String xfd1 = ref2.replaceAll("\\d+", "");

        xfd = fillChar(xfd, 3, '@', true);
        xfd1 = fillChar(xfd1, 3, '@', true);

        char[] letter = xfd.toCharArray();
        char[] letter1 = xfd1.toCharArray();
        int res = (letter[0] - letter1[0]) * 26 * 26 + (letter[1] - letter1[1]) * 26 + (letter[2] - letter1[2]);
        return res - 1;
    }

    private String fillChar(String str, int len, char let, boolean isPre) {
        int strLen = str.length();
        if (strLen < len) {
            if (isPre) {
                StringBuilder strBuilder = new StringBuilder(str);
                for (int i = 0; i < (len - strLen); i++) {
                    strBuilder.insert(0, let);
                }
                str = strBuilder.toString();
            } else {
                StringBuilder strBuilder = new StringBuilder(str);
                for (int i = 0; i < (len - strLen); i++) {
                    strBuilder.append(let);
                }
                str = strBuilder.toString();
            }
        }
        return str;
    }

    enum CellValueType {
        //
        BOOL, ERROR, FORMULA, INLINESTR, STRING, NUMBER, DATE, NULL
    }
}
