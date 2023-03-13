<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l"
          crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">

    <title>Cinema "Drudge's dreams</title>

    <c:if test="${sessionScope.locale == null}">
        <fmt:setLocale value="en"/>
    </c:if>
    <c:if test="${sessionScope.locale != null}">
        <fmt:setLocale value="${sessionScope.locale}"/>
    </c:if>
    <fmt:setBundle basename="localization" var="bundle"/>
</head>

<body>
<style>
    body {
        background: url(https://github.com/DmitryNext/cinema/blob/main/src/main/webapp/static/img/movies.jpg?raw=true) no-repeat center center fixed;
    }
    div {
        color: whitesmoke;
    }
</style>

<%@include file="WEB-INF/jspf/navbar.jsp" %>

<div class="container mt-5">
    <c:if test="${sessionScope.authenticated == null}">
        <fmt:message key="greeting" bundle="${bundle}"/>, Guest!
    </c:if>

    <c:if test="${sessionScope.user != null}">
        <fmt:message key="greeting" bundle="${bundle}"/>, ${sessionScope.user.getUsername()}!
    </c:if>
</div>

<%@include file="WEB-INF/jspf/footer.jsp" %>

</body>
</html>