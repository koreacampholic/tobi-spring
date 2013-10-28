package org.tobi.spring.ch03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcContext {
	
	private static Logger LOGGER = LoggerFactory.getLogger(JdbcContext.class);
	
	private DataSource dataSource;
	
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	/**
	 * 
	 * @param stmt
	 * @throws SQLException
	 */
	public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException{
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = dataSource.getConnection();
			ps = stmt.makePreparedStatement(c);
			ps.executeUpdate();
			LOGGER.info(ps.toString());
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {

				}
			}
			if (c != null) {
				try {
					c.close();
				} catch (SQLException e) {

				}
			}
		}
	}
	
	/**
	 * 
	 * @param stmt
	 * @return
	 * @throws SQLException
	 */
	public ResultSet workWithReadStatementStrategy(StatementStrategy stmt) throws SQLException{
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = dataSource.getConnection();
			ps = stmt.makePreparedStatement(c);
			rs = ps.executeQuery();
			LOGGER.info(ps.toString());
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {

				}
			}
			if (c != null) {
				try {
					c.close();
				} catch (SQLException e) {

				}
			}
		}
		
		return rs;
	}

}
