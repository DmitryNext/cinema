package com.cinema.controller;

import com.cinema.MappingProperties;
import com.cinema.ticket.TicketService;
import com.cinema.exception.DaoException;
import com.cinema.seat.Seat;
import com.cinema.seat.SeatBuilder;
import com.cinema.user.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is used to handle the POST request for registering a new ticket.
 */
public class UserTicketCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(UserTicketCommand.class);
    private static TicketService ticketService = TicketService.getInstance();
    private static String userTicketsPage;
    private static String errorPage;

    public UserTicketCommand() {
        LOGGER.debug("Initializing UserTicketCommand");
        MappingProperties properties = MappingProperties.getInstance();
        userTicketsPage = properties.getProperty("redirect.user.userTickets");
        errorPage = properties.getProperty("errorPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Execute placing a new ticket command");
        User user = (User) request.getSession().getAttribute("user");
        Integer sessionId = Integer.parseInt(request.getParameter("sessionId"));
        Seat seat = new SeatBuilder()
                .setId(Integer.parseInt(request.getParameter("id")))
                .setPlaceNumber(Integer.parseInt(request.getParameter("placeNumber")))
                .setRowNumber(Integer.parseInt(request.getParameter("rowNumber")))
                .build();
        try {
            ticketService.createTicket(user, sessionId, seat);
        } catch (DaoException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
            return errorPage;
        }
        return userTicketsPage;
    }
}