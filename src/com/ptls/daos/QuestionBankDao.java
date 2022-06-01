package com.ptls.daos;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.ptls.constants.Constants;
import com.ptls.models.QuestionBankModel;
import com.ptls.utilities.DatabaseManager;

public class QuestionBankDao {

	public List<QuestionBankModel> getRandomQuestions() throws SQLException, ClassNotFoundException{
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		String randomQuestionIds = getRandomQuestionIds();
		
		System.out.println(randomQuestionIds);
		PreparedStatement stmt=con.prepareStatement("select * from questionbank where questionid in ("+randomQuestionIds+");");  
		
		ResultSet rs=stmt.executeQuery();  
		
		List<QuestionBankModel> questionList = new ArrayList<QuestionBankModel>();
		
		while(rs.next()){
			QuestionBankModel qbm = new QuestionBankModel();
			qbm.setQuestionId(rs.getInt(1));
			qbm.setQuestionDesc(rs.getString(6));
			qbm.setOptionA(rs.getString(2));
			qbm.setOptionB(rs.getString(3));
			qbm.setOptionC(rs.getString(4));
			qbm.setOptionD(rs.getString(5));
			
			questionList.add(qbm);
		
		}
		
		DatabaseManager.getInstance().closeConnection(con);
		
		return questionList;
	}
	
	public boolean isOnlineTestResultAlreadyInserted(String aadhar, String appNum) throws ClassNotFoundException, SQLException{
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("select * from onlinetestresult where aadhar = ? and application_number = ?");  
		stmt.setString(1, aadhar);
		stmt.setString(2, appNum);
		ResultSet rs=stmt.executeQuery();  
		
		boolean doesRecordExist = false;
		
		if(rs.next()){
			
			doesRecordExist = true;
		}
		else{
			doesRecordExist = false;
		}
		
		DatabaseManager.getInstance().closeConnection(con);
		
		return doesRecordExist;
	}
	
	public Map<String, String> getCorrectAnswers(List<Integer> questionIds) throws SQLException, ClassNotFoundException{
		
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		StringBuilder sb = new StringBuilder();
		for(Integer i : questionIds){
			sb.append("'"+i+"',");
		}
		 
		String param = sb.toString();
		
		System.out.println(param);
		
		PreparedStatement stmt=con.prepareStatement("select * from questionbank where questionid in ("+(param.substring(0, param.length()-1))+")");  
		//Array array = con.createArrayOf("VARCHAR", questionIds.toArray()); DIDN'T WORK
		//stmt.setArray(1, array);
		//System.out.println(param.substring(0, param.length()-1));
		//stmt.setString(1, param.substring(0, param.length()-1));
		
		Map<String, String> questionWithAnswerMap = new HashMap<String, String>();
		
		ResultSet rs=stmt.executeQuery();  
		
		while(rs.next()){
			questionWithAnswerMap.put(rs.getInt(1)+ "" , rs.getString(7));
		}
		//System.out.println(questionWithAnswerMap.size());
		DatabaseManager.getInstance().closeConnection(con);
		
		return questionWithAnswerMap;
	}
	
	public void insertOnlineTestResult(String aadhar, String appNum) throws ClassNotFoundException, SQLException{
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("INSERT INTO onlinetestresult (aadhar, application_number, marks_out_of) VALUES (?, ?, ?)");
		stmt.setString(1, aadhar);
		stmt.setString(2, appNum);
		stmt.setInt(3, Constants.NUMBER_OF_QUESTIONS_REQUIRED * 5);
		
		stmt.executeUpdate();
		
		DatabaseManager.getInstance().closeConnection(con);
	}
	
	public void updateOnlineTestResult(int marksObt, String finalResult, String aadhar, String appNum) throws ClassNotFoundException, SQLException{
		Connection con = DatabaseManager.getInstance().getDBConnection();
		
		PreparedStatement stmt=con.prepareStatement("update onlinetestresult set marks_ob = ?, final_result = ? where aadhar = ? and application_number = ?");
		stmt.setInt(1, marksObt);
		stmt.setString(2, finalResult);
		stmt.setString(3, aadhar);
		stmt.setString(4, appNum);
		
		stmt.executeUpdate();
		
		DatabaseManager.getInstance().closeConnection(con);
	}

	private String getRandomQuestionIds() {
		int totalQuestionsInDB = Constants.NUMBER_OF_QUESTIONS_IN_DB;
		int totalQuestionsRequired = Constants.NUMBER_OF_QUESTIONS_REQUIRED;
		
		Set<Integer> questionIds = new HashSet<>();
		
		while (questionIds.size() < totalQuestionsRequired){
			Integer i = (new Random()).nextInt(totalQuestionsInDB)+1;
			
			
			//If same number is generated again, SET will not accept the duplicate number
			questionIds.add(i);
		}
		
		StringBuilder sb = new StringBuilder();
		
		for (Integer j : questionIds){
			sb.append("'"+j+"',");
		}
		
		return sb.substring(0, sb.toString().length()-1); // will not return last comma attached
	}
	
}
