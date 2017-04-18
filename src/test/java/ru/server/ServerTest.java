package ru.server;

import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerTest {

    private static final String IP = "localhost";
    private static final String PORT = "5555";

    @Test
    @Ignore
    public void testServer() throws Exception {

        URL url = new URL("http://" + IP + ":" + PORT + "/api/test");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        InputStream inputStream;
        int responseCode = connection.getResponseCode();

        System.out.println(responseCode);

        inputStream = connection.getInputStream();

        StringBuilder data = new StringBuilder();
        String inputLine;

        BufferedReader input = new BufferedReader(new InputStreamReader(
                inputStream));
        while ((inputLine = input.readLine()) != null) {
            data.append(inputLine);
        }
        System.out.println(data);

    }

}
