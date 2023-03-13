<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<html>
<%@include file="../jspf/header.jsp" %>

<style>
    body {
        background: url(https://github.com/DmitryNext/cinema/blob/main/src/main/webapp/img/userEdit.jpg?raw=true) no-repeat center center fixed;
    }
    div {
        color: whitesmoke;
    }
</style>

<%@include file="../jspf/navbar.jsp" %>

<div>
    <c:if test="${requestScope.msgUpdate != null}">
        <div class="alert alert-warning"><fmt:message key="bad.update" bundle="${bundle}"/></div>
    </c:if>
</div>

<div class="container">
    <h2><fmt:message key="user.edit" bundle="${bundle}"/></h2>

    <form action="${pageContext.request.contextPath}/admin/user/edit" method="POST">
        <div>
            <label class="col-sm-2 col-form-label"><fmt:message key="change.name" bundle="${bundle}"/></label>
            <input type="text" name="updateName" value="${updatedUser.getUsername()}"/>
        </div>
        <label class="col-sm-2 col-form-label"><fmt:message key="change.role" bundle="${bundle}"/></label>
        <c:forEach items="${roles}" var="role">
            <tr>
                <div>
                    <label><input type="radio" name="updateRole" value="${role.name()}"/>${role.name()}</label>
                </div>
            </tr>
        </c:forEach>

        <button type="submit" class="btn btn-success"><fmt:message key="save" bundle="${bundle}"/></button>
    </form>
    <hr>
</div>

<%@include file="../jspf/footer.jsp" %>
</body>
</html>