<%@page import="java.util.Date"%>
<%@page import="com.ptls.daos.PersonDao"%>
<%@page import="com.ptls.models.LicenseHolderModel"%>
<%@page import="com.ptls.models.AadharInfoModel"%>
<%@page import="com.ptls.daos.AadharAccessDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name = "viewport" content = "width=device-width,initial-scale=1.0"> 
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/css/bootstrap.min.css"
	rel="stylesheet">

<style type="text/css">
body {
	background: rgb(99, 39, 120)
}

.form-control:focus {
	box-shadow: none;
	border-color: #BA68C8
}

.profile-button {
	background: rgb(99, 39, 120);
	box-shadow: none;
	border: none
}

.profile-button:hover {
	background: #682773
}

.profile-button:focus {
	background: #682773;
	box-shadow: none
}

.profile-button:active {
	background: #682773;
	box-shadow: none
}

.back:hover {
	color: #682773;
	cursor: pointer
}

.labels {
	font-size: 11px
}

.add-experience:hover {
	background: #BA68C8;
	color: #fff;
	cursor: pointer;
	border: solid 1px #BA68C8
}
</style>
<title>Apply for Main License</title>
</head>
<body>
	<c:if test="${aadhar == null}">
		<%
			response.sendRedirect(request.getContextPath() + "/");
		%>
	</c:if>
	<div class="container rounded bg-white mt-5 mb-5">
		<%
			AadharAccessDao aad = new AadharAccessDao();
			PersonDao pd = new PersonDao();
			AadharInfoModel aam = null;
			LicenseHolderModel lhm = null;
			String aadh = (String) request.getSession().getAttribute("aadhar");

			if (aadh != "null") {
				aam = aad.getUserDataUsingAadharNumber(aadh);
				request.getSession().setAttribute("aam", aam);
				
				lhm = pd.getLicenseHolder4Details(aadh);
			}
		%>
		
		<c:if test="${aadhar != null}">
			<div class="row">
				<div class="col-md-3 border-right">
					<div
						class="d-flex flex-column align-items-center text-center p-3 py-5">
						<img class="rounded-circle mt-5" width="150px"
							src="http://localhost:8080/PTLS/imgs/<%=aam.getPhoto_url()%>.jpg"><span
							class="font-weight-bold"><%=aam.getFull_name()%></span><span
							class="text-black-50"><%=aam.getEmailAddress()%></span><span>
						</span>
					</div>
				</div>
				<!--  -->
				<div class="col-md-5 border-right">
				<form action="<%=getServletContext().getContextPath()%>/applyML" method="post" >
					<div class="p-3 py-5">
						<div
							class="d-flex justify-content-between align-items-center mb-3">
							<h4 class="text-right">Application for Learner's License</h4>
						</div>
						<div class="row mt-2">
							<div class="col-md-12">
								<label class="labels">Full Name</label><input type="text"
									class="form-control" disabled="disabled"
									placeholder="Full Name" name="full_name" value="<%=aam.getFull_name()%>">
							</div>
						</div>
						<div class="row mt-3">
							<div class="col-md-12">
								<label class="labels">Gender</label><input type="text"
									class="form-control" disabled="disabled"
									value="<%=aam.getGender()%>" name="gender">
							</div>
							<div class="col-md-12">
								<label class="labels">DOB</label><input type="text"
									class="form-control" disabled="disabled"
									value=" <fmt:formatDate type = "date" value = "<%=aam.getDob()%>" />"  name="dob">
							</div>
							<div class="col-md-12">
								<label class="labels">Age</label><input type="text"
									class="form-control" disabled="disabled"
									value="<%=aam.getAge()%>">
							</div>
							<div class="col-md-12">
								<label class="labels">Address</label><input type="text"
									class="form-control" disabled="disabled"
									value="<%=aam.getAddress()%>" name="address">
							</div>
							<div class="col-md-12">
								<label class="labels">Postcode</label><input type="text"
									class="form-control" disabled="disabled"
									value="<%=aam.getPostal_code()%>" name="postal_code">
							</div>
							<div class="col-md-12">
								<label class="labels">District</label><input type="text"
									class="form-control" disabled="disabled"
									value="<%=aam.getDistrict()%>" name="district">
							</div>
							<div class="col-md-12">
								<label class="labels">State</label><input type="text"
									class="form-control" disabled="disabled"
									value="<%=aam.getState()%>" name="state">
							</div>
							<div class="col-md-12">
								<label class="labels">Country</label><input type="text"
									class="form-control" disabled="disabled"
									value="<%=aam.getCountry()%>" name="country">
							</div>
							<div class="col-md-12">
								<label class="labels">Father's Name</label><input type="text"
									class="form-control" disabled="disabled"
									value="<%=aam.getFathers_name()%>" name="fathers_name">
							</div>
							<div class="col-md-12">
								<label class="labels">Phone Number</label><input type="text"
									class="form-control" disabled="disabled"
									value="<%=aam.getPh_n0()%>" name="ph_n0">
							</div>
						</div>
						<div class="row mt-3">
							<div class="col-md-6">
								<label class="labels">Place of Birth</label><input
									id="placeOfBirthId" type="text" class="form-control" disabled="disabled"
									 value="<%=lhm.getPlaceOfBirth()%>" name="placeOfBirth">
							</div>
							<div class="col-md-6">
								<label class="labels">Blood Group</label>
								<input
									id="placeOfBirthId" type="text" class="form-control" disabled="disabled"
									 value="<%=lhm.getBloodGroup()%>" name="bloodGroup">
							</div>
						</div>
						<div class="row mt-3">
							<div class="col-md-6">
								<label class="labels">Emergency Mobile Number</label><input 
									id="emergencyMobileNumberId" type="text" class="form-control"
									 value="<%=lhm.getEmergencyMobNo()%>" disabled="disabled" name="emergencyMobNo">
							</div>
							<div class="col-md-6">
								<label class="labels">Identification Mark</label><input
									id="identificationMarkId" type="text" class="form-control"
									value="<%=lhm.getIdentificationMark()%>" disabled="disabled" name="identificationMark">
							</div>
						</div>
						<div class="row mt-3">
							<div class="col-md-12">
								<label class="labels"><h4>Book Driving Test Slot</h4></label><input 
									id="drivingTestDate" type="date" class="form-control"
									required name="drivingTestDate" 
									min="<%= (new Date()).getYear()+1900%>-0<%= (new Date()).getMonth()+1%>-<%= (new Date()).getDate()%>"
									max="<%= (new Date()).getYear()+1900%>-0<%= (new Date()).getMonth()+3%>-<%= (new Date()).getDate()%>">
							</div>
						</div>
						
						
						<!-- <div class="row mt-3">
							<div class="col-md-12">
								<h5>Type of License/'s</h5>
							</div>
						</div>
						
						<div class="row mt-3">
							<label class="labels">Select the licenses you want to apply for</label>
								<select required
									id="typeOfLicId" class="form-control" name="typeOfLic" multiple> 
									<option value="MC 50cc">MC 50cc</option>
									<option value="LMV-NT">LMV-NT</option>
									<option value="FVG">FVG</option>
									<option value="MC EX50CC">MC EX50CC</option>
									<option value="MCWG">MCWG</option>
									<option value="HGMV">HGMV</option>
									<option value="HPMV">HPMV</option>
								</select>
						</div> -->
						
						<div class="mt-5 text-center">
							<input id="nextButtonId" class="btn btn-primary profile-button"
								type="submit" value="Next" />
						</div>
					</div>
				</form>
				</div>
				<div class="col-md-4">
					<div class="p-3 py-5">
						<div
							class="d-flex justify-content-between align-items-center experience">
							<span>RTO Information</span><span
								class="border px-3 p-1 add-experience"><i
								class="fa fa-plus"></i>&nbsp;RTO</span>
						</div>
						<br>
						<div class="col-md-12">
							<label class="labels">State</label><input disabled="disabled"
								type="text" class="form-control" placeholder="State"
								value="Jammu & Kashmir">
						</div>
						<br>
						<div class="col-md-12">
							<label class="labels">RTO Office</label><input
								disabled="disabled" type="text" class="form-control"
								placeholder="RTO Office" value="Srinagar">
						</div>
					</div>
				</div>
			</div>
		</c:if>
	</div>
	
<% 
	if(request.getMethod().equals("POST")){
		
	}
%>
	
</body>
<script type="text/javascript">
	
</script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

</html>