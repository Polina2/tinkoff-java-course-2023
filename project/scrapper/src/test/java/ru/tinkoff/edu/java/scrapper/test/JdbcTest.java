package ru.tinkoff.edu.java.scrapper.test;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.tinkoff.edu.java.scrapper.dto.Link;

import java.sql.*;

public class JdbcTest extends IntegrationEnvironment{

    @Test
    public void simpleTest() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_CONTAINER.getJdbcUrl(), DB_CONTAINER.getUsername(), DB_CONTAINER.getPassword());
        Statement statement = connection.createStatement();
        Link expected = new Link(1L, "https://github.com/testcontainers/testcontainers-java", null);
        statement.execute("INSERT INTO link(url) VALUES ('"+expected.url()+"')");
        ResultSet set = statement.executeQuery("SELECT * FROM link");
        set.next();
        Link actual = new Link(set.getLong("link_id"), set.getString("url"), set.getTimestamp("last_update"));
        Assertions.assertEquals(expected, actual);
    }
}
