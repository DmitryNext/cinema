<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>

<html>
<%@include file="../jspf/header.jsp" %>

<style>
    body {
        background: url(https://fs41.fex.net/preview/4383614958/0x0) no-repeat center center fixed;
    }
    div {
        color: whitesmoke;
    }
</style>

<%@include file="../jspf/navbar.jsp" %>

<div class="container mt-5">
    <div>
        <c:if test="${loginSuccess != null && loginSuccess == false}">
            <div class="alert alert-danger"><fmt:message key="error.login" bundle="${bundle}"/></div>
        </c:if>
    </div>
    <div>
        <c:if test="${requestScope.msgLogout != null}">
            <div class="alert alert-info"><fmt:message key="logout.message" bundle="${bundle}"/></div>
        </c:if>
    </div>
    <div>
        <c:if test="${sessionScope.msgSuccess != null}">
            <div class="alert alert-success"><fmt:message key="registration.success" bundle="${bundle}"/></div>
        </c:if>
    </div>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="user.name" bundle="${bundle}"/></label>
            <div class="col-sm-6">
                <input type="text" name="username" class="form-control" placeholder="Username"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="password" bundle="${bundle}"/></label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control" placeholder="Password"/>
            </div>
        </div>
        <button type="submit" class="btn btn-secondary"><fmt:message key="log.in" bundle="${bundle}"/></button>
        <br>
    </form>
    <br>
    <fmt:message key="registration.p1" bundle="${bundle}"/>
    <p><fmt:message key="registration.p2" bundle="${bundle}"/>
        <a href="${pageContext.request.contextPath}/registration">
            <fmt:message key="registration.p3" bundle="${bundle}"/></a>
        <fmt:message key="registration.p4" bundle="${bundle}"/></p>
</div>

<%@include file="../jspf/footer.jsp" %>
</body>
</html>