package com.cinema.movie;

import com.cinema.dao.DaoFactory;
import com.cinema.dao.MovieDao;
import com.cinema.exception.DaoException;
import com.cinema.service.Page;
import com.cinema.session.Session;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class MovieService {
    private static final Logger LOGGER = Logger.getLogger(MovieService.class);
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private static MovieService movieService;

    public static MovieService getInstance() {
        if (movieService == null) {
            synchronized (MovieService.class) {
                if (movieService == null) {
                    MovieService temp = new MovieService();
                    movieService = temp;
                }
            }
        }
        return movieService;
    }

    /**
     * This method gets all movies from the DB.
     */
    public List<Movie> getAllMovies() throws DaoException {
        try (MovieDao movieDao = daoFactory.createMovieDao()) {
            return movieDao.findAll();
        }
    }

    public Page<Movie> getAllMoviesPaginated(Integer pageNo, Integer pageSize,
                                             String sortDirection) throws DaoException {
        LOGGER.info("Getting page number " + pageNo + ", of size " + pageSize);
        try (MovieDao movieDao = daoFactory.createMovieDao()) {
            List<Movie> items = movieDao.findPages((pageNo - 1) * pageSize, pageSize, sortDirection);
            return new Page<Movie>(items, pageNo, pageSize);
        }
    }

    public Movie createMovie(Movie movie) throws DaoException {
        LOGGER.debug("Saving new movie");
        try (MovieDao movieDao = daoFactory.createMovieDao()) {
            return (Movie) movieDao.create(movie);
        }
    }

    public boolean deleteMovie(int id) throws DaoException {
        LOGGER.debug("Deleting a movie");
        try (MovieDao movieDao = daoFactory.createMovieDao()) {
            return movieDao.delete(id);
        }
    }
}