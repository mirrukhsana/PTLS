package com.ptls.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ptls.daos.AadharAccessDao;
import com.ptls.daos.PersonDao;
import com.ptls.models.AadharInfoModel;
import com.ptls.models.LicenseHolderModel;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("/PTLS/");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("TESTING!!!");
		
		boolean verified = false;
		
		LicenseHolderModel licenseHolder = new LicenseHolderModel();
		
		long aadhar = Long.parseLong(request.getParameter("aadhar"));
		
		AadharInfoModel aim = new AadharInfoModel();
		aim.setAadhar(aadhar+"");
		
		try{
			
			AadharAccessDao aad = new AadharAccessDao();
			verified = aad.verifyAadhar(aadhar+"");
			
			
		}catch (SQLException e) {
			System.out.println("DB Problem");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(verified){
			
			licenseHolder.setAadhar(aadhar);
			licenseHolder.setEmailId(request.getParameter("emailId"));
			licenseHolder.setPassword(request.getParameter("password"));
		
			System.out.println(licenseHolder); 
		
			PersonDao p_dao = new PersonDao();
		
		try {
			p_dao.addLicenseHolder(licenseHolder);
		} catch (SQLException e) {
			System.out.println("DB Problem");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getSession().setAttribute("aadhar", aadhar+"");
		
		response.sendRedirect(request.getContextPath() + "/");
	}
	
	else{
		
		request.setAttribute("isUserAuthenticatedSignup", "0");
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/");
		
		dispatcher.forward(request, response);
	}
}
}
