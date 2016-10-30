package ar.com.scratch.tmtest.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl
		extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

	private static final long serialVersionUID = 8168907057647334460L;
	private static final String DEFAULT_TENANT_ID = "tenant_0";

	@Autowired
	private DataSource defaultDataSource;

	@Autowired
	private DataSource dataSource1;

	@Autowired
	private DataSource dataSource2;

	private Map<String, DataSource> map;

	@PostConstruct
	public void load() {
		map = new HashMap<>();
		map.put("tenant_0", defaultDataSource);
		map.put("tenant_1", dataSource1);
		map.put("tenant_2", dataSource2);
	}

	@Override
	protected DataSource selectAnyDataSource() {
		return map.get(DEFAULT_TENANT_ID);
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
		return map.get(tenantIdentifier);
	}
}