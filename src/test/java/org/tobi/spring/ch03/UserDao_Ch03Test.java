package org.tobi.spring.ch03;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tobi.spring.abstracts.AbstractTest;
import org.tobi.spring.ch01.HsqlDBManager;
import org.tobi.spring.ch01.User;
import org.tobi.spring.ch04.DuplicateUserIdException;
import org.tobi.spring.ch04.Level;
import org.tobi.spring.ch04.UserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/applicationContext.xml")
public class UserDao_Ch03Test extends AbstractTest {

	private static Logger LOGGER = LoggerFactory.getLogger(UserDao_Ch03Test.class);
	
	//@Autowired
	//private ApplicationContext context;
	
	@Autowired
	UserDao dao;
	
	User user1,user2,user3 = null;
	

	@Before
	public void setUp() {
		setIscreateTable(true);
		super.setUp();
		
		//init test user info
		this.user1 = new User("gyumee", "박성철", "springno1", Level.BASIC, 1, 0);
		this.user2 = new User("leegw700", "이길원", "springno2", Level.SILVER, 55, 10);
		this.user3 = new User("bumjin", "박범진", "springno3", Level.GOLD, 100, 40);
	}

	@After
	public void tearDown()  {
		super.tearDown();
	}
	

	/**
	 * 주입정보를 xml로 작성하여 처리한 DI테스트
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Test
	public void deleteAll_test() throws SQLException {
		// Given
		//UserDao_Ch03 dao = context.getBean("userDaoCh03", UserDao_Ch03.class); 		

		// When
		dao.deleteAll();

		// Then
		assertThat(dao.getCount(), is(0));
	}
	
	@Test
	public void add_test() throws DuplicateUserIdException {
		// Given
		//UserDao_Ch03 dao = context.getBean("userDaoCh03", UserDao_Ch03.class);
		User user = new User();
		user.setId("1");
		user.setName("테스터1");
		user.setPassword("test1");
		user.setLevel(Level.BASIC);
		user.setLogin(1);
		user.setRecommend(0);
		
		// When
		dao.deleteAll();
		dao.add(user);
		User user2 = dao.get(user.getId());
		
		// Then
		assertThat(dao.getCount(), is(1));
		assertEquals(user2, user);
	}
	
	@Test
	public void update_test() throws DuplicateUserIdException{
		dao.deleteAll();
		
		dao.add(user1);
		dao.add(user2);
		
		user1.setName("오민규");
		user1.setName("springno6");
		user1.setLevel(Level.GOLD);
		user1.setLogin(1000);
		user1.setRecommend(999);
		
		dao.update(user1);
		
		User user1update = dao.get(user1.getId());
		assertThat(user1, is(user1update));
	}

}
