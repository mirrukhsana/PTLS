<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>

    <title>PTLS - Forget Password</title>
    <link href="<%= request.getContextPath()%>/css/ptls_1.css" rel="stylesheet" type="text/css">

</head>

<body>

    <div class="login-wrap">
	<div class="login-html">
		<input id="tab-1" type="radio" name="tab" class="sign-in" checked><label for="tab-1" class="tab">Forgot Password</label>
		<div class="login-form">
			<div class="sign-in-htm" style="transform : rotateY(360deg)">
			<form id="forgetPasswordForm" action="<%= request.getContextPath()%>/forgetpassword" method="post" onsubmit="return validateMyForm();">
			
				<div class="group">
					<p id='errorMsgForget' style="color: red; display: none;" ></p>
				</div>
				<br />
				<div class="group">
					<label for="user" class="label">Aadhar</label>
					<input id="aadhar" name="aadhar" type="text" class="input">
				</div>
				<br /> <br />
				<div class="group">
					<input id="forgetButton" type="submit" class="button" value="Resolve">
				</div>
			</form>			
			</div>	
		</div>
	</div>
</div>

<script type="text/javascript">
	
	function validateMyForm(){
		
		document.getElementById("forgetButton").disabled = true;
		
		var aadhar = document.getElementById("aadhar").value;
		
		if (isNaN(aadhar)){
			//alert(aadhar);
			document.getElementById("errorMsgForget").innerHTML = "Aadhar number is invalid";
			document.getElementById("errorMsgForget").style.display = "";
			document.getElementById("forgetButton").disabled = false;
			return false;
		}
		
		if(aadhar.length != 12){
			document.getElementById("errorMsgForget").innerHTML = "Aadhar number should be 12 digit long";
			document.getElementById("errorMsgForget").style.display = "";
			document.getElementById("forgetButton").disabled = false;
			return false;
		}
		
		document.getElementById("errorMsgForget").innerHTML = "";
		document.getElementById("errorMsgForget").style.display = "none";
		
		const xhttp = new XMLHttpRequest();
		
		//This method is called when the AJAX request returns the result
		xhttp.onload = function() {
		    //alert(this.getResponseHeader("result"));
		    
		    var ajaxResult = this.getResponseHeader("result");
		    document.getElementById("forgetButton").disabled = false;
		    //alert(ajaxResult);
		    if(ajaxResult == "1"){
		    	submitForgetPasswordForm();
		    }
		    else{
		    	document.getElementById("errorMsgForget").innerHTML = "Aadhar Number doesn't exist!";
				document.getElementById("errorMsgForget").style.display = "";
		    }
		}
		
		
		xhttp.open("GET", "/PTLS/help?key=1&value="+aadhar, false);
		xhttp.send();
		
		return false;
	}
	
	function submitForgetPasswordForm() {
        let form = document.getElementById("forgetPasswordForm");
        form.submit();
    }
	
</script>

</body>

</html>