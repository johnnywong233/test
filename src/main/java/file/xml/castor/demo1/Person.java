package file.xml.castor.demo1;

import java.util.Date;

/**
 * Author: Johnny
 * Date: 2016/11/20
 * Time: 11:48
 */
public class Person {
	private String name;
    private Date birthDay;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getBirthDay() {
        return birthDay;
    }
    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }


}