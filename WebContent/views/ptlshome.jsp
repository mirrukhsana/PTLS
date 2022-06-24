<%@page import="com.ptls.daos.LicenseDao"%>
<%@page import="com.ptls.daos.LLApplicationDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PTLS Home</title>

<style type="text/css">
	* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

body {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
   background-image: url("http://localhost:8080/PTLS/imgs/ptls-back.jpg") no-repeat center center/cover;
 
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
 <div class="menu-btn">
    <i class="fas fa-bars fa-2x"></i>
  </div>
  
<%	
	boolean isLLApplyVisible = (new LLApplicationDao()).applyLL_isVisible(((String)request.getSession().getAttribute("aadhar")));

	String aadhar = ((String)request.getSession().getAttribute("aadhar"));

	boolean canApplyForMainLicense = false;
	
	if(aadhar != null){
		canApplyForMainLicense = (new LicenseDao().canApplyForMainLicense(aadhar));
	}
	
String complaintParam = request.getParameter("complaintparam");

if(complaintParam != null){

%>
<script type="text/javascript"> alert("Your complaint has been sent to the PTLS Team! They will contact you very soon."); </script>

<%} %>
<c:if test="${aadhar == null}">
	<% response.sendRedirect(request.getContextPath()+"/");%>
</c:if>
  <div class="container">
    <!-- Navigation -->
    <nav class="main-nav">
      <img src="http://localhost:8080/PTLS/imgs/OneLogin.png" alt="OneLogin" class="logo">

      <ul class="main-menu">
        <li><a href="<%= request.getContextPath()%>/views/profile.jsp">My Profile (<%= request.getSession().getAttribute("aadhar")%>)</a></li>
        <li><a href="<%= request.getContextPath()%>/views/complaint.jsp">Complaint </a></li>
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

    <!-- Showcase -->
    <header class="showcase">
      <h2>Public Transport Licensing System</h2>
    </header>

    <!-- Home cards 1 -->
    <section class="home-cards">
    <%if(isLLApplyVisible) {%>
      <a href="<%= request.getContextPath()%>/views/applyforlearners.jsp">
      	<div style="border-style: outset;">
      		<img src="http://localhost:8080/PTLS/imgs/learnerslicensebkg.png" alt="" />
        	<h4>Apply for Learner's License</h4>
      	</div>
      </a>
     <%}%>
     <%if(canApplyForMainLicense) {%>
     <a href="<%= request.getContextPath()%>/views/applyfordrivinglicense.jsp">
	      <div style="border-style: outset;">
	      <img src="http://localhost:8080/PTLS/imgs/driverslicensebkg.png" alt="" />
	        <h3>Apply for Driving License</h3>
	      </div>
     </a>
     <%}%>
    </section>
</div>
 
</body>
</html>