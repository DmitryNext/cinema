package com.cinema;

import com.cinema.dao.DaoFactory;
import com.cinema.dao.JDBCUserDao;
import com.cinema.user.UserService;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UserServiceTest {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/cinema_servlet_test?user=root&password=Tan456*Y&characterEncoding=UTF-8";
    @InjectMocks
    UserService userService;
    @Mock
    JDBCUserDao jdbcUserDao;
    @Mock
    Connection connection;
    @Mock
    DaoFactory daoFactory;
    @BeforeClass
    public static void dbCreate() throws SQLException, IOException {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        Connection connection = DriverManager.getConnection(CONNECTION_URL);
        ScriptRunner sr = new ScriptRunner(connection);
        Reader reader = new BufferedReader(new FileReader("src/test/resources/whole_db_test_create.sql"));
        sr.runScript(reader);
        reader.close();
    }

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        connection = DBManager.getDataSource().getConnection();
        jdbcUserDao = new JDBCUserDao(connection);

    }

    @AfterClass
    public static void dropDown() throws SQLException {
        Connection connection = DBManager.getDataSource().getConnection();
        Statement st = connection.createStatement();
        st.executeUpdate("DROP database cinema_servlet_test;");
    }
}