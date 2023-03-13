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
import java.sql.*;

public class JDBCUserDaoTest {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/cinema_servlet_db_test?user=root&password=Tan456*Y&characterEncoding=UTF-8";
    Connection connection;
    JDBCUserDao JDBCUserDao;

    @BeforeClass
    public static void dbCreate() throws SQLException, IOException {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        Connection con = DriverManager.getConnection(CONNECTION_URL);
        ScriptRunner sr = new ScriptRunner(con);
        Reader reader = new BufferedReader(new FileReader("src/test/resources/cinema_servlet_db_test.sql"));
        sr.runScript(reader);
        reader.close();
    }

    @AfterClass
    public static void dropDown() throws SQLException {
        Connection conn = DBManager.getDataSource().getConnection();
        Statement st = conn.createStatement();
        st.executeUpdate("DROP database cinema_servlet_db_test;");
    }

    @Before
    public void userDaoCreate() throws SQLException {
        connection = DBManager.getDataSource().getConnection();
        JDBCUserDao = new JDBCUserDao(connection);
    }

    @org.junit.Test
    public void shouldCreateNewUser() throws DaoException {
        User newUser = new UserBuilder().setUsername("newUser")
                .setPassword("user12345")
                .setUserRole(Role.USER).build();
        Assert.assertNotNull(JDBCUserDao.create(newUser));
    }

    @org.junit.Test
    public void shouldFindUserById() throws DaoException {
        User user = JDBCUserDao.create(new UserBuilder().setUsername("user")
                .setPassword("user12345")
                .setUserRole(Role.USER).build());
        Assert.assertNotNull(JDBCUserDao.findById(user.getId()));
    }

    @org.junit.Test
    public void shouldFindUserByUsername() throws DaoException {
        User user = JDBCUserDao.create(new UserBuilder().setUsername("user1")
                .setPassword("user12345")
                .setUserRole(Role.USER).build());
        Assert.assertNotNull(JDBCUserDao.findUserByUsername(user.getUsername()));
    }

    @Test
    public void shouldReturnUserList() throws DaoException {
        JDBCUserDao.create(new UserBuilder().setUsername("user2")
                .setPassword("user12345")
                .setUserRole(Role.USER).build());
        JDBCUserDao.create(new UserBuilder().setUsername("user3")
                .setPassword("user12345")
                .setUserRole(Role.USER).build());
        Assert.assertEquals(5, JDBCUserDao.findAll().size());
    }
}

//    JDBCUserDao JDBCUserDao;
//
//    @Test
//    public void shouldAddUserToDatabase() throws SQLException, DaoException {
//        Connection connection = DBManager.getDataSource().getConnection();
//        JDBCUserDao = new JDBCUserDao(connection);
//        User newUser = new UserBuilder().setUsername("newUser")
//                .setPassword("user12345")
//                .setUserRole(Role.USER).build();
//        User createdUser = JDBCUserDao.create(newUser);
//        Assert.assertNotNull(createdUser);
//        Assert.assertEquals(newUser, createdUser);
//        connection.close();
//    }
//
//    @Test
//    public void shouldFindUserById() throws SQLException, DaoException {
//        Connection connection = DBManager.getDataSource().getConnection();
//        JDBCUserDao = new JDBCUserDao(connection);
//        User user = JDBCUserDao.create(new UserBuilder().setUsername("user")
//                .setPassword("user12345")
//                .setUserRole(Role.USER).build());
//        Assert.assertNotNull(JDBCUserDao.findById(user.getId()));
//        connection.close();
//    }
//
//    @Test
//    public void shouldFindUserByUsername() throws DaoException, SQLException {
//        Connection connection = DBManager.getDataSource().getConnection();
//        JDBCUserDao = new JDBCUserDao(connection);
//        User user = JDBCUserDao.create(new UserBuilder().setUsername("user11")
//                .setPassword("user12345")
//                .setUserRole(Role.USER).build());
//        Assert.assertNotNull(JDBCUserDao.findUserByUsername(user.getUsername()));
//        connection.close();
//    }
//
//    @Test
//    public void shouldReturnUserList() throws DaoException, SQLException {
//        Connection connection = DBManager.getDataSource().getConnection();
//        JDBCUserDao = new JDBCUserDao(connection);
//        JDBCUserDao.create(new UserBuilder().setUsername("user13")
//                .setPassword("user12345")
//                .setUserRole(Role.USER).build());
//        JDBCUserDao.create(new UserBuilder().setUsername("user12")
//                .setPassword("user12345")
//                .setUserRole(Role.USER).build());
//        Assert.assertEquals(5, JDBCUserDao.findAll().size());
//    }
//}