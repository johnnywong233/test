package junit.junitparams;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Johnny on 2018/4/13.
 */
@RunWith(JUnitParamsRunner.class)
public class JUnitParamsTest {
    @Test
    @Parameters({
            "3, false",
            "20, true",
            "29, false"})
    public void testHighStudent(int age, boolean valid) {
        System.out.println("age=" + age + ",valid=" + valid);
        Assert.assertEquals(new Student(age).isHighStudent(), valid);
    }

    @Test
    @Parameters(method = "ageValues")
    public void studentIsHighStudent(int age, boolean valid) {
        System.out.println("age=" + age + ",valid=" + valid);
        Assert.assertEquals(new Student(age).isHighStudent(), valid);
    }

    private Object[] ageValues() {
        return new Object[]{
                new Object[]{13, false},
                new Object[]{17, true},
                new Object[]{35, false}
        };
    }

    /**
     * 默认约定，当不指定@Parameters中的method方法时，JUnitParams默认查找“parametersFor + 方法名称”作为@Parameters的参数方法
     * 例如，@Parameters注解方法为：personIsAdult_2，则其默认关联的方法名称为：parametersForPersonIsAdult_2
     */
    @Test
    @Parameters
    public void studentIsHighStudent_2(int age, boolean valid) {
        System.out.println("age=" + age + ",valid=" + valid);
        Assert.assertEquals(new Student(age).isHighStudent(), valid);
    }

    private Object[] parametersForStudentIsHighStudent_2() {
        return new Object[]{
                new Object[]{13, false},
                new Object[]{17, true},
                new Object[]{36, false}
        };
    }

    /**
     * 变量传入对象
     */
    @Test
    @Parameters
    public void studentIsHighStudent_3(Student student, boolean valid) {
        System.out.println("age=" + student.getAge() + ",valid=" + valid);
        Assert.assertEquals(student.isHighStudent(), valid);
    }

    private Object[] parametersForStudentIsHighStudent_3() {
        return new Object[]{
                new Object[]{new Student(13), false},
                new Object[]{new Student(17), true},
                new Object[]{new Student(45), false}
        };
    }

    @Data
    @AllArgsConstructor
    public static class Student {
        private String name;
        private String studentNo;
        private int age;
        private String schoolName;

        Student(int age) {
            this.age = age;
        }

        boolean isHighStudent() {
            return age >= 15 && age < 25;
        }
    }
}
