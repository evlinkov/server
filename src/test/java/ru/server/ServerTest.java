package ru.server;

import java.net.URL;
import org.junit.Test;
import org.junit.Ignore;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import static org.junit.Assert.assertEquals;

public class ServerTest {

    private static final String IP = "localhost";
    private static final String PORT = "1337";

    @Test
    @Ignore
    public void testGET() throws Exception {
        URL url = new URL("http://" + IP + ":" + PORT + "/data");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        InputStream inputStream;
        int responseCode = connection.getResponseCode();

        assertEquals(responseCode, 200);

        inputStream = connection.getInputStream();

        StringBuilder data = new StringBuilder();
        String inputLine;

        BufferedReader input = new BufferedReader(new InputStreamReader(
                inputStream));
        while ((inputLine = input.readLine()) != null) {
            data.append(inputLine);
        }
        assertEquals(data.toString(), "all is ok");
    }

    @Test
    @Ignore
    public void testPOST() throws Exception {
        String receipt = new String(Files.readAllBytes(Paths.get("./src/main/resources/RequestExample.xml")));
        URL url = new URL("http://" + IP + ":" + PORT + "/getCategories");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/xml");
        connection.setDoOutput(true);
        byte[] bytes = receipt.getBytes("UTF-8");
        connection.getOutputStream().write(bytes);

        InputStream inputStream;
        int responseCode = connection.getResponseCode();
        inputStream = connection.getInputStream();
        assertEquals(responseCode, 200);

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
