<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<html>
<%@include file="../jspf/header.jsp" %>

<style>
    body {
        background: url(https://fs41.fex.net/preview/4384493423/0x0) no-repeat center center fixed;
    }
    div {
        color: whitesmoke;
    }
</style>

<%@include file="../jspf/navbar.jsp" %>

<div class="container mt-5">
    <h1><fmt:message key="boxOffice" bundle="${bundle}"/></h1>
</div>

<form action="${pageContext.request.contextPath}/user/saveticket" method="post">
    <div class="container mt-5">
        <div class="d-flex flex-row bd-highlight mb-3">
            <c:forEach items="${sessionSeats}" var="seat">
                <c:choose>
                    <c:when test="${seat.getSeatStatus().name() == 'FREE'}">
                        <div class="p-2 bd-highlight">
                            <img src="https://fs41.fex.net/preview/4384503310/0x0" class="rounded" alt="...">
                            <button type="submit" class="btn btn-success">
                                <fmt:message key="place" bundle="${bundle}"/> ${seat.getId()}</button>
                            <div class="card-body">
                                <input type="hidden" name="sessionId" value="${sessionId}"/>
                                <input type="hidden" name="id" value="${seat.id}"/>
                                <input type="hidden" name="placeNumber" value="${seat.placeNumber}"/>
                                <input type="hidden" name="rowNumber" value="${seat.rowNumber}"/>
                                <input type="hidden" name="p" value="1"/>
                                <input type="hidden" name="s" value="3"/>
                                <input type="hidden" name="sortDirection" value="asc">
                                </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="p-2 bd-highlight">
                            <img src="https://fs41.fex.net/preview/4384581451/0x0" class="rounded" alt="...">
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </div>
</form>

<%@include file="../jspf/footer.jsp" %>
</body>
</html>