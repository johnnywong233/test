package batch;

import batch.entity.Student;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

/**
 * Author: Johnny
 * Date: 2017/10/4
 * Time: 18:53
 */
@Service
public class StudentItemProcessor implements ItemProcessor<Student, Student> {
    @Override
    public Student process(Student student) throws Exception {
        student.setAge(student.getAge() + 1);
        student.setName(student.getName() + "awesome");
        return student;
    }
}
