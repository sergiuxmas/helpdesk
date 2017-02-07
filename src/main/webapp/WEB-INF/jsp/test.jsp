<%-- 
    Document   : test
    Created on : May 25, 2015, 10:08:26 PM
    Author     : Admin
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>test!</h1>
        <table border="1">
	<th>ID</th>
	<th>Denumire</th>
	<c:forEach items="${text}" var="diviziune">
		<tr>
			<td>${diviziune.id}</td>
			<td>${diviziune.nume}</td>
		</tr>
	</c:forEach>
</table>
    </body>
</html>
