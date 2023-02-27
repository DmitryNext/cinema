package com.cinema;

import com.cinema.dao.JDBCUserDao;
import com.cinema.exception.DaoException;
import com.cinema.user.Role;
import com.cinema.user.User;
import com.cinema.user.UserBuilder;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUserDaoTest {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/cinema_servlet_test?user=root&password=Tan456*Y&characterEncoding=UTF-8";
    Connection connection;
    JDBCUserDao userDao;

    @BeforeClass
    public static void dbCreate() throws SQLException, IOException {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        Connection connection = DriverManager.getConnection(CONNECTION_URL);
        ScriptRunner sr = new ScriptRunner(connection);
        Reader reader = new BufferedReader(new FileReader("src/test/resources/whole_db_test_create.sql"));
        sr.runScript(reader);
        reader.close();
    }

    @AfterClass
    public static void dropDown() throws SQLException {
        Connection connection = DBManager.getDataSource().getConnection();
        Statement st = connection.createStatement();
        st.executeUpdate("DROP database cinema_servlet_test;");
    }

    @Before
    public void userDaoCreate() throws SQLException {
        connection = DBManager.getDataSource().getConnection();
        userDao = new JDBCUserDao(connection);
    }

    @Test
    public void shouldCreateNewUser() throws DaoException {
        User newUser = new UserBuilder().setUsername("newUser")
                .setPassword("user12345")
                .setUserRole(Role.USER).build();
        Assert.assertNotNull(userDao.create(newUser));
    }

    @Test
    public void shouldFindUserById() throws DaoException {
        User user = userDao.create(new UserBuilder().setUsername("user")
                .setPassword("user12345")
                .setUserRole(Role.USER).build());
        Assert.assertNotNull(userDao.findById(user.getId()));
    }

    @Test
    public void shouldFindUserByUsername() throws DaoException {
        User user = userDao.create(new UserBuilder().setUsername("user11")
                .setPassword("user12345")
                .setUserRole(Role.USER).build());
        Assert.assertNotNull(userDao.findUserByUsername(user.getUsername()));
    }

    @Test
    public void shouldReturnUserList() throws DaoException {
        userDao.create(new UserBuilder().setUsername("user13")
                .setPassword("user12345")
                .setUserRole(Role.USER).build());
        userDao.create(new UserBuilder().setUsername("user12")
                .setPassword("user12345")
                .setUserRole(Role.USER).build());
        Assert.assertEquals(5, userDao.findAll().size());
    }
}