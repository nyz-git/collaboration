package com.niit.mks.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan({"com.niit.mks.model"})
@EnableTransactionManagement
@EnableWebMvc
public class HibernateConfig {

	/*
	 * Setting the properties for the H2 database
	 * 
	 * 
	 */

	private final static String JDBC_URL = "jdbc:h2:tcp://localhost/~/colab";
	private final static String JDBC_DRIVER_CLASS = "org.h2.Driver";
	private final static String JDBC_USERNAME = "sa";
	private final static String JDBC_PASSWORD = "";

	/*
	 * Specifying the data source
	 */

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(JDBC_DRIVER_CLASS);
		dataSource.setUrl(JDBC_URL);
		dataSource.setUsername(JDBC_USERNAME);
		dataSource.setPassword(JDBC_PASSWORD);
		return dataSource;
	}

	/*
	 * Creating the session factory and passing the data source
	 * 
	 */
	@Bean
	public SessionFactory sessionFactory(DataSource dataSource) {
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource);
		builder.addProperties(hibernateProperties());
		// builder.addAnnotatedClass(Blog.class);
		builder.scanPackages("com.niit.mks.model");
		return builder.buildSessionFactory();
	}

	/*
	 * specifying the hibernate properties
	 */

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.format_sql", "true");
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.lazy", "false");
		return properties;

	}

	/*
	 * To manage the transactions
	 * 
	 */

	@Bean
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		return txManager;

	}
}
