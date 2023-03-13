package com.cinema.controller;

import com.cinema.MappingProperties;
import com.cinema.movie.Genre;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is used to handle GET requests to the add movie page.
 */

public class GetAddMoviePageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(GetAddMoviePageCommand.class);
    private static String addMoviePage;
    private static String moviesPage;

    public GetAddMoviePageCommand() {
        LOGGER.debug("Initializing GetMoviesPageCommand");
        MappingProperties properties = MappingProperties.getInstance();
        addMoviePage = properties.getProperty("addMoviePage");
        moviesPage = properties.getProperty("moviesPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Execute the add movie page command");
        String resultPage = addMoviePage;
        if (request.getSession().getAttribute("authenticated") != null &&
                request.getSession().getAttribute("authenticated").equals(true)) {
            request.getSession().setAttribute("genres", Genre.values());
            return resultPage;
        } else {
            LOGGER.debug("Movie wasn't added, returning movies page");
            resultPage = moviesPage;
        }
        return resultPage;
    }
}