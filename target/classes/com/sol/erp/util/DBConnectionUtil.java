/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sol.erp.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.dbcp2.BasicDataSource;

import com.sol.erp.PropertiesHandler;
import com.sol.erp.constants.ApplicationConstants;

/**
 *
 * @author shekharkumar
 */
public class DBConnectionUtil {

	private static final Logger logger = Logger.getLogger(DBConnectionUtil.class.getName());
	private static Connection connection = null;

	private static BasicDataSource getDataSource() {
		BasicDataSource ds = null;
		Properties properties = PropertiesHandler.readProperties();
		if (properties != null) {
			String serverIP = properties.getProperty(ApplicationConstants.SERVER_IP);
			String serverPORT = properties.getProperty(ApplicationConstants.SERVER_PORT);

			String DBURL = "jdbc:mysql://" + serverIP + ":" + serverPORT + "/" + ApplicationConstants.DB_NAME
					+ "?useSSL=false";
			ds = new BasicDataSource();
			ds.setDriverClassName("com.mysql.jdbc.Driver");
			ds.setUsername(ApplicationConstants.DB_USER);
			ds.setPassword(ApplicationConstants.DB_PASSWORD);
			ds.setUrl(DBURL);

			//ds.setMinIdle(1000);
			//ds.setMaxIdle(10000);
			//ds.setMaxOpenPreparedStatements(50000);
		}
		return ds;
	}

	public static Connection getConnection() {
		try {

			connection = getDataSource().getConnection();

		} catch (SQLException ex) {
			Logger.getLogger(DBConnectionUtil.class.getName()).log(Level.SEVERE,
					ex.getMessage());
		}
		return connection;
	}

	public static boolean isDBRunning() {
		if (getConnection() != null) {
			return true;
		} else {
			return false;
		}
	}

	public static void closeConnection(ResultSet resultSet, Statement statement, Connection connection) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				logger.log(Level.SEVERE, e.getLocalizedMessage());
			}
		}
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				logger.log(Level.SEVERE, e.getLocalizedMessage());
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.log(Level.SEVERE, e.getLocalizedMessage());
			}
		}
	}

	public static void closeConnection(Statement statement, Connection connection) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				logger.log(Level.SEVERE, e.getLocalizedMessage());
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.log(Level.SEVERE, e.getLocalizedMessage());
			}
		}
	}

}
