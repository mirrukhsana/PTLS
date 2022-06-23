package com.ptls.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ptls.models.Complaint;
import com.ptls.utilities.DatabaseManager;

public class ComplaintDao {

	public boolean addComplaint(String aadhar, String name, String email, String complaint) throws SQLException, ClassNotFoundException{
		
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("insert into complaints (aadhar, name, email, complaint) values(?,?,?,?)");
		stmt.setString(1, aadhar);
		stmt.setString(2, name);
		stmt.setString(3, email);
		stmt.setString(4, complaint);
		
		int noOfQueriesExecuted = stmt.executeUpdate();
		
		System.out.println(noOfQueriesExecuted);
		
		DatabaseManager.getInstance().closeConnection(con);
		
		if(noOfQueriesExecuted == 1){
			return true;
		}
		else{
			return false;
		}
	}
	
	public List<Complaint> extractAllComplaints() throws ClassNotFoundException, SQLException{
		
		
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("select * from complaints");  
		
		ResultSet rs=stmt.executeQuery();  
		
		List<Complaint> listOfComplaints = new ArrayList<Complaint>();
		
		while(rs.next()){
			Complaint c = new Complaint();
			
			c.setAadhar(rs.getString(1));
			c.setName(rs.getString(2));
			c.setEmail(rs.getString(3));
			c.setComplaint(rs.getString(4));
			
			listOfComplaints.add(c);
		
		}
		
		DatabaseManager.getInstance().closeConnection(con);
		
		return listOfComplaints;
		
	}
	
}
