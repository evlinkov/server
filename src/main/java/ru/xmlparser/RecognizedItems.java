package ru.xmlparser;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name = "recognizedItems")
public class RecognizedItems {

    private List<Item> items;

    @XmlElement(name = "item")
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public RecognizedItems() { }

    public RecognizedItems(List<Item> items) {
        this.items = items;
    }

}
