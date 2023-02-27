package com.cinema.dao;

import com.cinema.Ticket.Ticket;
import com.cinema.Ticket.TicketBuilder;
import com.cinema.exception.DaoException;
import com.cinema.movie.Genre;
import com.cinema.movie.Movie;
import com.cinema.movie.MovieBuilder;
import com.cinema.seat.Seat;
import com.cinema.seat.SeatBuilder;
import com.cinema.session.Session;
import com.cinema.session.SessionBuilder;
import com.cinema.user.User;
import com.cinema.user.UserBuilder;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCTicketDao implements TicketDao {
    private static final Logger LOGGER = Logger.getLogger(JDBCTicketDao.class);
    private Connection connection;

    public JDBCTicketDao(Connection connection) {
        this.connection = connection;
    }


    /**
     * This method creates a new ticket in the DB.
     * This method also saves a new ticket id from the DB.
     */
    @Override
    public Ticket create(Object entity) throws DaoException {
        LOGGER.debug("Creating a new ticket");
        Ticket ticket = (Ticket) entity;
        try (PreparedStatement pstm = connection.prepareStatement
                ("INSERT INTO tickets (price, user_id, session_id, seat_id) VALUES (?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
             PreparedStatement pstm2 = connection.prepareStatement
                     ("SELECT * FROM sessions WHERE id = ?")) {
            connection.setAutoCommit(false);

            pstm2.setInt(1, ticket.getSession().getId());
            ResultSet rs = pstm2.executeQuery();

            ticket.setSession(mapSession(rs));
            ticket.setTicketPrice(ticket.getSession().getTicketPrice());
            rs.close();

            pstm.setBigDecimal(1, ticket.getTicketPrice());
            pstm.setInt(2, ticket.getUser().getId());
            pstm.setInt(3, ticket.getSession().getId());
            pstm.setInt(4, ticket.getSeat().getId());

            pstm.executeUpdate();

            try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ticket.setId(generatedKeys.getInt(1));
                }
            }

            updateSessionFreeSeats(ticket.getSession());
            updateSessionSeatStatus(ticket.getSession(), ticket.getSeat());

            connection.commit();
            connection.setAutoCommit(true);
            LOGGER.debug("The ticket has been added");
            return ticket;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("The ticket wasn't created");
        } finally {
            close();
        }
    }

    private void updateSessionFreeSeats(Session session) throws DaoException {
        try (PreparedStatement pstm = connection.prepareStatement
                ("UPDATE sessions set free_seats=? WHERE id=?;")) {
            pstm.setInt(1, session.getFreeSeats() - 1);
            pstm.setInt(2, session.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Session freeSeats couldn't be updated");
        }
    }

    private void updateSessionSeatStatus(Session session, Seat seat) throws DaoException {
        try (PreparedStatement pstm = connection.prepareStatement
                ("UPDATE session_seats set seat_status='SOLD' WHERE session_id=? AND seat_id=?;")) {
            pstm.setInt(1, session.getId());
            pstm.setInt(2, seat.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Session seatStatus couldn't be updated");
        }
    }

    private User mapUser(ResultSet rs) throws SQLException {
        User user = new User();
        if (rs.next()) {
            user = new UserBuilder().setId(rs.getInt("id"))
                    .setUsername(rs.getString("username"))
                    //role?
                    .build();
        }
        return user;
    }

    /**
     * This method gets all the tickets from the DB.
     *
     * @return List of tickets
     */
    @Override
    public List<Ticket> findAll() throws DaoException {
        LOGGER.debug("Creating a list of tickets");
        List<Ticket> ticketList = new ArrayList<>();
        try (PreparedStatement pstm = connection.prepareStatement("SELECT * FROM tickets")) {
            connection.setAutoCommit(false);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                ticketList.add(getTicket(rs));
            }

            rs.close();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Ticket list couldn't be loaded");
        }
        return ticketList;
    }

    private Ticket getTicket(ResultSet rs) throws DaoException, SQLException {
        Ticket ticket = new TicketBuilder().setId(rs.getInt("id"))
                .setTicketPrice(rs.getBigDecimal("price"))
                .setSession(addSessionToTicket(rs.getInt("session_id")))
                .setSeat(addSeatToTicket(rs.getInt("seat_id")))
                .setUser(addUserToTicket(rs.getInt("user_id")))
                .build();
        return ticket;
    }

    private Session addSessionToTicket(int id) throws DaoException {
        Session session;
        try (PreparedStatement ps = connection.prepareStatement
                ("SELECT * FROM sessions ses JOIN session_seats ss ON ses.id=ss.session_id WHERE ses.id = ?")) {
            ps.setInt(1, id);
            ResultSet rst = ps.executeQuery();
            session = mapSession(rst);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Failed to add session to the ticket");
        }
        return session;
    }

    private Session mapSession(ResultSet rs) throws SQLException, DaoException {
        Session session = new Session();
        if (rs.next()) {
            session = new SessionBuilder().setId(rs.getInt("id"))
                    .setDate(rs.getDate("date").toLocalDate())
                    .setTime(rs.getTime("time").toLocalTime())
                    .setTicketPrice(rs.getBigDecimal("price"))
                    .setFreeSeats(rs.getInt("free_seats"))
                    .setMovie(addMovieToSession(rs.getInt("movie_id")))
                    .build();
        }
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

    private Seat addSeatToTicket(int id) throws DaoException {
        Seat seat;
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM seats WHERE id=?")) {
            ps.setInt(1, id);
            ResultSet rst = ps.executeQuery();
            seat = mapSeat(rst);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Failed to add seat to the ticket");
        }
        return seat;
    }

    private Seat mapSeat(ResultSet rs) throws SQLException {
        Seat seat = new Seat();
        if (rs.next()) {
            seat = new SeatBuilder().setId(rs.getInt("id"))
                    .setRowNumber(rs.getInt("row_number"))
                    .setPlaceNumber(rs.getInt("place_number"))
                    .build();
        }
        return seat;
    }

    private User addUserToTicket(int id) throws DaoException {
        User user;
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE id=?")) {
            ps.setInt(1, id);
            ResultSet rst = ps.executeQuery();
            user = mapUser(rst);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Failed to add user to the ticket");
        }
        return user;
    }

    public List<Ticket> findByUserId(int userId, Integer offset, Integer size, String sortDirection) throws DaoException {
        LOGGER.debug("Creating a list of tickets with user id");
        List<Ticket> ticketList = new ArrayList<>();
        try (PreparedStatement pstm = connection.prepareStatement("SELECT * FROM tickets WHERE user_id = ? ORDER BY id "
                + sortDirection + " LIMIT ?, ?")) {
            pstm.setInt(1, userId);
            pstm.setInt(2, offset);
            pstm.setInt(3, size);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                ticketList.add(getTicket(rs));
            }

            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Ticket list with user id couldn't be loaded");
        } finally {
            close();
        }
        return ticketList;
    }

    public List<Ticket> findPages(Integer offset, Integer size, String sortDirection) throws DaoException {
        LOGGER.info("Getting page with offset " + offset + ", size " + size);
        List<Ticket> tickets = new ArrayList<>();
        try (PreparedStatement pstm = connection.prepareStatement("SELECT * FROM tickets ORDER BY id "
                + sortDirection + " LIMIT ?, ?")) {
            pstm.setInt(1, offset);
            pstm.setInt(2, size);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                tickets.add(mapTicket(rs));
            }
            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Ticket list couldn't be loaded");
        } finally {
            close();
        }
        return tickets;
    }

    private Ticket mapTicket(ResultSet rs) throws DaoException, SQLException {
        Ticket temp = new TicketBuilder().setId(rs.getInt("id"))
                .setTicketPrice(rs.getBigDecimal("price"))
                .setSession(addSessionToTicket(rs.getInt("session_id")))
                .setSeat(addSeatToTicket(rs.getInt("seat_id")))
                .setUser(addUserToTicket(rs.getInt("user_id")))
                .build();
        return temp;
    }

    @Override
    public Object findById(int id) throws DaoException {
        return null;
    }

    @Override
    public boolean update(Object entity) throws DaoException {
        return false;
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