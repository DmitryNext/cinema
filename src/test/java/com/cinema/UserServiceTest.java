package com.cinema;

import com.cinema.dao.DaoFactory;
import com.cinema.dao.JDBCUserDao;
import com.cinema.dao.UserDao;
import com.cinema.exception.DaoException;
import com.cinema.user.User;
import com.cinema.user.UserService;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/cinema_servlet_db_test?user=root&password=Tan456*Y&characterEncoding=UTF-8";

    @Mock
    UserService userService;
    @Mock
    UserDao userDao;

    @Mock
    DaoFactory daoFactory;
//    @Mock
//    Connection connection;
//    @Mock
//    DaoFactory daoFactory;

//    @BeforeClass
//    public static void dbCreate() throws SQLException, IOException {
//        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
//        Connection connection = DriverManager.getConnection(CONNECTION_URL);
//        ScriptRunner sr = new ScriptRunner(connection);
//        Reader reader = new BufferedReader(new FileReader("cinema_servlet_db_test.sql"));
//        sr.runScript(reader);
//        reader.close();
//    }
//
//    @Before
//    public void setUp() throws SQLException {
//        MockitoAnnotations.initMocks(this);
//        connection = DBManager.getDataSource().getConnection();
//        jdbcUserDao = new JDBCUserDao(connection);
//
//    }

//    @AfterClass
//    public static void dropDown() throws SQLException {
//        Connection connection = DBManager.getDataSource().getConnection();
//        Statement st = connection.createStatement();
//        st.executeUpdate("DROP database cinema_servlet_db_test;");
//    }


}