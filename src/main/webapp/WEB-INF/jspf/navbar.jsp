<!-- getbootstrap's navbar-->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">Cinema "Drudge's dreams"</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <!-- menu button navbar-->
        <span class="navbar-toggler-icon"></span>
    </button>

    <!-- menu description navbar-->
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.request.contextPath}/movies?p=1&s=5&sortDirection=${reverseSortDirection}">
                    <fmt:message key="movies" bundle="${bundle}"/></a>
            </li>
            <c:if test="${sessionScope.authenticated == true && sessionScope.role == 'ADMIN'}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/addmovie">
                        <fmt:message key="newMovie" bundle="${bundle}"/>
                    </a>
                </li>
            </c:if>
            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.request.contextPath}/schedule?p=1&s=4&sortDirection=${reverseSortDirection}&sortBy=id">
                    <fmt:message key="schedule" bundle="${bundle}"/></a>
            </li>
            <c:if test="${sessionScope.authenticated == true}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/user/profile">
                        <fmt:message key="user.profile" bundle="${bundle}"/></a>
                </li>
            </c:if>
            <c:if test="${sessionScope.authenticated == true && sessionScope.role == 'USER'}">
                <li class="nav-item">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/user/usertickets?p=1&s=10&sortDirection=${reverseSortDirection}">
                        <fmt:message key="myTickets" bundle="${bundle}"/></a>
                </li>
            </c:if>
            <c:if test="${sessionScope.authenticated == true && sessionScope.role == 'ADMIN'}">
                <li class="nav-item">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/admin/userlist?p=1&s=10&sortBy=id&sortDirection=asc">
                        <fmt:message key="userList" bundle="${bundle}"/></a>
                </li>
            </c:if>
        </ul>
        <div class="navbar-text mr-3">
            <a href="${pageContext.request.contextPath}/?locale=ua">
                <fmt:message key="lanquage.ua" bundle="${bundle}"/>
            </a>
            <a href="${pageContext.request.contextPath}/?locale=en">
                <fmt:message key="language.eng" bundle="${bundle}"/>
            </a>
        </div>
        <c:if test="${sessionScope.authenticated == null && sessionScope.role == null}">
            <div class="navbar-text mr-3">Guest</div>
        </c:if>
        <c:if test="${sessionScope.authenticated == true}">
            <div class="navbar-text mr-3">${sessionScope.user.getUsername()}</div>
        </c:if>
        <c:if test="${sessionScope.authenticated == true}">
            <div>
                <form action="${pageContext.request.contextPath}/logout" method="post">
                    <button type="submit" class="btn btn-light">
                        <fmt:message key="sign.out" bundle="${bundle}"/></button>
                </form>
            </div>
        </c:if>
        <c:if test="${sessionScope.authenticated == null}">
            <div>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/login" role="button">
                    <fmt:message key="login" bundle="${bundle}"/></a>
            </div>
        </c:if>
    </div>
</nav>