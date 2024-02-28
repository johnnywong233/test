package angular.service;

import angular.entity.Student;
import angular.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Author: Johnny
 * Date: 2017/9/20
 * Time: 22:19
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentRepository studentRepository;

    @Override
    public Page<Student> findPaginated(int page, int size) {
        return studentRepository.findAll(PageRequest.of(page, size));
    }
}
