package com.datamanagement.utils;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ConfigurationProperties
@Profile("cloud")
public class CloudFoundryDatabaseConfig extends AbstractCloudConfig {

 
	 @Bean
     public DataSource dataSource() {
         return connectionFactory().dataSource();
     }
    
    @Bean
	public JdbcTemplate setDataSource(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}
