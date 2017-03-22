package ru.server;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.HttpPrincipal;
import com.sun.tools.javac.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sun.net.httpserver.HttpExchange;
import ru.xmlparser.XmlParser;
import ru.xmlparser.ContextCheck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.String;

public class Authentication extends Authenticator {

    private static final Logger logger = LoggerFactory.getLogger(Authentication.class);
    private static final String TEST = "test";
    public static final Integer OK = 200;
    private final Integer INCORRECT_HEADER = 401;
    private final Integer INCORRECT_XML_FILE = 501;
    private final Integer BAD_CONNECT = 601;
    private XmlFile xmlFile;

    Authentication (XmlFile xmlFile) {
        this.xmlFile = xmlFile;
    }

    private StringBuffer getXmlFile(InputStream inputStream) throws IOException {
        StringBuffer xmlFile = new StringBuffer();
        BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            xmlFile.append(inputLine);
            xmlFile.append('\n');
        }
        if (xmlFile.length() > 0) {
            // delete last '\n'
            xmlFile.deleteCharAt(xmlFile.length() - 1);
        }
        //System.out.println(xmlFile);
        return xmlFile;
    }

    private Pair<Boolean, Integer> isCorrectMethod (String method) {
        logger.info(method);
        if (method.equals("POST")) {
            return Pair.of(true, OK);
        } else {
            return Pair.of(false, INCORRECT_HEADER);
        }
    }

    private Pair<Boolean, Integer> isCorrectXmlFile (String xmlFile) {
        //logger.info(xmlFile);
        try {
            XmlParser xmlParser = new XmlParser();
            ContextCheck contextCheck = xmlParser.xmlReader(xmlFile);
        } catch (Exception error) {
            return Pair.of(false, INCORRECT_XML_FILE);
        }
        return Pair.of(true, OK);
    }

    @Override
    public Result authenticate(HttpExchange httpExchange) {
        logger.info("somebody try connect");
        Pair<Boolean, Integer> correctMethod = isCorrectMethod(httpExchange.getRequestMethod());
        if (correctMethod.fst.equals(false)) {
            return new Failure(correctMethod.snd);
        }
        try {
            xmlFile.setXmlFile(getXmlFile(httpExchange.getRequestBody()).toString());
            Pair<Boolean, Integer> correctXmlFile = isCorrectXmlFile(xmlFile.getXmlFile());
            if (correctXmlFile.fst.equals(true)) {
                return new Success(new HttpPrincipal(TEST, TEST));
            } else {
                return new Failure(INCORRECT_XML_FILE);
            }
        } catch (Exception error) {
            return new Failure(BAD_CONNECT);
        }
    }
}