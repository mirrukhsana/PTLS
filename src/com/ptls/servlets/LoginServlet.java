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
import com.ptls.utilities.SecurityHandler;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	if(req.getParameter("param") != null){
			if(req.getParameter("param").equals("logout")){
				if(req.getSession().getAttribute("aadhar") != null){
					req.getSession().setAttribute("aadhar", null);
					req.getSession().setAttribute("admin", null);
				}
			}
		}
		resp.sendRedirect(req.getContextPath()+"/");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(((String)request.getParameter("aadhar")).equals("admin")){
			request.getSession().setAttribute("admin", "admin");
			response.sendRedirect(request.getContextPath() + "/views/adminhome.jsp");
			return;
		}
		
		PersonDao p_dao = new PersonDao();
		boolean isUserAutheticated = false;
		
		try {
			String encryptedPassword = SecurityHandler.encryptPassword(request.getParameter("password"));
			isUserAutheticated = p_dao.authenticateUser(request.getParameter("aadhar"), encryptedPassword);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(isUserAutheticated){
			request.getSession().setAttribute("aadhar", request.getParameter("aadhar"));
			//System.out.println("Session Set : "+request.getSession().getAttribute("aadhar"));
			try {
				AadharInfoModel aim = (new AadharAccessDao()).getUserDataUsingAadharNumber(request.getParameter("aadhar"));
				//System.out.println("NAMEE : "+aim.getFull_name());
				request.getSession().setAttribute("aim", aim);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			response.sendRedirect(request.getContextPath() + "/views/oneloginportal.jsp");
		}
		else{
			
			request.setAttribute("isUserAuthenticated", "0");
			//System.out.println(request.getContextPath());
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/");
			
			dispatcher.forward(request, response);
			
		}
	}

}
