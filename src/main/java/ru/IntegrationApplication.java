package ru;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.server.Server;

public class IntegrationApplication {

    private static final Logger logger = LoggerFactory.getLogger(IntegrationApplication.class);

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.createHttpServer();
    }
}
