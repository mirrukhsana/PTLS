<%@page import="java.util.ArrayList"%>
<%@page import="com.ptls.models.LearnersLicenseApplication"%>
<%@page import="com.ptls.daos.LLApplicationDao"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
 <%@page import="java.util.Date"%>
 <%@page import="com.ptls.models.AadharInfoModel"%>
<%@page import="com.ptls.daos.AadharAccessDao"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name = "viewport" content = "width=device-width, initial scale=1.0" >
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">

<title>profile</title>

<style type="text/css">

/*  Profile Style  */

body {
    color: #797979;
    background: #f1f2f7;
    font-family: 'Open Sans', sans-serif;
    padding: 0px !important;
    margin: 0px !important;
    font-size: 13px;
    text-rendering: optimizeLegibility;
    -webkit-font-smoothing: antialiased;
    -moz-font-smoothing: antialiased;
}

.profile-nav, .profile-info{
    margin-top:30px;   
}

.profile-nav .user-heading {
    background: #fbc02d;
    color: #fff;
    border-radius: 4px 4px 0 0;
    -webkit-border-radius: 4px 4px 0 0;
    padding: 30px;
    text-align: center;
}

.profile-nav .user-heading.round a  {
    border-radius: 50%;
    -webkit-border-radius: 50%;
    border: 10px solid rgba(255,255,255,0.3);
    display: inline-block;
}

.profile-nav .user-heading a img {
    width: 112px;
    height: 112px;
    border-radius: 50%;
    -webkit-border-radius: 50%;
}

.profile-nav .user-heading h1 {
    font-size: 22px;
    font-weight: 300;
    margin-bottom: 5px;
}

.profile-nav .user-heading p {
    font-size: 12px;
}

.profile-nav ul {
    margin-top: 1px;
}

.profile-nav ul > li {
    border-bottom: 1px solid #ebeae6;
    margin-top: 0;
    line-height: 30px;
}

.profile-nav ul > li:last-child {
    border-bottom: none;
}

.profile-nav ul > li > a {
    border-radius: 0;
    -webkit-border-radius: 0;
    color: #89817f;
    border-left: 5px solid #fff;
}

.profile-nav ul > li > a:hover, .profile-nav ul > li > a:focus, .profile-nav ul li.active  a {
    background: #f8f7f5 !important;
    border-left: 5px solid #fbc02d;
    color: #89817f !important;
}

.profile-nav ul > li:last-child > a:last-child {
    border-radius: 0 0 4px 4px;
    -webkit-border-radius: 0 0 4px 4px;
}

.profile-nav ul > li > a > i{
    font-size: 16px;
    padding-right: 10px;
    color: #bcb3aa;
}

.r-activity {
    margin: 6px 0 0;
    font-size: 12px;
}


.p-text-area, .p-text-area:focus {
    border: none;
    font-weight: 300;
    box-shadow: none;
    color: #c3c3c3;
    font-size: 16px;
}

.profile-info .panel-footer {
    background-color:#f8f7f5 ;
    border-top: 1px solid #e7ebee;
}

.profile-info .panel-footer ul li a {
    color: #7a7a7a;
}

.bio-graph-heading {
    background: #fbc02d;
    color: #fff;
    text-align: center;
    font-style: italic;
    padding: 40px 110px;
    border-radius: 4px 4px 0 0;
    -webkit-border-radius: 4px 4px 0 0;
    font-size: 16px;
    font-weight: 300;
}

.bio-graph-info {
    color: #89817e;
}

.bio-graph-info h1 {
    font-size: 22px;
    font-weight: 300;
    margin: 0 0 20px;
}

.bio-row {
    width: 50%;
    float: left;
    margin-bottom: 10px;
    padding:0 15px;
}

.bio-row p span {
    width: 100px;
    display: inline-block;
}

.bio-chart, .bio-desk {
    float: left;
}

.bio-chart {
    width: 40%;
}

.bio-desk {
    width: 60%;
}

.bio-desk h4 {
    font-size: 15px;
    font-weight:400;
}

.bio-desk h4.terques {
    color: #4CC5CD;
}

.bio-desk h4.red {
    color: #e26b7f;
}

.bio-desk h4.green {
    color: #97be4b;
}

.bio-desk h4.purple {
    color: #caa3da;
}

.file-pos {
    margin: 6px 0 10px 0;
}

.profile-activity h5 {
    font-weight: 300;
    margin-top: 0;
    color: #c3c3c3;
}

.summary-head {
    background: #ee7272;
    color: #fff;
    text-align: center;
    border-bottom: 1px solid #ee7272;
}

.summary-head h4 {
    font-weight: 300;
    text-transform: uppercase;
    margin-bottom: 5px;
}

.summary-head p {
    color: rgba(255,255,255,0.6);
}

ul.summary-list {
    display: inline-block;
    padding-left:0 ;
    width: 100%;
    margin-bottom: 0;
}

ul.summary-list > li {
    display: inline-block;
    width: 19.5%;
    text-align: center;
}

ul.summary-list > li > a > i {
    display:block;
    font-size: 18px;
    padding-bottom: 5px;
}

ul.summary-list > li > a {
    padding: 10px 0;
    display: inline-block;
    color: #818181;
}

ul.summary-list > li  {
    border-right: 1px solid #eaeaea;
}

ul.summary-list > li:last-child  {
    border-right: none;
}

.activity {
    width: 100%;
    float: left;
    margin-bottom: 10px;
}

.activity.alt {
    width: 100%;
    float: right;
    margin-bottom: 10px;
}

.activity span {
    float: left;
}

.activity.alt span {
    float: right;
}
.activity span, .activity.alt span {
    width: 45px;
    height: 45px;
    line-height: 45px;
    border-radius: 50%;
    -webkit-border-radius: 50%;
    background: #eee;
    text-align: center;
    color: #fff;
    font-size: 16px;
}

.activity.terques span {
    background: #8dd7d6;
}

.activity.terques h4 {
    color: #8dd7d6;
}
.activity.purple span {
    background: #b984dc;
}

.activity.purple h4 {
    color: #b984dc;
}
.activity.blue span {
    background: #90b4e6;
}

.activity.blue h4 {
    color: #90b4e6;
}
.activity.green span {
    background: #aec785;
}

.activity.green h4 {
    color: #aec785;
}

.activity h4 {
    margin-top:0 ;
    font-size: 16px;
}

.activity p {
    margin-bottom: 0;
    font-size: 13px;
}

.activity .activity-desk i, .activity.alt .activity-desk i {
    float: left;
    font-size: 18px;
    margin-right: 10px;
    color: #bebebe;
}

.activity .activity-desk {
    margin-left: 70px;
    position: relative;
}

.activity.alt .activity-desk {
    margin-right: 70px;
    position: relative;
}

.activity.alt .activity-desk .panel {
    float: right;
    position: relative;
}

.activity-desk .panel {
    background: #F4F4F4 ;
    display: inline-block;
}


.activity .activity-desk .arrow {
    border-right: 8px solid #F4F4F4 !important;
}
.activity .activity-desk .arrow {
    border-bottom: 8px solid transparent;
    border-top: 8px solid transparent;
    display: block;
    height: 0;
    left: -7px;
    position: absolute;
    top: 13px;
    width: 0;
}

.activity-desk .arrow-alt {
    border-left: 8px solid #F4F4F4 !important;
}

.activity-desk .arrow-alt {
    border-bottom: 8px solid transparent;
    border-top: 8px solid transparent;
    display: block;
    height: 0;
    right: -7px;
    position: absolute;
    top: 13px;
    width: 0;
}

.activity-desk .album {
    display: inline-block;
    margin-top: 10px;
}

.activity-desk .album a{
    margin-right: 10px;
}

.activity-desk .album a:last-child{
    margin-right: 0px;
}

/*navigation style*/
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




@media(max-width: 700px) {
 

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
}

.table-striped>tbody>tr:nth-child(odd)>td, 
.table-striped>tbody>tr:nth-child(odd)>th {
   background-color: #FFD580; // Choose your own color here
 }

</style>

</head>
<body>

<c:if test="${aadhar == null}">
	<% response.sendRedirect(request.getContextPath()+"/");%>
</c:if>

        <%
			AadharAccessDao aad = new AadharAccessDao();
			AadharInfoModel aim = null;
			String aadh = (String) request.getSession().getAttribute("aadhar");

			if (aadh != "null") {
				aim = aad.getUserDataUsingAadharNumber(aadh);
			}
			
			List<LearnersLicenseApplication> lla = (new LLApplicationDao()).extractAllApplicationsOfLicenseHolder(aadh);
			
			//Logic for Combining similar application data
			
			List<LearnersLicenseApplication> lla2 = new ArrayList<LearnersLicenseApplication>();
					
			String llAppNum = "";
			String licenseTypeCombined = "";
			
			LearnersLicenseApplication ll2 = new LearnersLicenseApplication();
			
			
			int j = 0;
			for(LearnersLicenseApplication ll : lla){
				j++;
				if(ll2.getAppNum() == null){
					
					if(ll2.getAppNum() == null){
						
						ll2.setAppNum(ll.getAppNum());
						ll2.setLicenseSubmissionDate(ll.getLicenseSubmissionDate());
						ll2.setApplicationStatus(ll.getApplicationStatus());
					}
					else{
						
						ll2.setLicenseType(licenseTypeCombined);
						licenseTypeCombined = "";
						lla2.add(ll2);
						
						ll2 = new LearnersLicenseApplication();
						
						ll2.setAppNum(ll.getAppNum());
						ll2.setLicenseSubmissionDate(ll.getLicenseSubmissionDate());
						ll2.setApplicationStatus(ll.getApplicationStatus());
					}
					
				}
				else if (!ll2.getAppNum().equals(ll.getAppNum())){
					ll2.setLicenseType(licenseTypeCombined);
					licenseTypeCombined = "";
					lla2.add(ll2);
					
					ll2 = new LearnersLicenseApplication();
					ll2.setAppNum(ll.getAppNum());
					ll2.setLicenseSubmissionDate(ll.getLicenseSubmissionDate());
					ll2.setApplicationStatus(ll.getApplicationStatus());
				}
				
				if(llAppNum.equals(ll.getAppNum())){
					licenseTypeCombined = licenseTypeCombined +", " +ll.getLicenseType();
				}
				else{
					llAppNum = ll.getAppNum();
					licenseTypeCombined = ll.getLicenseType();
				}
				//out.print(j);
				if (lla.size() == j){
					
					ll2.setLicenseType(licenseTypeCombined);
					licenseTypeCombined = "";
					lla2.add(ll2);
				}
			}
			
			
		%>
		
		
<div class="container">
    <!-- Navigation -->
    <nav class="main-nav">
      <img src="http://localhost:8080/PTLS/imgs/OneLogin.png" alt="OneLogin" class="logo">

      <ul class="main-menu">
        <li><a href="<%= request.getContextPath()%>/views/ptlshome.jsp">Home</a></li>
        <li><a href="<%=request.getContextPath()%>/login?param=logout">Logout</a></li>
      </ul>
		</nav>
</div>

<div class="container bootstrap snippets bootdey">
<div class="row">
  <div class="profile-nav col-md-3">
      <div class="panel">
          <div class="user-heading round">
              <a href="#">
                  <img src="https://bootdey.com/img/Content/avatar/avatar3.png" alt="">
              </a>
              <h1><%out.print(aim.getFull_name());%></h1>
              <p><%out.print(aim.getEmailAddress());%></p>
          </div>
      </div>
  </div>
  <div class="profile-info col-md-9">
      
      <div class="panel">
          
          <div class="panel-body bio-graph-info">
              <h1>Profile</h1>
              <div class="row">
                  <div class="bio-row">
                      <p><span>Aadhar </span>: <%=aim.getAadhar()%></p>
                  </div>
                  <div class="bio-row">
                      <p><span>Address </span>: <%=aim.getAddress()%></p>
                  </div>
                  <div class="bio-row">
                      <p><span>Pincode </span>: <%=aim.getPostal_code()%></p>
                  </div>
                  <div class="bio-row">
                      <p><span>Contact No</span>: <%=aim.getPh_n0()%></p>
                  </div>
              </div>
          </div>
      </div>
      <hr />
      
      
      <h4 style="text-align: center;">Applications Submitted</h4>
      <br />
      <div>
          <div class="row">
              <div class="col-md-12">
                  <table class="table table-striped">
					<thead>
					<tr>
					<th>S.No.</th>
					<th>Application No</th>
					<th>Submission Date</th>
					<th>Applied For</th>
					<th>License Type</th>
					<th>Status</th>
					</tr>
					</thead>
					
					<tbody>
					<% int i=0; for (LearnersLicenseApplication l : lla2){ i++;%>
						
						<tr>
						<td><%=i%></td>
						<td><%=l.getAppNum()%></td>
						<td><%=l.getLicenseSubmissionDate()%></td>
						<td><% String appliedFor = ""; if(l.getAppNum().substring(0, 1).equals("A")){appliedFor="Learners License";}
						else if (l.getAppNum().substring(0, 1).equals("P")){
							appliedFor="Main License";
						}
						else if (l.getAppNum().substring(0, 1).equals("R")){
							appliedFor="Renewal License";
						}
						%> <%=appliedFor%></td>
						<td><%=l.getLicenseType()%></td>
						<td><%=l.getApplicationStatus()%></td>
						</tr>
					<%} %>	
					</tbody>
					</table>
              </div>
      <hr />
      
      <br /><br />
      <h4 style="text-align: center;">License Information</h4>
      <br />
              <div class="col-md-12">
                  <table class="table table-striped">
					<thead>
					<tr>
					<th>S.No.</th>
					<th>Application No</th>
					<th>Submission Date</th>
					<th>Applied For</th>
					<th>Status</th>
					</tr>
					</thead>
					
					<tbody>
						<tr>
						<td>Cell</td>
						<td>Cell</td>
						<td>Cell</td>
						<td>Cell</td>
						<td>Cell</td>
						</tr>
						<tr>
					</tbody>
					</table>
              </div>
          </div>
      </div>
  </div>
</div>
</div>

		
</body>




</html>