package com.ptls.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.ptls.models.LicenseHolderModel;
import com.ptls.utilities.DatabaseManager;

public class PersonDao {

	public boolean addLicenseHolder(LicenseHolderModel lhm) throws SQLException, ClassNotFoundException{
		
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("insert into licenseholder (aadhar, email_id, password) values(?,?,?)");
		stmt.setString(1, lhm.getAadhar()+"");
		stmt.setString(2, lhm.getEmailId()+"");
		stmt.setString(3, lhm.getPassword()+"");
		
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
	
	
	public boolean updateLicenseHolder(LicenseHolderModel lhm) throws SQLException, ClassNotFoundException{
		System.out.println("HEREEEEEEEEEE14");
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("update licenseholder set blood_group = ?, emergency_mobile_number = ?, identification_mark = ?, place_of_birth = ? where aadhar = ?");
		stmt.setString(1, lhm.getBloodGroup());
		stmt.setString(2, lhm.getEmergencyMobNo());
		stmt.setString(3, lhm.getIdentificationMark());
		stmt.setString(4, lhm.getPlaceOfBirth());
		stmt.setString(5, lhm.getAadhar()+"");
		System.out.println("HEREEEEEEEEEE15");
		int noOfQueriesExecuted = stmt.executeUpdate();
		System.out.println("HEREEEEEEEEEE16");
		System.out.println(noOfQueriesExecuted);
		
		DatabaseManager.getInstance().closeConnection(con);
		
		if(noOfQueriesExecuted == 1){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	public boolean doesAadharExistInDB(String aadhar) throws ClassNotFoundException, SQLException{
		
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("select * from licenseholder where aadhar = ?");  
		stmt.setString(1, aadhar);
		ResultSet rs=stmt.executeQuery();  
		
		boolean doesAadharExist = false;
		
		if(rs.next()){
			System.out.println("Aadhar exists!"+rs.getString(1));
			doesAadharExist = true;
		}
		else{
			doesAadharExist = false;
		}
		
		DatabaseManager.getInstance().closeConnection(con);
		
		return doesAadharExist;
	}
	
		public boolean authenticateUser(String aadhar, String password) throws ClassNotFoundException, SQLException{
		
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("select * from licenseholder where aadhar = ? and password = ?");  
		stmt.setString(1, aadhar);
		stmt.setString(2, password);
		ResultSet rs=stmt.executeQuery();  
		
		boolean doesAadharExist = false;
		
		if(rs.next()){
			System.out.println("Aadhar exists!");
			doesAadharExist = true;
		}
		else{
			doesAadharExist = false;
		}
		
		DatabaseManager.getInstance().closeConnection(con);
		
		return doesAadharExist;
	}
		
	public String getPasswordOfAadhar(String aadhar) throws ClassNotFoundException, SQLException{
		
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("select * from licenseholder where aadhar = ?");  
		stmt.setString(1, aadhar);
		ResultSet rs=stmt.executeQuery();  
		
		String password = null;
		
		if(rs.next()){
			password = rs.getString("password");
		}
		
		DatabaseManager.getInstance().closeConnection(con);
		
		return password;
	}
	
	
	public String getEmailOfAadhar(String aadhar) throws ClassNotFoundException, SQLException{
		
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("select * from licenseholder where aadhar = ?");  
		stmt.setString(1, aadhar);
		ResultSet rs=stmt.executeQuery();  
		
		String emailid = null;
		
		if(rs.next()){
			emailid = rs.getString("email_id");
		}
		
		DatabaseManager.getInstance().closeConnection(con);
		
		return emailid;
	}
		
	public boolean doesAadharExistInForgetPasswordHistoryTable(String aadhar) throws ClassNotFoundException, SQLException{
		
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("select * from forgetpasswordhistory where aadhar = ?");  
		stmt.setString(1, aadhar);
		ResultSet rs=stmt.executeQuery();  
		
		boolean doesAadharExist = false;
		
		if(rs.next()){
			//System.out.println("Aadhar exists!"+rs.getString(1));
			doesAadharExist = true;
		}
		else{
			doesAadharExist = false;
		}
		
		DatabaseManager.getInstance().closeConnection(con);
		
		return doesAadharExist;
	}
	
	public boolean addPasswordForgetHistory(String aadhar, String code) throws SQLException, ClassNotFoundException{
		
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("insert into forgetpasswordhistory (aadhar, forgetcode, entrydate) values(?,?,?)");
		stmt.setString(1, aadhar);
		stmt.setString(2, code);
		stmt.setDate(3, (new java.sql.Date((new Date()).getTime())));
		
		int noOfQueriesExecuted = stmt.executeUpdate();
		
		//System.out.println(noOfQueriesExecuted);
		
		DatabaseManager.getInstance().closeConnection(con);
		
		if(noOfQueriesExecuted == 1){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean updateForgetPasswordHistory(String aadhar, String code) throws SQLException, ClassNotFoundException{
		//System.out.println("HEREEEEEEEEEE14");
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("update forgetpasswordhistory set forgetcode = ?, entrydate = ? where aadhar = ?");
		stmt.setString(1, code);
		stmt.setDate(2, (new java.sql.Date((new Date()).getTime())));
		stmt.setString(3, aadhar);
		
		int noOfQueriesExecuted = stmt.executeUpdate();
		
		DatabaseManager.getInstance().closeConnection(con);
		
		if(noOfQueriesExecuted == 1){
			return true;
		}
		else{
			return false;
		}
	}
	
	public String getOtpFromForgetPasswordHistory(String aadhar) throws ClassNotFoundException, SQLException{
		
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("select * from forgetpasswordhistory where aadhar = ?");  
		stmt.setString(1, aadhar);
		ResultSet rs=stmt.executeQuery();  
		
		String otp = "";
		
		if(rs.next()){
			//System.out.println("Aadhar exists!"+rs.getString(1));
			otp = rs.getString("forgetcode");
		}
		
		DatabaseManager.getInstance().closeConnection(con);
		
		return otp;
	}
	
	public boolean changePassword(String aadhar, String newPasswordEncrypted) throws SQLException, ClassNotFoundException{
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("update licenseholder set password = ? where aadhar = ?");
		stmt.setString(1, newPasswordEncrypted);
		stmt.setString(2,aadhar);
		
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
