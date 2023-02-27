package com.cinema.controller;

import com.cinema.MappingProperties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class LogoutCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LogoutCommand.class);
    private static String loginPage;

    public LogoutCommand() {
        LOGGER.debug("Initializing Logout command");
        MappingProperties properties = MappingProperties.getInstance();
        loginPage = properties.getProperty("redirect.login");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("User " + request.getRemoteUser() + " is logged out");
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute("user", null);
            session.setAttribute("authenticated", false);
            session.invalidate();
        }
        return loginPage;
    }
}