package com.hingebridge.devops.configs;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hingebridge.devops.utilities.AuthProperties;
import com.hingebridge.devops.utilities.DatasourceProperties;
import com.hingebridge.devops.utilities.EncryptUtil;

@Configuration
public class UtilConfig {
	@Autowired
	private AuthProperties prop;
	@Autowired
	private EncryptUtil encryptUtil;
	@Autowired
	DatasourceProperties datasourceProperties;
	
	@Bean
	public EncryptUtil encryptUtil() {
		return new EncryptUtil(prop.getEncryptKey());
	}
	
	@Bean
	@SuppressWarnings("rawtypes")
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(datasourceProperties.getDriver());
        dataSourceBuilder.url(datasourceProperties.getUrl());
        dataSourceBuilder.username(encryptUtil.decryptStringEncoded(datasourceProperties.getUsername()));
        dataSourceBuilder.password(encryptUtil.decryptStringEncoded(datasourceProperties.getPassword()));
        
        return dataSourceBuilder.build();
    }
}
