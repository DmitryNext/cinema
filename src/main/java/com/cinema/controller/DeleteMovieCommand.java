package com.cinema.controller;

import com.cinema.MappingProperties;
import com.cinema.exception.DaoException;
import com.cinema.movie.MovieService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is used to handle the POST request for deleting a movie.
 */

public class DeleteMovieCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(DeleteMovieCommand.class);
    private static MovieService movieService = MovieService.getInstance();
    private static String moviesPage;
    private static String errorPage;

    public DeleteMovieCommand() {
        LOGGER.debug("Initializing DeleteMovieCommand");
        MappingProperties properties = MappingProperties.getInstance();
        moviesPage = properties.getProperty("redirect.movies");
        errorPage = properties.getProperty("errorPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Execute a delete movie command");
        Integer movieId = Integer.parseInt(request.getParameter("movieId"));
        try {
            movieService.deleteMovie(movieId);
        } catch (DaoException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
            return errorPage;
        }
        return moviesPage;
    }
}