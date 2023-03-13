<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<html>
<%@include file="../jspf/header.jsp" %>

<head>
    <style>
        body {
            background: url(https://github.com/DmitryNext/cinema/blob/main/src/main/webapp/static/img/profile.jpg?raw=true) no-repeat center center fixed;
        }
        div {
            color: whitesmoke;
        }
    </style>
</head>

<%@include file="../jspf/navbar.jsp" %>

<div>
    <c:if test="${requestScope.msgBadUpdate != null}">
        <div class="alert alert-warning"><fmt:message key="bad.credentials" bundle="${bundle}"/></div>
    </c:if>
</div>

<div class="container mt-5">
    <h3><fmt:message key="edit.data" bundle="${bundle}"/></h3><br/>

    <form action="${pageContext.request.contextPath}/user/profile" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">
                <fmt:message key="new.password" bundle="${bundle}"/></label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control"
                       placeholder="Enter a new password or confirm an old one here"/>
                <small id="passwordHelp" class="form-text text-muted">
                    <fmt:message key="password.help" bundle="${bundle}"/></small>
            </div>
        </div>
        <button type="submit" class="btn btn-success"><fmt:message key="save" bundle="${bundle}"/></button>
    </form>
</div>

<%@include file="../jspf/footer.jsp" %>
</body>
</html>