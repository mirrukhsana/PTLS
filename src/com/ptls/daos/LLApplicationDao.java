package com.ptls.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ptls.models.AadharInfoModel;
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
	
	public boolean applyLL_isVisible(String aadhar) throws ClassNotFoundException, SQLException{
		
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("select * from llapplication where application_number = (select max(application_number) from llapplication where aadhar = ?)");  
		stmt.setString(1, aadhar);
		ResultSet rs=stmt.executeQuery();  
		
		boolean doesLL_APPL_exist = false;
		boolean isLatestLLApplicationRejected = false;
		
		if(rs.next()){ 
			if(rs.getString(9).equals("N") || rs.getString(10).equals("N")){
				isLatestLLApplicationRejected = true;
			}
			doesLL_APPL_exist = true;
		}
		else{
			doesLL_APPL_exist = false;
		}
		
		boolean isApplyLLVisible = false;
		
		if (!doesLL_APPL_exist){
			isApplyLLVisible = true;
			DatabaseManager.getInstance().closeConnection(con);
			return isApplyLLVisible;
		}
		
		if(isLatestLLApplicationRejected){
			isApplyLLVisible = true;
		}
		
		DatabaseManager.getInstance().closeConnection(con);
		return isApplyLLVisible;
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
	
	public ResultSet extractDataFromllapplication(String appnum) throws ClassNotFoundException, SQLException{
		
			Connection con = DatabaseManager.getInstance().getDBConnection();
			PreparedStatement stmt=con.prepareStatement("select * from llapplication where application_number = ?");  
			
			stmt.setString(1, appnum);
			ResultSet rs=stmt.executeQuery();
			
			if(rs.next()){
				//DatabaseManager.getInstance().closeConnection(con);
			return rs;
			}
			
			else{
				//DatabaseManager.getInstance().closeConnection(con);
				return null;
		}
	}
	
	public List<LearnersLicenseApplication> extractAllApplicationsOfLicenseHolder(String aadhar) throws ClassNotFoundException, SQLException{
		
		
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("select * from llapplication where aadhar = ?");  
		stmt.setString(1, aadhar);
		ResultSet rs=stmt.executeQuery();  
		
		List<LearnersLicenseApplication> listOfApplications = new ArrayList<LearnersLicenseApplication>();
		
		while(rs.next()){
			LearnersLicenseApplication lla = new LearnersLicenseApplication();
			lla.setAppNum(rs.getString(2));
			lla.setApplicationStatus(rs.getString(3));
			lla.setLicenseType(rs.getString(4));
			lla.setHealthStatus(rs.getString(9));
			lla.setCroStatus(rs.getString(10));
			
			
			Date submissionDate = rs.getDate(5);
			
			lla.setLicenseSubmissionDate(submissionDate);
			
			listOfApplications.add(lla);
		
		}
		
		DatabaseManager.getInstance().closeConnection(con);
		
		return listOfApplications;
		
	}
	
}
