package com.cinema.controller;

import org.apache.log4j.Logger;
import com.cinema.MappingProperties;
import com.cinema.movie.Movie;
import com.cinema.service.Page;
import com.cinema.movie.MovieService;
import com.cinema.exception.DaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is used to handle GET requests to the movies page.
 */

public class GetMoviesPageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(GetMoviesPageCommand.class);
    private static MovieService movieService = MovieService.getInstance();
    private static String moviesPage;
    private static String errorPage;

    public GetMoviesPageCommand() {
        LOGGER.debug("Initializing GetMoviesPageCommand");
        MappingProperties properties = MappingProperties.getInstance();
        moviesPage = properties.getProperty("moviesPage");
        errorPage = properties.getProperty("errorPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Execute the movies page command");
        Integer pageNo = Integer.parseInt(request.getParameter("p"));
        Integer pageSize = Integer.parseInt(request.getParameter("s"));
        String sortDirection = request.getParameter("sortDirection");
        try {
            Page<Movie> page = movieService.getAllMoviesPaginated(pageNo, pageSize, sortDirection);
            request.getSession().setAttribute("movies", page);
            request.getSession().setAttribute("currentPage", pageNo);
            request.getSession().setAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
            return moviesPage;
        } catch (DaoException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
            return errorPage;
        }
    }
}