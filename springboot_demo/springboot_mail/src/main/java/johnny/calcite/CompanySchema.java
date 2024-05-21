package johnny.calcite;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author johnny
 */
@Data
public class CompanySchema {
    private List<Employee> employeeList;
    private List<Department> departList;

    @Data
    @AllArgsConstructor
    public static class Employee {
        private String name;
        private String id;
        private String deptId;
    }

    @Data
    @AllArgsConstructor
    public static class Department {
        private String deptId;
        private String deptName;

    }
}
