package com.cinema.dao;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPoolHolder {
    private static final Logger LOGGER = Logger.getLogger(ConnectionPoolHolder.class);
    private static volatile DataSource dataSource;

    private ConnectionPoolHolder(){
        LOGGER.debug("Initializing ConnectionPoolHolder.class");
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    try {
                        Context context = new InitialContext();
                        dataSource = (DataSource) context.lookup("java:comp/env/jdbc/cinema");
                    } catch (NamingException e) {
                        LOGGER.error(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }
        return dataSource;
    }
}