package com.cinema.dao;

import com.cinema.exception.DaoException;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract UserDao createUserDao() throws DaoException;

    public abstract MovieDao createMovieDao() throws DaoException;

    public abstract SessionDao createSessionDao() throws DaoException;

    public abstract SeatDao createSeatDao() throws DaoException;

    public abstract TicketDao createTicketDao() throws DaoException;

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    DaoFactory temp = new JDBCDaoFactory();
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;
    }
}