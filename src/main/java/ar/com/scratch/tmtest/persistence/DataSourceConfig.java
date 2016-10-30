package ar.com.scratch.tmtest.persistence;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MultitenancyProperties.class)
public class DataSourceConfig {

	@Autowired
	private MultitenancyProperties multitenancyProperties;

	@Bean(name = { "dataSource", "defaultDataSource" })
	public DataSource defaultDataSource() {
		return multitenancyProperties.getDefaultDataSource().initializeDataSourceBuilder().build();
	}

	@Bean(name = "dataSource1")
	public DataSource dataSource1() {
		return multitenancyProperties.getDataSource1().initializeDataSourceBuilder().build();
	}

	@Bean(name = "dataSource2")
	public DataSource dataSource2() {
		return multitenancyProperties.getDataSource2().initializeDataSourceBuilder().build();
	}
}