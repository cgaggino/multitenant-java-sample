package ar.com.scratch.tmtest.persistence;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties("spring.multitenancy")
public class MultitenancyProperties {

	@NestedConfigurationProperty
	private DataSourceProperties defaultDataSource;

	@NestedConfigurationProperty
	private DataSourceProperties dataSource1;

	@NestedConfigurationProperty
	private DataSourceProperties dataSource2;

	public DataSourceProperties getDefaultDataSource() {
		return defaultDataSource;
	}

	public void setDefaultDataSource(DataSourceProperties datasource3) {
		this.defaultDataSource = datasource3;
	}

	public DataSourceProperties getDataSource1() {
		return dataSource1;
	}

	public void setDataSource1(DataSourceProperties dataSource1) {
		this.dataSource1 = dataSource1;
	}

	public DataSourceProperties getDataSource2() {
		return dataSource2;
	}

	public void setDataSource2(DataSourceProperties dataSource2) {
		this.dataSource2 = dataSource2;
	}
}