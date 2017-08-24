package java8;

/**
 * Author: Johnny
 * Date: 2017/8/24
 * Time: 20:02
 */
public class Student {
    private int id;
    private String name;
    private int age;
    private String grade;

    Student(){

    }

    Student(int id, String name, int age, String grade) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void sayHello() {
        System.out.println(this.id + " " + this.name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}
