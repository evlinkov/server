package ru.server;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import ru.database.MySQL;
import ru.xmlparser.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.xmlparser.ContextCheck;
import ru.xmlparser.XmlParser;

import java.io.IOException;
import java.io.OutputStream;

public class Handler implements HttpHandler {

    private static final Logger logger = LoggerFactory.getLogger(Handler.class);
    private static final String SEPARATION = " : ";
    private XmlFile xmlFile;

    Handler(XmlFile xmlFile) {
        this.xmlFile = xmlFile;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        try {

            XmlParser xmlParser = new XmlParser();
            ContextCheck check = xmlParser.xmlReader(xmlFile.getXmlFile());
            StringBuffer data = new StringBuffer("");

            for (ContextItem contextItem : check.getItems()) {
                data.append(contextItem.getItemName());
                data.append(SEPARATION);
                data.append(new MySQL().getCategory(contextItem.getItemName()));
                data.append("\n");
            }

            byte[] bytes = data.toString().getBytes();
            httpExchange.sendResponseHeaders(Authentication.OK, bytes.length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(bytes);
            outputStream.close();

        } catch (Exception e) {
            logger.info("Exception");
        }

    }

}

