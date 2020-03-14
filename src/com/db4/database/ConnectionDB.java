package com.db4.database;

import java.sql.*;


public class ConnectionDB {
	public static Connection instance = null;
	public static ConnectionDB connectionClass = null;
	private static String IP = "127.0.0.1";
	private static String portNumber = "3306";
	private static String databaseName = "newjava";
	private static String username = "root";
	private static String password = "root";
	private static boolean created = false;

	public ConnectionDB() throws SQLException {
		try {
			instance = DriverManager.getConnection("jdbc:mysql://" + IP + ":" + portNumber + "/" + databaseName,
					username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (instance != null) {
			System.out.println("connected !");
		}
		created = true;
	}

	public static Connection getInstance() {
		if (!created) {
			try {
				connectionClass = new ConnectionDB();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	public void closeConnection() throws SQLException {
		if (instance != null) {
			System.out.println("closing connection");
			instance.close();
		}
	}
}
