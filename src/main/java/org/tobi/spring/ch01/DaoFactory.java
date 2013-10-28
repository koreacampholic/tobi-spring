package org.tobi.spring.ch01;


import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
public class DaoFactory {

	private static Logger LOGGER = LoggerFactory.getLogger(UserDao.class);
	
	@Bean
	public UserDao userDao() {
		LOGGER.info("Create UserDao Object::");
		UserDao userDao = new UserDao();
		userDao.setDataSource(dataSource());
		return userDao;
	}
	
	@Bean
	public DataSource dataSource(){
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

		dataSource.setDriverClass(org.hsqldb.jdbcDriver.class);
		dataSource.setUrl("jdbc:hsqldb:hsql://localhost/TOBIDB");
		dataSource.setUsername("sa");
		
		return dataSource;
	}
		

}
