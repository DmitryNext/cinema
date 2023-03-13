package com.cinema;

import com.cinema.dao.DaoFactory;
import com.cinema.dao.JDBCUserDao;
import com.cinema.exception.DaoException;
import com.cinema.user.Role;
import com.cinema.user.User;
import com.cinema.user.UserBuilder;
import com.cinema.user.UserService;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.*;
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

import static org.mockito.Mockito.*;

public class UserServiceTest {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/cinema_servlet_db?user=root&password=Tan456*Y&characterEncoding=UTF-8";
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
        Connection con = DriverManager.getConnection(CONNECTION_URL);
        ScriptRunner sr = new ScriptRunner(con);
        Reader reader = new BufferedReader(new FileReader("src/test/resources/cinema_servlet_db_test.sql"));
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
        Connection conn = DBManager.getDataSource().getConnection();
        Statement st = conn.createStatement();
        st.executeUpdate("DROP database cinema_servlet_db_test;");
    }

//    @Test
//    public void shouldCreateNewUser() throws DaoException {
//        when(daoFactory.createUserDao()).thenReturn(jdbcUserDao);
//        userService = UserService.getInstance();
//        User newUser = new UserBuilder().setUsername("newUser")
//                .setPassword("user12345")
//                .setUserRole(Role.USER)
//                .build();
//        Assert.assertNotNull(userService.registerUser(newUser));
//    }
}