<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<html>
<%@include file="../jspf/header.jsp" %>

<style>
    body {
        background: url(https://github.com/DmitryNext/cinema/blob/main/src/main/webapp/img/movies.jpg?raw=true) no-repeat center center fixed;
    }
    h1 {
        color: whitesmoke;
    }
</style>

<%@include file="../jspf/navbar.jsp" %>

<div class="container mt-5">
    <h1><fmt:message key="films" bundle="${bundle}"/></h1>
</div>

<form action="${pageContext.request.contextPath}/admin/deletemovie" method="post">
    <div class="container mt-5">
        <div class="card-deck">
            <c:forEach items="${movies.items}" var="movie">
                <div class="card">
                    <img src="${movie.getPoster()}" class="card-img-top" alt="...">
                    <div class="card-body">
                        <h5 class="card-title"><fmt:message key="movieName"
                                                            bundle="${bundle}"/>${movie.getName()}</h5>
                        <p class="card-text"><fmt:message key="movieGenre"
                                                          bundle="${bundle}"/>${movie.getGenre().name()}</p>
                    </div>
                    <div class="card-footer">
                        <small class="text-muted"><fmt:message key="movieDuration"
                                                               bundle="${bundle}"/>${movie.getDuration()}</small>
                    </div>
                    <div>
                        <c:if test="${sessionScope.authenticated == true && sessionScope.role == 'ADMIN'}">
                            <button type="submit" class="btn btn-danger">
                                <fmt:message key="deleteMovie" bundle="${bundle}"/></button>
                            <input type="hidden" name="movieId" value="${movie.getId()}"/>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</form>

<div class="container mt-5">
    <c:if test="${sessionScope.authenticated == true && sessionScope.role == 'ADMIN'}">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/addmovie" role="button">
                <fmt:message key="addMovie" bundle="${bundle}"/></a>
    </c:if>
</div>

<div style="color: whitesmoke">
    <div class="row col-sm-20">
        <div class="col-sm-2"><fmt:message key="current.page" bundle="${bundle}"/>
            ${currentPage}
        </div>
        <div class="col-sm-2">
        </div>
        <div class="col-sm-1">
            <c:if test="${!movies.firstPage}">
                <a style="color: whitesmoke"
                   href="${pageContext.request.contextPath}/movies?p=${movies.pageNo-1}&s=${movies.pageSize}&sortDirection=${sortDirection}">
                    <fmt:message key="previous" bundle="${bundle}"/>
                </a>
            </c:if>
        </div>
        <div class="col-sm-1">
            <c:if test="${!movies.lastPage}">
                <a style="color: whitesmoke"
                   href="${pageContext.request.contextPath}/movies?p=${movies.pageNo+1}&s=${movies.pageSize}&sortDirection=${sortDirection}">
                    <span><fmt:message key="next" bundle="${bundle}"/></span>
                </a>
            </c:if>
        </div>
    </div>
</div>

<%@include file="../jspf/footer.jsp" %>
</body>
</html>