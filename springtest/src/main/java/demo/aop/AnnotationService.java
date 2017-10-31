package demo.aop;

import org.springframework.stereotype.Service;

@Service
public class AnnotationService {
    @Action(name = "add-1")
    void add1() {
        System.out.println("add-1");
    }

    void add2() {
        System.out.println("add-2");
    }

    @Action(name = "add-3")
    void add3() {
        System.out.println("add-3");
    }

}
