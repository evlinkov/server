package ru.database;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Ignore;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

import static org.junit.Assert.assertEquals;

public class MySQLTest {

    private Connection connection;
    private Statement statement;
    private ResultSet result;

    @Test
    @Ignore
    public void testCategory() throws IOException {
        String query = "SELECT category from category where nameOffer = '" + "cola" + "'";
        String category = null;

        DatabaseConfig databaseConfig = new DatabaseConfig();
        databaseConfig.initialization();

        try {

            connection = DriverManager.getConnection(databaseConfig.getConnectionPath(), databaseConfig.getProperties());
            statement = connection.createStatement();
            result = statement.executeQuery(query);

            while (result.next()) {
                category = result.getString(1);
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException se) { }
            try {
                statement.close();
            } catch (SQLException se) { }
            try {
                result.close();
            } catch (SQLException se) { }
        }
        assertEquals("drink", category);
    }

}
