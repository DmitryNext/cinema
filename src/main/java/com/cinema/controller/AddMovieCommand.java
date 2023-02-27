package com.cinema.controller;

import com.cinema.MappingProperties;
import com.cinema.exception.DaoException;
import com.cinema.movie.Genre;
import com.cinema.movie.Movie;
import com.cinema.movie.MovieBuilder;
import com.cinema.movie.MovieService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Time;

/**
 * This class is used to handle the POST request for adding a new movie.
 */
public class AddMovieCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(AddMovieCommand.class);
    private static MovieService movieService = MovieService.getInstance();
    private static String moviesPage;
    private static String errorPage;

    public AddMovieCommand() {
        LOGGER.debug("Initializing AddMovieCommand");
        MappingProperties properties = MappingProperties.getInstance();
        moviesPage = properties.getProperty("redirect.movies");
        errorPage = properties.getProperty("errorPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Execute an add new movie command");
        Movie movie = new MovieBuilder()
                .setName(request.getParameter("name"))
                .setGenre(Genre.valueOf(request.getParameter("genre")))
                .setDuration(Time.valueOf(request.getParameter("duration")))
                .setPoster(request.getParameter("poster"))
                .build();
        try {
            movieService.createMovie(movie);
            return moviesPage;
        } catch (DaoException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
            return errorPage;
        }
    }
}