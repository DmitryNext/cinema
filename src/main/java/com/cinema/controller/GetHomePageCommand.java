package com.cinema.controller;

import com.cinema.MappingProperties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.util.Objects;

/**
 * This class is used for handling GET requests to the homepage.
 */

public class GetHomePageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(GetHomePageCommand.class);
    private static String homePage;

    public GetHomePageCommand() {
        LOGGER.debug("Initializing GetHomePageCommand");
        MappingProperties properties = MappingProperties.getInstance();
        homePage = properties.getProperty("homePage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing the home page command");
        if (Objects.nonNull(request.getParameter("locale"))) {
            switch (request.getParameter("locale")) {
                case "en":
                    request.getSession().setAttribute("locale", "en");
                    break;
                case "ua":
                    request.getSession().setAttribute("locale", "ua");
                    break;
            }
        }
        return homePage;
    }
}