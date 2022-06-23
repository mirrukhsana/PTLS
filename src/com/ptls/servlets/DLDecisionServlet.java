package com.ptls.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ptls.daos.LLApplicationDao;
import com.ptls.daos.LicenseDao;

/**
 * Servlet implementation class DLDecisionServlet
 */
@WebServlet("/dldecision")
public class DLDecisionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DLDecisionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String appnum = request.getParameter("appnum");
		String decision = request.getParameter("dec");
		String licType = request.getParameter("lictype");
		
		LLApplicationDao llad = new LLApplicationDao();
		
		try {
			llad.updateDLApplicationStatus(appnum, licType, decision);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LicenseDao ld = new LicenseDao();
		
		try {
			ld.updateLicenseForDL(appnum, new Date(), calculateExpiryForDrivingLicense(new Date()));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect(request.getContextPath() + "/views/adminviewdlapps.jsp?dec="+decision);
	}
	
	private Date calculateExpiryForDrivingLicense(Date issueDate) {
		Calendar cal = Calendar.getInstance(); 
		cal.add(Calendar.YEAR, 10);
		Date expiryDate = cal.getTime();
		return expiryDate;
	}

}
