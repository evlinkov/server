package ru.xmlparser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class XmlParser {

    private static final Logger logger = LoggerFactory.getLogger(XmlParser.class);

    private static final String TAG_TOTAL = "total";
    private static final String TAG_ITEM = "item";
    private static final String TAG_NORMALIZED_VALUE = "normalizedValue";

    public ContextCheck xmlReader(String xmlString) throws Exception {

        ContextCheck check = new ContextCheck();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {

            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlString)));

            Element root = document.getDocumentElement();
            NodeList nodes = root.getChildNodes().item(1).getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (TAG_TOTAL.equals(node.getNodeName())) {
                    parseTotal(check, node);
                }
                if (TAG_ITEM.equals(node.getNodeName())) {
                    parseItem(check, node);
                }
            }

            return check;

        } catch (Exception error) {
            error.printStackTrace();
            throw error;
        }

    }

    private void parseTotal(ContextCheck check, Node total) {
        logger.info("parse totalSum");
        NodeList nodes = total.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (TAG_NORMALIZED_VALUE.equals(node.getNodeName())) {
                String value = node.getTextContent();
                check.setTotalSum(Double.valueOf(value));
            }
        }
    }

    private void parseItem(ContextCheck check, Node item) {
        NodeList nodes = item.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (TAG_NORMALIZED_VALUE.equals(node.getNodeName())) {
                String value = node.getTextContent();
                ContextItem contextItem = new ContextItem(value);
                check.addItem(contextItem);
            }
        }
    }

}
