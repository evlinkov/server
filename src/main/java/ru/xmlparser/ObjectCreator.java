package ru.xmlparser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;

public class ObjectCreator {

    private static final Logger logger = LoggerFactory.getLogger(ObjectCreator.class);

    public Receipts getReceipts(InputStream inputStream) {
        try {
            // дополнительно используется SAX, чтобы игнорировать xmlns
            JAXBContext jaxbContext = JAXBContext.newInstance(Receipts.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            XMLReader xmlReader = saxParserFactory.newSAXParser().getXMLReader();
            SAXSource source = new SAXSource(xmlReader, new InputSource(inputStream));

            return (Receipts)unmarshaller.unmarshal(source);
        } catch (Exception error) {
            logger.info("Xml input is incorrect");
            return null;
        }
    }

    public Receipts getReceipts(File file) {
        try {
            return getReceipts(new FileInputStream(file));
        } catch (Exception error) {
            logger.info("Xml input is incorrect");
            return null;
        }
    }

}
