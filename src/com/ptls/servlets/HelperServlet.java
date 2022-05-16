package com.ptls.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ptls.constants.Constants;
import com.ptls.daos.PersonDao;

/**
 * Servlet implementation class Helper
 */
@WebServlet("/help")
public class HelperServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelperServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		if(request.getParameter("key").equals(Constants.checkIfAadharExists)){
			System.out.println(request.getParameter("key"));
			System.out.println(request.getParameter("value"));
			String aadhar = request.getParameter("value");
			
			//Write code to see if Aadhar already exists in DB
			
			PersonDao p_dao = new PersonDao();
			boolean doesAadharExist=false;
			try {
				doesAadharExist = p_dao.doesAadharExistInDB(aadhar);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(doesAadharExist){
				response.addHeader("result", "1");
			}
			else{
				response.addHeader("result", "0");
			}
			return;
		}
	}

}
