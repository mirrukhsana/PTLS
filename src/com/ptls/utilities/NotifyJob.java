package com.ptls.utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set; 
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class NotifyJob {
	public static void main(String args[]){  
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/ptls","root","");
			
				Statement stmt=con.createStatement();   
				ResultSet rs = stmt.executeQuery("select * from llapplication where cro_check <> '0' and health_check <> '0' and notified = 0"); 

				Set<String> aadharsToNotify = new LinkedHashSet<>();
				
				while(rs.next()){
					aadharsToNotify.add(rs.getString(1));
				}
				
				System.out.println("here1");
				
				String params = "";
				
				StringBuilder sb = new StringBuilder("");
				for(String aadhar : aadharsToNotify){
					sb.append("'"+aadhar+"',");
				}
				System.out.println("here2");
				params = sb.toString().substring(0, sb.length()-1);

				Set<String> emailsToNotify = new LinkedHashSet<>();
				System.out.println("here3");
				PreparedStatement stmt2 = con.prepareStatement("select email_id from licenseholder where aadhar in (" +params +")");
				
				ResultSet rs2=stmt2.executeQuery();
				System.out.println("here4");
				while(rs2.next()){
					emailsToNotify.add(rs2.getString(1));
				}
				
				
				System.out.println("here5");
				sendEmail(emailsToNotify);
				
				PreparedStatement stmt3 = con.prepareStatement("update llapplication set notified = '1' where aadhar = ? and notified = '0'");
				
				for(String e : aadharsToNotify){
					stmt3.setString(1, e);					
					stmt3.executeUpdate();
				}
				
				con.close();
			}
			catch(Exception e){ 
				System.out.println(e);}  
		}

	private static void sendEmail(Set<String> emailsToNotify) {
		final String user = "";// change accordingly
		final String pass = "";

		// 1st step) Get the session object
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");// change accordingly
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");
		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, pass);
			}
		});
		
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			
			for(String emailId : emailsToNotify){
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailId));
			}
			
			message.setSubject("Your Driving License Application has been processed!");
			//message.setText((String)object);
			message.setContent("Hi, <br> <p>Your License Application has been Processed.</p><p>Please logon to ptls application to check application result. If your application was successfully verified. You can check if the online examination link has been enabled or not.</p>"
					+ "<p>If you have any concerns, please contact our team at jkptlsteam@gmail.com</p>", "text/html; charset=utf-8");
			
			// 3rd step)send message
			Transport.send(message);

			System.out.println("email sent!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}  
}
