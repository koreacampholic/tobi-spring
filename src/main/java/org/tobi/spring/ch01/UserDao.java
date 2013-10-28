package org.tobi.spring.ch01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDao {

	@SuppressWarnings("unused")
	private static Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

	DataSource dataSource;

	/*
	 * public UserDao(ConnectionMaker connectionMaker){ this.connectionMaker =
	 * connectionMaker; }
	 */

	public void add(User user) throws ClassNotFoundException, SQLException {

		Connection c = dataSource.getConnection();

		PreparedStatement ps = c
				.prepareStatement("insert into users(id,name,password) values (?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.executeUpdate();

		ps.close();
		c.close();

	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection c = dataSource.getConnection();

		PreparedStatement ps = c
				.prepareStatement("select * from users where id=?");
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		User user = null;
		if (rs.next()) {
			user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
		}else{
			LOGGER.info("ResultSet is empty :: {}", id);
		}

		rs.close();
		ps.close();
		c.close();

		return user;
	}

	public void delete(String id) throws ClassNotFoundException, SQLException {
		Connection c = dataSource.getConnection();

		PreparedStatement ps = c
				.prepareStatement("delete from users where id=?");
		ps.setString(1, id);

		ps.executeUpdate();

		ps.close();
		c.close();

	}
}
