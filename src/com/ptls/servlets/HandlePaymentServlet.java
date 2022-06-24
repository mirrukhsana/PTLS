package com.ptls.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.ptls.daos.LicenseDao;
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
		
		if(session.getAttribute("typeOfLicenses") == null){
			response.sendRedirect(request.getContextPath() + "/");
		}
		
		//This code is only for Driving license payment
		if(session.getAttribute("drivingTestDate") != null){
			
			String drivingTestDateString = (String) session.getAttribute("drivingTestDate");
			Date drivingTestDate = null;
			try {
				drivingTestDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(drivingTestDateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if(drivingTestDate == null) return;
			
			session.setAttribute("drivingTestDate", null);
			
			handlePaymentForDrivingLicense(request, session, response, drivingTestDate, ((String)request.getSession().getAttribute("aadhar")));
			return;
		}
		
		if(session.getAttribute("rlForm") != null){
			
			
			handlePaymentForRenewalLicense(request, session, response, ((String)request.getSession().getAttribute("aadhar")));
			return;
		}
		
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
		
		//build html message
		String htmlmessage = "<p>Hello " + ((AadharInfoModel)(request.getSession().getAttribute("aam"))).getFull_name()+",</p>";
		htmlmessage += "We are pleased to inform you that your Learners License Application with Application Number " +bdm.getApp_num() +" has been created successfully.";

		htmlmessage += "<h4>Please find the invoice details below<h4>";

		htmlmessage +="<table style='width:100%'> <tr> <th style='padding:5%;'>License Submission Date</th> <th style='padding:5%;'>Application Number</th> <th style='padding:5%;'>Payment</th> <th style='padding:5%;'>Billing Address</th></tr><tr><td style='padding:5%;'>"+(new Date()).getDate()+"-"+(new Date()).getMonth()+1+"-"+(new Date()).getYear()+1900+"</td><td style='padding:5%;'>"+bdm.getApp_num()+"</td><td style='padding:5%;'>Debit/Credit Card</td><td style='padding:5%;'>"+((AadharInfoModel)(request.getSession().getAttribute("aam"))).getAddress()+"</td></tr></table>";
		
		htmlmessage += "<hr />";
		
		htmlmessage +="<table style='width:100%'> <tr> <th style='padding:5%;'>License Type</th> <th style='padding:5%;'>Quantity</th> <th style='padding:5%;'>Amount</th></tr>";
		
		for (String licenseType : licenses){
			htmlmessage += "<tr><td style='padding:5%;'>"+licenseType+"</td><td style='padding:5%;'>"+1+"</td><td style='padding:5%;'>"+150.00+"</td></tr>";
		}
		
		htmlmessage += "<tr><td style='padding:5%;'>Online Test</td><td style='padding:5%;'>"+1+"</td><td style='padding:5%;'>"+50.00+"</td></tr></table>";
		htmlmessage += "<p></p>";
		htmlmessage +="<h5>Thank you for applying!</h5>";
		htmlmessage +="<h4>JK PTLS Team<h4>";
		htmlmessage +="<h6>Need help? Contact us jkptlsteam@gmail.com<h6>";
		
		//Success Email
		System.out.println("sending email to : "+((AadharInfoModel)(request.getSession().getAttribute("aam"))).getEmailAddress());
		Mailer.send(((AadharInfoModel)(request.getSession().getAttribute("aam"))).getEmailAddress(), "Application Successful", htmlmessage);
		
		//send Invoice page
		response.sendRedirect(request.getContextPath() + "/views/invoice.jsp");
	}

	private void handlePaymentForRenewalLicense(HttpServletRequest request, HttpSession session,
	HttpServletResponse response, String aadhar) {
		//Get latest llApplication Data
		List<LearnersLicenseApplication> dl_list = new ArrayList<>();
		
		LLApplicationDao llad = new LLApplicationDao();
		
		List<LearnersLicenseApplication> ll_list = null;
		
		try {
			ll_list = llad.getLatestApprovedLearnersApplication(aadhar);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//GENEFRATE APP NUM LOGIC for DL
		String app_num = generateAppNumForDL();
		
		for(LearnersLicenseApplication lla : ll_list){
			LearnersLicenseApplication lla2 = new LearnersLicenseApplication();
			lla2.setAadhar(aadhar);
			lla2.setAppNum(app_num);
			lla2.setApplicationStatus("Pending Renewal Approval");
			lla2.setLicenseType(lla.getLicenseType());
			lla2.setDobFileName(lla.getDobFileName());
			lla2.setAddressFileName(lla.getAddressFileName());
			lla2.setSignatureFileName(lla.getSignatureFileName());
			lla2.setLicenseSubmissionDate(new Date());
			
			dl_list.add(lla2);
		}
		
		BillingDataModel bdm = new BillingDataModel();
		
		bdm.setApp_num(app_num);
		bdm.setCard_num("************"+request.getParameter("cardnumber").substring(12));
		bdm.setName_on_card(request.getParameter("cardname"));
		bdm.setTotal_amount((Integer)session.getAttribute("totalAmount"));
		
		
		//Create Application
		LLApplicationDao lld = new LLApplicationDao();
		try {
			lld.createDLApplication(dl_list, new Date());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		request.getSession().setAttribute("dl_list", dl_list);
		
		//save billing data
		BillingDao bd = new BillingDao();
		try {
			bd.createBill(bdm);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		request.getSession().setAttribute("bdm", bdm);
		
		//build html message
		String htmlmessage = "<p>Hello " + ((AadharInfoModel)(request.getSession().getAttribute("aam"))).getFull_name()+",</p>";
		htmlmessage += "We are pleased to inform you that your Renewal License Application with Application Number " +bdm.getApp_num() +" has been created successfully.";
	
		htmlmessage += "<h4>Do keep track of the application in PTLS app to see if your application for renewal is approved.<h4>";
		
		htmlmessage += "<h4>Please find the invoice details below<h4>";
	
		htmlmessage +="<table style='width:100%'> <tr> <th style='padding:5%;'>License Submission Date</th> <th style='padding:5%;'>Application Number</th> <th style='padding:5%;'>Payment</th> <th style='padding:5%;'>Billing Address</th></tr><tr><td style='padding:5%;'>"+(new Date()).getDate()+"-"+(new Date()).getMonth()+1+"-"+(new Date()).getYear()+1900+"</td><td style='padding:5%;'>"+bdm.getApp_num()+"</td><td style='padding:5%;'>Debit/Credit Card</td><td style='padding:5%;'>"+((AadharInfoModel)(request.getSession().getAttribute("aam"))).getAddress()+"</td></tr></table>";
		
		htmlmessage += "<hr />";
		
		htmlmessage +="<table style='width:100%'> <tr> <th style='padding:5%;'>License Type</th> <th style='padding:5%;'>Quantity</th> <th style='padding:5%;'>Amount</th></tr>";
		
		String[] licenses = new String[dl_list.size()];
		
		int i=0;
		for(LearnersLicenseApplication l : dl_list){
			licenses[i] = l.getLicenseType();
			i++;
		}
		
		for (String licenseType : licenses){
			htmlmessage += "<tr><td style='padding:5%;'>"+licenseType+"</td><td style='padding:5%;'>"+1+"</td><td style='padding:5%;'>"+150.00+"</td></tr>";
		}
		
		htmlmessage += "<tr><td style='padding:5%;'>Form Fee</td><td style='padding:5%;'>"+1+"</td><td style='padding:5%;'>"+50.00+"</td></tr></table>";
		htmlmessage += "<p></p>";
		htmlmessage +="<h5>Thank you for applying!</h5>";
		htmlmessage +="<h4>JK PTLS Team<h4>";
		htmlmessage +="<h6>Need help? Contact us jkptlsteam@gmail.com<h6>";
		
		//Success Email
		System.out.println("sending email to : "+((AadharInfoModel)(request.getSession().getAttribute("aam"))).getEmailAddress());
		Mailer.send(((AadharInfoModel)(request.getSession().getAttribute("aam"))).getEmailAddress(), "Application Successful", htmlmessage);
		
		//send Invoice page
		try {
			response.sendRedirect(request.getContextPath() + "/views/invoice.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
					
	}

	private void handlePaymentForDrivingLicense(HttpServletRequest request, HttpSession session, HttpServletResponse response, Date drivingTestDate, String aadhar) {
		
		//Get latest llApplication Data
		List<LearnersLicenseApplication> dl_list = new ArrayList<>();
		
		LLApplicationDao llad = new LLApplicationDao();
		
		List<LearnersLicenseApplication> ll_list = null;
		
		try {
			ll_list = llad.getLatestApprovedLearnersApplication(aadhar);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//GENEFRATE APP NUM LOGIC for DL
		String app_num = generateAppNumForDL();
		
		for(LearnersLicenseApplication lla : ll_list){
			LearnersLicenseApplication lla2 = new LearnersLicenseApplication();
			lla2.setAadhar(aadhar);
			lla2.setAppNum(app_num);
			lla2.setApplicationStatus("Pending Driving Test");
			lla2.setLicenseType(lla.getLicenseType());
			lla2.setDobFileName(lla.getDobFileName());
			lla2.setAddressFileName(lla.getAddressFileName());
			lla2.setSignatureFileName(lla.getSignatureFileName());
			lla2.setLicenseSubmissionDate(new Date());
			
			dl_list.add(lla2);
		}
		
		BillingDataModel bdm = new BillingDataModel();
		
		bdm.setApp_num(app_num);
		bdm.setCard_num("************"+request.getParameter("cardnumber").substring(12));
		bdm.setName_on_card(request.getParameter("cardname"));
		bdm.setTotal_amount((Integer)session.getAttribute("totalAmount"));
		
		
		//Create Application
		LLApplicationDao lld = new LLApplicationDao();
		try {
			lld.createDLApplication(dl_list, drivingTestDate);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		request.getSession().setAttribute("dl_list", dl_list);
		
		//save billing data
		BillingDao bd = new BillingDao();
		try {
			bd.createBill(bdm);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		request.getSession().setAttribute("bdm", bdm);
		
		//build html message
		String htmlmessage = "<p>Hello " + ((AadharInfoModel)(request.getSession().getAttribute("aam"))).getFull_name()+",</p>";
		htmlmessage += "We are pleased to inform you that your Drivers License Application with Application Number " +bdm.getApp_num() +" has been created successfully.";
	
		htmlmessage += "<h4>You have also booked the driving test slot date as : "+drivingTestDate+"<h4>";
		
		htmlmessage += "<h4>Please find the invoice details below<h4>";
	
		htmlmessage +="<table style='width:100%'> <tr> <th style='padding:5%;'>License Submission Date</th> <th style='padding:5%;'>Application Number</th> <th style='padding:5%;'>Payment</th> <th style='padding:5%;'>Billing Address</th></tr><tr><td style='padding:5%;'>"+(new Date()).getDate()+"-"+(new Date()).getMonth()+1+"-"+(new Date()).getYear()+1900+"</td><td style='padding:5%;'>"+bdm.getApp_num()+"</td><td style='padding:5%;'>Debit/Credit Card</td><td style='padding:5%;'>"+((AadharInfoModel)(request.getSession().getAttribute("aam"))).getAddress()+"</td></tr></table>";
		
		htmlmessage += "<hr />";
		
		htmlmessage +="<table style='width:100%'> <tr> <th style='padding:5%;'>License Type</th> <th style='padding:5%;'>Quantity</th> <th style='padding:5%;'>Amount</th></tr>";
		
		String[] licenses = new String[dl_list.size()];
		
		int i=0;
		for(LearnersLicenseApplication l : dl_list){
			licenses[i] = l.getLicenseType();
			i++;
		}
		
		for (String licenseType : licenses){
			htmlmessage += "<tr><td style='padding:5%;'>"+licenseType+"</td><td style='padding:5%;'>"+1+"</td><td style='padding:5%;'>"+150.00+"</td></tr>";
		}
		
		htmlmessage += "<tr><td style='padding:5%;'>Form Fee</td><td style='padding:5%;'>"+1+"</td><td style='padding:5%;'>"+50.00+"</td></tr></table>";
		htmlmessage += "<p></p>";
		htmlmessage +="<h5>Thank you for applying!</h5>";
		htmlmessage +="<h4>JK PTLS Team<h4>";
		htmlmessage +="<h6>Need help? Contact us jkptlsteam@gmail.com<h6>";
		
		//Success Email
		System.out.println("sending email to : "+((AadharInfoModel)(request.getSession().getAttribute("aam"))).getEmailAddress());
		Mailer.send(((AadharInfoModel)(request.getSession().getAttribute("aam"))).getEmailAddress(), "Application Successful", htmlmessage);
		
		//send Invoice page
		try {
			response.sendRedirect(request.getContextPath() + "/views/invoice.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private String generateAppNumForDL() {
		//Get last license number
		String appnumstring = null;
		
		try{
			String appnum = null;
		    LLApplicationDao lld = new LLApplicationDao();
		    appnum = lld.extractLastAppnumberForDL(); //ex A000000000001
		
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
		    	appnumstring = "M000000000001";
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
