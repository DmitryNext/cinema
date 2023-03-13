package com.cinema.user;

import com.cinema.service.Page;
import org.apache.log4j.Logger;

import com.cinema.dao.UserDao;
import com.cinema.exception.DaoException;
import com.cinema.dao.DaoFactory;

import java.util.List;

/**
 * This class helps to get information from DAO layer.
 */
public class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class);
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private static UserService userService;

    public static UserService getInstance() {
        if(userService == null) {
            synchronized (UserService.class) {
                if(userService == null) {
                    UserService temp = new UserService();
                    userService = temp;
                }
            }
        }
        return userService;
    }

    /**
     * This method gets all users from the DB.
     */
    public List<User> getAllUsers() throws DaoException {
        LOGGER.debug("Fetching all users from the DB");
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findAll();
        }
    }

    public Page<User> getAllUsersPaginated(Integer pageNo, Integer pageSize, String sortDirection,
                                           String sortBy) throws DaoException {
        LOGGER.debug("Fetching all users from a paginated DB");
        try (UserDao userDao = daoFactory.createUserDao()) {
            List<User> items = userDao.findPages((pageNo - 1) * pageSize, pageSize, sortDirection, sortBy);
            return new Page<User>(items, pageNo, pageSize);
        }
    }

    /**
     * This method checks if the username is already in use.
     * @param username username to check.
     * @param password password to check
     * @return the user from the DB found for these credentials.
     */
    public User getUserByCredentials(String username, String password) throws DaoException {
        LOGGER.debug("Getting user from DB by credentials");
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findUserByUsernameAndPassword(username, password);
        }
    }

    public User registerUser(User user) throws DaoException {
        LOGGER.debug("Registration of a new user");
        try (UserDao userDao = daoFactory.createUserDao()) {
            return (User) userDao.create(user);
        }
    }

    public User findUserByUsername(String username) throws DaoException {
        LOGGER.debug("User search by username " + username);
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findUserByUsername(username);
        }
    }

    public User findUserById(Integer id) throws DaoException {
        LOGGER.debug("User search by id " + id);
        try (UserDao userDao = daoFactory.createUserDao()) {
            return (User) userDao.findById(id);
        }
    }

    public boolean updateProfile(User user) throws DaoException {
        LOGGER.debug("Updating a user from the DB");
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.update(user);
        }
    }

    public boolean saveUser(User user, String updateRole) throws DaoException {
        LOGGER.debug("Saving new users' access rights to the DB");
        user.setUserRole(Role.valueOf(updateRole));
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.save(user);
        }
    }
}