package grammar.holder;

/**
 * Author: Johnny
 * Date: 2017/7/15
 * Time: 17:16
 */
public class User {
    private Integer id;
    private String homepage;
    private String name;
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    String getHomepage() {
        return homepage;
    }

    void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
