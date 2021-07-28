<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<title>방 만들기</title>

<!-- Custom fonts for this template-->
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet"
	type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link href="css/room.css" rel="stylesheet">

<!-- jQuery -->
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>


<script type="text/javascript" src="/meeting/resources/js/util.js"></script>
<script type="text/javascript" src="/meeting/resources/js/created.js"></script>

<script type="text/javascript">
    
	function sendIt() {
		
		f = document.myForm;
		
		str = f.subject.value;
		//util에 있는 trim
		str = str.trim();
		if(!str) {
			alert("\n방 카테고리를 선택하세요!");
			f.subject.focus();
			return;
		}
		f.subject.value = str;
		
		
		str = f.totalP.value;
		//util에 있는 trim
		str = str.trim();
		if(!str) {
			alert("\n방 인원설정을 선택하세요!");
			f.totalP.focus();
			return;
		}
		if(str === 0) {
			f.totalPDirect.value;
		}else {
			f.totalP.value = str;
		}
		
		
		str = f.title.value;
		str = str.trim();
		if(!str) {
			alert("\n방 이름을 입력하세요!");
			f.title.focus();
			return;
		}
		f.title.value = str;
		
		
		str = f.keyword.value;
		//util에 있는 trim
		str = str.trim();
		if(!str) {
			alert("\n키워드를 입력하세요!");
			f.keyword.focus();
			return;
		}
		f.keyword.value = str;
		
		
		str = f.introduce.value;
		str = str.trim();
		if(!str) {
			alert("\n방 소개를 입력하세요!");
			f.introduce.focus();
			return;
		}
		f.introduce.value = str; 
		
		str = f.roomProfile.value;
		if(str === '파일선택'){
			alert("프로필 사진도 필수 요소 입니다.");	
			return;
		}
		
		
		//가상경로
		f.action = "<%=cp%>/created_ok.action";
		f.submit(); 
		
	}
	
	function changeValue(obj) {
		alert(obj.value);
	}
	
	//직접 입력
	$(function(){

      	//직접입력 인풋박스 기존에는 숨어있다가
		$("#totalPDirect").hide();
		
		$("#totalP").change(function() {
		
		    //직접입력을 누를 때 나타남
			if($("#totalP").val() == "0") {
					$("#totalPDirect").show();
				}  else {
					$("#totalPDirect").hide();
				}
		
		})
		
	});
    
    </script>

<style type="text/css">
.bg-article-travle-basic {
	background-image: url("https://source.unsplash.com/featured/?travel");
	background-size: cover;
}

.bg-article-study-basic {
	background-image: url("https://source.unsplash.com/featured/?study");
	background-size: cover;
}

.bg-article-restaurant-basic {
	background-image:
		url("https://source.unsplash.com/featured/?restaurant");
	background-size: cover;
}

.bg-article-exercise-basic {
	background-image:
		url("https://source.unsplash.com/featured/?weight-training");
	background-size: cover;
}

.bg-article-mountain-basic {
	background-image:
		url("https://source.unsplash.com/featured/?nature-mountain");
	background-size: cover;
}

.max_width {
	max-width: 700px;
	text-align: center;
}

.card-body {
	background-color: #F7F7F7;
}

.fileBox label {
	display: inline-block;
	padding: .5em .75em;
	color: #fff;
	font-size: inherit;
	line-height: normal;
	vertical-align: middle;
	background-color: #488FF7;
	cursor: pointer;
	border: 1px solid #ebebeb;
	border-bottom-color: #e2e2e2;
	border-radius: .25em;
}

.fileBox input[type="file"] {
	position: absolute;
	width: 1px;
	height: 1px;
	padding: 0;
	margin: -1px;
	overflow: hidden;
	clip: rect(0, 0, 0, 0);
	border: 0;
}

.fileBox .upload-name {
	display: inline-block;
	padding: .5em .75em; /* label의 패딩값과 일치 */
	font-size: inherit;
	font-family: inherit;
	line-height: normal;
	vertical-align: middle;
	background-color: #f5f5f5;
	border: 1px solid #ebebeb;
	border-bottom-color: #e2e2e2;
	border-radius: .25em;
	-webkit-appearance: none; /* 네이티브 외형 감추기 */
	-moz-appearance: none;
	appearance: none;
}

}
.filebox .upload-display { /* 이미지가 표시될 지역 */
	margin-bottom: 5px;
}

@media ( min-width : 768px) {
	.filebox .upload-display {
		display: inline-block;
		margin-right: 5px;
		margin-bottom: 0;
	}
}

.filebox .upload-thumb-wrap { /* 추가될 이미지를 감싸는 요소 */
	display: inline-block;
	width: 54px;
	padding: 2px;
	vertical-align: middle;
	border: 1px solid #ddd;
	border-radius: 5px;
	background-color: #fff;
}

.filebox .upload-display img { /* 추가될 이미지 */
	display: block;
	max-width: 100%;
	width: 100% \9;
	height: auto;
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
	<c:when test="${empty subject}"><body class="bg-article-mountain-basic"></c:when>
	<c:when test="${subject eq '운동' }"><body class="bg-article-exercise-basic"></c:when>
	<c:when test="${subject eq '여행' }"><body class="bg-article-travle-basic"></c:when>
	<c:when test="${subject eq '맛집' }"><body class="bg-article-restaurant-basic"></c:when>
	<c:when test="${subject eq '공부' }"><body class="bg-article-study-basic"></c:when>
</c:choose>
<body class="bg-gradient-primary">

	<div class="container max_width">

		<div class="card o-hidden border-0 shadow-lg my-5">
			<div class="card-body p-0">
				<!-- Nested Row within Card Body -->
				<div>
					<!-- <div class="col-lg-5 d-none d-lg-block bg-register-image"></div> -->
					<div>
						<div class="p-5">
							<div class="text-center">
								<h1 class=" text-gray-900 mb-4" style="font-weight: 700;">
									방 만들기
								</h1>
								<p style="color: #D1421D">⁕모든 요소가 필수 요소입니다.</p>
							</div>
					
							<form action="" name="myForm" class="user" method="post"
								enctype="multipart/form-data">
								<div class="form-group row">
									<div class="col-sm-6 mb-3 mb-sm-0">
										<!-- <input type="text" class="form-control form-control-user" id="exampleFirstName"
                                            placeholder="방 카테고리"> -->
                                        <c:choose>
                                        <c:when test="${empty subject}">
										<select name="subject"
											class="custom-select custom-select-sm form-control form-control-sm">
											<option value="">방 카테고리</option>
											<option value="여행">여행</option>
											<option value="맛집">맛집</option>
											<option value="운동">운동</option>
											<option value="공부">공부</option>
										</select>
										</c:when>
										<c:otherwise>
											<h4>${subject}</h4>
											<input type="hidden" name="subject" value="${subject}">
										</c:otherwise>
										</c:choose>
									</div>
									<div class="col-sm-6 mb-3 mb-sm-0">

										<select name="totalP" id="totalP"
											class="custom-select custom-select-sm form-control form-control-sm">
											<option value="">방 인원설정</option>
											<option value="10">10</option>
											<option value="30">30</option>
											<option value="50">50</option>
											<option value="100">100</option>
											<option value="0">직접입력</option>
										</select> <input type="text" name="totalPDirect" id="totalPDirect"
											style="width: 200px;" class="form-control form-control-user"
											value="0" />

									</div>
									<!-- <div class="col-sm-6">
                                        <input type="text" class="form-control form-control-user" id="exampleLastName"
                                            placeholder="방 이름">
                                    </div> -->
								</div>
								<div class="form-group">
									<input name="title" type="text" value="${list.title }"
										class="form-control form-control-user" id="exampleInputEmail"
										placeholder="방 이름" style="border-radius: 6px;">
								</div>
								<div class="form-group">
									<input name="keyword" type="text" value="${list.keyword }"
										class="form-control form-control-user" id="exampleInputEmail"
										placeholder="#키워드" style="border-radius: 6px;">
								</div>
								<div>
									<textarea name="introduce" rows="12" cols="63" style="resize: none;"
										placeholder=" 방을 소개해주세요! &#13;&#10; 정성들여 글을 쓰면 신청자가 더 많아져요!" 
										class="form-control"><c:if test="${!empty list }">${list.introduce }&#10;${list.content }&#10;${list.location }</c:if></textarea>
								</div>
								<div class="form-group fileBox preview-image">
						
									<input class="upload-name" name="roomProfile" value="파일선택" disabled="disabled">
									
									<label for="input_file" style="font-size: 1rem;margin-top: 8px;border: 0;">배경화면 선택</label>
									<input type="file" name="roomProfileFile" id="input_file" class="chooseFile"/>
								</div>
						
								<input type="hidden" name="manager" value="${sessionScope.userInfo.userId }"/>

								<input type="button" class="btn btn-primary btn-user btn-block"
									value="생 성 하 기" onclick="sendIt();" 
									style="border-radius: 6px; font-weight: 600; font-style:normal; font-size: 1rem;
									background-color: #488FF7; border: 0 " />

							</form>
							<hr>
							<!-- <div class="text-center">
                                <a class="small" href="forgot-password.html">Forgot Password?</a>
                            </div> -->
							<div class="text-center">
								<a class="backWholeList" href="<%=cp%>/list.action">전체 방 목록으로 돌아가기</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>


	<script type="text/javascript">
		$(document).ready(function(){
			var fileUpload = $('.fileBox .chooseFile');
			
			fileUpload.on('change',function(){
				if(window.FileReader){
					var fileName = $(this)[0].files[0].name;
				}else{
					var fileName = $(this).val().split('/').pop().split('\\').pop();	
				}
				
				$(this).siblings('.upload-name').val(fileName);
				
			});
		});
		
		var imgTarget = $('.preview-image .upload-hidden');
		
		imgTarget.on('change',function(){
			var parent = $(this).parent();
			parent.children('.upload-display').remove();
			
			if(window.FileReader){
				
				if(!$(this)[0].files[0].type.match(/image\//))
					return;

				

				var reader = new FileReader();
				reader.onload = function(e) {
					var src = e.target.result;
					parent.prepend('<div class="upload-display"><div class="upload-thumb-wrap"><img src="'+src+'" class="upload-thumb"></div></div>');
				}
				reader.readAsDataURL($(this)[0].files[0]);
			}

			else {
				$(this)[0].select();
				$(this)[0].blur();

				var imgSrc = document.selection.createRange().text;
				parent.prepend('<div class="upload-display"><div class="upload-thumb-wrap"><img class="upload-thumb"></div></div>');

			var img = $(this).siblings('.upload-display').find('img');
			img[0].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enable='true',sizingMethod='scale',src=\""
										+ imgSrc + "\")";

		}

	});
	</script>
	

	<!-- Bootstrap core JavaScript-->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript-->
	
	<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
	<script src="vendor/jquery-easing/jquery.easing.min.js"></script>
	
	
	<!-- Custom scripts for all pages-->
	<script src="js/sb-admin-2.min.js"></script>

</body>
</html>