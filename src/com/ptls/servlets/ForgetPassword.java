package com.ptls.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ptls.daos.PersonDao;
import com.ptls.utilities.Mailer;

/**
 * Servlet implementation class ForgetPassword
 */
@WebServlet("/forgetpassword")
public class ForgetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForgetPassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PersonDao pd = new PersonDao();
		
		Random rand = new Random();
		
		int randomNumber = rand.nextInt(999999);
		
		String six_digit_code = String.format("%06d", randomNumber);
		String aadhar = request.getParameter("aadhar");
		try {
			
			
			if(pd.doesAadharExistInForgetPasswordHistoryTable(aadhar)){
				pd.updateForgetPasswordHistory(aadhar, six_digit_code);
			}
			else{
				pd.addPasswordForgetHistory(aadhar, six_digit_code);
			}
		
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//send email
		StringBuilder sb = new StringBuilder("");
		sb.append("Hi, <br />");
		sb.append("<p>We have received the request from you to reset the password. Please find the OTP to set new password for your account below.</p>");
		String OTP = six_digit_code.substring(0, 1)+" "+six_digit_code.substring(1, 2)+" "+six_digit_code.substring(2, 3)
		+" "+six_digit_code.substring(3, 4)+" "+six_digit_code.substring(4, 5)+" "+six_digit_code.substring(5);
		sb.append("<h3 style='text-align:center'>");
		sb.append(OTP);
		sb.append("</h3>");
		
		sb.append("<p>Please ignore this email if you haven't requested to reset the password</p>");
		sb.append("<p>Best Regards,</p>");
		sb.append("<p>JKPTLS Team</p>");
		
		String email="";
		try {
			email = pd.getEmailOfAadhar(aadhar);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Mailer.send(email, "OTP - Password Change Request for your OneLogin Account!", sb.toString());
		
		//redirect to otp page
		request.getSession().setAttribute("otpRequested", "1");
		request.getSession().setAttribute("aadharForOTP", aadhar);
		
		response.sendRedirect(request.getContextPath() + "/views/OTP.jsp");
		
	}

}
