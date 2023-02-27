package com.cinema.controller;

import com.cinema.session.Session;
import com.cinema.service.Page;
import com.cinema.exception.DaoException;
import com.cinema.MappingProperties;
import com.cinema.session.SessionService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * This class is used to handle GET and POST requests to the schedule page.
 */

public class GetSchedulePageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(GetSchedulePageCommand.class);
    private static SessionService sessionService = SessionService.getInstance();
    private static String schedulePage;
    private static String errorPage;

    public GetSchedulePageCommand() {
        LOGGER.debug("Initializing GetSchedulePageCommand");
        MappingProperties properties = MappingProperties.getInstance();
        schedulePage = properties.getProperty("schedulePage");
        errorPage = properties.getProperty("errorPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing the schedule page command");
        Integer pageNo = Integer.parseInt(request.getParameter("p"));
        Integer pageSize = Integer.parseInt(request.getParameter("s"));
        String sortDirection = request.getParameter("sortDirection");
        String sortBy = request.getParameter("sortBy");
        String movieName = request.getParameter("movieNameSort");
        String sessionDate = request.getParameter("sessionDateSort");
        String sessionTime = request.getParameter("sessionTimeSort");
        String sessionFreeSeats = request.getParameter("sessionFreeSeatsSort");

        try {
            Page<Session> page = sessionService.getAllSessionsPaginated(pageNo, pageSize, sortDirection, sortBy);
            if (Objects.nonNull(movieName) && !movieName.isEmpty()) {
                page = sessionService.findSessionSortByMovieName(pageNo, pageSize, sortDirection);
                request.getSession().setAttribute("movieNameSort", true);
            } else if (Objects.nonNull(sessionDate) && !sessionDate.isEmpty()) {
                page = sessionService.findSessionSortByDate(pageNo, pageSize, sortDirection);
                request.getSession().setAttribute("sessionDateSort", true);
            } else if (Objects.nonNull(sessionTime) && !sessionTime.isEmpty()) {
                page = sessionService.findSessionSortByTime(pageNo, pageSize, sortDirection);
                request.getSession().setAttribute("sessionTimeSort", true);
            } else if (Objects.nonNull(sessionFreeSeats) && sessionFreeSeats.isEmpty()) {
                page = sessionService.findSessionSortByFreeSeats(pageNo, pageSize, sortDirection);
                request.getSession().setAttribute("sessionFreeSeatsSort", true);
            }

            request.getSession().setAttribute("schedule", page);
            request.getSession().setAttribute("currentPage", pageNo);
            request.getSession().setAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

            return schedulePage;
        } catch (DaoException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
            return errorPage;
        }
    }
}