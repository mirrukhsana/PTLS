package com.ptls.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ptls.utilities.DatabaseManager;

public class LLApplicationDao {

	public int extractAppnumber() throws ClassNotFoundException, SQLException{
		
		Connection con = DatabaseManager.getInstance().getConnectionOfAadharDB();
		PreparedStatement stmt=con.prepareStatement("select MAX(app_number) from LLapplication ");  
		int appnum = stmt.executeUpdate(); 
		
		DatabaseManager.getInstance().closeConnection(con);
		return appnum;
	}
}
