package servlet.test;

import servlet.config.DataSource;
import servlet.domain.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johnny on 2018/3/26.
 */
public class HikariTest {
    private static final String SQL_QUERY = "select * from emp";

    private static List<Employee> fetchData() {
        List<Employee> employees = null;
        try (Connection con = DataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_QUERY);
             ResultSet rs = pst.executeQuery()) {
            employees = new ArrayList<>();
            Employee employee;
            while (rs.next()) {
                employee = new Employee();
                employee.setEmpNo(rs.getInt("empno"));
                employee.setName(rs.getString("ename"));
                employee.setJob(rs.getString("job"));
                employee.setMgr(rs.getInt("mgr"));
                employee.setHireDate(rs.getDate("hire_date"));
                employee.setSal(rs.getInt("sal"));
                employee.setComm(rs.getInt("comm"));
                employee.setDeptNo(rs.getInt("deptno"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public static void main(String[] args) {
        fetchData().forEach(System.out::println);
    }

}