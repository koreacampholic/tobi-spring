package org.tobi.spring.ch01;

import org.hsqldb.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HsqlDBManager {
	private static Logger LOGGER = LoggerFactory.getLogger(HsqlDBManager.class);
	private static Server hsqlServer = null;
	
	static{
		hsqlServer = new Server();
		hsqlServer.setLogWriter(null);
		hsqlServer.setSilent(true);
		hsqlServer.setDatabaseName(0, "TOBIDB");
		hsqlServer.setDatabasePath(0, "file:TOBIDB");
	}
	
	
	public static int startHsqlTobiDB(){
		LOGGER.info("Start HsqlDB Server!!!");
		return hsqlServer.start();
	}
	
	public static int stopHsqlTobiDB(){
		LOGGER.info("Stop HsqlDB Server!!!");
		return hsqlServer.stop();
	}

}
