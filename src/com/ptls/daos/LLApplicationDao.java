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
import com.ptls.models.LicenseHolderModel;
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
			
			if(checkIfLastOnlineTestFailed(aadhar)){
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

	private boolean checkIfLastOnlineTestFailed(String aadhar) throws ClassNotFoundException, SQLException {
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("select * from onlinetestresult where application_number = (select max(application_number) from llapplication where aadhar = ?) and final_result = 'FAIL'");  
		stmt.setString(1, aadhar);
		ResultSet rs=stmt.executeQuery();  
		
		boolean result = false;
		
		if(rs.next()) result = true;
		else result = false;
		
		rs.close();
		con.close();
		
		return result;
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
	
	public List<LearnersLicenseApplication> extractAllDLApplicationsOfLicenseHolder(String aadhar) throws ClassNotFoundException, SQLException{
		
		
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("select * from dlapplication where aadhar = ?");  
		stmt.setString(1, aadhar);
		ResultSet rs=stmt.executeQuery();  
		
		List<LearnersLicenseApplication> listOfApplications = new ArrayList<LearnersLicenseApplication>();
		
		while(rs.next()){
			LearnersLicenseApplication lla = new LearnersLicenseApplication();
			lla.setAppNum(rs.getString(2));
			lla.setApplicationStatus(rs.getString(3));
			lla.setLicenseType(rs.getString(4));
			lla.setHealthStatus("");
			lla.setCroStatus("");
			
			
			Date submissionDate = rs.getDate(5);
			
			lla.setLicenseSubmissionDate(submissionDate);
			
			listOfApplications.add(lla);
		
		}
		
		DatabaseManager.getInstance().closeConnection(con);
		
		return listOfApplications;
		
	}
	
	public String getonlineTestResultUsingAppNum(String app_num) throws ClassNotFoundException, SQLException{
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("select * from onlinetestresult where application_number = ?");  
		stmt.setString(1, app_num);
		ResultSet rs=stmt.executeQuery();  
		
		String testResult = null; //null means haven't given the test yet
		
		while(rs.next()){
			//This loop will run only once because every app_num will have only result
			testResult = rs.getString("final_result");
		}
		
		DatabaseManager.getInstance().closeConnection(con);
		
		return testResult;
	}
	
	public String getLicenseTypesUsingAppNum(String app_num) throws ClassNotFoundException, SQLException{
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("select * from llapplication where application_number = ?");  
		stmt.setString(1, app_num);
		ResultSet rs=stmt.executeQuery();  
		
		String testResult = "";
		
		while(rs.next()){
			//This loop will run only once because every app_num will have only result
			testResult = testResult + rs.getString("licensetype") +", ";
		}
		
		DatabaseManager.getInstance().closeConnection(con);
		
		return testResult.substring(0, testResult.length()-1);
	}

	public List<LearnersLicenseApplication> getLatestApprovedLearnersApplication(String aadhar) throws ClassNotFoundException, SQLException{
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("select * from llapplication where aadhar = ? and application_status='APPROVED' and application_submission_date = "
				+ "(select MAX(application_submission_date) from llapplication where aadhar = ?) and application_number = (select MAX(application_number) from llapplication where aadhar = ?)");  
		stmt.setString(1, aadhar);
		stmt.setString(2, aadhar);
		stmt.setString(3, aadhar);
		ResultSet rs=stmt.executeQuery();  
		
		List<LearnersLicenseApplication> listOfApplications = new ArrayList<LearnersLicenseApplication>();
		
		while(rs.next()){
			LearnersLicenseApplication lla = new LearnersLicenseApplication();
			//lla.setAppNum(rs.getString(2));
			//lla.setApplicationStatus(rs.getString(3));
			lla.setLicenseType(rs.getString(4));
			lla.setDobFileName(rs.getString(6));
			lla.setAddressFileName(rs.getString(7));
			lla.setSignatureFileName(rs.getString(8));
			
			listOfApplications.add(lla);
		
		}
		
		DatabaseManager.getInstance().closeConnection(con);
		
		return listOfApplications;
	}

	public String extractLastAppnumberForDL() throws ClassNotFoundException, SQLException {
		Connection con = DatabaseManager.getInstance().getDBConnection();
		PreparedStatement stmt=con.prepareStatement("select MAX(application_number) from dlapplication ");  
		
		ResultSet rs=stmt.executeQuery();
		
		String appnum = null;
		
		if(rs.next()){
			appnum = rs.getString(1);
			System.out.println("Latest App Num is : "+appnum);
		}
		
		DatabaseManager.getInstance().closeConnection(con);
		return appnum;
	}

	public void createDLApplication(List<LearnersLicenseApplication> dl_list, Date driving_test_date) throws ClassNotFoundException, SQLException {
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		for(LearnersLicenseApplication lla : dl_list){
			PreparedStatement stmt=con.prepareStatement("INSERT INTO dlapplication (aadhar, application_number, application_status, licensetype, application_submission_date,"
					+ " dob_file, address_file, signature_file, driving_test_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			stmt.setString(1, lla.getAadhar());
			stmt.setString(2, lla.getAppNum());
			stmt.setString(3, lla.getApplicationStatus());
			stmt.setString(4, lla.getLicenseType());
			stmt.setDate(5, (new java.sql.Date((new Date()).getTime())));
			stmt.setString(6, lla.getDobFileName());
			stmt.setString(7, lla.getAddressFileName());
			stmt.setString(8, lla.getSignatureFileName());
			stmt.setDate(9, (new java.sql.Date(driving_test_date.getTime())));
			
			int noOfQueriesExecuted = stmt.executeUpdate();
			
			System.out.println(noOfQueriesExecuted);
		}
		
		DatabaseManager.getInstance().closeConnection(con);
	}
	
	public List<LearnersLicenseApplication> getAllPendingDLApplications() throws ClassNotFoundException, SQLException{
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("select * from dlapplication where application_status='Pending Driving Test' or application_status='Pending Renewal Approval'");  
		
		ResultSet rs=stmt.executeQuery();  
		
		List<LearnersLicenseApplication> listOfApplications = new ArrayList<LearnersLicenseApplication>();
		
		while(rs.next()){
			LearnersLicenseApplication lla = new LearnersLicenseApplication();
			lla.setAadhar(rs.getString(1));
			lla.setAppNum(rs.getString(2));
			lla.setApplicationStatus(rs.getString(3));
			lla.setLicenseType(rs.getString(4));
			
			listOfApplications.add(lla);
		
		}
		
		DatabaseManager.getInstance().closeConnection(con);
		
		return listOfApplications;
	}
	
	public boolean updateDLApplicationStatus(String appnum, String licType, String decision) throws SQLException, ClassNotFoundException{
		
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("update dlapplication set application_status = ? where application_number = ? and licensetype = ? ");
		
		String newApplicationStatus = "";
		
		if(decision.equals("A")){
			newApplicationStatus = "Approved";
		}
		else newApplicationStatus = "Rejected";
		
		stmt.setString(1, newApplicationStatus);
		stmt.setString(2, appnum);
		stmt.setString(3, licType);
		
		int noOfQueriesExecuted = stmt.executeUpdate();
		
		DatabaseManager.getInstance().closeConnection(con);
		
		if(noOfQueriesExecuted == 1){
			return true;
		}
		else{
			return false;
		}
	}
	
}
