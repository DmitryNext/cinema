package com.cinema.seat;

import com.cinema.dao.DaoFactory;
import com.cinema.dao.SeatDao;
import com.cinema.exception.DaoException;
import org.apache.log4j.Logger;

import java.util.List;

public class SeatService {
    private static final Logger LOGGER = Logger.getLogger(SeatService.class);
    private static DaoFactory daoFactory = DaoFactory.getInstance();
    private static SeatService seatService;

    public static SeatService getInstance() {
        if (seatService == null) {
            synchronized (SeatService.class) {
                if (seatService == null) {
                    SeatService temp = new SeatService();
                    seatService = temp;
                }
            }
        }
        return seatService;
    }

    /**
     * This method gets all seats from the DB.
     */
    public List<Seat> getAllSeats() throws DaoException {
        LOGGER.debug("Fetching all seats from the DB");
        try (SeatDao seatDao = daoFactory.createSeatDao()) {
            return seatDao.findAll();
        }
    }

    /**
     * This method gets all seats with status from the DB.
     */
    public static List<Seat> getSeatsWithStatus(int sessionId) throws DaoException {
        LOGGER.debug("Fetching all seats with status from the DB");
        try (SeatDao seatDao = daoFactory.createSeatDao()) {
            return seatDao.findSeatsWithStatus(sessionId);
        }
    }
}