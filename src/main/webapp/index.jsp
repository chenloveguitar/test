<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>java动态编译</title>
</head>
<body>

	<form action="<%=path%>/compiler" method="post">
		类全限定名:<input type="text" name="className" value="">
		方法名:<input type="text" name="methodName" value="">
		源文件:<textarea rows="10" cols="10" name="source">
		</textarea>
		<input type="submit" value="执行">
	</form>
</body>
</html>