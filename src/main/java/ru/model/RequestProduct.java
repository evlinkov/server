package ru.model;

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlElement;

@XmlType(name = "product")
public class RequestProduct {

    private String name;
    private double amount;
    private double price;
    private String category;

    @XmlElement(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @XmlElement(name = "amount")
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }


    @XmlElement(name = "price")
    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }


    @XmlElement(name = "category")
    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

}
