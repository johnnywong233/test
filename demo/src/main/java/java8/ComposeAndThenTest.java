package java8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.testng.annotations.Test;

/**
 * Author: Johnny
 * Date: 2017/2/13
 * Time: 15:42
 */
public class ComposeAndThenTest {

    @Test
    private void test() {
        Function<Integer, Integer> times2 = e -> e * 2;
        Function<Integer, Integer> squared = e -> e * e;
        System.out.println(times2.compose(squared).apply(4));// Returns 32
        System.out.println(times2.andThen(squared).apply(4));// Returns 64
    }

    @Test
    //http://www.java2s.com/Tutorials/Java/java.util.function/Consumer/1040__Consumer.andThen.htm
    private void test1() {
        Consumer<String> c = (x) -> System.out.println(x.toLowerCase());
        c.andThen(c).accept("Java2s.com");

        List<Student> students = Arrays.asList(
                new Student(1, 3, "John"),
                new Student(2, 4, "Jane"),
                new Student(3, 3, "Jack"));

        Consumer<Student> raiser = e -> e.gpa = e.gpa * 1.1;
        raiseStudents(students, System.out::println);
        raiseStudents(students, raiser.andThen(System.out::println));
    }

    @Test
    private void test2() {
        Function<Employee, Integer> funcEmpToString = Employee::getAge;
        System.out.println(funcEmpToString);
        List<Employee> employeeList =
                Arrays.asList(new Employee("Tom Jones", 45),
                        new Employee("Harry Major", 25),
                        new Employee("Ethan Hardy", 65),
                        new Employee("Nancy Smith", 15),
                        new Employee("Deborah Sprightly", 29));
        List<Employee> empNameListInitials = applyIdentityToEmpList(employeeList, Function.identity());
        empNameListInitials.forEach(System.out::println);
    }

    @Test
    //http://www.javabrahman.com/java-8/java-8-java-util-function-function-tutorial-with-examples/
    private void test3() {
        Function<Employee, String> funcEmpToString = (Employee e) -> e.getName();
        Function<Employee, Employee> funcEmpFirstName =
                (Employee e) -> {
                    int index = e.getName().indexOf(" ");
                    String firstName = e.getName().substring(0, index);
                    e.setName(firstName);
                    return e;
                };
                Employee employee = new Employee();
                employee.setAge(22);
                employee.setName("soon");
        List<Employee> employeeList =
                Arrays.asList(new Employee("Tom Jones", 45),
                        new Employee("Harry Major", 25),
                        new Employee("Ethan Hardy", 65),
                        new Employee("Nancy Smith", 15),
                        new Employee("Deborah Sprightly", 29), 
                        employee);
        List<String> empFirstNameList = convertEmpListToNamesList(employeeList, funcEmpToString.compose(funcEmpFirstName));
        empFirstNameList.forEach(str -> {
            System.out.print(" " + str);
        });
    }

    private static void raiseStudents(List<Student> employees, Consumer<Student> fx) {
        employees.forEach(fx);
    }

    public static List<String> convertEmpListToNamesList(List<Employee> employeeList, Function<Employee, String> funcEmpToString) {
        return employeeList.stream().map(funcEmpToString).collect(Collectors.toList());
    }

    public static List<Employee> applyIdentityToEmpList(List<Employee> employeeList, Function<Employee, Employee> funcEmpToEmp) {
        return employeeList.stream().map(funcEmpToEmp).collect(Collectors.toList());
    }

    private class Student {
        public int id;
        public double gpa;
        public String name;

        Student(int id, long g, String name) {
            this.id = id;
            this.gpa = g;
            this.name = name;
        }

        @Override
        public String toString() {
            return id + ">" + name + ": " + gpa;
        }
    }

    private class Employee {

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String name;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int age;

        public Employee(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        public Employee() {
        	
        }
        
    }


}
