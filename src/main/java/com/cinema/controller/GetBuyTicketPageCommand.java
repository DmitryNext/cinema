package com.cinema.controller;

import com.cinema.MappingProperties;
import com.cinema.exception.DaoException;
import com.cinema.seat.Seat;
import com.cinema.seat.SeatService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to handle GET and POST requests to the buyTicket page.
 */

public class GetBuyTicketPageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(GetBuyTicketPageCommand.class);
    private static SeatService seatService = SeatService.getInstance();
    private static String buyTicketPage;
    private static String errorPage;

    public GetBuyTicketPageCommand() {
        LOGGER.debug("Initializing GetSchedulePageCommand");
        MappingProperties properties = MappingProperties.getInstance();
        buyTicketPage = properties.getProperty("buyTicketPage");
        errorPage = properties.getProperty("errorPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing the buy ticket page command");
        Integer sessionId = Integer.parseInt(request.getParameter("sessionId"));
        try {
            List<Seat> seatList = seatService.getSeatsWithStatus(sessionId);
            request.getSession().setAttribute("sessionSeats", seatList);
            request.getSession().setAttribute("sessionId", sessionId);
            return buyTicketPage;
        } catch (DaoException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
            return errorPage;
        }
    }
}