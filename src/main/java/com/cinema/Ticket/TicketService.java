package com.cinema.Ticket;

import com.cinema.dao.DaoFactory;
import com.cinema.dao.TicketDao;
import com.cinema.exception.DaoException;
import com.cinema.seat.Seat;
import com.cinema.service.Page;
import com.cinema.session.SessionBuilder;
import com.cinema.session.SessionService;
import com.cinema.user.User;
import org.apache.log4j.Logger;

import java.util.List;

public class TicketService {
    private static final Logger LOGGER = Logger.getLogger(SessionService.class);
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private static TicketService ticketService;

    public static TicketService getInstance() {
        if (ticketService == null) {
            synchronized (TicketService.class) {
                if (ticketService == null) {
                    TicketService temp = new TicketService();
                    ticketService = temp;
                }
            }
        }
        return ticketService;
    }

    public Ticket createTicket(User user, int sessionId, Seat seat) throws DaoException {
        LOGGER.debug("Saving new user ticket");
        Ticket temp = new TicketBuilder()
                .setUser(user)
                .setSession(new SessionBuilder().setId(sessionId).build())
                .setSeat(seat)
                .build();
        try (TicketDao ticketDao = daoFactory.createTicketDao()) {
            return (Ticket) ticketDao.create(temp);
        }
    }

    public Page<Ticket> getUserTicketsPaginated(int userId, Integer pageNo, Integer pageSize,
                                                String sortDirection) throws DaoException {
        LOGGER.debug("Fetching all the user tickets from the DB paginated");
        try (TicketDao ticketDao = daoFactory.createTicketDao()) {
            List<Ticket> items = ticketDao.findByUserId(userId, (pageNo - 1) * pageSize, pageSize, sortDirection);
            return new Page<Ticket>(items, pageNo, pageSize);
        }
    }
}