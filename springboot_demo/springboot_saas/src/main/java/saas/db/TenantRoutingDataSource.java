package saas.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import saas.util.TenantContextHolder;

/**
 * Author: Johnny
 * Date: 2017/8/17
 * Time: 0:11
 */
public class TenantRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContextHolder.getBusinessName();
    }
}
