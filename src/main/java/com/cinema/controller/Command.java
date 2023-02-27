package com.cinema.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This interface represents classes that are used as commands.
 */

public interface Command {
    /**
     * @param request  - Http request from servlet.
     * @param response - Http response from servlet.
     * @return a string representing the view to forward/redirect to.
     */
    String execute(HttpServletRequest request, HttpServletResponse response);
}