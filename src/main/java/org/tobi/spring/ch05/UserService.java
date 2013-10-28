package org.tobi.spring.ch05;

import java.util.List;

import org.tobi.spring.ch01.User;
import org.tobi.spring.ch04.DuplicateUserIdException;
import org.tobi.spring.ch04.Level;
import org.tobi.spring.ch04.UserDao;

public class UserService {

	public static final int MIN_RECOMMEND_FOR_GOLD = 30;
	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
	UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 
	 */
	public void upgradeLevels() {
		List<User> users = userDao.getAll();
		for (User user : users) {
			Boolean changed = null;
			if(canUpgradeLevel(user)){
				upgradeLevel(user);
			}
		}
	}
	
	private void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.update(user);
		
	}

	private boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();
		
		switch(currentLevel){
		case BASIC: return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
		case SILVER: return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
		case GOLD: return false;
		default: throw new IllegalArgumentException("Unkown Level:"+ currentLevel);
		}

	}

	/**
	 * 
	 * @param user
	 * @throws DuplicateUserIdException
	 */
	public void add(User user) throws DuplicateUserIdException{
		if(user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}

}
