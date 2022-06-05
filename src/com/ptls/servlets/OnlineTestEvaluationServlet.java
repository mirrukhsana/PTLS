package com.ptls.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ptls.constants.Constants;
import com.ptls.daos.LicenseDao;
import com.ptls.daos.QuestionBankDao;

/**
 * Servlet implementation class OnlineTestEvaluationServlet
 */
@WebServlet("/evaluate")
public class OnlineTestEvaluationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OnlineTestEvaluationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map<String, String[]> userAnswers = request.getParameterMap();
		
		List<Integer> questionIds = new ArrayList<Integer>();
		
		String aadhar = null;
		String app_num = null;
		
		for(Entry<String, String[]> e : userAnswers.entrySet()){
			if(!e.getKey().equals("aad") && !e.getKey().equals("appnum")){
				questionIds.add(Integer.parseInt(e.getKey()));
			}
			else{
				if(e.getKey().equals("aad")){
					aadhar = e.getValue()[0];
				}
				else{
					app_num = e.getValue()[0];
				}
			}
			//System.out.println(e.getKey());
			//System.out.println(((String[])e.getValue())[0]);
		}
		
		int marksObtained = 0;
		
		QuestionBankDao qbd = new QuestionBankDao();
		
		try {
			Map<String, String> questionsWithCorrectAnswers = qbd.getCorrectAnswers(questionIds);
			
			for(Entry<String, String> e : questionsWithCorrectAnswers.entrySet()){

				String answerGivenByUserForThisKey = userAnswers.get(e.getKey())[0];
				String correctAnswer = e.getValue();
				
				System.out.println("user answer : "+answerGivenByUserForThisKey+" , correct answer: "+correctAnswer);
				
				if(answerGivenByUserForThisKey.equals(correctAnswer)){
					marksObtained += 5;
				}
				
			}
			
			System.out.println("Total Marks obtained : "+marksObtained);
			
			String finalResult = null;
			
			if(marksObtained >= (Constants.NUMBER_OF_QUESTIONS_REQUIRED*4)){
				finalResult = Constants.PASS;
				
				Date issueDate = new Date();
				
				Date expiryDate = calculateExpiryForLearningLicense(issueDate);
				
				insertLearningLicense(aadhar, app_num, new Date(),expiryDate);
				
			}
			else
				finalResult = Constants.FAIL;
			
			qbd.updateOnlineTestResult(marksObtained, finalResult, aadhar, app_num);
			
			if(finalResult.equals(Constants.PASS)){
				request.setAttribute("userPassedTheTest", "1");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/LearnersLicense.jsp");
				
				dispatcher.forward(request, response);
			}
			else{
				request.setAttribute("userPassedTheTest", "0");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/profile.jsp");
				
				dispatcher.forward(request, response);
			}
			
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	private void insertLearningLicense(String aadhar, String app_num, Date issueDate, Date expiryDate) {
		
		LicenseDao ld = new LicenseDao();
		try {
			ld.addLicense(aadhar, app_num, issueDate, expiryDate);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}

	private Date calculateExpiryForLearningLicense(Date issueDate) {
		Calendar cal = Calendar.getInstance(); 
		cal.add(Calendar.MONTH, 6);
		Date expiryDate = cal.getTime();
		return expiryDate;
	}

}
