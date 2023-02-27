package com.cinema.dao;

import com.cinema.exception.DaoException;
import com.cinema.session.Session;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface SessionDao extends GenericDao {

    List<Session> findPages(Integer offset, Integer size, String sortDirection, String sortBy)
            throws DaoException;

    List<Session> findSessionSortByMovieName(Integer offset, Integer size, String sortDirection) throws DaoException;

    List<Session> findSessionSortByDate(Integer offset, Integer size, String sortDirection) throws DaoException;

    List<Session> findSessionSortByTime(Integer offset, Integer size, String sortDirection) throws DaoException;

    List<Session> findSessionSortByFreeSeats(Integer offset, Integer size, String sortDirection) throws DaoException;
}