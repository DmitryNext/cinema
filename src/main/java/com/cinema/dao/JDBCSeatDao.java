package com.cinema.dao;

import com.cinema.exception.DaoException;
import com.cinema.seat.Seat;
import com.cinema.seat.SeatBuilder;
import com.cinema.seat.SeatStatus;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCSeatDao implements SeatDao {
    private static final Logger LOGGER = Logger.getLogger(JDBCSeatDao.class);
    private Connection connection;

    public JDBCSeatDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * This method creates a new seat in the DB.
     * This method also saves a new seat id from the DB.
     */

    @Override
    public Seat create(Object entity) throws DaoException {
        LOGGER.debug("Creating a new seat");
        Seat seat = (Seat) entity;
        try {
            PreparedStatement pstm = connection.prepareStatement
                    ("INSERT INTO seats (rowNumber, placeNumber) VALUES (?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, seat.getRowNumber());
            pstm.setInt(2, seat.getPlaceNumber());

            pstm.executeUpdate();

            try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    seat.setId(generatedKeys.getInt(1));
                }
            }
            pstm.close();

            LOGGER.debug("The seat has been added");
            return seat;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("The seat was not created");
        } finally {
            close();
        }
    }

    /**
     * This method searches a seat by seat id.
     * @return fetches the seat from the DB.
     */

    @Override
    public Seat findById(int id) throws DaoException {
        LOGGER.debug("Getting a seat with the id " + id);
        Seat seat;
        try (PreparedStatement pstm = connection.prepareStatement("SELECT * FROM seats WHERE id = ?")) {
            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();

            seat = mapSeat(rs);
            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("The seat was not found");
        }
        return seat;
    }

    /**
     * Fetches existing seat from the DB with declared parameters of:
     * //     * @param rowNumber
     * //     * @param placeNumber
     */

    private Seat mapSeat(ResultSet rs) throws SQLException {
        Seat temp = new SeatBuilder().setId(rs.getInt("id"))
                .setRowNumber(rs.getInt("row_number"))
                .setPlaceNumber(rs.getInt("place_number"))
                .build();
        return temp;
    }

    /**
     * This method fetches all seats from the DB.
     * @return list of seats.
     */

    @Override
    public List<Seat> findAll() throws DaoException {
        List<Seat> seatList = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM seats");
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                seatList.add(mapSeat(rs));
            }
            rs.close();
            pstm.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Seat list couldn't be loaded");
        } finally {
            close();
        }
        return seatList;
    }

    public List<Seat> findSeatsWithStatus(int sessionId) throws DaoException {
        List<Seat> seatList = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement
                    ("SELECT * FROM session_seats ss JOIN seats s ON s.id=ss.seat_id WHERE ss.session_id LIKE ?");
            pstm.setInt(1, sessionId);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                seatList.add(createSeatWithStatus(rs));
            }

            rs.close();
            pstm.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Seat list couldn't be loaded");
        } finally {
            close();
        }
        return seatList;
    }

    private Seat createSeatWithStatus(ResultSet rs) throws SQLException {
        Seat s = new Seat();
        s.setId(rs.getInt("seat_id"));
        s.setRowNumber(rs.getInt("row_number"));
        s.setPlaceNumber(rs.getInt("place_number"));
        s.setSeatStatus(SeatStatus.valueOf(rs.getString("seat_status")));
        return s;
    }

    @Override
    public boolean update(Object entity) throws DaoException {
        return false;
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