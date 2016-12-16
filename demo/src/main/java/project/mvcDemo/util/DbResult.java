package project.mvcDemo.util;

import java.io.Serializable;

public class DbResult {
	private int affectedRows;		// 受影响的行数
	private Serializable generatedKey;	// 生成的主键

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
