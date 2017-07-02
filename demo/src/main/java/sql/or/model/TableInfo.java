package sql.or.model;

/**
 * Author: Johnny
 * Date: 2017/7/1
 * Time: 22:25
 */
public class TableInfo {
    private String databaseName;

    private String tableName;

    private String fullName;

    public String getDatabaseName() {
        return databaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        TableInfo tableInfo = (TableInfo) o;
        return this.databaseName.equals(tableInfo.getDatabaseName()) && this.tableName.equals(tableInfo.getTableName()) && this.fullName.equals(tableInfo.getFullName());
    }

    @Override
    public int hashCode() {
        int result = this.tableName.hashCode();
        result = 31 * result + this.databaseName.hashCode();
        result = 31 * result + this.fullName.hashCode();
        return result;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
}
