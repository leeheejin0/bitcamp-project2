<%@ page language="java" 
  contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<title>새 프로젝트</title>
</head>
<body>
<h1>새 프로젝트: 3단계(JSP)</h1>
<form action='add' method='post'>
팀원: <br>
<%
out.flush();
request.getRequestDispatcher("/jsp/project/member_list.jsp").include(request,response);
%>
<p><input type='submit' value='등록'>
<a href='list'>취소</a></p>
</form>
</body>
</html>