<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>Insert title here</title>

<!-- Custom fonts for this template -->
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet"
	type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="css/room.css" rel="stylesheet">
<link href="css/requestMsgModal.css" rel="stylesheet">


<!-- Custom styles for this page -->
<link href="vendor/datatables/dataTables.bootstrap4.min.css"
	rel="stylesheet">

</head>

<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">
    <!-- SIDE BAR -->
    <jsp:include page="/WEB-INF/views/sidebar.jsp"></jsp:include>



        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">
            
            	<!-- TOP BAR -->
            	<jsp:include page="/WEB-INF/views/header.jsp"></jsp:include>
				<!-- Begin Page Content -->
				<div class="container-fluid">

				<!-- End of Topbar -->
					<!-- Page Heading -->			
					&nbsp;&nbsp;
											
						<div class="form-group row">
						<label>
						<a href="reco_update" class="btn btn-secondary btn-icon-split">
							<span class="icon text-white-50"> <i
								class="fas fa-arrow-right"></i>
						</span> <span class="text">자료 업데이트</span>
						</a>
						</label>
					
                <pre>                       </pre>      	
				<form action="reco" method="get">
					<div class="form-group row" style="float: none; margin:100 auto;">
						&nbsp;&nbsp;
							<select name="subject" class="form-control" style="width:100px;height:40px;">
										<option value="">전체</option>
										<option value="study">스터디</option>
										<option value="travel">여행</option>
										<option value="sports">운동</option>
							</select>
						&nbsp;&nbsp;
							<select name="searchKey" class="form-control" style="width:150px;height:40px;">
										<option value="title">장소명</option>
										<option value="keyword">장소유형</option>
										<option value="location">소재지주소</option>
							</select>
						&nbsp;&nbsp;
						<select name="sort" class="form-control" style="width:150px;height:40px;">
										<option value="distance">거리순</option>
										<option value="grade">평점순</option>
										<option value="papularity">인기순</option>
							</select>
						&nbsp;&nbsp;
						<input type="hidden" id="startLat" name="lat">
	                    <input type="hidden" id="startLon" name="lon"> 
						    <input name="searchValue"
						    id="search-input" type="search" id="form1" class="form-control" style="width:500px;height:40px;"/>
						  
						  <button id="search-button" type="submit" class="btn btn-primary" onclick="geolocation();">
						    <i class="fas fa-search"></i>
						  </button>
						  
						  
					</div>	
				</form>	
				
				</div>
					
				
				<br/>
					

					<div>${dto.subject }</div>

					<div
						class="d-sm-flex align-items-center justify-content-between mb-4">
						<h1 class="h3 mb-0 text-gray-800">
						<c:if test="${subject!=null }">
						${subject }
						</c:if>
						<c:if test="${subject=='' }">
						All
						</c:if>
						</h1>
					</div>

					<div class="row">
						<div class="col-lg-3">

							<!-- Basic Card Example -->
							<c:forEach var="dto" items="${lists }">
								
								<div class="card shadow mb-4" onclick="location.href='reco_showMap?recoNum=${dto.recoNum}'"
								style="cursor:pointer;">
									<div class="card-header py-3">
										<a>
											<h6 class="m-0 font-weight-bold text-primary">${dto.title }
											<span style="text-align: right;">⭐&nbsp;${dto.star }</span>
											</h6>
											
										</a>
									</div>
									<div class="card-body" align="center">
											유형 : ${dto.keyword}<br />
											${dto.introduce}<br />
											${dto.location}
										
									</div>
								</div>
						</div>
						<div class="col-lg-3">
							</c:forEach>

						</div>
					</div>
				</div>
			</div>
			<!-- Pagenation -->
					<div class="container">
			<div class="row">
				<div class="col" align="center">
					<ul class="pagination justify-content-center">
					<c:set var="startNum" value="${pageNum-(pageNum-1)%5 }"/>
					<c:set var="lastNum" value="${maxNum }"/>
					<c:if test="${startNum>1 }">
							<a class="page-link" href="reco?subject=${subject }&searchKey=${searchKey }&searchValue=${searchValue }&pageNum=${startNum-1 }">
								 이전
							</a>
						</c:if>	
							<c:forEach var="i" begin="0" end="4">
								<c:if test="${startNum+i<=maxNum }">
								<a class="page-link" href="reco?subject=${subject }&searchKey=${searchKey }&searchValue=${searchValue }&pageNum=${startNum+i }">
								 ${startNum+i }
								 </a>
								 </c:if>
							</c:forEach>
							
					<c:if test="${startNum+5<maxNum }">
							<a class="page-link" href="reco?subject=${subject }&searchKey=${searchKey }&searchValue=${searchValue }&pageNum=${startNum+5}">
								 다음
							</a>
						</c:if>	
					</ul>
				</div>
			</div>
		</div>

			<!-- Footer -->
            <footer class="sticky-footer bg-white">
                <div class="container my-auto">
                    <div class="copyright text-center my-auto">
                        <span>Copyright &copy; Your Website 2020</span>
                    </div>
                </div>
            </footer>
            <!-- End of Footer -->

        </div>
        <!-- End of Content Wrapper -->

    </div>
    <!-- End of Page Wrapper -->

    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fas fa-angle-up"></i>
    </a>

    <!-- Logout Modal-->
    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                    <a class="btn btn-primary" href="login.html">Logout</a>
                </div>
            </div>
        </div>
    </div>
	
    <!-- Bootstrap core JavaScript-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
	<script src="js/sb-admin-2.min.js"></script>
	
	<!-- geolocation으로 gps수신 -->
	<script src="js/geolocation.js"></script>
	
	<!-- loading.js -->
	<script src="js/loading.js"></script>
</body>
</html>