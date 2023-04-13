package ru.tinkoff.edu.java.scrapper.test;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.*;

public class JdbcTest extends IntegrationEnvironment{

    @Test
    public void simpleTest() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_CONTAINER.getJdbcUrl(), DB_CONTAINER.getUsername(), DB_CONTAINER.getPassword());
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("""
                SELECT COUNT(*)
                FROM information_schema.tables
                WHERE table_name = 'link' OR table_name = 'tg_chat' OR table_name = 'subscription'""");
        int count = -1;
        if (result.next())
            count = result.getInt(1);
        Assertions.assertEquals(3, count);
    }
}
