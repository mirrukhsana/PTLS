package com.ptls.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.ptls.constants.Constants;
import com.ptls.models.AadharInfoModel;
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
	}
	
	DatabaseManager.getInstance().closeConnection(con);
	
	return licenseModel;
}
}
