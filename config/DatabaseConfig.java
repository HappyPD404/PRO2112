package com.example.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
	@Value("${example.datasource.url}")
	private String url;

	@Value("${example.datasource.username}")
	private String user;

	@Value("${example.datasource.password}")
	private String password;

	@Value("${example.datasource.driverClassName}")
	private String driverClass;

	private String secretKey = "MollaShopSpringBootAndThymeleaf";

//	@Bean
//	DataSource dataSource() {
//		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//		dataSourceBuilder.url(CryptionAES.decrypt(url, secretKey));
//		dataSourceBuilder.driverClassName(driverClass);
//		dataSourceBuilder.username(CryptionAES.decrypt(user, secretKey));
//		dataSourceBuilder.password(CryptionAES.decrypt(password, secretKey));
//		return dataSourceBuilder.build();
//	}
	@Bean
	DataSource dataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.url(url);
		dataSourceBuilder.driverClassName(driverClass);
		dataSourceBuilder.username(user);
		dataSourceBuilder.password(password);
		return dataSourceBuilder.build();
	}
}