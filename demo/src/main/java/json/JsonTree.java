package json;

/**
 * Author: Johnny
 * Date: 2017/1/19
 * Time: 22:47
 * 生成形象化的JSON　tree
 */
public class JsonTree {
    private void getTreePicture(String prefix, boolean isTail, StringBuilder treePicture) {
        treePicture.append(prefix + (isTail ? "└── " : "├── ") + pathName + (columnName == null ? "" : "  [" + columnName + "]") + System.lineSeparator());
        for (int i = 0; i < childNodeList.size() - 1; i++) {
            childNodeList.get(i).getTreePicture(prefix + (isTail ? "    " : "│   "), false, treePicture);
        }
        if (childNodeList.size() > 0) {
            childNodeList.get(childNodeList.size() - 1).getTreePicture(prefix + (isTail ? "    " : "│   "), true, treePicture);
        }
    }


    public String getTreePicture() {
        StringBuilder treePicture = new StringBuilder();
        getTreePicture("", true, treePicture);
        return treePicture.toString();
    }
}
