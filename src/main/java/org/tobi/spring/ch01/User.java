package org.tobi.spring.ch01;

import org.tobi.spring.ch04.Level;

public class User {

	String id;
	String name;
	String password;
	Level level;
	int login;
	int recommend;
	
	/**
	 * 
	 */
	public User(){
		
	}
	
	/**
	 * 
	 * @param id
	 * @param name
	 * @param password
	 * @param level
	 * @param login
	 * @param recommend
	 */
	public User(String id, String name, String password, Level level, int login, int recommend){
		this.id = id;
		this.name = name;
		this.password = password;
		this.level = level;
		this.login = login;
		this.recommend = recommend;
	}

	public int getLogin() {
		return login;
	}

	public void setLogin(int login) {
		this.login = login;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object obj) {
		User cuser = (User) obj;
		if (this.id.equals(cuser.getId()) && this.name.equals(cuser.getName())
				&& this.password.equals(cuser.getPassword())
				&& this.level == cuser.getLevel()
				&& this.login == cuser.getLogin()
				&& this.recommend == cuser.getRecommend())
			return true;

		return false;
	}
	
	public void upgradeLevel(){
		Level nextLevel = this.level.nextLevel();
		if(nextLevel==null){
			throw new IllegalStateException(this.level + "은 업그레이드 불가");
		}
		else{
			this.level = nextLevel;
		}
	}
}
