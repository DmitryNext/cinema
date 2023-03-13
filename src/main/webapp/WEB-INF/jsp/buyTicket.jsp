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
            background: url(https://github.com/DmitryNext/cinema/blob/main/src/main/webapp/static/img/buyTicket.jpg?raw=true) no-repeat center center fixed;
        }
        div {
            color: whitesmoke;
        }
    </style>
</head>

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
                            <img src="https://github.com/DmitryNext/cinema/blob/main/src/main/webapp/static/img/seat_free.jpg?raw=true" class="rounded" alt="...">
                            <button type="submit" class="btn btn-success">
                                    ${seat.getPlaceNumber()}
                                <input type="hidden" name="sessionId" value="${sessionId}"/>
                                <input type="hidden" name="id" value="${seat.getId()}"/>
                                <input type="hidden" name="placeNumber" value="${seat.getPlaceNumber()}"/>
                                <input type="hidden" name="rowNumber" value="${seat.getRowNumber()}"/>
                            </button>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="p-2 bd-highlight">
                            <img src="https://github.com/DmitryNext/cinema/blob/main/src/main/webapp/static/img/seat_sold.jpg?raw=true" class="rounded" alt="...">
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