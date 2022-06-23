package com.ptls.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ptls.daos.LLApplicationDao;
import com.ptls.models.LearnersLicenseApplication;

/**
 * Servlet implementation class ApplyDrivingLicenseServlet
 */
@WebServlet("/applyML")
public class ApplyDrivingLicenseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApplyDrivingLicenseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect(request.getContextPath()+"/");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String drivingTestDate = request.getParameter("drivingTestDate");
		System.out.println(drivingTestDate);
		
		request.getSession().setAttribute("drivingTestDate", drivingTestDate);
		
		LLApplicationDao llad = new LLApplicationDao();
		
		List<LearnersLicenseApplication> llaList = null;
		try {
			llaList = llad.getLatestApprovedLearnersApplication((String) request.getSession().getAttribute("aadhar"));
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String[] licenses = new String[llaList.size()];
		
		int i=0;
		for(LearnersLicenseApplication lla : llaList){
			licenses[i] = lla.getLicenseType();
			i++;
		}
		
		request.getSession().setAttribute("typeOfLicenses", licenses);
		request.getSession().setAttribute("totalAmount", licenses.length*150+50); //includes online test fee
		request.getSession().setAttribute("dlForm", "dlForm");
		
		try {
			response.sendRedirect(request.getContextPath() + "/views/payment.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
