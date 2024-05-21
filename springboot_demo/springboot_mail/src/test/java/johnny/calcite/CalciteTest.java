package johnny.calcite;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.calcite.adapter.java.ReflectiveSchema;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaPlus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author johnny
 */
@Slf4j
public class CalciteTest {
    private static final CompanySchema companySchema = new CompanySchema();

    @BeforeAll
    static void setup() {
        CompanySchema.Department dept1 = new CompanySchema.Department("HR", "Human Resource");
        CompanySchema.Department dept2 = new CompanySchema.Department("MKT", "Marketing");
        CompanySchema.Department dept3 = new CompanySchema.Department("FIN", "Finance");

        CompanySchema.Employee emp1 = new CompanySchema.Employee("Tom", "1234", "HR");
        CompanySchema.Employee emp2 = new CompanySchema.Employee("Harry", "39731", "FIN");
        CompanySchema.Employee emp3 = new CompanySchema.Employee("Danny", "45632", "FIN");
        CompanySchema.Employee emp4 = new CompanySchema.Employee("Jenny", "78654", "MKT");

        companySchema.setDepartList(Lists.newArrayList(dept1, dept2, dept3));
        companySchema.setEmployeeList(Lists.newArrayList(emp1, emp2, emp3, emp4));
    }

    @Test
    void whenCsvSchema_thenQuerySuccess() throws SQLException {
        Properties info = new Properties();
        info.put("model", getPath("model.json"));
        try (Connection connection = DriverManager.getConnection("jdbc:calcite:", info)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from trades.trade");
            assertEquals(3, resultSet.getMetaData().getColumnCount());
            List<Integer> tradeIds = new ArrayList<>();
            while (resultSet.next()) {
                tradeIds.add(resultSet.getInt("tradeId"));
            }
            assertEquals(3, tradeIds.size());
        }
    }

    private String getPath(String model) {
        URL url = ClassLoader.getSystemClassLoader().getResource(model);
        assert url != null;
        return url.getPath();
    }

    @Test
    void whenQueryEmployeesObject_thenQuerySuccess() throws SQLException {
        Properties info = new Properties();
        info.setProperty("lex", "JAVA");
        Connection connection = DriverManager.getConnection("jdbc:calcite:", info);
        CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);
        SchemaPlus rootSchema = calciteConnection.getRootSchema();
        Schema schema = new ReflectiveSchema(companySchema);
        rootSchema.add("company", schema);
        Statement statement = calciteConnection.createStatement();
        String query = "select dept.deptName, count(emp.id) "
                + "from company.employees as emp "
                + "join company.departments as dept "
                + "on (emp.deptId = dept.deptId) "
                + "group by dept.deptName";
        assertDoesNotThrow(() -> {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                log.info("Dept Name:" + resultSet.getString(1) + " No. of employees:" + resultSet.getInt(2));
            }
        });
    }
}
