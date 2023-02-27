package com.cinema.dao;

import com.cinema.exception.DaoException;
import com.cinema.user.User;

import java.util.List;

public interface UserDao extends GenericDao {

    /**
     * This method searches a user by username and password.
     */

    User findUserByUsernameAndPassword(String username, String password) throws DaoException;

    /**
     * This method searches a user by username.
     */

    User findUserByUsername(String username) throws DaoException;

    boolean save(User user) throws DaoException;

    List<User> findPages(Integer offset, Integer size, String sortDirection, String sortBy)
            throws DaoException;
}