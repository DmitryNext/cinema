package com.cinema.controller;

import com.cinema.service.Page;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.cinema.user.UserService;
import com.cinema.MappingProperties;
import com.cinema.user.User;
import com.cinema.exception.DaoException;

public class GetUserListPageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(GetUserListPageCommand.class);
    private static String userListPage;
    private static String errorPage;
    private static UserService userService = UserService.getInstance();

    public GetUserListPageCommand() {
        LOGGER.debug("Initializing GetUserListPageCommand");
        MappingProperties properties = MappingProperties.getInstance();
        userListPage = properties.getProperty("userListPage");
        errorPage = properties.getProperty("errorPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing send user list page command");
        Integer pageNo = Integer.parseInt(request.getParameter("p"));
        Integer pageSize = Integer.parseInt(request.getParameter("s"));
        String sortDirection = request.getParameter("sortDirection");
        String sortBy = request.getParameter("sortBy");
        try {
            Page<User> page = userService.getAllUsersPaginated(pageNo, pageSize, sortDirection, sortBy);

            request.getSession().setAttribute("sortDirection", sortDirection);
            request.getSession().setAttribute("sortBy", sortBy);
            request.getSession().setAttribute("currentPage", pageNo);
            request.getSession().setAttribute("userTickets", page);
            request.getSession().setAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
            request.getSession().setAttribute("userList", page);
        } catch (DaoException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
            return errorPage;
        }
        return userListPage;
    }
}