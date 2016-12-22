package project.mvcDemo.util;

import java.io.Serializable;

public class DbResult {
    private int affectedRows;
    private Serializable generatedKey;

    public DbResult(int affectedRows, Serializable generatedKey) {
        this.affectedRows = affectedRows;
        this.generatedKey = generatedKey;
    }

    public int getAffectedRows() {
        return affectedRows;
    }

    public Serializable getGeneratedKey() {
        return generatedKey;
    }
}
