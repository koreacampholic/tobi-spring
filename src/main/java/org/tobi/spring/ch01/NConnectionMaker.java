package org.tobi.spring.ch01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NConnectionMaker implements ConnectionMaker {

	public Connection makeConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName("org.hsqldb.jdbcDriver");
		Connection c = DriverManager.getConnection(
				"jdbc:hsqldb:hsql://localhost/TOBIDB", "sa", "");
		return c;
	}

}
