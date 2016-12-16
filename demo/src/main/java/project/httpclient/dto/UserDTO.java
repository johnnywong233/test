package project.httpclient.dto;

/**
 * Author: Johnny
 * Date: 2016/10/29
 * Time: 0:47
 */
public class UserDTO {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserDTO [name=" + name + ", age=" + age + "]";
    }

}
