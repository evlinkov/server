package ru.xmlparser;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "item")
public class Item {

    private Name name;

    @XmlElement(name = "name")
    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Item() { }

    public Item(Name name) {
        this.name = name;
    }

}
