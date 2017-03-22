package ru.xmlparser;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class XmlParserTest {

    @Test
    public void totalSumTest() {
        try {
            String str = new String(Files.readAllBytes(Paths.get("./src/test/java/resources/correctxml.txt")));
            XmlParser xmlParser = new XmlParser();
            ContextCheck check = xmlParser.xmlReader(str);
            Assert.assertTrue(check.getTotalSum() == 54.26);
        } catch (Exception ex) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void badXmlText() {
        try {
            String str = new String(Files.readAllBytes(Paths.get("./src/test/java/resources/badxml.txt")));
            XmlParser xmlParser = new XmlParser();
            ContextCheck check = xmlParser.xmlReader(str);
            Assert.assertTrue(false);
        } catch (Exception ex) {
            Assert.assertTrue(true);
        }
    }

}
