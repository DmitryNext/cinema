package com.cinema.dao;

import com.cinema.Ticket.Ticket;
import com.cinema.exception.DaoException;

import java.util.List;

public interface TicketDao extends GenericDao {

    List<Ticket> findPages(Integer offset, Integer size, String sortDirection) throws DaoException;

    List<Ticket> findByUserId(int userId, Integer offset, Integer size, String sortDirection) throws DaoException;
}