package org.tobi.spring.ch01;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.tobi.spring.abstracts.AbstractTest;

public class UserDaoTest extends AbstractTest {

	private static Logger LOGGER = LoggerFactory.getLogger(UserDaoTest.class);

	@Before
	public void setUp() {
		super.setUp();
	}

	@After
	public void tearDown() {
		super.tearDown();
	}

	@Test
	public void addTest() throws ClassNotFoundException, SQLException {
		// Given
		UserDao dao = new DaoFactory().userDao();

		User user = new User();
		user.setId("whiteship");
		user.setName("백기선");
		user.setPassword("married");

		dao.delete(user.getId());

		// When
		dao.add(user);

		// Then
		assertEquals(user, dao.get(user.getId()));
	}

	@SuppressWarnings("resource")
	@Test
	public void addContextTest() throws ClassNotFoundException, SQLException {
		// Given
		ApplicationContext context = new AnnotationConfigApplicationContext(
				DaoFactory.class);
		UserDao dao = context.getBean("userDao", UserDao.class); // 'userDao' ::
																	// method
																	// name

		User user = new User();
		user.setId("whiteship");
		user.setName("백기선");
		user.setPassword("married");

		dao.delete(user.getId());

		// When
		dao.add(user);

		// Then
		assertEquals(user, dao.get(user.getId()));
	}

	@Test
	public void singletoneTest() {
		// Given
		ApplicationContext context = new AnnotationConfigApplicationContext(
				DaoFactory.class);
		UserDao dao1 = context.getBean("userDao", UserDao.class); 
		UserDao dao2 = context.getBean("userDao", UserDao.class); 

		// When

		// Log
		LOGGER.info("dao1:{},dao2:{}", dao1, dao2);

		// Then

		assertEquals(dao1, dao2);
	}

	@Test
	public void countingConnectionMakerTest() throws ClassNotFoundException,
			SQLException {
		// Given
		ApplicationContext context = new AnnotationConfigApplicationContext(
				DaoFactory.class);
		UserDao dao = context.getBean("userDao", UserDao.class);

		// When
		User user = new User();
		user.setId("whiteship");
		user.setName("백기선");
		user.setPassword("married");

		dao.delete(user.getId());
		dao.add(user);
		dao.get(user.getId());

		// Then
		CountingConnectionMaker ccm = context.getBean("connectionMaker",
				CountingConnectionMaker.class);
		assertEquals(3, ccm.count);

	}

	/**
	 * 주입정보를 xml로 작성하여 처리한 DI테스트
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void addXmlContextTest() throws ClassNotFoundException, SQLException {
		// Given
		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		UserDao dao = context.getBean("userDao", UserDao.class); 

		User user = new User();
		user.setId("whiteship");
		user.setName("백기선");
		user.setPassword("married");

		dao.delete(user.getId());

		// When
		dao.add(user);

		// Then
		// assertEquals(user, dao.get(user.getId()));
		assertThat(dao.get(user.getId()), is(user));
	}

}
