<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>

    <title>PTLS - Login</title>
    <link href="css/ptls_1.css" rel="stylesheet" type="text/css">

</head>

<body>
<c:if test="${aadhar != null}">
	<%response.sendRedirect(request.getContextPath()+"/views/oneloginportal.jsp"); %>
</c:if>
    <div class="login-wrap">
	<div class="login-html">
		<input id="tab-1" type="radio" name="tab" class="sign-in" checked><label for="tab-1" class="tab">Sign In</label>
		<input id="tab-2" type="radio" name="tab" class="sign-up"><label for="tab-2" class="tab">Sign Up</label>
		<div class="login-form">
			<div class="sign-in-htm">
			<form id="signinForm" action="login" method="post" onsubmit="return validateMySignInForm();">
			
			<c:if test='${requestScope["isUserAuthenticated"] == "0"}'>
				<div class="group">
					<p id='errorMsg' style="color: red;" >Invalid Credentials!</p>
				</div>
			</c:if>
				<div class="group">
					<p id='errorMsgLogin' style="color: red; display: none;" ></p>
				</div>
				<div class="group">
					<label for="user" class="label">Aadhar</label>
					<input id="user" name="aadhar" type="text" class="input">
				</div>
				<div class="group">
					<label for="pass" class="label">Password</label>
					<input id="password" name="password" type="password" class="input" data-type="password">
				</div>
				<div class="group">
					<input id="loginButton" type="submit" class="button" value="Sign In">
				</div>
			</form>
				<div class="hr"></div>
				<div class="foot-lnk">
					<a href="#forgot">Forgot Password?</a>
				</div>
			</div>
			<div class="sign-up-htm">
			<form id="signupForm" action="register" method="post" onsubmit="return validateMyForm();">
				
				<c:if test='${requestScope["isUserAuthenticatedSignup"] == "0"}'>
				<div class="group">
					<p id='errorMsg2' style="color: red;" >Aadhar Does'nt exist!</p>
				</div>
			</c:if>
				
				<div class="group">
					<p id='errorMsgRegister' style="color: red; display: none;" ></p>
				</div>
				
				<div class="group">
					<label for="aadhar" class="label">Aadhar</label>
					<input id="aadhar" name="aadhar" type="number" class="input">
				</div>
				<div class="group">
					<label for="emailId" class="label">Email Address</label>
					<input id="emailId" name="emailId" type="text" class="input">
				</div>
				<div class="group">
					<label for="pass" class="label">Password</label>
					<input id="pass" name="password" type="password" class="input" data-type="password">
				</div>
				<div class="group">
					<label for="confirmPass" class="label">Repeat Password</label>
					<input id="confirmPass" name="confirmPassword" type="password" class="input" data-type="password">
				</div>
				<div class="group">
					<input id="submitButton" type="submit" class="button" value="Sign Up">
				</div>
				<div class="hr"></div>
				<div class="foot-lnk">
					<label for="tab-1">Already Member?</a>
				</div>
			</form>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	
	function validateMyForm(){
		
		document.getElementById("submitButton").disabled = true;
		
		var aadhar = document.getElementById("aadhar").value;
		var emailId = document.getElementById("emailId").value;
		var password = document.getElementById("pass").value;

		var errorMsg2 = document.getElementById("errorMsg2");
		errorMsg2.style.display = "none";
		var confirmPass = document.getElementById("confirmPass").value;
		
		if (isNaN(aadhar)){
			//alert(aadhar);
			document.getElementById("errorMsgRegister").innerHTML = "Aadhar number is invalid";
			document.getElementById("errorMsgRegister").style.display = "";
			document.getElementById("submitButton").disabled = false;
			return false;
		}
		//alert(aadhar);
		//alert(aadhar.length);
		
		if(aadhar.length != 12){
			document.getElementById("errorMsgRegister").innerHTML = "Aadhar number should be 12 digit long";
			document.getElementById("errorMsgRegister").style.display = "";
			document.getElementById("submitButton").disabled = false;
			return false;
		}
		
		var re = /\S+@\S+\.\S+/;
		if(!re.test(emailId)){
			document.getElementById("errorMsgRegister").innerHTML = "Email Id is invalid";
			document.getElementById("errorMsgRegister").style.display = "";
			document.getElementById("submitButton").disabled = false;
			return false;
		}
		
		if(password != confirmPass){
			document.getElementById("errorMsgRegister").innerHTML = "Confirm Password should match the Password field";
			document.getElementById("errorMsgRegister").style.display = "";
			document.getElementById("submitButton").disabled = false;
			return false;
		}
		
		//alert(password.length);
		if(password.length < 8){
			document.getElementById("errorMsgRegister").innerHTML = "Password should be atleast 8 character long";
			document.getElementById("errorMsgRegister").style.display = "";
			document.getElementById("submitButton").disabled = false;
			return false;
		}
		
		document.getElementById("errorMsgRegister").innerHTML = "";
		document.getElementById("errorMsgRegister").style.display = "none";
		
		const xhttp = new XMLHttpRequest();
		
		//This method is called when the AJAX request returns the result
		xhttp.onload = function() {
		    //alert(this.getResponseHeader("result"));
		    
		    var ajaxResult = this.getResponseHeader("result");
		    document.getElementById("submitButton").disabled = false;
		    if(ajaxResult == "1"){
		    	document.getElementById("errorMsgRegister").innerHTML = "Aadhar already exists!";
				document.getElementById("errorMsgRegister").style.display = "";
		    }
		    else{
		    	submitSignupForm();
		    }
		}
		
		
		xhttp.open("GET", "help?key=1&value="+aadhar, false);
		xhttp.send();
		
		return false;
	}
	
	function submitSignupForm() {
        let form = document.getElementById("signupForm");
        form.submit();
    }
	
	function submitSigninForm() {
        let form = document.getElementById("signinForm");
        form.submit();
    }
	
	
	function validateMySignInForm(){
		document.getElementById("loginButton").disabled = true;
		
		var aadhar = document.getElementById("user").value;
		var password = document.getElementById("password").value;
		
		if (isNaN(aadhar)){
			//alert(aadhar);
			document.getElementById("errorMsgLogin").innerHTML = "Aadhar number is invalid";
			document.getElementById("errorMsgLogin").style.display = "";
			document.getElementById("loginButton").disabled = false;
			return false;
		}
		
		if(aadhar.length != 12){
			document.getElementById("errorMsgLogin").innerHTML = "Aadhar number should be 12 digit long";
			document.getElementById("errorMsgLogin").style.display = "";
			document.getElementById("loginButton").disabled = false;
			return false;
		}
		
		if(aadhar.length < 1){
			document.getElementById("errorMsgLogin").innerHTML = "Aadhar cannot be empty";
			document.getElementById("errorMsgLogin").style.display = "";
			document.getElementById("loginButton").disabled = false;
			return false;
		}
		
		if(password.length < 1){
			document.getElementById("errorMsgLogin").innerHTML = "Password cannot be empty";
			document.getElementById("errorMsgLogin").style.display = "";
			document.getElementById("loginButton").disabled = false;
			return false;
		}
		
		submitSigninForm();
	}
	
/* function reload(){
	location.reload();
	return false;
} */
	
</script>
<% if (request.getAttribute("isUserAuthenticatedSignup") == "0"){%>
		<script type="text/javascript">
		document.getElementById("tab-2").click();
		</script>
	<%}%>
</body>

</html>