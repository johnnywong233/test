package file.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import java.io.IOException;

import static utils.FileUtil.readFile;

public class ParseWrongXml {
    public static void main(String[] args) throws DocumentException, IOException {
        String xml = "ï»¿<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\n" +
                "<error xmlns=\"http://schemas.microsoft.com/ado/2007/08/dataservices/metadata\">\n" +
                "  <code>AccountIsDisabled</code>\n" +
                "  <message xml:lang=\"en-US\">The specified account is disabled.\n" +
                "RequestId:eea42317-0002-001b-23d5-6ff5e7000000\n" +
                "Time:2017-01-16T08:46:24.7954385Z</message>\n" +
                "</error>\n";
        xml = xml.replaceAll("[^\\x20-\\x7e]", "");
        Document document = DocumentHelper.parseText(xml);
        System.out.println(document);

        String xml1 = "<HTML>\n" +
                "    <HEAD>\n" +
                "        <TITLE>Network Error</TITLE>\n" +
                "    </HEAD>\n" +
                "    <BODY>\n" +
                "        <FONT face=\"Helvetica\">\n" +
                "            <big>\n" +
                "                <strong></strong>\n" +
                "            </big>\n" +
                "            <BR>\n" +
                "        </FONT>\n" +
                "        <blockquote>\n" +
                "            <TABLE border=0 cellPadding=1 width=\"80%\">\n" +
                "            <TR>\n" +
                "                <TD>\n" +
                "                    <FONT face=\"Helvetica\">\n" +
                "                        <big>Network Error (tcp_error)</big>\n" +
                "                        <BR>\n" +
                "                        <BR>\n" +
                "                    </FONT>\n" +
                "                </TD>\n" +
                "            </TR>\n" +
                "            <TR>\n" +
                "                <TD>\n" +
                "                    <FONT face=\"Helvetica\">\n" +
                "                        A communication error occurred: \"\"\n" +
                "                    </FONT>\n" +
                "                </TD>\n" +
                "            </TR>\n" +
                "            <TR>\n" +
                "                <TD>\n" +
                "                    <FONT face=\"Helvetica\">\n" +
                "                        The Web Server may be down, too busy, or experiencing other problems preventing it from\n" +
                "                        responding to requests. You may wish to try again at a later time.\n" +
                "                    </FONT>\n" +
                "                </TD>\n" +
                "            </TR>\n" +
                "            <TR>\n" +
                "                <TD>\n" +
                "                    <FONT face=\"Helvetica\" SIZE=2>\n" +
                "                    <BR>\n" +
                "                        For assistance, contact your network support team.\n" +
                "                    </FONT>\n" +
                "                </TD>\n" +
                "            </TR>\n" +
                "        </TABLE>\n" +
                "    </blockquote>\n" +
                "</FONT>\n" +
                "        </BODY></HTML>";
        document = DocumentHelper.parseText(xml1);
        System.out.println(document);

        String filePath = "C:\\work\\test_git\\test\\demo\\src\\main\\resources\\wrong.xml";
        document = DocumentHelper.parseText(readFile(filePath));
        System.out.println(document);
    }

}
