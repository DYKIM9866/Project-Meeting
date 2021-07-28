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
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script src="js/header.js?ver=3"></script>
<link href="css/header.css" rel="stylesheet" type="text/css">
</head>
<body>
	
	<!-- Topbar -->
	<nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

	<!-- Sidebar Toggle (Topbar) -->
	
	<form class="form-inline">
		<button id="sidebarToggleTop"
			class="btn btn-link d-md-none rounded-circle mr-3">
			<i class="fa fa-bars"></i>
		</button>
	</form>


	<!-- Topbar Navbar -->
	<ul class="navbar-nav ml-auto">
	
<!-- Nav Item - Alerts -->
                        <li class="nav-item dropdown no-arrow mx-1">
                            <a class="nav-link dropdown-toggle" href="#" id="alertsDropdown" role="button"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <i class="fas fa-bell fa-fw"></i>
                                <!-- Counter - Alerts -->
                                <span class="badge badge-danger badge-counter">${noticeList.size() }</span>
                            </a>
                            <!-- Dropdown - Alerts -->
                            <div class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in"
                                aria-labelledby="alertsDropdown">
                                <h6 class="dropdown-header">
                                    Alerts Center
                                </h6>
                                <c:choose>
                                	<c:when test="${empty noticeList}">
                                		<a class="dropdown-item d-flex align-items-center" href="#">
	                                	
	                                    <div>
	                                        <span class="font-weight-bold">지난 7일간의 알림이 없습니다.</span>
	                                    </div>
	                                </a>
                                	</c:when>
                                	<c:otherwise>
		                                <c:forEach var="noticeDTO" items="${noticeList}">
			                                <a class="dropdown-item d-flex align-items-center" href="#">
			                                	
			                                    <div>
			                                        <div class="small text-gray-500">${noticeDTO.created }</div>
			                                        <span class="font-weight-bold">${noticeDTO.noticeMsg }</span>
			                                    </div>
			                                </a>
		                                
		                                </c:forEach>
	                                </c:otherwise>
                                </c:choose>
                            </div>
                        </li>	
	
		<!-- Nav Item - Messages -->
		<li class="nav-item dropdown no-arrow mx-1"><a
			class="nav-link dropdown-toggle" href="#" id="messagesDropdown"
			role="button" data-toggle="dropdown" aria-haspopup="true"
			aria-expanded="false"> <i class="fas fa-envelope fa-fw"></i> <!-- Counter - Messages -->
				<span class="badge badge-danger badge-counter">${msgList.size() }</span>
		</a> <!-- Dropdown - Messages -->
			<div
				class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in"
				aria-labelledby="messagesDropdown">
				<h6 class="dropdown-header">Message Center</h6>

				<c:if test="${msgList.size()==0 }">
					<a class="dropdown-item d-flex align-items-center"
						href="#">
						<div class="font-weight-bold">
							<div class="text-truncate">요청메세지가 없습니다.</div>
						</div>
					</a>
				</c:if>

				<c:forEach var="msgDTO" items="${msgList}">

					<c:choose>
						<c:when test="${msgDTO.status== 0 }">
							<a class="dropdown-item d-flex align-items-center msg-confirm"
								data-toggle="modal" href="#requestMsgModal"
								data-msgnum="${msgDTO.msgNum}" data-sender="${msgDTO.sender}"  data-recipient="${msgDTO.recipient}"
								data-roomnum = "${msgDTO.roomNum}"
								data-msg="${msgDTO.msg}" data-introduce="${msgDTO.introduce}">
								<div class="dropdown-list-image mr-3">
									<img class="rounded-circle" src="img/undraw_profile_1.svg"
										alt="...">
								</div>
								<div class="font-weight-bold">
									<div class="text-truncate">${msgDTO.msg}</div>
									<div class="small text-gray-500">${msgDTO.created}</div>
								</div>
							</a>

						</c:when>

					</c:choose>
				</c:forEach></li>

		<div class="topbar-divider d-none d-sm-block"></div>

		<!-- Nav Item - User Information -->
		<li class="nav-item dropdown no-arrow"><a
			class="nav-link dropdown-toggle" href="#" id="userDropdown"
			role="button" data-toggle="dropdown" aria-haspopup="true"
			aria-expanded="false"> <span
				class="mr-2 d-none d-lg-inline text-gray-600 small">[${sessionScope.userInfo.userName}]
					님 반갑습니다.</span> 
					<img class="img-profile rounded-circle" style="border: 1px solid black;"
					src="/meeting/image/${sessionScope.userInfo.ustoredFileName}"/>
		</a> 
		
		<!-- Dropdown - User Information -->
			<div
				class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
				aria-labelledby="userDropdown">
				<a class="dropdown-item" href="myPage.action"> <i
					class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i> MyPage
				</a>

				<div class="dropdown-divider"></div>
				<a class="dropdown-item" href="logout.action"> <i
					class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
					Logout
				</a>
			</div></li>

	</ul>

	</nav>
	<!-- End of Topbar -->

	<!-- request Modal-->
	<div class="modal fade" id="requestMsgModal" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">요청메세지</h5>
					<button class="close" type="button" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body">
					<input type="text" class="modalMsg" disabled="disabled" style="border: 0px; background-color: #fff"><br><br>
					<p style="margin: 0">소개글</p>
					<textarea class="modalIntroduce" rows="10" cols="73" disabled="disabled" style="resize: none; background-color: #fff"></textarea>
				</div>
				<div class="modal-footer">
						<a class="btn btn-primary" href="#" onclick="requestAccept();">수락</a>
						<a class="btn btn-danger" href="#" onclick="requestReject();">거절</a>
				</div>
			</div>
		</div>
	</div>
	
	<!-- Modal Message -->
	<script type="text/javascript">
		var sender = "";
		var recipient = "";
    	var msgNum = "";
    	var roomNum = "";
    	$(document).on("click",".msg-confirm",function(){
    		msgNum = $(this).data('msgnum');
    		sender = $(this).data("sender");
    		recipient = $(this).data("recipient");
    		roomNum = $(this).data("roomnum");
    		var msg = $(this).data('msg'); //지정해준 값 가져오기
    		var introduce = $(this).data('introduce');
    		
    		$(".modalMsg").val(msg);
    		$(".modalIntroduce").val(introduce);
    	});
    	
    	function requestAccept() {
    		
    		var sendData = "msgNum=" + msgNum + "&sender=" + sender +"&recipient=" + recipient + "&roomNum=" + roomNum;
    		$.ajax({
    			type:'POST',
    			url:"modalAccept.action",
    	        data: sendData,
    	        success:function(data){
    	        	if(data=='success'){
    	        		window.location.reload();
    	        	}else if(data=='fail'){
    	        		alert("인원초과!");
    	        	}
    	        	
    	        }
    		});
		}
    	function requestReject(){
    		
    		var sendData = "msgNum=" + msgNum + "&sender=" + sender +"&recipient=" + recipient + "&roomNum=" + roomNum;
    		
    		$.ajax({
    			url: "modalReject.action",
    			type:'POST',
    			data:sendData,
    			success:function(data){
    				window.location.reload();
    			}
    			
    		});
			
    	}
    	
    
    
    </script>


</body>
</html>