<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>OTP Ptls</title>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/otp.css">
</head>
<body>
<%
	if(request.getSession().getAttribute("otpRequested") != null){
		if(!request.getSession().getAttribute("otpRequested").equals("1")){
			response.sendRedirect(request.getContextPath()+"/views/index.jsp");
		}
	}
	else{
		response.sendRedirect(request.getContextPath()+"/views/index.jsp");
	}

	request.getSession().setAttribute("otpRequested",null);
	String aadhar = "";
	if(request.getSession().getAttribute("aadharForOTP") != null){
		aadhar = new String((String) request.getSession().getAttribute("aadharForOTP"));
	}
%>
	<div class="container">
		<h1>An OTP has been sent to your Aadhar registered Email. Please Enter OTP</h1>
		<div class="userInput">
		<form id="otpForm" action="<%= request.getContextPath()%>/verifyotp" method="post" onsubmit="return validateOTPForm();">
			<input type="text" style="visibility: hidden;" name="aadhar" value="<%= aadhar%>">
			<input required type="text" id='ist' name="otp1" maxlength="1" onkeyup="clickEvent(this,'sec')">
			<input required type="text" id="sec" name="otp2" maxlength="1" onkeyup="clickEvent(this,'third')">
			<input required type="text" id="third" name="otp3" maxlength="1" onkeyup="clickEvent(this,'fourth')">
			<input required type="text" id="fourth" name="otp4" maxlength="1" onkeyup="clickEvent(this,'fifth')">
			<input required type="text" id="fifth" name="otp5" maxlength="1" onkeyup="clickEvent(this,'sixth')">
			<input required type="text" id="sixth" name="otp6" maxlength="1">
		</form>
		</div>
		<button onclick="submitOTPForm()">CONFIRM</button>
	</div>
	
<script type="text/javascript">

function clickEvent(first,last){
	if(first.value.length){
		document.getElementById(last).focus();
	}
}

function validateOTPForm(){
	
	//Validation
	
	submitOTPForm();
}

function submitOTPForm(){
	let form = document.getElementById("otpForm");
    form.submit();
}

</script>
</body>
</html>