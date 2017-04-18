package ru.xmlparser;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "receipt")
public class Receipt {

    private RecognizedItems recognizedItems;

    @XmlElement(name = "recognizedItems")
    public RecognizedItems getRecognizedItems() {
        return recognizedItems;
    }

    public void setRecognizedItems(RecognizedItems recognizedItems) {
        this.recognizedItems = recognizedItems;
    }

    public Receipt() { }

    public Receipt (RecognizedItems recognizedItems) {
        this.recognizedItems = recognizedItems;
    }

}
