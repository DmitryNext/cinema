package com.cinema.dao;

import com.cinema.exception.DaoException;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {
    private static final Logger LOGGER = Logger.getLogger(JDBCDaoFactory.class);
    private DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public UserDao createUserDao() throws DaoException {
        LOGGER.debug("Creating a new connection for JDBCUserDao");
        return new JDBCUserDao(getConnection());
    }

    @Override
    public MovieDao createMovieDao() throws DaoException {
        LOGGER.debug("Creating a new connection for JDBCMovieDao");
        return new JDBCMovieDao(getConnection());
    }

    @Override
    public SessionDao createSessionDao() throws DaoException {
        LOGGER.debug("Creating a new connection for JDBCSessionDao");
        return new JDBCSessionDao(getConnection());
    }

    @Override
    public SeatDao createSeatDao() throws DaoException {
        LOGGER.debug("Creating a new connection for JDBCSeatDao");
        return new JDBCSeatDao(getConnection());
    }

    @Override
    public TicketDao createTicketDao() throws DaoException {
        LOGGER.debug("Creating a new connection for JDBCTicketDao");
        return new JDBCTicketDao(getConnection());
    }

    private Connection getConnection() throws DaoException {
        LOGGER.debug("Getting a new connection to the DB");
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("The connection couldn't be closed");
        }
    }
}