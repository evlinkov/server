package ru.xmlparser;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class ObjectCreatorTest {

    @Test
    public void objectCreatorTest() throws Exception {
        List<String> answers = new ArrayList<String>();
        answers.add("Luchador");
        answers.add("To Tell the Truth");
        answers.add("Muskoka");
        answers.add("Cornbread");
        answers.add("Foie Gras");
        answers.add("Gumbo 50C");
        answers.add("Pimpfish");
        answers.add("Steel head");
        answers.add("Carrot");
        answers.add("Rice Pudding");
        Collections.sort(answers);

        File file = new File("./src/test/java/resources/RealCheck.xml");
        Receipts receipts = new ObjectCreator().getReceipts(file);
        List<String> assumptions = new ArrayList<String>();
        for (Receipt receipt : receipts.getReceipts()) {
            RecognizedItems recognizedItems = receipt.getRecognizedItems();
            assertEquals(answers.size(), recognizedItems.getItems().size());

            for (Item item: recognizedItems.getItems()) {
                assumptions.add(item.getName().getText());
            }
        }
        Collections.sort(assumptions);

        assertEquals(answers, assumptions);
    }

}
