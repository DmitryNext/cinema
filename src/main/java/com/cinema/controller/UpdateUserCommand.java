package com.cinema.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import com.cinema.user.UserService;
import com.cinema.MappingProperties;
import com.cinema.user.User;
import com.cinema.exception.DaoException;

import java.util.Objects;

/**
 * This class is used to handle GET and POST requests to the user edit page.
 * This class is also used to send to the user edit page.
 */

public class UpdateUserCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(GetUserListPageCommand.class);
    private static String userEditPage;
    private static String errorPage;
    private static String userListPage;
    private static UserService userService = UserService.getInstance();

    public UpdateUserCommand() {
        LOGGER.debug("Initializing UpdateUserCommand");
        MappingProperties properties = MappingProperties.getInstance();
        userEditPage = properties.getProperty("userEditPage");
        errorPage = properties.getProperty("errorPage");
        userListPage = properties.getProperty("redirect.admin.userList");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing update user command");
        HttpSession session = request.getSession();
        User userToUpdate = (User) session.getAttribute("updatedUser");
        if (Objects.isNull(request.getParameter("updateName")) ||
                Objects.isNull(request.getParameter("updateRole"))) {
            request.setAttribute("msgUpdate", " ");
            return userEditPage;
        }
        try {
            userToUpdate.setUsername(request.getParameter("updateName"));
            userService.saveUser(userToUpdate, request.getParameter("updateRole"));
            return userListPage;
        } catch (DaoException e) {
            return errorPage;
        }
    }
}
