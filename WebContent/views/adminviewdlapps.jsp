<%@page import="com.ptls.models.LearnersLicenseApplication"%>
<%@page import="java.util.List"%>
<%@page import="com.ptls.daos.LLApplicationDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%
	
	LLApplicationDao llad = new LLApplicationDao();

	List<LearnersLicenseApplication> list = llad.getAllPendingDLApplications();
%>

<html lang="en">

    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Dashboard - PTLS Admin</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
        <link href="<%= request.getContextPath()%>/css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>
    </head>
    <body class="sb-nav-fixed">
    <%
		String dec = request.getParameter("dec");
		if(dec != null){
			if(dec.equals("A")){ %>
<script type="text/javascript">
	alert("The application has been Approved!");
</script>
				
<%
			}
			else {%>
<script type="text/javascript">
	alert("The application has been Rejected!");
</script>
<%		}}
	
%>
        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <!-- Navbar Brand-->
            <a class="navbar-brand ps-3" href="http://localhost:8080/PTLS/views/adminhome.jsp">JK-PTLS Admin</a>
            <!-- Sidebar Toggle-->
            <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
            <!-- Navbar Search-->
            <form class="d-none d-md-inline-block form-inline ms-auto me-0 me-md-3 my-2 my-md-0">
                <div class="input-group">
                    <input class="form-control" type="text" placeholder="Search for..." aria-label="Search for..." aria-describedby="btnNavbarSearch" />
                    <button class="btn btn-primary" id="btnNavbarSearch" type="button"><i class="fas fa-search"></i></button>
                </div>
            </form>
            <!-- Navbar-->
            <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#!">Settings</a></li>
                        <li><a class="dropdown-item" href="#!">Activity Log</a></li>
                        <li><hr class="dropdown-divider" /></li>
                        <li><a class="dropdown-item" href="<%=request.getContextPath()%>/login?param=logout">Logout</a></li>
                    </ul>
                </li>
            </ul>
        </nav>
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                            <div class="sb-sidenav-menu-heading">DL</div>
                            <a class="nav-link" href="http://localhost:8080/PTLS/views/adminhome.jsp">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Dashboard
                            </a>
                            
                        </div>
                    </div>
                    <div class="sb-sidenav-footer">
                        <div class="small">Logged in as:</div>
                        Admin
                    </div>
                </nav>
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Dashboard</h1>
                        
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-table me-1"></i>
                                DL Applications [Pending Approval]
                            </div>
                            <div class="card-body">
                                <table id="datatablesSimple">
                                    <thead>
                                        <tr>
                                            <th>Aadhar</th>
                                            <th>Application Number</th>
                                            <th>Status</th>
                                            <th>License Type</th>
                                            <!-- <th>Application Submission Date</th> -->
                                            <!-- <th>Driving Test Date</th> -->
                                            <th>Decision</th>
                                        </tr>
                                    </thead>
                                    <tfoot>
                                        <tr>
                                            <th>Aadhar</th>
                                            <th>Application Number</th>
                                            <th>Status</th>
                                            <th>License Type</th>
                                            <!-- <th>Application Submission Date</th>
                                            <th>Driving Test Date</th> -->
                                            <th>Decision</th>
                                        </tr>
                                    </tfoot>
                                    <tbody>
                                    <%for (LearnersLicenseApplication l : list){ %>
                                        <tr>
                                            <td>**** **** **** <%=l.getAadhar().substring(8) %> </td>
                                            <td><%=l.getAppNum() %></td>
                                            <td><%=l.getApplicationStatus() %></td>
                                            <td><%=l.getLicenseType() %></td>
                                            <!-- <td>2011/04/25</td>
                                            <td>$320,800</td> -->
                                            <td><a href="http://localhost:8080/PTLS/dldecision?appnum=<%=l.getAppNum()%>&lictype=<%=l.getLicenseType()%>&dec=A">Approve</a> OR <a href="http://localhost:8080/PTLS/dldecision?appnum=<%=l.getAppNum()%>&lictype=<%=l.getLicenseType()%>&dec=R">Reject</a></td>
                                        </tr> <%} %>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        
                    </div>
                </main>
                <footer class="py-4 bg-light mt-auto">
                    <div class="container-fluid px-4">
                        <div class="d-flex align-items-center justify-content-between small">
                            <div class="text-muted">Copyright &copy; PTLS 2022</div>
                            <div>
                                <a href="#">Privacy Policy</a>
                                &middot;
                                <a href="#">Terms &amp; Conditions</a>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="<%= request.getContextPath()%>/js/scripts.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script src="<%= request.getContextPath()%>/assets/demo/chart-area-demo.js"></script>
        <script src="<%= request.getContextPath()%>/assets/demo/chart-bar-demo.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
        <script src="<%= request.getContextPath()%>/js/datatables-simple-demo.js"></script>
    </body>
</html>
