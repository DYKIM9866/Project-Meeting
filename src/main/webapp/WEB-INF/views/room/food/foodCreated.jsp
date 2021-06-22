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
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>맛집 방 만들기</title>

    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="css/room.css" rel="stylesheet">
    
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    
    <script type="text/javascript" src="/meeting/resources/js/util.js"></script>
    <script type="text/javascript">
    
	function sendIt() {
		
		f = document.myForm;
		
		
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
		
		
		//가상경로
		f.action = "<%=cp%>/foodCreated_ok.action";
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
    
    	#file {
    	
    		display:none;
    		
    	}
    	
    	h4 {
    	
    		font-style: oblique;
			font-weight: bold;
			font-size: 30px;
			color: #4D71DB;
    	
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
                                <h1 class="h4 text-gray-900 mb-4"><b>맛집 방 만들기</b></h1>
                            </div>
                            <form action="" name="myForm" class="user" method="post" enctype="multipart/form-data">
                                <div class="form-group row">
                                    <div class="col-sm-6 mb-3 mb-sm-0">
                                        <h4>맛집</h4>
                                    </div>
                                    <div class="col-sm-6 mb-3 mb-sm-0">
                                    	<select name="totalP" id="totalP" class="custom-select custom-select-sm form-control form-control-sm">
                                        	<option value="">방 인원설정</option>
                                        	<option value="10">10</option>
                                        	<option value="30">30</option>
                                        	<option value="50">50</option>
                                        	<option value="100">100</option>
                                        	<option value="0">직접입력</option>
                                        </select>
                                        <input type="text" name="totalPDirect" id="totalPDirect" style="width:200px;" class="form-control form-control-user" value="0"/>
                                   </div>
                                </div>
                                <div class="form-group">
                                    <input name="title" type="text" class="form-control form-control-user" id="exampleInputEmail"
                                        placeholder="방 이름">
                                </div>
                                <div class="form-group">
                                    <input name="keyword" type="text" class="form-control form-control-user" id="exampleInputEmail"
                                        placeholder="#키워드">
                                </div>
                                <div>
                               		<textarea name="introduce" rows="12" cols="63" placeholder="방을 소개해주세요." class="form-control"></textarea>
                               	</div><br/>
                               	
                               	<div class="form-group">
                                    <input type="file" name="file" id="file" value="방 프로필 이미지"/>
                                    <button type="button" class="btn btn-primary btn-user btn-block" id="btn"
                                    style="font-size: 0.8rem; border-radius: 10rem; padding: 0.75rem 1rem;">방 프로필 이미지</button>
                                </div>
                               	
                               	<input type="hidden" name="subject" value="맛집">
                                <input type="hidden" name="manager" value="${sessionScope.userInfo.userId }">
                                <input type="hidden" name="member" value="${sessionScope.userInfo.userId }">
                                
                                <input type="button" class="btn btn-primary btn-user btn-block" value="생 성 하 기" onclick="sendIt();">
                                
                            </form>
                            <hr>
                            <!-- <div class="text-center">
                                <a class="small" href="forgot-password.html">Forgot Password?</a>
                            </div> -->
                            <div class="text-center">
                                <a class="small" href="<%=cp%>/foodList.action">맛집 방 목록으로 돌아가기</a>
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

</body>
</html>