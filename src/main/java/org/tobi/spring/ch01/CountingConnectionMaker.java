package org.tobi.spring.ch01;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker{
	
	int count = 0;
	private ConnectionMaker realConnectionMaker;
	
	/*public CountingConnectionMaker(ConnectionMaker realConnectionMaker){
		this.realConnectionMaker = realConnectionMaker;
	}*/
	

	public Connection makeConnection() throws ClassNotFoundException,
			SQLException {
			this.count++;
			return realConnectionMaker.makeConnection();
	}

	public void setRealConnectionMaker(ConnectionMaker realConnectionMaker) {
		this.realConnectionMaker = realConnectionMaker;
	}
	
	

}
