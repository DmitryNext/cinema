<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<html>
<%@include file="../jspf/header.jsp" %>

<body>
<style>
    body {
        background: url(https://github.com/DmitryNext/cinema/blob/main/src/main/webapp/static/img/userEdit.jpg?raw=true) no-repeat center center fixed;
    }
    div {
        color: whitesmoke;
    }
</style>

<%@include file="../jspf/navbar.jsp" %>

<div class="container my-2">
    <h1><fmt:message key="userList" bundle="${bundle}"/></h1>

    <table class="table table-striped table-dark">
        <thead>
        <tr>
            <th>
                <a href="${pageContext.request.contextPath}/admin/userlist?p=1&s=10&sortBy=username&sortDirection=${reverseSortDirection}">
                    <fmt:message key="username" bundle="${bundle}"/>
                </a>
            </th>
            <th>
                <a href="${pageContext.request.contextPath}/admin/userlist?p=1&s=10&sortBy=role_id&sortDirection=${reverseSortDirection}">
                    <fmt:message key="user.role" bundle="${bundle}"/>
                </a>
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${userList.items}" var="user">
            <tr>
                <td>${user.getUsername()}</td>
                <td>${user.getUserRole().name()}</td>
                <td><a href="${pageContext.request.contextPath}/admin/user?id=${user.getId()}" class="btn btn-dark">
                    <fmt:message key="edit" bundle="${bundle}"/>
                </a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div>
        <div class="row col-sm-20">
            <div class="col-sm-2"><fmt:message key="current.page" bundle="${bundle}"/>
                ${currentPage}
            </div>
            <div class="col-sm-1">
                <c:if test="${!userList.firstPage}">
                    <a href="${pageContext.request.contextPath}/admin/userlist?p=${userList.pageNo-1}&s=${userList.pageSize}&sortBy=${sortBy}&sortDirection=${sortDirection}">
                        <fmt:message key="previous" bundle="${bundle}"/>
                    </a>
                </c:if>
            </div>

            <div class="col-sm-1">
                <c:if test="${!userList.lastPage}">
                    <a href="${pageContext.request.contextPath}/admin/userlist?p=${userList.pageNo+1}&s=${userList.pageSize}&sortBy=${sortBy}&sortDirection=${sortDirection}">
                        <span><fmt:message key="next" bundle="${bundle}"/></span>
                    </a>
                </c:if>
            </div>
        </div>
    </div>
</div>

<%@include file="../jspf/footer.jsp" %>
</body>
</html>