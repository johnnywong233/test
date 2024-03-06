package mybatis.model.mapper;

import mybatis.domain.Student;

/**
 * @author johnny
 */
public interface StudentMapper {
    Student getStudentById(int id);

    int updateStudentName(String name, int id);

    String getStudentByIdWithClassInfo(int i);

    int addStudent(Student student);
}
