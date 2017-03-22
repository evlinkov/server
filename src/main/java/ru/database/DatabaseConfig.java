package ru.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class DatabaseConfig {

    private final String FILE_NAME = "config.txt";

    private static final String HOST = "host";
    private static final String TABLE = "table";
    private static final String PORT = "port";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    private String host;
    private String table;
    private String port;
    private String user;
    private String password;

    public void initialization() throws IOException {
        String data = new String(Files.readAllBytes(Paths.get("./src/main/java/resources/config.txt")));
        for (String line : data.split(String.valueOf('\n'))) {
            String[] parts = line.split(String.valueOf('='));
            String key = parts[0];
            String value = parts[1];
            if (key.equals(HOST)) {
                host = value;
            }
            if (key.equals(TABLE)) {
                table = value;
            }
            if (key.equals(PORT)) {
                port = value;
            }
            if (key.equals(USER)) {
                user = value;
            }
            if (key.equals(PASSWORD)) {
                password = value;
            }
        }
    }

    public Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty(USER, user);
        properties.setProperty(PASSWORD, password);
        properties.setProperty("useSSL", "false");
        return properties;
    }

    public String getConnectionPath() {
        return "jdbc:mysql://" + host + ":" + port + "/" + table;
    }

}

