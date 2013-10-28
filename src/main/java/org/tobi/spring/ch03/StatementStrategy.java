package org.tobi.spring.ch03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 
 * @author gshwang
 *
 */
public interface StatementStrategy {

	public PreparedStatement makePreparedStatement(Connection c) throws SQLException;
}
