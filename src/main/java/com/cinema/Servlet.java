package com.cinema;

import java.io.IOException;
import java.util.HashSet;

import com.cinema.controller.Command;
import com.cinema.controller.CommandManager;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet(name = "servlet", value = "/")
public class Servlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(Servlet.class);
    private CommandManager commandManager;

    public void init(ServletConfig config) throws ServletException {
        LOGGER.info("Initializing Servlet");
        config.getServletContext().setAttribute("loggedUsers", new HashSet<String>());
        commandManager = new CommandManager();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Processing of the request for receipt");
        processRequest(request, response);
    }

    /**
     * This method gets a command instance mapped to http get/post method based on a request.
     * This method also sets redirection or forward action according to the result of execute() method.
     */

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        path = path.replaceAll(".*/cinema", "");
        Command command = commandManager.getCommand(path);
        String page = command.execute(request, response);
        if (page.contains("redirect:")) {
            response.sendRedirect(page.replace("redirect:", "/cinema"));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Processing of the request for publication");
        processRequest(request, response);
    }
}