<%@page import="com.ptls.models.AadharInfoModel"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="../css/loader.css" rel="stylesheet" type="text/css">
<title>PTLS Payment</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style type="text/css">
	.row {
  display: -ms-flexbox; /* IE10 */
  display: flex;
  -ms-flex-wrap: wrap; /* IE10 */
  flex-wrap: wrap;
  margin: 0 -16px;
}

.col-25 {
  -ms-flex: 25%; /* IE10 */
  flex: 25%;
}

.col-50 {
  -ms-flex: 50%; /* IE10 */
  flex: 50%;
}

.col-75 {
  -ms-flex: 75%; /* IE10 */
  flex: 75%;
}

.col-25,
.col-50,
.col-75 {
  padding: 0 16px;
}

.container {
  background-color: #f2f2f2;
  padding: 20px 250px 15px 200px;
  border: 1px solid lightgrey;
  border-radius: 3px;
}

input[type=text] {
  width: 100%;
  margin-bottom: 20px;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 3px;
}
select{
  width: 100%;
  margin-bottom: 20px;
  padding: 12px;
  border: 1px solid #ccc;
   border-radius: 3px;
}

label {
  margin-bottom: 10px;
  display: block;
}

.icon-container {
  margin-bottom: 20px;
  padding: 7px 0;
  font-size: 24px;
}

.btn {
  background-color: #04AA6D;
  color: white;
  padding: 12px;
  margin: 10px 0;
  border: none;
  width: 100%;
  border-radius: 3px;
  cursor: pointer;
  font-size: 17px;
}

.btn:hover {
  background-color: #45a049;
}

span.price {
  float: right;
  color: grey;
}

/* Responsive layout - when the screen is less than 800px wide, make the two columns stack on top of each other instead of next to each other (and change the direction - make the "cart" column go on top) */
@media (max-width: 800px) {
  .row {
    flex-direction: column-reverse;
  }
  .col-25 {
    margin-bottom: 20px;
  }
}
</style>
</head>
<body>
<div class="row">
  <div class="col-75">
    <div class="container">
    <%AadharInfoModel aam = (AadharInfoModel) request.getSession().getAttribute("aam"); %>
      <form id="billingForm" action="<%=getServletContext().getContextPath()%>/handlepayment" method="post" onsubmit="return validateBilling();">

        <div class="row">
          <div class="col-50">
            <h3>Billing Address</h3>
            <label for="fname"><i class="fa fa-user" ></i> Full Name</label>
            <input type="text" id="fname" name="firstname"  disabled = "disabled"  value="<%= aam.getFull_name()%>">
            <label for="email"><i class="fa fa-envelope"></i> Email</label>
            <input type="text" id="email" name="email" disabled = "disabled" value="<%= aam.getEmailAddress()%>">
            <label for="adr"><i class="fa fa-address-card-o"></i> Address</label>
            <input type="text" id="adr" name="address" disabled = "disabled"  value="<%= aam.getAddress()%>">
            <label for="city"><i class="fa fa-institution"></i> City</label>
            <input type="text" id="city" name="city"  disabled = "disabled" value="<%= aam.getDistrict()%>">

            <div class="row">
              <div class="col-50">
                <label for="state">State</label>
                <input type="text" id="state" name="state" disabled = "disabled" value="<%= aam.getState()%>">
              </div>
              <div class="col-50">
                <label for="zip">Pin</label>
                <input type="text" id="Pin" name="Pin" disabled = "disabled" value="<%= aam.getPostal_code()%>">
              </div>
            </div>
          </div>

          <div class="col-50">
            <h3>Payment</h3>
            <label for="fname">Accepted Cards</label>
            <div class="icon-container">
              <i class="fa fa-cc-visa" style="color:navy;"></i>
              <i class="fa fa-cc-amex" style="color:blue;"></i>
              <i class="fa fa-cc-mastercard" style="color:red;"></i>
              <i class="fa fa-cc-discover" style="color:orange;"></i>
            </div>
            
            <label  for="cname">Name on Card</label>
            <input required type="text" id="cname" name="cardname" placeholder="Farzana Minaam Rukhsana">
            <label  for="ccnum">Credit card number</label>
            <input required type="text" id="ccnum" name="cardnumber" placeholder="1111222233334444">
            <label for="expmonth">Exp Month</label>
            <select  required id="expmonth" name="expmonth" >
            	<option value = ""></option>
            	<option value = "0">January</option>
            	<option value = "1">February</option>
            	<option value = "2">March</option>
            	<option value = "3">April</option>
            	<option value = "4">May</option>
            	<option value = "5">June</option>
            	<option value = "6">July</option>
            	<option value = "7">August</option>
            	<option value = "8">September</option>
            	<option value = "9">October</option>
            	<option value = "10">November</option>
            	<option value = "11">December</option>
            </select>

			
            <div class="row">
              <div class="col-50">
                <label for="expyear">Exp Year</label>
                <input required type="text" id="expyear" name="expyear" placeholder="2018">
              </div>
              <div class="col-50">
                <label for="cvv">CVV</label>
                <input required type="text" id="cvv" name="cvv" placeholder="352">
              </div>
            </div>
          </div>

        </div>
       
        <input type="submit" value="Continue to checkout" class="btn">
      </form>
    </div>
  </div>


  <div class="col-25">
    <div class="container">
      <h4>Types of Licenses
        <span class="price" style="color:black">
          <i class="fa fa-car"></i>
          <b></b>
        </span>
      </h4>
    <c:forEach items="${typeOfLicenses}" var="lic">
      <p>${lic} <span class="price">150</span></p>
	</c:forEach>
		<p><% if(request.getSession().getAttribute("dlForm") != null){out.print("DL Form Fee ");} else{out.print("Online Test Fee ");}%> <span class="price">50</span></p>
      <hr>
      <p>Total <span class="price" style="color:black"><b>${totalAmount}</b></span></p>
    </div>
  </div>
</div>

<div id="loaderDiv" class="">Loading&#8230;</div>
</body>


<script type="text/javascript">

	function submitForm() {
	    let form = document.getElementById("billingForm");
	    form.submit();
	}
	
	function stateChange() {
	    setTimeout(function () {
	        	document.getElementById("loaderDiv").className = "";
	    		submitForm();
	    }, 3000);
	}

	function validateBilling(){
		var cardnum = document.getElementById("ccnum").value;
		var Expyear = document.getElementById("expyear").value;
		var Cvv = document.getElementById("cvv").value;
		var expMonth = document.getElementById("expmonth").value;
		var loaderDiv = document.getElementById("loaderDiv");
		
		if (isNaN(cardnum) || cardnum.length != 16){
			alert("Invalid Card Number!");
			return false;
		}
		
		if (isNaN(Expyear) || Expyear.length != 4 || Expyear < 2022 ){
			alert("Invalid Year!");
			return false;
		}
		
		if (isNaN(Cvv) || Cvv.length !=3) {
			alert("Invalid Cvv!");
			return false;
		}
		
		const d = new Date();
		var month = d.getMonth();
		
		if(Expyear == 2022){
			if (expMonth < month){
				alert("Invalid Month!");
				return false;
			}
		}
		
		loaderDiv.className = "loading";
		stateChange();
		return false;
	}
</script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

</html>