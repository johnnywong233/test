package sql;

public class DBOptions {
	//设置jdbc驱动
	private String driverClassName = "org.postgresql.Driver";
	//数据库连接URL
	private String databaseURL = "jdbc:postgresql://localhost:5432/postgres?useServerPrepStmts=false";
	//用户名
	private String databaseUser = "postgres";
	//密码
	private String databasePassword = "1Qaz";
	//设置最大连接数
	private int maxConnection = 20;
	//设置最长等待时间
	private int maxTimeToWait = 2000;// 2 seconds

	/**
	 * @return Returns the databasePassword.
	 */
	public String getDatabasePassword() {
		return databasePassword;
	}

	/**
	 * @param databasePassword
	 *            The databasePassword to set.
	 */
	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}

	/**
	 * @return Returns the databaseURL.
	 */
	public String getDatabaseURL() {
		return databaseURL;
	}

	/**
	 * @param databaseURL
	 *            The databaseURL to set.
	 */
	public void setDatabaseURL(String databaseURL) {
		this.databaseURL = databaseURL;
	}

	/**
	 * @return Returns the databaseUser.
	 */
	public String getDatabaseUser() {
		return databaseUser;
	}

	/**
	 * @param databaseUser
	 *            The databaseUser to set.
	 */
	public void setDatabaseUser(String databaseUser) {
		this.databaseUser = databaseUser;
	}

	/**
	 * @return Returns the driverClassName.
	 */
	public String getDriverClassName() {
		return driverClassName;
	}

	/**
	 * @param driverClassName
	 *            The driverClassName to set.
	 */
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	/**
	 * @return Returns the maxConnection.
	 */
	public int getMaxConnection() {
		return maxConnection;
	}

	/**
	 * @param maxConnection
	 *            The maxConnection to set.
	 */
	public void setMaxConnection(int maxConnection) {
		this.maxConnection = maxConnection;
	}

	/**
	 * @return Returns the maxTimeToWait.
	 */
	public int getMaxTimeToWait() {
		return maxTimeToWait;
	}

	/**
	 * @param maxTimeToWait
	 *            The maxTimeToWait to set.
	 */
	public void setMaxTimeToWait(int maxTimeToWait) {
		this.maxTimeToWait = maxTimeToWait;
	}
}
