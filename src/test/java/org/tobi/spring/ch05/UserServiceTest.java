package org.tobi.spring.ch05;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tobi.spring.abstracts.AbstractTest;
import org.tobi.spring.ch01.User;
import org.tobi.spring.ch04.Level;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/applicationContext.xml")
public class UserServiceTest extends AbstractTest{
	
	@Autowired
	UserService userService;
	
	List<User> users;
	
	@Before
	public void setUp(){
		super.setUp();
		users = Arrays.asList(
					new User("bumjin","박범진","p1",Level.BASIC,49,0),
					new User("joytouch","강명성","p2",Level.BASIC,50,0),
					new User("erwins","신승한","p3",Level.SILVER,60,29),
					new User("madnite1","이상호","p4",Level.SILVER,60,30),
					new User("green","오민규","p5",Level.GOLD,100,100)
				);
	}
	
	@After
	public void tearDown(){
		super.tearDown();
	}
	
	@Test
	public void bean_test(){
		assertThat(this.userService, is(notNullValue()));
	}

}
