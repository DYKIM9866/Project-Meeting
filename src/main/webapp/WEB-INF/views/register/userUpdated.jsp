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

<title>Register</title>

<!-- Custom fonts for this template-->
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet"
	type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link href="css/sb-admin-2.min.css" rel="stylesheet">
<link href="css/register.css" rel="stylesheet">

<style type="text/css">
#file {
	display: none;
}
</style>


</head>

<body class="bg-gradient-primary">

	<div class="container">

		<div class="card o-hidden border-0 shadow-lg my-5">
			<div class="card-body p-0">
				<!-- Nested Row within Card Body -->
				<div class="row">
					<div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
					<div class="col-lg-7">
						<div class="p-5">
							<div class="text-center">
								<h1 class="h4 text-gray-900 mb-4">Create an Account!</h1>
							</div>
							<form name="myForm" class="user" method="post"
								enctype="multipart/form-data">

								<div class="form-group">
									<span class="errorMessage nameCon hide"></span>
								</div>

								<div class="form-group row">
									<div class="col-sm-8">
										<input type="text" name="name"
											class="form-control form-control-user btn-active"
											id="userName" placeholder="이름" value="${dto.name }">
									</div>

									<div class="col-sm-4 "
										style="vertical-align: middle; text-align: left;">

										<c:if test="${dto.gender == 1}">
											<input type="radio" id="man" value=1 name="gender"
												style="display: none;" checked="checked">
											<label for="man" class="genderLabel"><span>남</span></label>
										</c:if>

										<c:if test="${dto.gender == 2}">
											<input type="radio" id="woman" value=2 name="gender"
												style="display: none;" checked="checked">
											<label for="woman" class="genderLabel"><span>여</span></label>
										</c:if>

									</div>

								</div>

								<div class="form-group row">
									<div class="col-sm-8">${dto.userId }</div>
								</div>

								<!-- type="email" -->
								<div class="form-group">
									<input type="text" name="email"
										class="form-control form-control-user btn-active"
										id="InputEmail" placeholder="이메일" value="${dto.email }">
								</div>


								<div class="form-group">
									<span class="errorMessage passwordCon hide"></span>
								</div>

								<div class="form-group row">
									<div class="col-sm-6 mb-3 mb-sm-0">
										<input type="password" name="userPwd"
											class="form-control form-control-user btn-active"
											id="InputPassword" placeholder="비밀번호">
									</div>
									<div class="col-sm-6">
										<input type="password" name="userPwdCon"
											class="form-control form-control-user btn-active"
											id="RepeatPassword" placeholder="비밀번호 확인">
									</div>
								</div>

								<div class="form-group">
									<span class="errorMessage telCon hide"></span>
								</div>

								<div class="form-group">
									<input type="text" name="tel"
										class="form-control form-control-user btn-active" id="tel"
										placeholder="전화번호" value="${dto.tel }">
								</div>

								<div class="form-group">
									<input type="file" name="file" id="file" value="Profile Image" />
									<button type="button"
										class="btn btn-primary btn-user btn-block" id="btn"
										style="font-size: 0.8rem; border-radius: 10rem; padding: 0.75rem 1rem;">Profile
										Image</button>
								</div>

								<input type="hidden" name="userId" value="${dto.userId }">

								<input type="button" value="수 정 하 기"
									class="btn btn-primary registerBtn btn-user btn-block"
									onclick="sendIt();" /> <input type="button" value="수 정 취 소"
									class="btn btn-primary registerBtn btn-user btn-block"
									onclick="javascript:location.href='<%=cp%>/myPage.action';" />

								<hr>

							</form>
							<div align="center">
								<p>비밀번호를 입력하셔야 회원정보 수정이 가능합니다!!</p>
							</div>
						</div>
					</div>
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

	<!-- confirm -->
	<script src="js/register.js"></script>
	<script type="text/javascript">
	
		function sendIt() {
			f.action = "<%=cp%>/userUpdated_ok.action";
			f.submit();
		}
		
		//파일 버튼
		$(function() {
			
			$('#btn').click(function (e) {
				e.preventDefault();
				$('#file').click();
			});
			
		});
		
		function changeValue(obj) {
			alert(obj.value);
		}
	
	</script>

</body>

</html>