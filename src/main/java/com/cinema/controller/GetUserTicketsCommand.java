package com.cinema.controller;

import com.cinema.MappingProperties;
import com.cinema.Ticket.*;
import com.cinema.exception.DaoException;
import com.cinema.user.User;
import org.apache.log4j.Logger;
import com.cinema.service.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is used to handle the GET request to the userTickets page.
 *  * This class is also used to send to the userTickets page.
 */
public class GetUserTicketsCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(GetUserTicketsCommand.class);
    private static TicketService ticketService = TicketService.getInstance();
    private static String userTicketsPage;
    private static String errorPage;

    public GetUserTicketsCommand() {
        LOGGER.debug("Initializing GetUserTicketsCommand");
        MappingProperties properties = MappingProperties.getInstance();
        userTicketsPage = properties.getProperty("userTicketsPage");
        errorPage = properties.getProperty("errorPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing to send the userTickets page");
        Integer pageNo = Integer.parseInt(request.getParameter("p"));
        Integer pageSize = Integer.parseInt(request.getParameter("s"));
        String sortDirection = request.getParameter("sortDirection");
        User user = (User) request.getSession().getAttribute("user");
        try {
            Page<Ticket> page = ticketService.getUserTicketsPaginated(user.getId(), pageNo, pageSize, sortDirection);
            request.getSession().setAttribute("userTickets", page);
            request.getSession().setAttribute("currentPage", pageNo);
            request.getSession().setAttribute("sortDirection", sortDirection);
            request.getSession().setAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
        } catch (DaoException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
            return errorPage;
        }
        return userTicketsPage;
    }
}