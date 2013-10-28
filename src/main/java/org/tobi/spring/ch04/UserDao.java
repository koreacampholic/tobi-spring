package org.tobi.spring.ch04;

import java.util.List;

import org.tobi.spring.ch01.User;

public interface UserDao {

	void add(User user) throws DuplicateUserIdException;
	User get(String id);
	List<User> getAll();
	void deleteAll();
	int getCount();
	public void update(User user);
}
