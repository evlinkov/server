package ru.server;

import com.sun.net.httpserver.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private final Integer PORT = 1337;
    private final Integer MAXIMUM_NUMBER_OF_TCP_CONNECTIONS = 500;

    public void createHttpServer() throws IOException {
        HttpServer httpServer = HttpServer.create();
        logger.info("httpServer is created");
        httpServer.bind(new InetSocketAddress(PORT), MAXIMUM_NUMBER_OF_TCP_CONNECTIONS);
        logger.info("port : " + PORT);
        logger.info("maximum number of tcp connections : " + MAXIMUM_NUMBER_OF_TCP_CONNECTIONS);

        XmlFile xmlFile = new XmlFile();

        HttpContext httpContext = httpServer.createContext("/", new Handler(xmlFile));
        httpContext.setAuthenticator(new Authentication(xmlFile));

        httpServer.setExecutor(null);
        httpServer.start();

        logger.info("httpServer is start working");

    }
}
