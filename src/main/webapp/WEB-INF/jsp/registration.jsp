<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<html>
<%@include file="../jspf/header.jsp" %>

<body>
<head>
    <style>
        body {
            background: url(https://fs41.fex.net/preview/4383614954/0x0) no-repeat center center fixed;
        }
        div {
            color: whitesmoke;
        }
    </style>
</head>

<%@include file="../jspf/navbar.jsp" %>

<div class="container mt-5">
    <div class="mb-1"><fmt:message key="add.user" bundle="${bundle}"/></div>
    <div>
        <c:if test="${requestScope.msg != null}">
            <div class="alert alert-warning"><fmt:message key="bad.credentials" bundle="${bundle}"/></div>
        </c:if>
    </div>
    <div>
        <c:if test="${requestScope.msgExists != null}">
            <div class="alert alert-warning"><fmt:message key="user.exists" bundle="${bundle}"/></div>
        </c:if>
    </div>
    <form action="${pageContext.request.contextPath}/registration" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="user.name" bundle="${bundle}"/></label>
            <div class="col-sm-6">
                <input type="text" name="username" class="form-control" placeholder="Username"/>
                <small id="usernameHelp" class="form-text text-muted">
                    <fmt:message key="username.help" bundle="${bundle}"/></small>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="password" bundle="${bundle}"/></label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control" placeholder="Password"/>
                <small id="passwordHelp" class="form-text text-muted">
                    <fmt:message key="password.help" bundle="${bundle}"/></small>
            </div>
        </div>
        <button type="submit" class="btn btn-secondary">
            <fmt:message key="registration" bundle="${bundle}"/></button>
    </form>
</div>

<%@include file="../jspf/footer.jsp" %>
</body>
</html>