package johnny.test;

import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.jupiter.api.Test;

public class ModelTest {

    @Test
    public void testSchemaExport() {
        SchemaExport export = new SchemaExport();
        export.createOnly(null, null);
    }
}
