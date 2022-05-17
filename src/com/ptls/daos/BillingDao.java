package com.ptls.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ptls.models.BillingDataModel;
import com.ptls.utilities.DatabaseManager;

public class BillingDao {

public void createBill(BillingDataModel bdm) throws SQLException, ClassNotFoundException{
		
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
			PreparedStatement stmt=con.prepareStatement("INSERT INTO billingdata (application_number, name_on_card, card_num, total_amount) "
					+ "VALUES (?,?,?,?)");
			
			stmt.setString(1, bdm.getApp_num());
			stmt.setString(2, bdm.getName_on_card());
			stmt.setString(3, bdm.getCard_num());
			stmt.setInt(4, bdm.getTotal_amount());
			
			int noOfQueriesExecuted = stmt.executeUpdate();
			
			System.out.println(noOfQueriesExecuted);
		
		DatabaseManager.getInstance().closeConnection(con);
		
	}
	
}
