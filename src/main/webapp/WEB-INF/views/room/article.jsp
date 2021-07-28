<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>방 정보</title>

<!-- Custom fonts for this template-->
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet"
	type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link href="css/room.css" rel="stylesheet">

	<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>


<style type="text/css">
h2 {
	font: italic bold 3em/1em Georgia, serif;
	color: #4D71DB;
	display: inline;
}

h1 {
	font: italic bold 3em/1em Georgia, serif;
	background-color: #C0C0C0;
	color: #000000;
	display: inline;
}
.card-css{
	background-color: #C7D4D4;
}

.articleProfileContainer{
	text-align: center;
	vertical-align:middle;
	padding: 20px;
	height: 400px;
	
	
}
.articleProfileImgContainer{
	text-align: center;
	vertical-align:middle;
	margin-top: 20px;
}

.articleProfileImg{
	border: 0.05rem solid #263959;
	border-radius: 100%;
	width: 300px;
	height: 300px;
}
.articleProfileChange{
	text-align: right;
	
}
.articleProfileChangeA{
	color: #000;
	transition: .5s;
	font-weight: 600;
}
.articleProfileChangeA:hover{
	color: #000;
	text-decoration: none;
	font-weight: 600;
	text-shadow: 1px 1px 1px gray;
	transition: .5s;
}

.articleProfileCategoryContainer{
	text-align: left;
}

.articleProfileCategory{
	color: #000;
	margin: 10px;
	font-size: 1.0rem;
	font-weight: 600;
}
.articleTitle{
	font-size: 2.6rem;
	font-weight: 900;
	color: #000000;
}
.articleTag{
	font-size: 1rem;
	font-weight: 700;
	color:  #CBCDD4; 
}
.articleP{
	margin: 0;
	margin-bottom: 10px;
}
.articleFormGroup{
	margin-bottom: 5px; 
}

.bg-article-travle-basic{
	background-image: url("https://source.unsplash.com/featured/?travel");
	background-size: cover;
}
.bg-article-study-basic{
	background-image: url("https://source.unsplash.com/featured/?study");
	background-size: cover;
}
.bg-article-restaurant-basic{
	background-image: url("https://source.unsplash.com/featured/?restaurant");
	background-size: cover;
}
.bg-article-exercise-basic{
	background-image: url("https://source.unsplash.com/featured/?weight-training");
	background-size: cover;
}
.articleBottomContainer{
	padding: 25px;
}
.articleBottom{
	border-radius: 10px;
	background-color: #f4f5f9;
}
.btn-size{
	width: 30%;
}

.backWholeList{
	color: #000;
	font-size: 9px;
	font-size: 1rem;
}
.backWholeList:hover{
	color: #263959;
	text-decoration: none;
	font-size: 1rem;
}


</style>

</head>
<c:choose>
	<c:when test="${dto.subject eq '운동' }"><body class="bg-article-exercise-basic"></c:when>
	<c:when test="${dto.subject eq '여행' }"><body class="bg-article-travle-basic"></c:when>
	<c:when test="${dto.subject eq '맛집' }"><body class="bg-article-restaurant-basic"></c:when>
	<c:when test="${dto.subject eq '공부' }"><body class="bg-article-study-basic"></c:when>
</c:choose>


	<div class="container">

		<!-- Outer Row -->
		<div class="row justify-content-center">

			<div class="col-xl-10 col-lg-12 col-md-9">

				<div class="card-css card o-hidden border-0 shadow-lg my-5" >

					<!-- url('${pageContext.request.contextPath}/resources/upload/${dto.storedFileName }'); -->
					<div class="card-body p-0 articleBackground" align="left">
					
					
					
					<div class="articleProfileContainer" >
						<div class="articleProfileCategoryContainer">
							<p class="articleProfileCategory">카테고리 > ${dto.subject}</p>
						</div>
						<div class="articleProfileImgContainer">
							<img class="articleProfileImg" src='<spring:url value="/upload/${dto.storedFileName }"/>'>
						</div>
						<c:if test="${sessionScope.userInfo.userId eq dto.manager}">
							<form id="profileImgForm">
								<div class="articleProfileChange">
									<a class="articleProfileChangeA" href="#" onclick="roomProfileImgChange();">프로필 변경</a>
									<div style="display: none;"><input name="roomProfile" type="file"/></div>
								</div>
							</form>
						</c:if>
					</div>
					
					<hr style="margin: 0;">
						<div class="articleBottomContainer">
							<div class="p-5 articleBottom" >
								<div>
									<p class="articleTitle">${dto.title }</p>
									
								</div>
								<div class="articleFormGroup">
									<div class="articleTag">태그</div>
									<p class="articleP">${dto.keyword }</p>
								</div>
								
								
								<div class="articleFormGroup">
									<p class="articleP">방장 : ${dto.manager }</p>
								</div>
								
	
								<div class="articleFormGroup">
									<p class="articleP">${dto.introduce }</p>
								</div>
	
								<div class="articleFormGroup">
									<p class="articleP">창설일 : ${dto.created }&nbsp;&nbsp;&nbsp;&nbsp;참가자 :
										${dto.currentP } / ${dto.totalP } 명</p>
								</div>
	
								
	
								<div class="form-group">
									<!-- 마이룸 테이블의 member 가져오기 : dto.manager || dto.member -->
									<c:if test="${memberUser == 0}">
										<c:choose>
											<c:when test="${dto.currentP >= dto.totalP }">
												<p style="font-size: 1.6rem; ">인원이 가득차서 신청이 불가능해요!</p>
											</c:when>
										
										<c:otherwise>
											<a data-toggle="modal" href="#proposeRoom" 
												data-roomnum="${dto.roomNum }" data-title = "${dto.title}"
												class="btn btn-primary btn-user btn-propose btn-size"> 방 참여 신청하기 </a>
										</c:otherwise>
										</c:choose>
									</c:if>
									<!-- 마이룸 테이블의 member 가져오기 : dto.manager && dto.member -->
									<c:if test="${memberUser != 0}">
										<a href="<%=cp%>/tmain.action?roomNum=${dto.roomNum }" class="btn btn-primary btn-user btn-block btn-size">
										방 참가하기</a>
									</c:if>
								</div>
							</div>

							<hr>
							<div class="text-center">
								<a class="backWholeList" href="<%=cp%>/list.action?${params }">전체 방
									목록으로 돌아가기</a>
							</div>
							
						</div>
					</div>

				</div>

			</div>

		</div>

	</div>
	
	<!-- proposeRoom Modal -->
	<div class="modal fade" id="proposeRoom" role="dialog" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<input class="modal-title" id="proposeModalLabel" type="text"
						disabled="disabled" style="width: 600px; border: 0; background-color: #fff;">
					<button class="close" type="button" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body">
					<h6>간단하게 소개 적어주세요</h6>
					<sup>(<span id="nowByte">0</span>/1000bytes)</sup>
					<textarea class="proposeIntroduce" rows="10" cols="73" onkeyup="checkBytes(this);"></textarea>
					
				</div>
				<div class="modal-footer">
					<a class="btn btn-primary" onclick="proposeRoom();">전송</a>
				
				</div>
			</div>
		</div>
	</div>
	
	<!-- Modal 처리 -->
	<script type="text/javascript">
		
		var roomNum = "";
		var status = "";
		
		$(document).on("click",".btn-propose",function(){
			
			var roomTitle = $(this).data("title");
			roomNum = $(this).data("roomnum"); 
			
			//메세지가 있는 지 확인
			var para = "roomNum="+roomNum;
			$.ajax({
				url:"msgConfirm.action",
				type:'POST',
				data: para,
				success:function(data){
					if(data=="exist"){
						alert("이미 신청 하셨어요! 방장이 메세지를 확인 할 때 까지 기다려 주세요");
						window.location.href = "list.action";
					}else if(data=="noexist"){
						$("#proposeModalLabel").val('['+roomTitle+']의 매니저에게 요청보내기');
					}
				}
			});

		});
		
		function proposeRoom(){
			var introduce = $(".proposeIntroduce").val();
			var sendData = "roomNum="+roomNum+"&introduce=" + introduce;
			
			$.ajax({
				url: "requestMsg.action",
				type: "POST",
				data: sendData,
				success:function(data){
					alert("신청이 완료되었습니다.");
					window.location.reload();
				}
			});
		}
		
		function checkBytes(obj){
			const maxByte = 1000;
			const text_val = obj.value; //입력한 문자
		    const text_len = text_val.length; //입력한 문자수
		    
		    var totalByte=0;
		    for(let i=0; i<text_len; i++){
		    	const each_char = text_val.charAt(i);
		        const uni_char = escape(each_char) //유니코드 형식으로 변환
		        if(uni_char.length>4){
		        	// 한글 : 2Byte
		            totalByte += 2;
		        }else{
		        	// 영문,숫자,특수문자 : 1Byte
		            totalByte += 1;
		        }
		    }
		    
		    if(totalByte>maxByte){
		    	alert('최대 1000Byte까지만 입력가능합니다.');
		        	document.getElementById("nowByte").innerText = totalByte;
		            document.getElementById("nowByte").style.color = "red";
		        }else{
		        	document.getElementById("nowByte").innerText = totalByte;
		            document.getElementById("nowByte").style.color = "green";
		        }
		    }
		
		function roomProfileImgChange() {
			$("input[name='roomProfile']").click();
		}
		
		$("input[name='roomProfile']").change(function(e){
			var frm = document.getElementById('profileImgForm');
			frm.method = 'POST';
			frm.enctype = 'multipart/form-data';
	        var fileData = new FormData(frm);
			
	        fileData.append("roomNum", ${dto.roomNum});
	        
			$.ajax({
				type:"POST",
				url: "alterationRoomProfileImg.action",
				data: fileData,
				processData: false,
	            contentType: false,
	            success:function(){
	            	window.location.reload();
	            },
	            error : function(request,status,error) {  
		               alert("code:"+request.status+"\n"+"error:"+error);
		        }
			});
		})
		
	</script>
	

	<!-- Bootstrap core JavaScript-->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript-->
	<script src="vendor/jquery-easing/jquery.easing.min.js"></script>
	
	<!-- Custom scripts for all pages-->
	<script src="js/sb-admin-2.min.js"></script>

</body>

</html>