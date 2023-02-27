<%@ page isErrorPage="true" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Cinema "Drudge's dreams"</title>
</head>
<body>
<h2>This is an error page</h2><br/>
<h2>It looks like you don't have permission to access the requested page</h2><br/><br/>

but you could always <a class="nav-link" href="${pageContext.request.contextPath}/">
    visit</a> the home page ;)
</body>
</html>