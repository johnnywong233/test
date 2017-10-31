package demo.demo3;

public class UseFunctionService {
    private FunctionService functionService;

    void setFunctionService(FunctionService functionService) {
        this.functionService = functionService;
    }

    String sayHello(String word) {
        return functionService.sayHello(word);
    }
}
