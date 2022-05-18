package com.ptls.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ptls.constants.Constants;
import com.ptls.daos.AadharAccessDao;
import com.ptls.daos.BillingDao;
import com.ptls.daos.LLApplicationDao;
import com.ptls.models.AadharInfoModel;
import com.ptls.models.BillingDataModel;
import com.ptls.models.LearnersLicenseApplication;
import com.ptls.utilities.Mailer;

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
			lla.setAadhar((String)request.getSession().getAttribute("aadhar"));
			lla.setAddressFileName((String) session.getAttribute("addressProof"));
			lla.setApplicationStatus(Constants.PROCESSING);
			
			
			lla.setAppNum(app_num);
			
			lla.setCroStatus("0");
			lla.setDobFileName((String) session.getAttribute("dobDoc"));
			lla.setHealthStatus("0");
			lla.setNotifiedStatus("0");
			lla.setSignatureFileName((String) session.getAttribute("signature"));
			lla.setLicenseType(licenseType);
			
			ll_list.add(lla);
		}
		
		BillingDataModel bdm = new BillingDataModel();
		
		bdm.setApp_num(app_num);
		bdm.setCard_num("************"+request.getParameter("cardnumber").substring(12));
		bdm.setName_on_card(request.getParameter("cardname"));
		bdm.setTotal_amount((Integer)session.getAttribute("totalAmount"));

		//Create Application
		LLApplicationDao lld = new LLApplicationDao();
		try {
			lld.createApplication(ll_list);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		request.getSession().setAttribute("ll_list", ll_list);
		
		//save billing data
		BillingDao bd = new BillingDao();
		try {
			bd.createBill(bdm);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		request.getSession().setAttribute("bdm", bdm);
		
		
		PrintWriter Writer = response.getWriter();
		String imagepath = "/imgs/PTLSlogo.png";
		//build html message
		String htmlmessage = "<html>";
		htmlmessage += "<img src="+imagepath+" height=50% width=50% >";
		htmlmessage +="<br/><br/>"+((AadharInfoModel)(request.getSession().getAttribute("aam"))).getFull_name()+ "!<h4>Your Learners Application has been created." + "<br/><br/>";
		htmlmessage += "Your Application number : "  + bdm.getApp_num() + "</h4>";
		htmlmessage += "</html>";
		
		//Success Email
		System.out.println("sending email to : "+((AadharInfoModel)(request.getSession().getAttribute("aam"))).getEmailAddress());
		Mailer.send(((AadharInfoModel)(request.getSession().getAttribute("aam"))).getEmailAddress(), "Application Suucessful", htmlmessage);
		
		//send Invoice page
		response.sendRedirect(request.getContextPath() + "/views/invoice.jsp");
	}

	private String generateAppNum() {
		
		//Get last license number
		String appnumstring = null;
		
		try{
			String appnum = null;
		    LLApplicationDao lld = new LLApplicationDao();
		    appnum = lld.extractLastAppnumber(); //ex A000000000001
		
		    if(appnum != null){
		    	String firstAppLetter = appnum.substring(0, 1); //ex A
			    String restOfTheAppNum = appnum.substring(1);  //000000000001
			    
			    int restOfAppNumINT = Integer.parseInt(restOfTheAppNum);
			    System.out.println("restOfAppNumINT : " + restOfAppNumINT);
			    
			    restOfAppNumINT++;
			    
			    appnumstring = String.format("%012d", restOfAppNumINT);
			    
			    appnumstring = firstAppLetter + appnumstring;
		    }
		    else{
		    	appnumstring = "A000000000001";
		    }
		    
		}catch (SQLException e) {
			System.out.println("DB Problem");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Based on last license number, generate new license number
		return appnumstring;
	}

}
