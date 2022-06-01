package com.ptls.utilities;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashSet;
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
				
				Map<String, String> notifyMsgs = new HashMap<>(); //First String is Aadhar, Second String is result
				
				boolean noAadharsToNotify = true;
				
				while(rs.next()){
					noAadharsToNotify = false;
					aadharsToNotify.add(rs.getString(1));
					if(!notifyMsgs.containsKey(rs.getString(1))){
						if(rs.getString(9).equals("Y") && rs.getString(10).equals("Y")){
							notifyMsgs.put(rs.getString(1), "APPROVED");
						}
						else{
							notifyMsgs.put(rs.getString(1), "REJECTED");
						}
					}
				}
				
				if(noAadharsToNotify){
					con.close();
					return;
				}
				
				System.out.println("here1");
				
				String params = "";
				
				StringBuilder sb = new StringBuilder("");
				for(String aadhar : aadharsToNotify){
					sb.append("'"+aadhar+"',");
				}
				System.out.println("here2");
				params = sb.toString().substring(0, sb.length()-1);

				Map<String, String> emailsToNotify = new HashMap<>();
				System.out.println("here3");
				PreparedStatement stmt2 = con.prepareStatement("select aadhar, email_id from licenseholder where aadhar in (" +params +")");
				
				ResultSet rs2=stmt2.executeQuery();
				System.out.println("here4");
				while(rs2.next()){
					String message = notifyMsgs.get(rs2.getString(1));
					emailsToNotify.put(rs2.getString(2), message);
				}
				
				
				System.out.println("here5");
				sendEmail(emailsToNotify);
				
				PreparedStatement stmt3 = con.prepareStatement("update llapplication set notified = '1', application_status = ? where aadhar = ? and notified = '0'");
				
				for(Map.Entry<String, String> aadharAndMessage : notifyMsgs.entrySet()){
					stmt3.setString(1, aadharAndMessage.getValue());
					stmt3.setString(2, aadharAndMessage.getKey());					
					stmt3.executeUpdate();
				}
				
				con.close();
			}
			catch(Exception e){ 
				System.out.println(e);}  
		}

	private static void sendEmail(Map<String, String> emailsToNotify) {
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
			
			for(Map.Entry<String, String> emailIdAndMessage : emailsToNotify.entrySet()){
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailIdAndMessage.getKey()));
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
