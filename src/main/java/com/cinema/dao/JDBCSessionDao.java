package com.cinema.dao;

import com.cinema.exception.DaoException;
import com.cinema.movie.*;
import com.cinema.session.Session;
import com.cinema.session.SessionBuilder;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCSessionDao implements SessionDao {
    private static final Logger LOGGER = Logger.getLogger(JDBCSessionDao.class);
    private Connection connection;

    public JDBCSessionDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * This method creates a new session in the DB.
     * This method also saves a new session id from the DB.
     */
    @Override
    public Session create(Object entity) throws DaoException {
        LOGGER.debug("Creating a new session");
        Session session = (Session) entity;
        try (PreparedStatement pstm = connection.prepareStatement
                ("INSERT INTO sessions (date, time, price, free_seats, movie_id) VALUES (?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
             PreparedStatement pstm2 = connection.prepareStatement
                     ("SELECT * FROM movies WHERE name = ? AND genre = ? AND duration = ? AND poster = ?")) {
            connection.setAutoCommit(false);

            pstm2.setString(1, session.getMovie().getName());
            pstm2.setString(2, session.getMovie().getGenre().name());
            pstm2.setTime(3, session.getMovie().getDuration());
            pstm2.setString(4, session.getMovie().getPoster());
            ResultSet rs = pstm2.executeQuery();

            session.setMovie(mapMovie(rs));
            rs.close();

            pstm.setDate(1, Date.valueOf(session.getDate()));
            pstm.setTime(2, Time.valueOf(session.getTime()));
            pstm.setBigDecimal(3, session.getTicketPrice());
            pstm.setInt(4, session.getFreeSeats());
            pstm.setInt(5, session.getMovie().getId());

            pstm.executeUpdate();

            try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    session.setId(generatedKeys.getInt(1));
                }
            }

            connection.commit();
            connection.setAutoCommit(true);
            LOGGER.debug("The session has been added");
            return session;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            rollbackQuiet(connection);
            throw new DaoException("The session wasn't created");
        }
    }

    private Movie mapMovie(ResultSet rs) throws SQLException {
        Movie movie = new Movie();
        if (rs.next()) {
            movie = new MovieBuilder().setId(rs.getInt("id"))
                    .setName(rs.getString("name"))
                    .setGenre(Genre.valueOf(rs.getString("genre")))
                    .setDuration(Time.valueOf(rs.getString("duration")))
                    .setPoster(rs.getString("poster"))
                    .build();
        }
        return movie;
    }

    private void rollbackQuiet(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public Session findById(int id) throws DaoException {
        LOGGER.debug("Getting session with id " + id);
        Session session = new Session();
        try (PreparedStatement ps = connection.prepareStatement
                ("SELECT * FROM sessions WHERE id = ?;")) {
            connection.setAutoCommit(false);

            ps.setInt(1, id);
            ResultSet rst = ps.executeQuery();

            while (rst.next()) {
                session = getSession(rst);
            }

            connection.commit();
            connection.setAutoCommit(true);
            rst.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            rollbackQuiet(connection);
            throw new DaoException("The session wasn't found by id");
        }
        return session;
    }

    /**
     * This method gets the individual sessions from the result set.
     *
     * @return List of sessions
     */
    private Session getSession(ResultSet rs) throws DaoException, SQLException {
        Session session = new SessionBuilder().setId(rs.getInt("id"))
                .setDate(rs.getDate("date").toLocalDate())
                .setTime(rs.getTime("time").toLocalTime())
                .setTicketPrice(rs.getBigDecimal("price"))
                .setFreeSeats(rs.getInt("free_seats"))
                .setMovie(addMovieToSession(rs.getInt("movie_id")))
                .build();
        return session;
    }

    private Movie addMovieToSession(int id) throws DaoException {
        Movie movie;
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM movies WHERE id=?")) {
            ps.setInt(1, id);
            ResultSet rst = ps.executeQuery();
            movie = mapMovie(rst);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Failed to add movie to the session");
        }
        return movie;
    }

    /**
     * This method gets all the sessions from the DB.
     *
     * @return List of sessions
     */
    @Override
    public List<Session> findAll() throws DaoException {
        LOGGER.debug("Creating a list of sessions");
        List<Session> sessionList = new ArrayList<>();
        try (PreparedStatement pstm = connection.prepareStatement("SELECT * FROM sessions")) {
            connection.setAutoCommit(false);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                sessionList.add(getSession(rs));
            }

            rs.close();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            rollbackQuiet(connection);
            throw new DaoException("Session list couldn't be loaded");
        }
        return sessionList;
    }

    @Override
    public boolean update(Object entity) throws DaoException {
        LOGGER.debug("Updating current session: " + entity);
        Session session = (Session) entity;
        try (PreparedStatement pstm = connection.prepareStatement
                ("UPDATE sessions set date=?, time=?, price=?, free_seats=?, movie_id=? WHERE id=?;",
                        Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, session.getDate().toString());
            pstm.setString(2, session.getTime().toString());
            pstm.setString(3, session.getTicketPrice().toString());
            pstm.setInt(4, session.getFreeSeats());
            pstm.setInt(5, session.getMovie().getId());

            pstm.executeUpdate();

            LOGGER.debug("The session id: " + session.getId() + " has been updated");
            pstm.close();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("The session wasn't being updated");
        } finally {
            close();
        }
    }

    @Override
    public List<Session> findPages(Integer offset, Integer size,
                                          String sortDirection, String sortBy) throws DaoException {
        LOGGER.debug("Getting page with offset " + offset + ", size " + size);
        List<Session> temp = new ArrayList<>();
        try (PreparedStatement pstm = connection.prepareStatement("SELECT * FROM sessions ORDER BY " +
                sortBy + " " + sortDirection + " LIMIT ?, ?")) {
            pstm.setInt(1, offset);
            pstm.setInt(2, size);
            ResultSet rs = pstm.executeQuery();

            while(rs.next()) {
                temp.add(getSession(rs));
            }
            rs.close();

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("No pages could be found");
        }
        finally {
            close();
        }
        return temp;
    }

    @Override
    public List<Session> findSessionSortByMovieName(Integer offset, Integer size,
                                                    String sortDirection) throws DaoException {
        LOGGER.debug("Getting the session by movie name with offset " + offset + ", size " + size);
        List<Session> temp = new ArrayList<>();
        try (PreparedStatement pstm = connection.prepareStatement
                ("SELECT * FROM sessions ses JOIN movies mov ON ses.movie_id=mov.id ORDER BY mov.name " +
                        sortDirection + " LIMIT ?, ?")) {
            pstm.setInt(1, offset);
            pstm.setInt(2, size);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                temp.add(getSession(rs));
            }

            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("No sessions were found by the name");
        } finally {
            close();
        }
        return temp;
    }

    @Override
    public List<Session> findSessionSortByDate(Integer offset, Integer size,
                                           String sortDirection) throws DaoException {
        LOGGER.debug("Getting session by date with offset " + offset + ", size " + size);
        List<Session> temp = new ArrayList<>();
        try (PreparedStatement pstm = connection.prepareStatement
                ("SELECT * FROM sessions ses JOIN movies mov ON ses.movie_id=mov.id ORDER BY ses.date " +
                        sortDirection + " LIMIT ?, ?")) {
            pstm.setInt(1, offset);
            pstm.setInt(2, size);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                temp.add(getSession(rs));
            }

            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("No sessions were found by the date");
        } finally {
            close();
        }
        return temp;
    }

    @Override
    public List<Session> findSessionSortByTime(Integer offset, Integer size,
                                               String sortDirection) throws DaoException {
        LOGGER.debug("Getting session by time with offset " + offset + ", size " + size);
        List<Session> temp = new ArrayList<>();
        try (PreparedStatement pstm = connection.prepareStatement
                ("SELECT * FROM sessions ses JOIN movies mov ON ses.movie_id=mov.id ORDER BY ses.time " +
                        sortDirection + " LIMIT ?, ?")) {
            pstm.setInt(1, offset);
            pstm.setInt(2, size);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                temp.add(getSession(rs));
            }

            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("No sessions were found by the time");
        } finally {
            close();
        }
        return temp;
    }

    public List<Session> findSessionSortByFreeSeats(Integer offset, Integer size,
                                                String sortDirection) throws DaoException {
        LOGGER.debug("Getting the session by the number of freeSeats with offset " + offset + ", size " + size);
        List<Session> temp = new ArrayList<>();
        try (PreparedStatement pstm = connection.prepareStatement
                ("SELECT * FROM sessions ses JOIN movies mov ON ses.movie_id=mov.id ORDER BY ses.free_seats " +
                        sortDirection + " LIMIT ?, ?")) {
            pstm.setInt(2, offset);
            pstm.setInt(3, size);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                temp.add(getSession(rs));
            }

            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("No sessions were found by the number of freeSeats");
        } finally {
            close();
        }
        return temp;
    }

    @Override
    public void close() throws DaoException {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DaoException("The connection couldn't be closed");
        }
    }
}