package ru.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySQL {

    private static final Logger logger = LoggerFactory.getLogger(MySQL.class);

    private final String NAME_OFFER = "nameOffer";
    private final String CATEGORY = "category";

    private Connection connection;
    private Statement statement;
    private ResultSet result;

    public String getCategory(String nameOffer) throws IOException {

        String query = "SELECT " + CATEGORY + " from category where " + NAME_OFFER + " = '" + nameOffer + "'";
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
            } catch (SQLException se) {
                logger.info("connection no close");
            }
            try {
                statement.close();
            } catch (SQLException se) {
                logger.info("statement no close");
            }
            try {
                result.close();
            } catch (SQLException se) {
                logger.info("result no close");
            }
        }
        if (category == null) {
            category = "other";
        }
        return category;
    }

}
