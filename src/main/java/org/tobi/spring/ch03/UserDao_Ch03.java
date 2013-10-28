package org.tobi.spring.ch03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.tobi.spring.ch01.User;
import org.tobi.spring.ch04.DuplicateUserIdException;
import org.tobi.spring.ch04.Level;
import org.tobi.spring.ch04.UserDao;

public class UserDao_Ch03 implements UserDao {

	@SuppressWarnings("unused")
	private static Logger LOGGER = LoggerFactory.getLogger(UserDao_Ch03.class);

	@Deprecated
	private JdbcContext jdbcContext;

	private DataSource dataSource;

	private RowMapper<User> rowMapper = new RowMapper<User>() {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setLevel(Level.valueOf(rs.getInt("level")));
			user.setLogin(rs.getInt("login"));
			user.setRecommend(rs.getInt("recommend"));
			return user;
		}
	};

	/**
	 * Use spring JdbcTemplate Object
	 */
	private JdbcTemplate jdbcTemplate;

	/*
	 * public UserDao(ConnectionMaker connectionMaker){ this.connectionMaker =
	 * connectionMaker; }
	 */

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dataSource = dataSource;
	}

	public void setJdbcContext(JdbcContext jdbcContext) {
		this.jdbcContext = jdbcContext;
	}

	public void add(final User user) throws DuplicateUserIdException {
		try {
			this.jdbcTemplate
					.update("insert into users(id,name,password,level,login,recommend) values (?,?,?,?,?,?)",
							user.getId(), user.getName(), user.getPassword(),
							user.getLevel().intValue(), user.getLogin(),
							user.getRecommend());
		} catch (DuplicateKeyException e) {
			// 예외전환
			throw new DuplicateUserIdException(e);
		}

	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public User get(final String id) {

		User user = this.jdbcTemplate.queryForObject(
				"select * from users where id=?", new Object[] { id },
				this.rowMapper);

		return user;
	}

	/**
	 * 
	 * @return
	 */
	public List<User> getAll() {
		List<User> userlist = this.jdbcTemplate.query(
				"select * from users order by id", this.rowMapper);
		return userlist;
	}

	/**
	 * 
	 * @param id
	 * @throws SQLException
	 */
	public void delete(final String id) throws SQLException {

		this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {

			public PreparedStatement makePreparedStatement(Connection c)
					throws SQLException {
				PreparedStatement ps = c
						.prepareStatement("delete from users where id=?");
				ps.setString(1, id);
				return ps;
			}
		});
	}

	/**
	 * 
	 * @throws SQLException
	 */
	public void deleteAll() {
		this.jdbcTemplate.update("delete from users");
	}

	/**
	 * 
	 * @throws SQLException
	 */
	public int getCount() {
		int count = this.jdbcTemplate.query(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				return con.prepareStatement("select count(*) from users");
			}
		}, new ResultSetExtractor<Integer>() {
			public Integer extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				rs.next();
				return rs.getInt(1);
			}
		});

		return count;

	}

	public void update(User user) {
		
		this.jdbcTemplate
				.update("update users set name=?,password=?,level=?,login=?,recommend=? where id=?",
						user.getName(), user.getPassword(), user.getLevel().intValue(),
						user.getLogin(), user.getRecommend(), user.getId());

	}
}
