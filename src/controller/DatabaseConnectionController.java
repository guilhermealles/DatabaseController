package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectionController {
	private static final String DATABASE_ADDRESS = "jdbc:mysql://192.168.0.104:3306/NetComputing";
	private Connection connection;
	private Statement statement;
	private String query;
	
	public DatabaseConnectionController() {
		connection = null;
		statement = null;
		query = null;
	}
	
	public void connect() {
		try {
			connection = DriverManager.getConnection(DATABASE_ADDRESS, "root", "toor");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void setQuery(String query) {
		this.query = query;
		
		//TODO
		this.query = "INSERT INTO netcomputing-simple VALUES(1, 42.0);";
	}
	
	public ResultSet executeQuery() throws Exception {
		if (this.query == null) {
			throw new Exception("Error when executing query (is it null?);");
		}
		statement = connection.createStatement();
		ResultSet result = null;
		statement.executeUpdate(this.query);
		return result;
	}
	
	public void closeConnection() {
		try {
			this.connection.close();
		}
		catch (SQLException e) {
			
		}
	}
}
