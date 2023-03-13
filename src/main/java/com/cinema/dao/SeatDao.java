package com.cinema.dao;

import com.cinema.exception.DaoException;
import com.cinema.seat.Seat;

import java.util.List;

public interface SeatDao extends GenericDao {

    List<Seat> findSeatsWithStatus(int sessionId) throws DaoException;
}