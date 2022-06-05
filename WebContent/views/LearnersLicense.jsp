<%@page import="com.ptls.daos.LicenseDao"%>
<%@page import="com.ptls.models.LicenseModel"%>
<%@page import="com.ptls.models.AadharInfoModel"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Learners License</title>
<link
	href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.no-icons.min.css"
	rel="stylesheet">
<link
	href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css"
	rel="stylesheet">
	
	
<style type="text/css">

* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

body {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  background: #fff;
  color: #000;
  font-size: 15px;
  line-height: 1.5;
}

a {
  color: #262626;
  text-decoration: none;
}

p, h3, h4 {
	margin: 5px;
}

ul {
  list-style: none;
}

.container {
  width: 90%;
  max-width: 1100px;
  margin: auto;
}

	body{
    font-family:Roboto;
}
.card{
  height: 310px;
  width: 500px;
  background-image:linear-gradient(rgba(19, 170, 82, 1),rgba(19, 170, 82, 0.8));
  border-radius: 20px;
  color:white;
  margin:20px;
  font-size: 16px;
  box-shadow: 2px 2px 20px #707070;
  margin-left: 30%;
  margin-top: 7%;
}
.top-block{
  display: inline-block;
  width:500px;
  height:155px;
}
.bottom-block{
  display: inline-block;
  width:500px;
  height:155px;
  margin-top: -30px;
}
.card-chip{
  float:left;
  margin: 20px;
}
.card-name{
  float:right;
  margin:20px;
  position:relative;
  font-size: 28px;
  font-weight:550;
}
.card-number{
  font-size: 28px;
  margin: -60px 0 0 0;
  text-align-last: center; 
}
.balance{
  float:left;
  margin: 20px;
  position:relative;
  top:38px;
}
.card-balance{
  font-weight:700;
  font-size: 30px;
  margin-top:10px;
}
.card-icon{
  float:right;
  margin: 0 20px 0 0;
  margin-top:75px;
}
.circle-left{
  margin-right: -15px;
  opacity:0.7;
}


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

.menu-btn {
  cursor: pointer;
  position: absolute;
  top: 20px;
  right: 30px;
  z-index: 2;
  display: none;
}

.btn {
  cursor: pointer;
  display: inline-block;
  border: 0;
  font-weight: bold;
  padding: 10px 20px;
  background: #262626;
  color: #fff;
  font-size: 15px;;
}

.btn:hover {
  opacity: 0.9;
}

.dark {
  color: #fff;
}

.dark .btn {
  background: #f4f4f4;
  color: #333;
}

/* Showcase */
.showcase {
  width: 100%;
  height: 150px;
  /* background: url('https://i.ibb.co/zGSDGCL/slide1.png') no-repeat center center/cover; */
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  justify-content: flex-end;
  padding-bottom: 50px;
  margin-bottom: 20px;
}

.showcase h2, .showcase p {
  margin-bottom: 10px;
}

.showcase .btn {
  margin-top: 20px;
}

/* Home cards */
.home-cards {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  grid-gap: 20px;
  margin-bottom: 40px;
}

.home-cards img {
  width: 100%;
  margin-bottom: 20px;
}

.home-cards h3 {
  margin-bottom: 5px;
}

.home-cards a {
  display: inline-block;
  padding-top: 10px;
  color: #0067b8;
  text-transform: uppercase;
  font-weight: bold;
}

.home-cards a:hover i {
  margin-left: 10px;
}



@media(max-width: 700px) {
  .menu-btn {
    display: block;
  }

  .menu-btn:hover {
    opacity: 0.5;
  }

  .main-nav ul.right-menu {
    margin-right: 50px;
  }

  .main-nav ul.main-menu {
    display: block;
    position: absolute;
    top:0;
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

  .home-cards {
    grid-template-columns: repeat(2, 1fr);
  }

  .xbox .content p {
    display: none;
  }

  .xbox .content h2 {
    margin-bottom: 20px;
  }

  .carbon .content {
    width: 85%;
  }

  .links .links-inner {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media(max-width: 500px) {
  .home-cards {
    grid-template-columns: 1fr;
  }

  .links .links-inner {
    grid-template-columns: 1fr;
  }

  .links .links-inner ul {
    margin-bottom: 20px;
  }
}	
</style>	
</head>

<body>
<c:if test="${aadhar == null}">
	<% response.sendRedirect(request.getContextPath()+"/");%>
</c:if>
<%
	AadharInfoModel aim = (AadharInfoModel) request.getSession().getAttribute("aim");
	//out.print(aim.getFull_name());
	String aadhar = (String) request.getSession().getAttribute("aadhar");
	
	LicenseDao ld = new LicenseDao();
	
	LicenseModel lm = ld.getUserLicenseUsingAadharNumber(aadhar);
	
	String lic_part = String.format("%05d", Integer.parseInt(lm.getLicID()));
	String year = (lm.getIssueDate().getYear()+1900) + "";

	if(aim == null){
		aim = new AadharInfoModel();
	}

	if(request.getAttribute("userPassedTheTest").equals("1")){
%>
<script type="text/javascript"> alert("You have passed the test! Below is your Learners License. Please take a print out of it for yourself."); </script>
		
<%}%>

	<div class="container">
	    <!-- Navigation -->
	    <nav class="main-nav">
	      <img src="http://localhost:8080/PTLS/imgs/OneLogin.png" alt="OneLogin" class="logo">
	
	      <ul class="main-menu">
	        <li><a href="<%= request.getContextPath()%>/views/profile.jsp">My Profile (<%= request.getSession().getAttribute("aadhar")%>)</a></li>
	        <li><a href="<%=request.getContextPath()%>/login?param=logout">Logout</a></li>
	      </ul>
	
	      <ul class="right-menu">
	        <li>
	          <a href="#">
	            <i class="fas fa-search"></i>
	          </a>
	        </li>
	        <li>
	          <a href="#">
	            <i class="fas fa-shopping-cart"></i>
	          </a>
	        </li>
	      </ul>
	    </nav>
	 </div>

	<div class='card'>
		<div class='top-block'>
			<div class='card-chip'>
				<i class="icon-credit-card icon-3x"></i>
			</div>
			<span class='card-name'> Government of J&K </span>
		</div>
		<div class='card-number'>
			<p>L-JK01 <%= year %> <%= lic_part %></p>
			<h6><%=aim.getFull_name() %></h6>
			<h6>Issue Date : <%= lm.getIssueDate() %> - Expiry Date : <%= lm.getExpiryDate() %></h6>
		</div>
		<div class='bottom-block'>
			<div class='balance'>
				<div>Signature Licensing Authority</div>
				<div class='card-balance'></div>
			</div>
			<div class='card-icon'>
				<span class='circle-left'> <i class="icon-circle icon-3x "></i>
				</span> <i class="icon-circle icon-3x"></i>
			</div>
		</div>
	</div>
</body>
</html>