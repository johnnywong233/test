package johnny;

/**
 * Author: Johnny
 * Date: 2017/3/11
 * Time: 15:52
 */
public class User {

    private String username;

    private String email;

    void setEmail(String email) {
        this.email = email;
    }

    String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
