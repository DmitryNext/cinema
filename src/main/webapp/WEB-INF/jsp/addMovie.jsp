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
    label {
        color: whitesmoke;
    }
    h1 {
        color: whitesmoke;
    }
</style>

<%@include file="../jspf/navbar.jsp" %>

<div class="container mt-5">
    <h1><fmt:message key="addMovie" bundle="${bundle}"/></h1>

    <form action="${pageContext.request.contextPath}/admin/savemovie" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="enter.name" bundle="${bundle}"/></label>
            <div class="col-sm-6">
                <input type="text" name="name" class="form-control" placeholder="name"/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="choose.genre" bundle="${bundle}"/></label>
            <select name="genre">
                <c:forEach items="${genres}" var="genre">
                    <option value="${genre.name()}">${genre.name()}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="enter.duration" bundle="${bundle}"/></label>
            <div class="col-sm-6">
                <input type="text" name="duration" class="form-control" placeholder="duration"/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="enter.poster" bundle="${bundle}"/></label>
            <div class="col-sm-6">
                <input type="text" name="poster" class="form-control" placeholder="poster"/>
            </div>
        </div>

        <button type="submit" class="btn btn-success"><fmt:message key="save.movie" bundle="${bundle}"/></button>
    </form>
</div>

<%@include file="../jspf/footer.jsp" %>
</body>
</html>