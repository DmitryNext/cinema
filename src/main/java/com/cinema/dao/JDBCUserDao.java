package com.cinema.dao;

import com.cinema.exception.DaoException;
import com.cinema.user.Role;
import com.cinema.user.User;
import com.cinema.user.UserBuilder;
import com.cinema.service.Encoder;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCUserDao implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(JDBCUserDao.class);
    private Connection connection;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * This method creates a new user in the DB.
     * This method also saves a new user id from the DB.
     */

    @Override
    public User create(Object entity) throws DaoException {
        LOGGER.debug("Creating a new user");
        User user = (User) entity;
        try {
            PreparedStatement pstm = connection.prepareStatement
                    ("INSERT INTO users (username, password, role_id) VALUES (?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, user.getUsername());
            pstm.setString(2, Encoder.encrypt(user.getPassword()));
            pstm.setInt(3, user.getUserRole().ordinal() + 1);

            pstm.executeUpdate();

            try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
            pstm.close();

            LOGGER.debug("The user has been added");
            return user;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("The user was not registered");
        }
    }

    /**
     * This method searches a user by user id.
     * @return fetches the user from the DB.
     */

    @Override
    public User findById(int id) throws DaoException {
        LOGGER.debug("Getting the user with the id " + id);
        User user;
        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            pstm.setLong(1, id);

            ResultSet rs = pstm.executeQuery();

            user = getUser(rs);
            pstm.close();
            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("The user was not found");
        } finally {
            close();
        }
        return user;
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        User user = null;
        if (resultSet.next()) {
            user = new UserBuilder().setId(resultSet.getInt("id"))
                    .setUsername(resultSet.getString("username"))
                    .setPassword(resultSet.getString("password"))
                    .setUserRole(Role.values()[resultSet.getInt("role_id") - 1])
                    .build();
        }
        return user;
    }

    /**
     * This method fetches users from the DB.
     * @return list of users.
     */
    @Override
    public List<User> findAll() throws DaoException {
        List<User> userList = new ArrayList<>();
        try (PreparedStatement pstm = connection.prepareStatement("SELECT * FROM users")) {
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                User user = new UserBuilder().setId(rs.getInt("id"))
                        .setUsername(rs.getString("username"))
                        .setPassword(rs.getString("password"))
                        .setUserRole(Role.values()[rs.getInt("role_id") - 1])
                        .build();
                userList.add(user);
            }

            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Users were not found");
        } finally {
            close();
        }
        return userList;
    }

    /**
     * This method updates the current user.
     * @param entity represents the current user.
     */
    @Override
    public boolean update(Object entity) throws DaoException {
        LOGGER.debug("Updating the current user: " + entity);
        User user = (User) entity;
        try {
            PreparedStatement pstm = connection.prepareStatement
                    ("UPDATE users set password = ? WHERE id = ?;",
                    Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, Encoder.encrypt(user.getPassword()));
            pstm.setInt(2, user.getId());

            pstm.executeUpdate();

            LOGGER.debug("The user " + user.getUsername() + " has been updated");
            pstm.close();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("The user was not updated");
        } finally {
            close();
        }
    }

    /**
     * This method searches for a user by username and password.
     * @param username represents the username from the DB.
     * @param password represents the encrypted password from the DB.
     */
    @Override
    public User findUserByUsernameAndPassword(String username, String password) throws DaoException {
        LOGGER.debug("Getting user with username: " + username + "and password: *");
        User user;
        PreparedStatement pstm;
        try {
            pstm = connection.prepareStatement
                    ("SELECT * FROM users WHERE username = ? AND password = ?");
            pstm.setString(1, username);
            pstm.setString(2, Encoder.encrypt(password));
            ResultSet rs = pstm.executeQuery();
            user = getUser(rs);
            pstm.close();
            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("The user was not found by username and password");
        } finally {
            close();
        }
        return user;
    }

    /**
     * This method searches for a user by username.
     * @param username represents the username from the DB.
     */
    @Override
    public User findUserByUsername(String username) throws DaoException {
        User user;
        try {
            PreparedStatement pstm = connection.prepareStatement
                    ("SELECT * FROM users WHERE username = ?");
            pstm.setString(1, username);

            ResultSet rs = pstm.executeQuery();

            user = getUser(rs);
            pstm.close();
            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("User not found by username");
        } finally {
            close();
        }
        return user;
    }

    /**
     * This method saves authorities of a new user such as username and userRole.
     */
    @Override
    public boolean save(User user) throws DaoException {
        LOGGER.debug("Updating current user authorities: " + user);
        try {
            PreparedStatement pstm = connection.prepareStatement
                    ("UPDATE users set username = ?, role_id = ? WHERE id = ?;",
                    Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, user.getUsername());
            pstm.setInt(2, user.getUserRole().ordinal() + 1);
            pstm.setInt(3, user.getId());

            pstm.executeUpdate();

            LOGGER.debug("The user " + user.getUsername() + " has been saved");
            pstm.close();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("The user was not saved");
        }
    }

    @Override
    public List<User> findPages(Integer offset, Integer size,
                                String sortDirection, String sortBy) throws DaoException {
        LOGGER.info("Getting page with offset " + offset + ", size " + size);
        List<User> users = new ArrayList<>();
        try (PreparedStatement pstm = connection.prepareStatement("SELECT * FROM users ORDER BY " +
                sortBy + " " + sortDirection + " LIMIT ?, ?")) {
            pstm.setInt(1, offset);
            pstm.setInt(2, size);
            ResultSet rs = pstm.executeQuery();

            while(rs.next()) {
                User user = new UserBuilder().setId(rs.getInt("id"))
                        .setUsername(rs.getString("username"))
                        .setPassword(rs.getString("password"))
                        .setUserRole(Role.values()[rs.getInt("role_id") - 1])
                        .build();
                users.add(user);
            }

            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Users were not found");
        } finally {
            close();
        }
        return users;
    }

    @Override
    public void close() throws DaoException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DaoException("The connection couldn't be closed");
        }
    }
}