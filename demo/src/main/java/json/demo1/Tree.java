package json.demo1;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class Tree<T> {
    /**
     * 节点ID
     */
    private String id;
    /**
     * 显示节点文本
     */
    private String text;
    /**
     * 节点状态，open closed
     */
    private String state = "open";
    /**
     * 节点是否被选中 true false
     */
    private boolean checked = false;
    /**
     * 节点属性
     */
    private List<Map<String, Object>> attributes;
    /**
     * 节点的子节点
     */
    private List<Tree<T>> children = new ArrayList<>();
    /**
     * 是否有父节点
     */
    private boolean isParent = false;
    /**
     * 是否有子节点
     */
    private boolean hasChildren = false;
    /**
     * 父ID
     */
    private String parentId;
}