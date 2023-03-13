package com.cinema.controller;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * This class handles the mapping of URLs to commands.
 */

public class CommandManager {
    private static final Logger LOGGER = Logger.getLogger(CommandManager.class);
    private Map<String, Command> commandMap;

    public CommandManager() {
        LOGGER.debug("Initializing CommandManager");

        commandMap = new HashMap<>();

        commandMap.put("/", new GetHomePageCommand());
        commandMap.put("/movies", new GetMoviesPageCommand());
        commandMap.put("/schedule", new GetSchedulePageCommand());
        commandMap.put("/login", new GetLoginPageCommand());
        commandMap.put("/registration", new GetRegistrationPageCommand());
        commandMap.put("/logout", new LogoutCommand());
        commandMap.put("/user/profile", new GetUserProfileCommand());
        commandMap.put("/user/saveticket", new UserTicketCommand());
        commandMap.put("/user/usertickets", new GetUserTicketsCommand());
        commandMap.put("/user/buyticket", new GetBuyTicketPageCommand());
        commandMap.put("/admin/user", new GetUserEditFormCommand());
        commandMap.put("/admin/userlist", new GetUserListPageCommand());
        commandMap.put("/admin/user/edit", new UpdateUserCommand());
        commandMap.put("/admin/savemovie", new AddMovieCommand());
        commandMap.put("/admin/deletemovie", new DeleteMovieCommand());
        commandMap.put("/admin/addmovie", new GetAddMoviePageCommand());

        LOGGER.debug("The command container has been successfully initialized");
    }

    /**
     * This method is used to get a command instance mapped to http get or post method, based on a request.
     */
    public Command getCommand(String path) {
        return commandMap.getOrDefault(path, new GetHomePageCommand());
    }
}