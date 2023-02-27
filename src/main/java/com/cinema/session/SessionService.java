package com.cinema.session;

import com.cinema.dao.DaoFactory;
import com.cinema.exception.DaoException;
import com.cinema.dao.SessionDao;
import com.cinema.service.Page;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SessionService {
    private static final Logger LOGGER = Logger.getLogger(SessionService.class);
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private static SessionService sessionService;

    public static SessionService getInstance() {
        if (sessionService == null) {
            synchronized (SessionService.class) {
                if (sessionService == null) {
                    SessionService temp = new SessionService();
                    sessionService = temp;
                }
            }
        }
        return sessionService;
    }

    /**
     * This method gets all sessions from the DB.
     */
    public List<Session> getAllSessions() throws DaoException {
        try (SessionDao sessionDao = daoFactory.createSessionDao()) {
            return sessionDao.findAll();
        }
    }

    public Page<Session> getAllSessionsPaginated(Integer pageNo, Integer pageSize,
                                                 String sortDirection, String sortBy) throws DaoException {
        LOGGER.debug("Fetching all the sessions from the DB paginated");
        try (SessionDao sessionDao = daoFactory.createSessionDao()) {
            List<Session> items = sessionDao.findPages
                    ((pageNo - 1) * pageSize, pageSize, sortDirection, sortBy);
            return new Page<Session>(items, pageNo, pageSize);
        }
    }

    public Page<Session> findSessionSortByMovieName(Integer pageNo, Integer pageSize,
                                                    String sortDirection) throws DaoException {
        LOGGER.debug("Searching sessions from the DB by movieName");
        try (SessionDao sessionDao = daoFactory.createSessionDao()) {
            List<Session> items = sessionDao.findSessionSortByMovieName
                    ((pageNo - 1) * pageSize, pageSize, sortDirection);
            return new Page<Session>(items, pageNo, pageSize);
        }
    }

    public Page<Session> findSessionSortByDate(Integer pageNo, Integer pageSize,
                                           String sortDirection) throws DaoException {
        LOGGER.debug("Searching sessions from the DB by sessionDate");
        try (SessionDao sessionDao = daoFactory.createSessionDao()) {
            List<Session> items = sessionDao.findSessionSortByDate
                    ((pageNo - 1) * pageSize, pageSize, sortDirection);
            return new Page<Session>(items, pageNo, pageSize);
        }
    }

    public Page<Session> findSessionSortByTime(Integer pageNo, Integer pageSize,
                                               String sortDirection) throws DaoException {
        LOGGER.debug("Searching sessions from the DB by sessionTime");
        try (SessionDao sessionDao = daoFactory.createSessionDao()) {
            List<Session> items = sessionDao.findSessionSortByTime
                    ((pageNo - 1) * pageSize, pageSize, sortDirection);
            return new Page<Session>(items, pageNo, pageSize);
        }
    }

    public Page<Session> findSessionSortByFreeSeats(Integer pageNo, Integer pageSize,
                                                    String sortDirection) throws DaoException {
        LOGGER.debug("Searching sessions from the DB by the number of free seats");
        try (SessionDao sessionDao = daoFactory.createSessionDao()) {
            List<Session> items = sessionDao.findSessionSortByFreeSeats
                    ((pageNo - 1) * pageSize, pageSize, sortDirection);
            return new Page<Session>(items, pageNo, pageSize);
        }
    }
}