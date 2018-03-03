package test;

import entity.Person;
import entity.ReportFieldConfig;
import entity.Student;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import util.FieldsConfigLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Johnny on 2018/3/3.
 */
public class StudentOutput {
    private static List<String> fields = Arrays.asList("studentId", "studentName", "studentAble");

    public static void main(String[] args) {
        List<Person> students = getPersons();
        List<String> studentInfos = students.stream().map(
                p -> getOneStudentInfo(p, fields)
        ).collect(
                Collectors.toList());
        System.out.println(String.join("\n", studentInfos));
    }

    private static String getOneStudentInfo(Person p, List<String> fields) {
        List<String> stuInfos = new ArrayList<>();
        fields.forEach(
                field -> {
                    ReportFieldConfig fieldConfig = FieldsConfigLoader.getFieldConfig(field);

                    Binding binding = new Binding();
                    binding.setVariable("stu", p);
                    GroovyShell shell = new GroovyShell(binding);
                    Object result = shell.evaluate(fieldConfig.getScript());
                    System.out.println("result from groovy script: " + result);
                    stuInfos.add(String.valueOf(result));
                }
        );
        return String.join(",", stuInfos);
    }

    private static List<Person> getPersons() {
        Person s1 = new Student("s1", "johnny", "Study");
        Person s2 = new Student("s2", "wong", "Piano");
        return Arrays.asList(s1, s2);
    }
}