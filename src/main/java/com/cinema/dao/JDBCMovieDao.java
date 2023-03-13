package com.cinema.dao;

import com.cinema.movie.*;
import com.cinema.exception.DaoException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCMovieDao implements MovieDao {
    private static final Logger LOGGER = Logger.getLogger(JDBCMovieDao.class);
    private Connection connection;

    public JDBCMovieDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * This method creates a new movie in the DB.
     * This method also saves a new movie id from the DB.
     */

    @Override
    public Movie create(Object entity) throws DaoException {
        LOGGER.debug("Creating a new movie");
        Movie movie = (Movie) entity;
        try {
            PreparedStatement pstm = connection.prepareStatement
                    ("INSERT INTO movies (name, genre, duration, poster) VALUES (?, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, movie.getName());
            pstm.setString(2, movie.getGenre().name());
            pstm.setTime(3, movie.getDuration());
            pstm.setString(4, movie.getPoster());

            pstm.executeUpdate();

            try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    movie.setId(generatedKeys.getInt(1));
                }
            }
            pstm.close();

            LOGGER.debug("The movie has been added");
            return movie;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("The movie was not created");
        } finally {
            close();
        }
    }

    /**
     * This method searches a movie by movie id.
     *
     * @return fetches the movie from the DB.
     */

    @Override
    public Movie findById(int id) throws DaoException {
        LOGGER.debug("Getting a movie with the id " + id);
        Movie movie = null;
        try (PreparedStatement pstm = connection.prepareStatement("SELECT * FROM movies WHERE id = ?")) {
            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();

            movie = mapMovie(rs);
            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("The movie was not found");
        }
        return movie;
    }

    /**
     * Fetches existing movie from the DB with declared parameters of:
     * //     * @param name
     * //     * @param genre - genre enum name
     * //     * @param duration
     */

    private Movie mapMovie(ResultSet rs) throws SQLException {
        Movie temp = new MovieBuilder().setId(rs.getInt("id"))
                .setName(rs.getString("name"))
                .setGenre(Genre.valueOf(rs.getString("genre")))
                .setDuration(Time.valueOf(rs.getString("duration")))
                .setPoster(rs.getString("poster"))
                .build();
        return temp;
    }

    /**
     * This method fetches movies from the DB.
     * @return list of movies.
     */

    @Override
    public List<Movie> findAll() throws DaoException {
        List<Movie> movieList = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM movies");
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                movieList.add(mapMovie(rs));
            }

            rs.close();
            pstm.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Movie list couldn't be loaded");
        }
        return movieList;
    }

    public List findPages(Integer offset, Integer size, String sortDirection) throws DaoException {
        LOGGER.info("Getting page with offset " + offset + ", size " + size);
        List<Movie> movies = new ArrayList<>();
        try (PreparedStatement pstm = connection.prepareStatement("SELECT * FROM movies ORDER BY genre "
                + sortDirection + " LIMIT ?, ?")) {
            pstm.setInt(1, offset);
            pstm.setInt(2, size);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                movies.add(mapMovie(rs));
            }

            rs.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Movie list couldn't be loaded");
        } finally {
            close();
        }
        return movies;
    }

    @Override
    public boolean update(Object entity) throws DaoException {
        LOGGER.debug("Updating the current movie: " + entity);
        Movie movie = (Movie) entity;
        try (PreparedStatement pstm = connection.prepareStatement
                ("UPDATE movies set name = ?, genre = ?, duration = ?, poster = ? WHERE id = ?;",
                        Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, movie.getName());
            pstm.setString(2, movie.getGenre().name());
            pstm.setTime(3, movie.getDuration());
            pstm.setString(4,movie.getPoster());
            pstm.setInt(5, movie.getId());

            pstm.executeUpdate();

            LOGGER.debug("Movie id: " + movie.getId() + " has been updated");
            pstm.close();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("The movie was not updated");
        } finally {
            close();
        }
    }

    @Override
    public boolean delete(int id) throws DaoException {
        LOGGER.debug("Deleting the current movie: " + id);
        try (PreparedStatement pstm = connection.prepareStatement("DELETE from movies WHERE id = ?;")) {
            pstm.setInt(1, id);

            pstm.executeUpdate();

            LOGGER.debug("Movie id: " + id + " has been deleted");
            pstm.close();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("The movie was not deleted");
        } finally {
            close();
        }
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