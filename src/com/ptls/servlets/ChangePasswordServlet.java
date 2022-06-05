package com.ptls.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ptls.daos.PersonDao;
import com.ptls.utilities.SecurityHandler;

/**
 * Servlet implementation class ChangePasswordServlet
 */
@WebServlet("/changepassword")
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePasswordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getSession().getAttribute("aadharForOTP") != null){
			String aadhar = (String) request.getSession().getAttribute("aadharForOTP");
			request.getSession().setAttribute("aadharForOTP", null);
			
			PersonDao pd = new PersonDao();
			String newPassword = request.getParameter("password");
			//String aadhar = request.getParameter("aadhar");
			
			String newPasswordEncrypted = SecurityHandler.encryptPassword(newPassword);
			boolean isPasswordUpdated = false;
			try {
				isPasswordUpdated = pd.changePassword(aadhar, newPasswordEncrypted);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
			if(isPasswordUpdated){
				request.setAttribute("isPasswordChanged", "1");
				//System.out.println(request.getContextPath());
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/");
				
				dispatcher.forward(request, response);
			}
			else{
				//DB Issue, then what?
			}
		}
		else{
			response.sendRedirect(request.getContextPath() + "/");
		}
		
	}

}
