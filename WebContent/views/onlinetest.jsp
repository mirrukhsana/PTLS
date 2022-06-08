<%@page import="com.ptls.models.QuestionBankModel"%>
<%@page import="java.util.List"%>
<%@page import="com.ptls.daos.QuestionBankDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Online Test</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css"
	rel="stylesheet">

<style type="text/css">
/* Nav */
.main-nav {
	display: flex;
	align-items: center;
	justify-content: space-between;
	height: 60px;
	padding: 20px 0;
	font-size: 13px;
}

.main-nav .logo {
	width: 80px;
}

.main-nav ul {
	display: flex;
}

.main-nav ul li {
	padding: 0 10px;
}

.main-nav ul li a {
	padding-bottom: 2px;
}

.main-nav ul li a:hover {
	border-bottom: 2px solid #262626;
}

.main-nav ul.main-menu {
	flex: 1;
	margin-left: 20px;
}

.main-nav ul.main-menu {
	flex: 1;
	margin-left: 20px;
}

@media ( max-width : 700px) {
	.main-nav ul.right-menu {
		margin-right: 50px;
	}
	.main-nav ul.main-menu {
		display: block;
		position: absolute;
		top: 0;
		left: 0;
		background: #f2f2f2;
		width: 50%;
		height: 100%;
		border-right: #ccc 1px solid;
		opacity: 0.9;
		padding: 30px;
		transform: translateX(-500px);
		transition: transform 0.5s ease-in-out;
	}
	.main-nav ul.main-menu li {
		padding: 10px;
		border-bottom: #ccc solid 1px;
		font-size: 14px;
	}
	.main-nav ul.main-menu li:last-child {
		border-bottom: 0;
	}
	.main-nav ul.main-menu.show {
		transform: translateX(-20px);
	}
}

.table-striped>tbody>tr:nth-child(odd)>td, .table-striped>tbody>tr:nth-child(odd)>th
	{
	background-color: #FFD580;
	//
	Choose
	your
	own
	color
	here
}

body {
	background-color: rgb(99, 39, 120);
}
}

label.radio {
	cursor: pointer;
}

span.radio input {
	position: absolute;
	top: 0;
	left: 0;
	visibility: hidden;
	pointer-events: none;
}

span.radio {
	padding: 4px 0px;
	display: inline;
	color: green;
	width: 10px;
	text-align: center;
	border-radius: 3px;
	margin-top: 7px;
	text-transform: lowercase;
}

span.radio input:checked+span {
	border-color: black;
	background-color: green;
	color: #fff;
}

.ans {
	margin-left: 36px !important;
}

.btn:focus {
	outline: 0 !important;
	box-shadow: none !important;
}

.btn:active {
	outline: 0 !important;
	box-shadow: none !important;
}

.toto {

      width: 200px;
      height: 200px;
      border: 2px solid black;
      position: absolute;
      margin-top : 5px;
      margin-left: 5px;
    }
video {
      width: 200px;
      height: 200px;
      object-fit: cover;
      position: relative;
    }

</style>

</head>
<body>
	<c:if test="${aadhar == null}">
		<% response.sendRedirect(request.getContextPath()+"/");%>
	</c:if>

	<% 
		QuestionBankDao qbd = new QuestionBankDao();
		
		boolean examGiven = qbd.isOnlineTestResultAlreadyInserted(request.getParameter("aad"), request.getParameter("appnum"));
	
		if(examGiven){
			response.sendRedirect(request.getContextPath()+"/views/profile.jsp");
		}
		else{
			qbd.insertOnlineTestResult(request.getParameter("aad"), request.getParameter("appnum"));
		}
		
		List<QuestionBankModel> questionList = qbd.getRandomQuestions();
		
	%>

	<div class="toto">
	  <video id="vid"></video>
	</div>
	<div class="container">
		<!-- Navigation -->
		<nav class="main-nav"> <img
			src="http://localhost:8080/PTLS/imgs/OneLogin.png" alt="OneLogin"
			class="logo"> </nav>
	</div>

	<div class="container mt-5">
		<div class="d-flex justify-content-center row">
			<div class="col-md-10 col-lg-10">
				<div class="border">
					<div class="question bg-white p-3 border-bottom">
						<div
							class="d-flex flex-row justify-content-between align-items-center mcq">
							<h4>Learning License - Online Test</h4>
							<span>(8 Questions each having 8 marks. Pass percentage is 80%)</span>
						</div>
					</div>
					<form action="<%=getServletContext().getContextPath()%>/evaluate" method="post" >
					
					
					
					
					
					<input style="visibility: hidden;" name="appnum" value='<%=request.getParameter("appnum") %>' type="text" />
					<input style="visibility: hidden;" name="aad" value='<%=request.getParameter("aad") %>' type="text" />
					
					
					
					
					<% for (QuestionBankModel qbm: questionList){%>
					<div class="question bg-white p-3 border-bottom">
						<div class="d-flex flex-row align-items-center question-title">
							<h3 class="text-danger">Q.</h3>
							<h5 class="mt-1 ml-2"><%=qbm.getQuestionDesc()%></h5>
						</div>
						<div class="ans ml-2">
							<input type="radio" name="<%=qbm.getQuestionId()%>"
								value="a"> a.<span class="radio"> <%=qbm.getOptionA()%></span>
							
						</div>
						<div class="ans ml-2">
							 <input type="radio" name="<%=qbm.getQuestionId()%>"
								value="b"> b.<span class="radio"> <%=qbm.getOptionB()%></span>
							
						</div>
						<div class="ans ml-2">
							 <input type="radio"
								value="c" name="<%=qbm.getQuestionId()%>"> c.<span class="radio"> <%=qbm.getOptionC()%></span>
							
						</div>
						<div class="ans ml-2">
							 <input type="radio" name="<%=qbm.getQuestionId()%>"
								value="d">d.<span class="radio"> <%=qbm.getOptionD()%></span>
							
						</div>
					</div>
					<%}%>
					<div
						class="d-flex flex-row justify-content-between align-items-center p-3 bg-white">
						<input
							class="btn btn-primary border-success align-items-center btn-success"
							type="submit" value="Submit">
						
					</div>
					</form>
				</div>
			</div>
		</div>
	</div>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
	window.onload = function() {
		alert("Pressing the back button will make you fail the test. Please, don't hit back button!");
		grabImage();
	};
	
	 function grabImage() {
		   var video = document.getElementById("vid");
		   var mediaDevices = navigator.mediaDevices;
		   vid.muted = true;

		     // Accessing the user camera and video.
		     mediaDevices
		       .getUserMedia({
		         video: true,
		         audio: true,
		       })
		       .then((stream) => {

		         // Changing the source of video to current stream.
		         video.srcObject = stream;
		         video.addEventListener("loadedmetadata", () => {
		           video.play();
		         });
		       })
		       .catch(alert);
		 }
	
</script>

</body>
</html>