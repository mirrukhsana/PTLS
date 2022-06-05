package com.ptls.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ptls.daos.PersonDao;

/**
 * Servlet implementation class OTPVerificationServlet
 */
@WebServlet("/verifyotp")
public class OTPVerificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public OTPVerificationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.sendRedirect(req.getContextPath() + "/");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getSession().getAttribute("aadharForOTP") != null){
			String userOTP = request.getParameter("otp1")+request.getParameter("otp2")+request.getParameter("otp3")
			+request.getParameter("otp4")+request.getParameter("otp5")+request.getParameter("otp6");

			String aadhar = request.getParameter("aadhar");
			request.getSession().setAttribute("aadharForOTP", null);
			
			PersonDao pd = new PersonDao();
			String realOTP="";
			try {
				realOTP = pd.getOtpFromForgetPasswordHistory(aadhar);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
			System.out.println("user OTP : "+userOTP+" , OTP for DB : "+realOTP);
			if(realOTP.equals(userOTP)){
				request.getSession().setAttribute("aadharForOTP", aadhar);
				response.sendRedirect(request.getContextPath() + "/views/changepassword.jsp");
			}
			else{
			
			}
		}
		else{
			response.sendRedirect(request.getContextPath() + "/");
		}
		
	}

}
