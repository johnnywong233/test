package sql.or.model;

/**
 * Author: Johnny
 * Date: 2017/7/1
 * Time: 22:27
 */
public class ColumnInfo {
    private String name;
    private String type;

    public ColumnInfo(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
