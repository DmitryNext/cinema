package com.cinema.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class is used to validate information entered during registration and updating.
 */

public class Validator {

    public static boolean validateRegistration(String username, String password) {
        if (Objects.isNull(username) || Objects.isNull(password)) {
            return false;
        }
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        if (!username.matches("([A-Za-z0-9-]+)") ||
                !password.matches("((?=.*\\d)(?=.*[A-Za-z]).{8,15})")) {
            return false;
        }
        return true;
    }

    public static boolean validateProfileUpdate(String password) {
        if (Objects.isNull(password) || password.isEmpty()) {
            return false;
        }
        if (!password.matches("((?=.*\\d)(?=.*[A-Za-z]).{8,15})")) {
            return false;
        }
        return true;
    }

    public static Map<String, String> validatePageRequest(String page, String size,
                                                          String sortDirection, String sortBy) {
        Map<String, String> pageParams = new HashMap<>();
        pageParams.put("page", page);
        pageParams.put("size", size);
        pageParams.put("sortBy", sortBy);
        pageParams.put("sortDirection", sortDirection);
        return pageParams;
    }
}