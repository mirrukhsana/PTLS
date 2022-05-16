package com.ptls.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ptls.models.AadharInfoModel;
import com.ptls.utilities.DatabaseManager;

public class AadharAccessDao {
	
public AadharInfoModel getUserDataUsingAadharNumber(String aadhar) throws ClassNotFoundException, SQLException{
		
		Connection con = DatabaseManager.getInstance().getConnectionOfAadharDB();
		
		PreparedStatement stmt=con.prepareStatement("select * from aadharmock where aadhar = ?");  
		stmt.setString(1, aadhar);
		ResultSet rs=stmt.executeQuery();  
		
		AadharInfoModel aadharInfoModel = null;
		
		if(rs.next()){
			aadharInfoModel = new AadharInfoModel();
			aadharInfoModel.setAadhar(aadhar);
			aadharInfoModel.setFull_name(rs.getString("full_name"));
			aadharInfoModel.setGender(rs.getString("gender"));
			aadharInfoModel.setPhoto_url(rs.getString("photo_url"));
			aadharInfoModel.setAddress(rs.getString("address"));
			aadharInfoModel.setPostal_code(rs.getString("postal_code"));
			aadharInfoModel.setDistrict(rs.getString("district"));
			aadharInfoModel.setState(rs.getString("state"));
			aadharInfoModel.setCountry(rs.getString("country"));
			aadharInfoModel.setFathers_name(rs.getString("father_name"));
			aadharInfoModel.setPh_n0(rs.getString("ph_no"));
			aadharInfoModel.setEmailAddress(rs.getString("email"));
			
			java.util.Date utilDate = new Date(rs.getDate("dob").getTime());
			
			aadharInfoModel.setDob(utilDate);
			
			aadharInfoModel.setAge(calculateAge(aadharInfoModel.getDob()));
		
		}
		
		DatabaseManager.getInstance().closeConnection(con);
		
		return aadharInfoModel;
	}

private String calculateAge(Date dob) {
	if(dob != null){
		DateFormat formatter = new SimpleDateFormat("yyyyMMdd");                           
	    int d1 = Integer.parseInt(formatter.format(dob));                            
	    int d2 = Integer.parseInt(formatter.format(new Date()));         
	    
	    System.out.println(d2 +" "+d1);
	    
	    int age = (d2 - d1) / 10000;                                                       
	    return age+"";
	}
	else {
		return "NA";
	}
}

public boolean verifyAadhar(String aadhar) throws ClassNotFoundException, SQLException{
	
	Connection con = DatabaseManager.getInstance().getConnectionOfAadharDB();
	PreparedStatement stmt=con.prepareStatement("select * from aadharmock where aadhar = ?");  
	stmt.setString(1, aadhar);
	ResultSet rs=stmt.executeQuery(); 
	
	boolean verified = false;
	
	if(rs.next()){
		
		verified = true;
	}
	DatabaseManager.getInstance().closeConnection(con);
	return verified;
}
}
