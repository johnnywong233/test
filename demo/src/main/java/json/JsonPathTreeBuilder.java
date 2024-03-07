package json;

import lombok.Data;
import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * 生成形象化的JSON　tree
 * by Sylar
 */
public class JsonPathTreeBuilder {

    private static final String PATH_DELIMITER = "/";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final Logger logger = LoggerFactory.getLogger(JsonPathTreeBuilder.class.getName());
    private static Map<String, String> columnMapping;

    @BeforeClass
    public static void init() {
        BasicConfigurator.configure();
        columnMapping = new LinkedHashMap<>();
        columnMapping.put("MODIFIED", "/summary/modified");
        columnMapping.put("GUID", "/summary/guid");
        columnMapping.put("A", null);
        columnMapping.put("B", " ");
        columnMapping.put("C", " / ");
        columnMapping.put("D", "");
        columnMapping.put("C", " / / ");
    }

    public static Node build(Map<String, String> columnMapping) {
        logMapping(columnMapping);
        Node rootNode = new Node("ROOT");
        Node pathNode;
        for (String columnName : columnMapping.keySet()) {
            pathNode = rootNode;
            String fullPath = columnMapping.get(columnName);
            List<String> pathList = getRelativePath(fullPath);
            if (pathList.size() > 0) {
                for (String pathName : pathList) {
                    pathNode = pathNode.addChildNode(pathName);
                }
                pathNode.setColumnName(columnName);
            }
        }
        logTreePicture(rootNode);
        return rootNode;
    }

    private static List<String> getRelativePath(String path) {
        List<String> relativePaths = new ArrayList<>();
        String[] relativePathArr;
        if (path != null && path.trim().length() > 0) {
            relativePathArr = path.trim().split(PATH_DELIMITER);
            //remove empty String and null;
            for (String relativePath : relativePathArr) {
                if (relativePath != null && !"".equalsIgnoreCase(relativePath) && !"".equalsIgnoreCase(relativePath.trim())) {
                    relativePaths.add(relativePath.trim());
                }
            }
        }
        return relativePaths;
    }

    private static void logTreePicture(Node rootNode) {
        if (logger.isDebugEnabled()) {
            logger.debug("Build path tree for columnMapping successfully!");
            logger.debug("Root Node: [{}]", rootNode.toString());
            logger.debug("Column Mapping Path tree:" + LINE_SEPARATOR + rootNode.getTreePicture());
        }
    }

    private static void logMapping(Map<String, String> columnMapping) {
        if (logger.isDebugEnabled()) {
            logger.debug("Going to build path tree for columnMapping:");
            for (String columnName : columnMapping.keySet()) {
                String columnPath = columnMapping.get(columnName);
                logger.debug("Column Name [{}] , Path [{}]", columnName, columnPath);
            }
        }
    }

    @Test
    public void testPathTreeBuilder() {
        Node rootNode = JsonPathTreeBuilder.build(columnMapping);
        assertEquals(getPic(), rootNode.getTreePicture());
    }

    private String getPic() {
        return "└── ROOT" + LINE_SEPARATOR +
                "    └── summary" + LINE_SEPARATOR +
                "        ├── modified  [MODIFIED]" + LINE_SEPARATOR +
                "        └── guid  [GUID]" + LINE_SEPARATOR;
    }

}

@Data
class Node {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private String pathName;
    private Node parentNode;
    private List<Node> childNodeList;
    private String columnName;

    public Node(String pathName) {
        if (pathName == null) {
            throw new IllegalArgumentException("Build Node failed, path name can't not be null.");
        }
        this.pathName = pathName;
        this.childNodeList = new LinkedList<>();
    }

    synchronized Node addChildNode(String pathName) {
        for (Node existChildNode : this.childNodeList) {
            if (existChildNode.pathName.equals(pathName)) {
                return existChildNode;
            }
        }
        Node childNode = new Node(pathName);
        childNode.setParentNode(this);
        this.childNodeList.add(childNode);
        return childNode;
    }

    String getTreePicture() {
        StringBuilder treePicture = new StringBuilder();
        getTreePicture("", true, treePicture);
        return treePicture.toString();
    }

    private void getTreePicture(String prefix, boolean isTail, StringBuilder treePicture) {
        treePicture.append(prefix + (isTail ? "└── " : "├── ") + pathName + (columnName == null ? "" : "  [" + columnName + "]") + LINE_SEPARATOR);
        for (int i = 0; i < childNodeList.size() - 1; i++) {
            childNodeList.get(i).getTreePicture(prefix + (isTail ? "    " : "│   "), false, treePicture);
        }
        if (childNodeList.size() > 0) {
            childNodeList.get(childNodeList.size() - 1).getTreePicture(prefix + (isTail ? "    " : "│   "), true, treePicture);
        }
    }
}
