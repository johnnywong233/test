package angular.web;

import angular.entity.Student;
import angular.exception.ResourceNotFoundException;
import angular.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Johnny
 * Date: 2017/9/20
 * Time: 23:18
 */
@RestController
public class StudentController {
    @Autowired
    private StudentService service;

    //http://localhost:8080/student/get?page=0&size=2
    @RequestMapping(value = "/student/get", params = {"page", "size"}, method = RequestMethod.GET, produces = "application/json")
    public Page<Student> findPaginated(@RequestParam("page") int page, @RequestParam("size") int size) throws ResourceNotFoundException {
        Page<Student> resultPage = service.findPaginated(page, size);
        if (page > resultPage.getTotalPages()) {
            throw new ResourceNotFoundException();
        }
        return resultPage;
    }
}
