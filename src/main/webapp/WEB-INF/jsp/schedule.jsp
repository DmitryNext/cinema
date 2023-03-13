<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<html>
<%@include file="../jspf/header.jsp" %>

<body>
<head>
    <style>
        body {
            background: url(https://github.com/DmitryNext/cinema/blob/main/src/main/webapp/static/img/schedule.jpg?raw=true) no-repeat center center fixed;
        }
        h1 {
            color: whitesmoke;
        }
    </style>
</head>

<%@include file="../jspf/navbar.jsp" %>

<div class="container mt-5">
    <h1><fmt:message key="sessions" bundle="${bundle}"/></h1>
</div>

<div class="container mt-5">
    <div class="dropdown">
        <button class="btn btn-secondary dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="false">
            <fmt:message key="sorting" bundle="${bundle}"/>
        </button>
        <div class="dropdown-menu">
            <a class="dropdown-item"
               href="${pageContext.request.contextPath}/schedule?p=1&s=4&sortDirection=${reverseSortDirection}&movieNameSort=true">
                <fmt:message key="sortName" bundle="${bundle}"/></a>
            <a class="dropdown-item"
               href="${pageContext.request.contextPath}/schedule?p=1&s=4&sortDirection=${reverseSortDirection}&sessionDateSort=true">
                <fmt:message key="sortDate" bundle="${bundle}"/></a>
            <a class="dropdown-item"
               href="${pageContext.request.contextPath}/schedule?p=1&s=4&sortDirection=${reverseSortDirection}&sessionTimeSort=true">
                <fmt:message key="sortTime" bundle="${bundle}"/></a>
            <a class="dropdown-item"
               href="${pageContext.request.contextPath}/schedule?p=1&s=4&sortDirection=${reverseSortDirection}&sessionFreeSeatsSort=true">
                <fmt:message key="sortFreeSeats" bundle="${bundle}"/></a>
        </div>
    </div>

    <div class="container mt-5">
        <div class="card-deck">
            <c:forEach items="${schedule.items}" var="session">
                <div class="card">
                    <img src="${session.getMovie().getPoster()}" class="card-img-top" alt="...">
                    <div class="card-body">
                        <h5 class="card-title"><fmt:message key="movieName"
                                                            bundle="${bundle}"/>${session.getMovie().getName()}</h5>
                        <p class="card-text"><fmt:message key="sessionTime"
                                                          bundle="${bundle}"/>${session.getDate()} ${session.getTime()}</p>
                    </div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item"><fmt:message key="freeSeats"
                                                                 bundle="${bundle}"/>${session.getFreeSeats()}</li>
                        <li class="list-group-item"><fmt:message key="ticketPrice"
                                                                 bundle="${bundle}"/>${session.getTicketPrice()}
                            <fmt:message key="uah" bundle="${bundle}"/></li>
                        <li class="list-group-item"><fmt:message key="movieDuration"
                                                                 bundle="${bundle}"/>${session.getMovie().getDuration()}</li>
                    </ul>
                    <div class="card-body">
                        <a href="${pageContext.request.contextPath}/user/buyticket?sessionId=${session.getId()}"
                           class="card-link"><fmt:message key="buy" bundle="${bundle}"/></a>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div style="color: whitesmoke">
            <div class="row col-sm-20">
                <div class="col-sm-2"><fmt:message key="current.page" bundle="${bundle}"/>
                    ${currentPage}
                </div>
                <div class="col-sm-2">
                </div>
                <div class="col-sm-1">
                    <c:if test="${!schedule.firstPage}">
                        <a style="color: whitesmoke" href="${pageContext.request.contextPath}/schedule?p=${schedule.pageNo-1}&s=${schedule.pageSize}&sortDirection=${sortDirection}">
                            <fmt:message key="previous" bundle="${bundle}"/>
                        </a>
                    </c:if>
                </div>
                <div class="col-sm-1">
                    <c:if test="${!schedule.lastPage}">
                        <a style="color: whitesmoke" href="${pageContext.request.contextPath}/schedule?p=${schedule.pageNo+1}&s=${schedule.pageSize}&sortDirection=${sortDirection}">
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