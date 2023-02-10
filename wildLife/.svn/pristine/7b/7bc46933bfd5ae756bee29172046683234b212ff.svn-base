package com.globits.wl.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {
		// Core
		"com.globits.core.domain", "com.globits.core.auditing", "com.globits.core.repository",
		"com.globits.core.service", "com.globits.core.service.impl",
		// Cms
		"com.globits.cms.domain",  "com.globits.cms.repository",
		"com.globits.cms.service", "com.globits.cms.service.impl",
		// Security
		"com.globits.security.domain", "com.globits.security.repository", "com.globits.security.service",
		"com.globits.security.service.impl",
		// wl
		"com.globits.wl.domain", "com.globits.wl.repository", "com.globits.wl.service",
		"com.globits.wl.service.impl"})

@EnableJpaRepositories(basePackages = { "com.globits.core.repository","com.globits.cms.repository","com.globits.security.repository","com.globits.wl.repository" })
public class DatabaseConfig {
	
	@Autowired
	private Environment env;

	@Bean
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		dataSource.setUrl(env.getProperty("spring.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.datasource.username"));
		dataSource.setPassword(env.getProperty("spring.datasource.password"));

		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		EntityManagerFactory factory = entityManagerFactory().getObject();
		return new JpaTransactionManager(factory);
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(Boolean.TRUE);

		factory.setDataSource(dataSource());
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.globits.core.domain","com.globits.cms.domain","com.globits.security.domain","com.globits.wl.domain");

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
		jpaProperties.put("hibernate.max_fetch_depth",
				env.getProperty("spring.jpa.properties.hibernate.max_fetch_depth"));
		jpaProperties.put("hibernate.jdbc.fetch_size",
				env.getProperty("spring.jpa.properties.hibernate.jdbc.fetch_size"));
		jpaProperties.put("hibernate.jdbc.batch_size",
				env.getProperty("spring.jpa.properties.hibernate.jdbc.batch_size"));
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		jpaProperties.put("hibernate.show_sql", env.getProperty("spring.jpa.show-sql"));
		jpaProperties.put("hibernate.c3p0.min_size", env.getProperty("spring.jpa.properties.hibernate.c3p0.min_size"));
		jpaProperties.put("hibernate.c3p0.max_size", env.getProperty("spring.jpa.properties.hibernate.c3p0.max_size"));
		jpaProperties.put("hibernate.c3p0.timeout", env.getProperty("spring.jpa.properties.hibernate.c3p0.timeout"));
		jpaProperties.put("hibernate.c3p0.max_statements",
				env.getProperty("spring.jpa.properties.hibernate.c3p0.max_statements"));

		factory.setJpaProperties(jpaProperties);
		factory.afterPropertiesSet();
		factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());

		return factory;
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

}
