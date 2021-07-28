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
<title>ShowMap</title>

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
				<!-- Begin Page Content -->
				<div class="container-fluid">
					

					<!-- Page Heading -->
					
					<div
						class="d-sm-flex align-items-center justify-content-between mb-4">
						<h1 class="h3 mb-0 text-gray-800">${list.subject }</h1>
					</div>
					
					<!-- Basic Card Example -->
                            <div class="card shadow mb-4">
                            
                                <div class="card-header py-3">
                                
                                    <h6 class="m-0 font-weight-bold text-primary">${list.title }
                                    <span style="text-align: right;">⭐&nbsp;${list.star }</span>
                                    </h6>
                                    
                                </div>
                                
                                <div class="card-body row">
                                <div id="map" style="width:500px;height:500px;" class="col-sm-5"></div>
                       
						<div class="col-sm-7" align="center">
						유형 : ${list.keyword}<br/>
						${list.introduce}<br/>
						${list.content }<br/>
						${list.location }&nbsp;&nbsp;
						<span><a href="javascript:openWindowPop('https://map.kakao.com/link/to/${list.title },${list.lat },${list.lon}', '갈찾기');">
						<img alt="kakao_map" src="img/map.png"></a></span>
						<br/>
						<c:if test="${dto.right==1 }">
							<c:choose>
								<c:when test="${list.subject eq 'study'}">
									<button onclick="location.href = 'created.action?subject=공부&recoNum=${list.recoNum}'" type="button" class="btn btn-primary" style="width: 120px; height: 40px;">방만들기</button>
								</c:when>
								<c:when test="${list.subject eq 'travel'}">
									<button onclick="location.href = 'created.action?subject=여행&recoNum=${list.recoNum}'" type="button" class="btn btn-primary" style="width: 120px; height: 40px;">방만들기</button>
								</c:when>
								<c:when test="${list.subject eq 'sports'}">
									<button onclick="location.href = 'created.action?subject=운동&recoNum=${list.recoNum}'" type="button" class="btn btn-primary" style="width: 120px; height: 40px;">방만들기</button>
								</c:when>
							</c:choose>
						</c:if>
						<c:if test="${dto.right!=1 }">
							<button onclick="alert('이메일 인증을 해주세요!');location.href = 'myPage.action';" type="button" class="btn btn-primary" style="width: 120px; height: 40px;">방만들기</button>
						</c:if>
						<br/>
						<br/>
						<!--  -->
						<div class="card mb-2">
							<div class="card-header bg-light">
							        <i class="fa fa-comment fa"></i> REVIEW
							</div>
							<div class="card-body">
								<ul class="list-group list-group-flush">
								    <li class="list-group-item">
									<form action="reco_recoComment">
									<div class="form-inline mb-2">
										<label for="replyId"><i class="fa fa-user-circle-o fa-2x"></i></label>
										<input type="hidden" name="recoNum" value="${recoNum }">
										<input type="text" name="userId" value="${info.userId }" class="form-control ml-2" id="reviewId" readonly>
										&nbsp;&nbsp;
										⭐ : &nbsp;&nbsp;
										<select name="star" class="form-control" style="width:100px;height:40px;">
										<option value="0">0</option>
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
										</select>
									</div>
										<input type="text" name="content" class="form-control" id="exampleFormControlTextarea1" rows="3"  maxlength="100">
										<input type="submit" class="btn btn-dark mt-3">
								    </form>
								    </li>
								</ul>
							</div>
						</div>
						<!-- Basic Card Example -->
							
						
						</div>
						
                                </div>
                                
                            </div>
                            <!--  -->
                            <div class="row">
                            <c:forEach var="dto" items="${commentLists }">
                            <div class="col-lg-3">
                 <div class="card mb-2">
							<div class="card-header bg-light">
							        <i class="fa fa-comment fa"></i>
							        ${dto.userId }
							        
							
							        
							</div>
							
							<div class="card-body">
								<ul class="list-group list-group-flush">
								    <li class="list-group-item">
									
									<div class="form-inline mb-2">
										<label for="replyId"><i class="fa fa-user-circle-o fa-2x"></i></label>
											⭐ : ${dto.star }<br/>
											${dto.content }
									</div>
										
								    
								    </li>
								</ul>
							</div>
						</div>
						</div>
						</c:forEach>           
                       <!--  -->     		
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
	<!-- lat과 lon을 js로 넘기기 위해 set -->
	<script>
	var lat = '${list.lat}';
	var lon = '${list.lon}';
	</script>
	
    <!-- Bootstrap core JavaScript-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin-2.min.js"></script>

	<!-- showMap -->
	<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=955f20db57985bdfe58bf91db6e7d4aa"></script>
	<script src="js/showMap.js"></script>
	
	<!-- recoComment -->
	<script src="js/recoComment.js"></script>
	
	<!-- 카카오맵링크 -->
	<script src="js/kakao_map.js"></script>
	
</body>
</html>