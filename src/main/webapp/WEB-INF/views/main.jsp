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
</head>
<body>
메인페이지
<a href="<%=cp%>/index.action">방 카테고리</a>
<a href="<%=cp%>/myPage.action">마이페이지</a>
<form action="logout.action" method="post">
<input type="submit" value="로그아웃">
</form>
</body>
</html>