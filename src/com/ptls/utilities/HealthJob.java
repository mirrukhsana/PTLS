package com.ptls.utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 

public class HealthJob{  
	public static void main(String args[]){  
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/healthdb","root","");

			Connection con2=DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/ptls","root","");

			
				Statement stmt1=con2.createStatement();   
				ResultSet rs1 = stmt1.executeQuery("select * from llapplication where health_check = '0'"); 

				List<String> aadharsForHealthCheck = new ArrayList<String>();
				
				while(rs1.next()){
					aadharsForHealthCheck.add(rs1.getString(1));
				}
				
				Map<String, String> healthResults = new HashMap<>();
				
				String param = "";
				
				StringBuilder sb = new StringBuilder("");
				for(String aadhar : aadharsForHealthCheck){
					sb.append("'"+aadhar+"',");
				}
			
				param = sb.toString().substring(0, sb.length()-1);
				
				Statement stmt=con.createStatement();  
				ResultSet rs=stmt.executeQuery("select * from healthmock where aadhar in ("+param+");");  
				while(rs.next())  {
					healthResults.put(rs.getString(1), rs.getString(2));
					//System.out.println(rs.getString(1)+"  "+rs.getString(2));  
				}
				
				PreparedStatement stmt2 = con2.prepareStatement("update llapplication set health_check = ? where aadhar = ? and health_check = '0'");
				
				for(Map.Entry<String, String> e : healthResults.entrySet()){
					stmt2.setString(1, e.getValue());
					stmt2.setString(2, e.getKey());
					
					stmt2.executeUpdate();
				}
				
				
				con.close();  
				con2.close();
			}
			catch(Exception e){ 
				System.out.println(e);}  
		}  
}  
