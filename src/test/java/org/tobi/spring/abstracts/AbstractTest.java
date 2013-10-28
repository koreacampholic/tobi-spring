package org.tobi.spring.abstracts;

import java.net.URL;

import javax.sql.DataSource;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tobi.spring.ch01.HsqlDBManager;
import org.tobi.spring.ch04.UserDao;


public class AbstractTest {
	
	boolean iscreateTable = false;

	static{
			// init log
			URL log4jConfig = AbstractTest.class.getClassLoader().getResource("log4j.property");
			if (log4jConfig != null)
				PropertyConfigurator.configure(log4jConfig);
			else
				BasicConfigurator.configure();
	}
	
	@Autowired
	DataSource dataSource;
	
	
	@Before
	public void setUp(){
		HsqlDBManager.startHsqlTobiDB();
		
		// create table
		if(iscreateTable){
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			jdbcTemplate.execute("drop table users if exists");
			jdbcTemplate.execute("create table users( "
					+ "id varchar(10) primary key, "
					+ "name varchar(20) not null, "
					+ "password varchar(10) not null, "
					+ "level tinyint not null, "
					+ "login integer not null, "
					+ "recommend integer not null)");
		}
	}
	
	@After
	public void tearDown(){
		HsqlDBManager.stopHsqlTobiDB();
	}

	public void setIscreateTable(boolean iscreateTable) {
		this.iscreateTable = iscreateTable;
	}
	
	
}
