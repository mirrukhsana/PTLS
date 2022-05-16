package com.ptls.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ptls.constants.Constants;
import com.ptls.models.BillingDataModel;
import com.ptls.models.LearnersLicenseApplication;

/**
 * Servlet implementation class HandlePaymentServlet
 */
@WebServlet("/handlepayment")
public class HandlePaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandlePaymentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//get data from page, license types and billing data
		
		HttpSession session = request.getSession();
		
		String[] licenses = (String[]) session.getAttribute("typeOfLicenses");
		List<LearnersLicenseApplication> ll_list = new ArrayList<>();
		
		//GENEFRATE APP NUM LOGIC
		String app_num = generateAppNum();
		
		for (String licenseType : licenses){
			LearnersLicenseApplication lla = new LearnersLicenseApplication();
			lla.setAddressFileName((String) session.getAttribute("addressProof"));
			lla.setApplicationStatus(Constants.PROCESSING);
			
			
			//lla.setAppNum(app_num);
			
			lla.setCroStatus("0");
			lla.setDobFileName((String) session.getAttribute("dobDoc"));
			lla.setHealthStatus("0");
			lla.setNotifiedStatus("0");
			lla.setSignatureFileName((String) session.getAttribute("signature"));
			lla.setLicenseType(licenseType);
			
			ll_list.add(lla);
		}
		
		BillingDataModel bdm = new BillingDataModel();
		
		//bdm.setApp_num(app_num);
		bdm.setCard_num("************"+request.getParameter("cardnumber").substring(12));
		bdm.setName_on_card(request.getParameter("cardname"));
		bdm.setTotal_amount((Integer)session.getAttribute("totalAmount"));
		

		
		//create application in db
		
		//save billing data
		
		//Success Email
		
		//send Invoice page
	}

	private String generateAppNum() {
		
		//Get last license number
		
		//Based on last license number, generate new license number
		
		return null;
	}

}
