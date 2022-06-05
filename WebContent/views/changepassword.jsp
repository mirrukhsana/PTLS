<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PTLS</title>
<link href="<%= request.getContextPath()%>/css/changepassword.css" rel="stylesheet" type="text/css">
</head>
<body>
<%
String aadhar = "";
if(request.getSession().getAttribute("aadharForOTP") != null){
	aadhar = new String((String) request.getSession().getAttribute("aadharForOTP"));
}
else{
	response.sendRedirect(request.getContextPath()+"/");
}
%>
<div id="container">
  
  <div id="header">
    <center><h1>Change Password</h1></center>
  </div> 
  <div id="form">
  <form id="changePasswordForm" action="<%= request.getContextPath()%>/changepassword" method="post" >
	<%-- <input type="text" style="visibility: hidden;" name="aadhar" value="<%= aadhar%>"/> --%>
    <input type="password" placeholder="New Password" id="passOne" name="password"/>
    <input type="password" placeholder="Confirm Password" id="passTwo" name="confirmPassword"/>
  </form>
  </div>
  <div id="footer" class="incorrect" onclick="submitForm();">
    <center><h1 id="footerText"></h1></center>
  </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	  var passOne = $("#passOne").val();
	  var passTwo = $("#passTwo").val();
	  
	  $("#footerText").html("Fields don't match");
	  
	  var checkAndChange = function()
	  {
	    if(passOne.length < 1){
	      if($("#footer").hasClass("correct")){
	        $("#footer").removeClass("correct").addClass("incorrect");
	        $("#footerText").html("They don't match");
	      }else{
	        $("#footerText").html("They don't match");
	      }
	    }
	    else if($("#footer").hasClass("incorrect"))
	    {
	      if(passOne == passTwo){
	        $("#footer").removeClass("incorrect").addClass("correct");
	        $("#footerText").html("Continue");
	      }
	    }
	    else
	    {
	      if(passOne != passTwo){
	        $("#footer").removeClass("correct").addClass("incorrect");
	        $("#footerText").html("They don't match");
	      } 
	    }   
	  }
	  
	  
	  
	  $("input").keyup(function(){
	    var newPassOne = $("#passOne").val();
	    var newPassTwo = $("#passTwo").val();
	    
	    passOne = newPassOne;
	    passTwo = newPassTwo;
	    
	    checkAndChange();
	  });
	});
	function submitForm(){
		let form = document.getElementById("changePasswordForm");
		
		var newPassOne = $("#passOne").val();
	    var newPassTwo = $("#passTwo").val();
	    
		if(newPassOne.length < 8){
			alert("Minimum Password length is 8");
			return;
		}
		if(newPassTwo.length < 8){
			alert("Minimum Password length is 8");
			return;
		}
		
		if(newPassOne == newPassTwo){
		    form.submit();
		}
		
	}
</script>


</body>
</html>