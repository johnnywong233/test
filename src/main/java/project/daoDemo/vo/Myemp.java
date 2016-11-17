package project.daoDemo.vo;

/*
 * http://huangxiniu.iteye.com/blog/1717196
 */
public class Myemp {
	private int id;         //标识
    private String username;//用户名
    private String password;//密码
    /* 以下为Get Set访问 */
    public int getId() {
       return id;
    }
    public void setId(int id) {
       this.id = id;
    }
    public String getUsername() {
       return username;
    }
    public void setUsername(String username) {
       this.username = username;
    }
    public String getPassword() {
       return password;
    }
    public void setPassword(String password) {
       this.password = password;
    }
}
