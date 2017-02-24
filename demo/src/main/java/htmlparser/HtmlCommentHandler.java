package htmlparser;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Author: Johnny
 * Date: 2017/2/11
 * Time: 16:52
 */
public class HtmlCommentHandler {

	//TODO
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(new File("C:\\work\\test_git\\test\\demo\\src\\main\\resources\\testHtmlComment.html")));
        System.out.println(readHtmlContentWithoutComment(bf));
    }

    private static class HtmlCommentDetector {
        private static final String COMMENT_START = "<!--";
        private static final String COMMENT_END = "-->";

        // 该字符串是否是html注释行，包含注释的开始标签且结束标签"<!-- -->"
        private static boolean isCommentLine(String line) {
            return containsCommentStartTag(line) && containsCommentEndTag(line)
                    && line.indexOf(COMMENT_START) < line.indexOf(COMMENT_END);
        }

        // 是否包含注释的开始标签
        private static boolean containsCommentStartTag(String line) {
            return StringUtils.isNotEmpty(line) &&
                    line.contains(COMMENT_START);
        }

        // 是否包含注释的结束标签
        private static boolean containsCommentEndTag(String line) {
            return StringUtils.isNotEmpty(line) &&
                    line.contains(COMMENT_END);
        }

        /**
         * 删除该行中的注释部分
         */
        private static String deleteCommentInLine(String line) {
            while (isCommentLine(line)) {
                int start = line.indexOf(COMMENT_START) + COMMENT_START.length();
                int end = line.indexOf(COMMENT_END);
                line = line.substring(start, end);
            }
            return line;
        }

        // 获取开始注释符号之前的内容
        private static String getBeforeCommentContent(String line) {
            if (!containsCommentStartTag(line))
                return line;
            return line.substring(0, line.indexOf(COMMENT_START));
        }

        // 获取结束注释行之后的内容
        private static String getAfterCommentContent(String line) {
            if (!containsCommentEndTag(line))
                return line;
            return line.substring(line.indexOf(COMMENT_END) + COMMENT_END.length());
        }
    }

    /**
     * 读取html内容，去掉注释
     */
    public static String readHtmlContentWithoutComment(BufferedReader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        String line;
        // 当前行是否在注释中
        boolean inComment = false;
        while ((line = reader.readLine()) != null) {
            // 如果包含注释标签
            while (HtmlCommentDetector.containsCommentStartTag(line) ||
                    HtmlCommentDetector.containsCommentEndTag(line)) {
                // 将成对出现的注释标签之间的内容删除
                // <!-- comment -->
                if (HtmlCommentDetector.isCommentLine(line)) {
                    line = HtmlCommentDetector.deleteCommentInLine(line);
                }
                // 如果不是注释行，但是依然存在开始标签和结束标签，结束标签一定在开始标签之前
                // xxx -->content<!--
                else if (HtmlCommentDetector.containsCommentStartTag(line) && HtmlCommentDetector.containsCommentEndTag(line)) {
                    // 获取结束标签之后，开始标签之前的文本，并且将 inComment设置为true
                    line = HtmlCommentDetector.getAfterCommentContent(line);
                    line = HtmlCommentDetector.getBeforeCommentContent(line);
                    inComment = true;
                }
                // 如果只存在开始标签，因为注释标签不支持嵌套，只有开始标签的行一定不会inComment
                // content <!--
                else if (!inComment && HtmlCommentDetector.containsCommentStartTag(line)) {
                    // 将 inComment 设置为true。获取开始标签之前的内容
                    inComment = true;
                    line = HtmlCommentDetector.getBeforeCommentContent(line);
                }
                // 如果只存在结束标签，因为注释标签不支持嵌套，只有结束标签的行一定inComment
                // -->content
                else if (inComment && HtmlCommentDetector.containsCommentEndTag(line)) {
                    // 将 inComment 设置为false。获取结束标签之后的内容
                    inComment = false;
                    line = HtmlCommentDetector.getAfterCommentContent(line);
                }
                // 保存该行非注释的内容
                if (StringUtils.isNotEmpty(line))
                    builder.append(line);
            }
            // 保存该行不存在任何注释标签的并且inComment = false的行
            if (StringUtils.isNotEmpty(line) && !inComment)
                builder.append(line);
        }
        return builder.toString();
    }
}
