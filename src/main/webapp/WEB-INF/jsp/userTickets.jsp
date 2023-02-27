<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<html>
<%@include file="../jspf/header.jsp" %>

<style>
    body {
        background: url(https://fs39.fex.net/preview/4383614943/0x0) no-repeat center center fixed;
    }
    h1 {
        color: whitesmoke;
    }
</style>

<%@include file="../jspf/navbar.jsp" %>

<div class="container mt-5">
    <h1><fmt:message key="userTickets" bundle="${bundle}"/></h1>
</div>

<div class="container mt-5">
    <table class="table table-striped table-dark">
        <thead>
        <tr>
            <th scope="col"><fmt:message key="ticketId" bundle="${bundle}"/></th>
            <th scope="col"><fmt:message key="price" bundle="${bundle}"/></th>
            <th scope="col"><fmt:message key="ticketMovieName" bundle="${bundle}"/></th>
            <th scope="col"><fmt:message key="ticketSessionTime" bundle="${bundle}"/></th>
            <th scope="col"><fmt:message key="ticketSeatId" bundle="${bundle}"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${userTickets.items}" var="ticket">
            <tr>
                <th scope="row">${ticket.getId()}</th>
                <td>${ticket.getTicketPrice()}</td>
                <td>${ticket.getSession().getMovie().getName()}</td>
                <td>${ticket.getSession().getDate()} ${ticket.getSession().getTime()}</td>
                <td>${ticket.getSeat().getId()}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div style="color: whitesmoke">
        <div class="row col-sm-20">
            <div class="col-sm-2"><fmt:message key="current.page" bundle="${bundle}"/>
                ${currentPage}
            </div>
            <div class="col-sm-2">
            </div>
            <div class="col-sm-1">
                <c:if test="${!userTickets.firstPage}">
                    <a style="color: whitesmoke"
                       href="${pageContext.request.contextPath}/usertickets?p=${userTickets.pageNo-1}&s=${userTickets.pageSize}&sortDirection=${sortDirection}">
                        <fmt:message key="previous" bundle="${bundle}"/>
                    </a>
                </c:if>
            </div>
            <div class="col-sm-1">
                <c:if test="${!userTickets.lastPage}">
                    <a style="color: whitesmoke"
                       href="${pageContext.request.contextPath}/usertickets?p=${userTickets.pageNo+1}&s=${userTickets.pageSize}&sortDirection=${sortDirection}">
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