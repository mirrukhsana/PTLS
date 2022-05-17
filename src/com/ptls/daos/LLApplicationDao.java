package com.ptls.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.ptls.models.LearnersLicenseApplication;
import com.ptls.utilities.DatabaseManager;

public class LLApplicationDao {
	
	
	public void createApplication(List<LearnersLicenseApplication> llaList) throws SQLException, ClassNotFoundException{
		
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		for(LearnersLicenseApplication lla : llaList){
			PreparedStatement stmt=con.prepareStatement("INSERT INTO llapplication (aadhar, application_number, application_status, licensetype, application_submission_date,"
					+ " dob_file, address_file, signature_file, health_check, cro_check, notified) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			stmt.setString(1, lla.getAadhar());
			stmt.setString(2, lla.getAppNum());
			stmt.setString(3, lla.getApplicationStatus());
			stmt.setString(4, lla.getLicenseType());
			stmt.setDate(5, (new java.sql.Date((new Date()).getTime())));
			stmt.setString(6, lla.getDobFileName());
			stmt.setString(7, lla.getAddressFileName());
			stmt.setString(8, lla.getSignatureFileName());
			stmt.setString(9, lla.getHealthStatus());
			stmt.setString(10, lla.getCroStatus());
			stmt.setString(11, lla.getNotifiedStatus());
			
			int noOfQueriesExecuted = stmt.executeUpdate();
			
			System.out.println(noOfQueriesExecuted);
		}
		
		DatabaseManager.getInstance().closeConnection(con);
		
	}

	public String extractLastAppnumber() throws ClassNotFoundException, SQLException{
		
		Connection con = DatabaseManager.getInstance().getDBConnection();
		PreparedStatement stmt=con.prepareStatement("select MAX(application_number) from llapplication ");  
		
		ResultSet rs=stmt.executeQuery();
		
		String appnum = null;
		
		if(rs.next()){
			appnum = rs.getString(1);
			System.out.println("Latest App Num is : "+appnum);
		}
		
		DatabaseManager.getInstance().closeConnection(con);
		return appnum;
	}
}
