package com.ptls.utilities;

import java.sql.*;

public class DatabaseManager {
	
	private static DatabaseManager dbManager = null;
	
	private DatabaseManager(){
		
	}
	
	public static DatabaseManager getInstance(){
		if (dbManager == null){
			dbManager = new DatabaseManager();
		}
		
		return dbManager;
	}
	
	public Connection getDBConnection() throws ClassNotFoundException{
		
		Connection con = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ptls","root","");
			
			if(con != null){
				System.out.println("DB Connection successful!");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	public Connection getConnectionOfAadharDB() throws ClassNotFoundException{
		
		Connection con = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/aadhardb","root","");
			
			if(con != null){
				System.out.println("DB Connection successful!");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	public void closeConnection(Connection con){
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
