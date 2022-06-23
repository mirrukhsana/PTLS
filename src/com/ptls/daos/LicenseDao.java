package com.ptls.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.ptls.constants.Constants;
import com.ptls.models.AadharInfoModel;
import com.ptls.models.LearnersLicenseApplication;
import com.ptls.models.LicenseModel;
import com.ptls.utilities.DatabaseManager;

public class LicenseDao {
	
	public boolean addLicense(String aadhar, String app_num, Date issueDate, Date expiryDate) throws SQLException, ClassNotFoundException{
			
			Connection con = DatabaseManager.getInstance().getDBConnection();
			
			PreparedStatement stmt=con.prepareStatement("insert into licenseinfo (aadhar, application_number, issue_date, expiry_date, lic_status) values(?,?,?,?,?)");
			stmt.setString(1, aadhar);
			stmt.setString(2, app_num);
			stmt.setDate(3, (new java.sql.Date(issueDate.getTime())));
			stmt.setDate(4, (new java.sql.Date(expiryDate.getTime())));
			stmt.setString(5, Constants.ISSUED);
			
			int noOfQueriesExecuted = stmt.executeUpdate();
			
			DatabaseManager.getInstance().closeConnection(con);
			
			if(noOfQueriesExecuted == 1){
				return true;
			}
			else{
				return false;
			}
		}
	
	public boolean updateLicenseForDL(String app_num, Date issueDate, Date expiryDate) throws SQLException, ClassNotFoundException{
		
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt2=con.prepareStatement("select aadhar from dlapplication where application_number = ?");
		stmt2.setString(1, app_num);
		
		ResultSet rs=stmt2.executeQuery();
		
		rs.next();
		String aadhar = rs.getString("aadhar");
		
		PreparedStatement stmt=con.prepareStatement("update licenseinfo set application_number = ?, issue_date = ?, expiry_date = ? where aadhar = ? ");
		
		stmt.setString(1, app_num);
		stmt.setDate(2, (new java.sql.Date(issueDate.getTime())));
		stmt.setDate(3, (new java.sql.Date(expiryDate.getTime())));
		stmt.setString(4, aadhar);
		
		int noOfQueriesExecuted = stmt.executeUpdate();
		
		DatabaseManager.getInstance().closeConnection(con);
		
		if(noOfQueriesExecuted == 1){
			return true;
		}
		else{
			return false;
		}
	}
	
	public LicenseModel getUserLicenseUsingAadharNumber(String aadhar) throws ClassNotFoundException, SQLException{
		
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("select * from licenseinfo where aadhar = ?");  
		stmt.setString(1, aadhar);
		ResultSet rs=stmt.executeQuery();  
		
		LicenseModel licenseModel = null;
		
		if(rs.next()){
			licenseModel = new LicenseModel();
			licenseModel.setAadhar(aadhar);
			licenseModel.setIssueDate(new Date(rs.getDate("issue_date").getTime()));
			licenseModel.setExpiryDate(new Date(rs.getDate("expiry_date").getTime()));
			licenseModel.setLicID(rs.getString("lic_id"));
			licenseModel.setAppnum(rs.getString("application_number"));
			
			String licenseStatus = "";
			if(rs.getString("lic_status").equals("I")){
				licenseStatus = "Issued";
			}
			
			licenseModel.setLicStatus(licenseStatus);
		}
		
		DatabaseManager.getInstance().closeConnection(con);
		
		return licenseModel;
	}
	
	public boolean canApplyForMainLicense(String aadhar) throws ClassNotFoundException, SQLException{
		
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("select * from licenseinfo where aadhar = ?");  
		stmt.setString(1, aadhar);
		ResultSet rs=stmt.executeQuery();  
		
		String applicationNum = null;
		Date expiryDateOfCurrentLic = null;
		
		if(rs.next()){
			applicationNum = rs.getString("application_number");
			expiryDateOfCurrentLic = new Date(rs.getDate("expiry_date").getTime());
		}
		
		
		boolean alreadyAppliedForDL = false;
		PreparedStatement stmt2=con.prepareStatement("select * from dlapplication where aadhar = ? and application_status = 'Pending Driving Test'");  
		stmt2.setString(1, aadhar);
		ResultSet rs2=stmt2.executeQuery();  
		
		if(rs2.next()){
			alreadyAppliedForDL = true;
		}
		
		System.out.println("$$$$$$$$$$$$$$$$$$ "+applicationNum +" "+expiryDateOfCurrentLic +" "+alreadyAppliedForDL);
		
		//Both of the conditions should be true
		boolean conditionOfHavingLearnersLicense = false;
		boolean conditionOfHavingLearnersExpiryGreaterThanCurrentDate = false;
		boolean canApplyForMainLicense = false;
		
		if(applicationNum != null){
			String firstLetterOfLicense = applicationNum.substring(0, 1);
			
			if(firstLetterOfLicense.equals("A")){
				conditionOfHavingLearnersLicense = true;
			}
			
			if(expiryDateOfCurrentLic.getTime() > (new Date()).getTime()){
				conditionOfHavingLearnersExpiryGreaterThanCurrentDate = true;
			}
			
			if(conditionOfHavingLearnersLicense && conditionOfHavingLearnersExpiryGreaterThanCurrentDate && !alreadyAppliedForDL){
				canApplyForMainLicense = true;
			}
			
		}
		else{
			canApplyForMainLicense = false;
		}
		
		DatabaseManager.getInstance().closeConnection(con);
		
		System.out.println(canApplyForMainLicense);
		
		System.out.println(conditionOfHavingLearnersLicense+" "+conditionOfHavingLearnersExpiryGreaterThanCurrentDate+" "+alreadyAppliedForDL);
		return canApplyForMainLicense;
	}
	
	public String getLicenseTypesUsingAadhar(String aadhar) throws ClassNotFoundException, SQLException{
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("select * from llapplication where application_number = (select application_number from licenseinfo where aadhar = ?)");  
		stmt.setString(1, aadhar);
		ResultSet rs=stmt.executeQuery();  
		
		String testResult = "";
		
		while(rs.next()){
			//This loop will run only once because every app_num will have only result
			testResult = testResult + rs.getString("licensetype") +",";
		}
		
		DatabaseManager.getInstance().closeConnection(con);
		
		return testResult.substring(0, testResult.length()-1);
	}
	
	public String getApprovedLicenseTypesForDLUsingAadhar(String aadhar) throws ClassNotFoundException, SQLException{
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("select * from dlapplication where application_number = (select application_number from licenseinfo where aadhar = ?) and application_status='Approved'");  
		stmt.setString(1, aadhar);
		ResultSet rs=stmt.executeQuery();  
		
		String testResult = "";
		
		while(rs.next()){
			//This loop will run only once because every app_num will have only result
			testResult = testResult + rs.getString("licensetype") +",";
		}
		
		DatabaseManager.getInstance().closeConnection(con);
		
		return testResult.substring(0, testResult.length()-1);
	}
}
