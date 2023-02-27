package com.cinema.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import com.cinema.user.UserService;
import com.cinema.MappingProperties;
import com.cinema.user.User;
import com.cinema.user.UserBuilder;
import com.cinema.user.Role;
import com.cinema.exception.DaoException;

import java.util.Objects;

import static com.cinema.service.Validator.validateRegistration;

/**
 * This class is used to handle GET and POST requests to the registration page.
 * This class is also used to send to the registration page.
 */

public class GetRegistrationPageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(GetRegistrationPageCommand.class);
    private static UserService userService = UserService.getInstance();
    private static String registrationPage;
    private static String loginPage;
    private static String errorPage;

    public GetRegistrationPageCommand() {
        LOGGER.debug("Initializing GetRegistrationPageCommand");
        MappingProperties properties = MappingProperties.getInstance();
        registrationPage = properties.getProperty("registrationPage");
        loginPage = properties.getProperty("redirect.login");
        errorPage = properties.getProperty("errorPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing the registration page command");
        if (!validateRegistration(request.getParameter("username"), request.getParameter("password"))) {
            LOGGER.debug("Incorrect credentials, the registration page is returned.");
            request.setAttribute("msg", " ");
            return registrationPage;
        }
        try {
            if (Objects.nonNull(userService.findUserByUsername(request.getParameter("username")))) {
                request.setAttribute("msgExists", " ");
                return registrationPage;
            }
            User user = new UserBuilder().setUsername(request.getParameter("username"))
                    .setPassword(request.getParameter("password"))
                    .setUserRole(Role.USER)
                    .build();
            userService.registerUser(user);
        } catch (DaoException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
            return errorPage;
        }
        LOGGER.debug("The user is successfully registered.");
        request.getSession().setAttribute("msgSuccess", "You have successfully registered!");
        return loginPage;
    }
}