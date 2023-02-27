package com.cinema.dao;

import com.cinema.exception.DaoException;
import com.cinema.movie.Movie;

import java.util.List;

public interface MovieDao extends GenericDao {
    boolean delete(int id) throws DaoException;

    List<Movie> findPages(Integer offset, Integer size, String sortDirection) throws DaoException;
}