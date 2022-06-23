package com.ptls.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ptls.daos.ComplaintDao;
import com.ptls.models.AadharInfoModel;

/**
 * Servlet implementation class ComplaintServlet
 */
@WebServlet("/complaint")
public class ComplaintServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComplaintServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String complaint = request.getParameter("complaint");
		
		AadharInfoModel aim = (AadharInfoModel) request.getSession().getAttribute("aim");
		
		ComplaintDao cd = new ComplaintDao();
		
		try {
			cd.addComplaint((String)request.getSession().getAttribute("aadhar"), aim.getFull_name(), aim.getEmailAddress(), complaint);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect(request.getContextPath() + "/views/ptlshome.jsp?complaintparam=1");
		
	}

}
